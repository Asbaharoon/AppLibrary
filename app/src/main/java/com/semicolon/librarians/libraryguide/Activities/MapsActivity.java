package com.semicolon.librarians.libraryguide.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.semicolon.librarians.libraryguide.Adapters.PlaceAutocompleteAdapter;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;

import java.io.IOException;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private AutoCompleteTextView library_search_ed;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String INTERNET = "android.permission.INTERNET";
    private static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    private static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    private static final int PERMISSION_REQUEST = 400;
    private PlaceAutocompleteAdapter autocompleteAdapter;
    private GeoDataClient geoDataClient;
    private TextView tv_ok;
    boolean permission_granted = false, is_connected = false;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private double myLat = 0.0, myLng = 0.0;
    private LatLngBounds latLngBounds = new LatLngBounds(
            new LatLng(-33.880490, 151.184363),
            new LatLng(-33.858754, 151.229596));

    private PlaceAutocompleteAdapter adapter;
    private Marker marker;
    private String country_code = "SA";
    private AutocompleteFilter filter;
    private LocationManager manager;
    private final int gps_req = 2330;
    private AlertDialog gpsDialog;
    private LatLng startPos;
    private LatLng endPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, Tags.font, true);

        initView();
        if (isServiceOk()) {
            CheckPermission();
        }


    }

    private boolean CheckGps() {
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (manager != null) {
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                return true;
            }
        }
        return false;
    }

    private void CreateGpsAlert() {
        gpsDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.open_gps)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, gps_req);
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                }).setCancelable(false).create();
        gpsDialog.setCanceledOnTouchOutside(false);
        gpsDialog.show();
    }

    private void initView() {
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(this);
        geoDataClient = Places.getGeoDataClient(this, null);
        library_search_ed = (AutoCompleteTextView) findViewById(R.id.library_search_tv);
        library_search_ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                }
                return false;
            }
        });


    }


    private void initMap() {
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.setBuildingsEnabled(true);
            mMap.setTrafficEnabled(false);
            mMap.setIndoorEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.maps));

            // getDeviceLocation();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        mMap.setMyLocationEnabled(true);
        Log.e("b","mm");
        initGoogleApiClient();

        geoDataClient = Places.getGeoDataClient(this,null);
        /*filter = new AutocompleteFilter.Builder()
                .setCountry(country_code)
                .build();*/
        filter = new AutocompleteFilter.Builder()
                .setCountry(country_code)
                .build();

        adapter = new PlaceAutocompleteAdapter(this,geoDataClient,latLngBounds,filter);
        library_search_ed.setAdapter(adapter);
        library_search_ed.setOnItemClickListener(itemClickListener);

    }

    private void initGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
        googleApiClient.connect();
    }

    private void initLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setSmallestDisplacement(10f);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdate() {
        initLocationRequest();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] Permitions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this,Permitions,7788);
        }else
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

        }
    }

    private void getCountryCode(double myLat, double myLng) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocation(myLat,myLng,1);
            if (addressList.size()>0)
            {
                Address address = addressList.get(0);
                if (address!=null)
                {
                    country_code = address.getCountryCode();
                    filter = new AutocompleteFilter.Builder()
                            .setCountry(country_code)
                            .build();
                    adapter = new PlaceAutocompleteAdapter(this,geoDataClient,latLngBounds,filter);
                    library_search_ed.setAdapter(adapter);
                    library_search_ed.setOnItemClickListener(itemClickListener);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void CheckPermission()
    {
        String [] permissions=new String[]{ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION};

        if (ActivityCompat.checkSelfPermission(this,ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                if (CheckGps())
                {
                    initMap();

                }else
                    {
                        CreateGpsAlert();
                    }
            }else
                {
                    ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQUEST);

                }
        }
        else
        {
            ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQUEST);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_REQUEST)
        {
            if (grantResults.length>0)
            {
                for (int i =0;i<grantResults.length;i++)
                {
                    if (grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(MapsActivity.this, R.string.ser_not_found, Toast.LENGTH_SHORT).show();
                        return;

                    }

                }
                if (CheckGps())
                {
                    initMap();
                }else
                {
                    CreateGpsAlert();
                }
            }

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.tv_ok)
        {
            Intent intent = new Intent();
            intent.putExtra("lat",myLat);
            intent.putExtra("lng",myLng);
            setResult(MapsActivity.RESULT_OK,intent);


            finish();

        }
    }







    private boolean isServiceOk() {

        int is_available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);
        if (is_available==ConnectionResult.SUCCESS)
        {
            Toast.makeText(MapsActivity.this, R.string.ser_valid, Toast.LENGTH_SHORT).show();
            return  true;
        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(is_available))
        {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapsActivity.this,is_available,9001);
            dialog.show();
        }
        return false;
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (googleApiClient!=null)
            googleApiClient.connect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode==gps_req)
        {
            if (resultCode==RESULT_OK)
            {
                if (CheckGps())
                {
                    initMap();
                }else
                {
                    CreateGpsAlert();
                }
            }else {
                if (CheckGps()) {
                   initMap();
                } else {
                    CreateGpsAlert();
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location!=null)
        {
            myLat = location.getLatitude();
            myLng = location.getLongitude();
            AddMarker(myLat,myLng);
            tv_ok.setVisibility(View.VISIBLE);
            getCountryCode(myLat,myLng);
            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {

                    myLat = cameraPosition.target.latitude;
                    myLat = cameraPosition.target.longitude;
                    marker.setPosition(cameraPosition.target);

                    Log.e("lat",myLat+"");
                    Log.e("lng",myLat+"");

                }
            });
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
        }
    }

    private void AddMarker(double myLat, double myLng) {
        marker =mMap.addMarker(new MarkerOptions().position(new LatLng(myLat,myLng)).icon(BitmapDescriptorFactory.fromBitmap(createMarkerBitmap())));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLat,myLng),16.7f));

    }

    private Bitmap createMarkerBitmap()
    {
        Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.map_marker);
        int newWidth = 55;
        int newHeight = 55;
        float scaleWidth = ((float)newWidth/bitmap1.getWidth());
        float scaleHeight = ((float)newHeight/bitmap1.getHeight());

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap returnedBitmap = Bitmap.createBitmap(bitmap1,0,0,bitmap1.getWidth(),bitmap1.getHeight(),matrix,true);
        return returnedBitmap;
    }
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AutocompletePrediction item = adapter.getItem(i);
            String place_id = item.getPlaceId();
            PendingResult<PlaceBuffer> bufferPendingResult = Places.GeoDataApi.getPlaceById(googleApiClient,place_id);
            bufferPendingResult.setResultCallback(resultCallback);

        }
    };

    private ResultCallback<PlaceBuffer> resultCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {

            try {
                if (!places.getStatus().isSuccess())
                {
                    places.release();
                    return;
                }
                tv_ok.setText(null);
                Place place = places.get(0);
                myLat = place.getLatLng().latitude;
                myLng = place.getLatLng().longitude;


                startPos = marker.getPosition();
                endPos = place.getLatLng();
                animateMarker(startPos,endPos,marker,"0");


                places.release();
            }catch (NullPointerException e)
            {

            }

        }
    };


    private void animateMarker(LatLng startPos, LatLng endPos, final Marker marker, final String flag)
    {
        final LatLng startPosition = startPos;
        final LatLng finalPosition = endPos;
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 3000;
        final boolean hideMarker = false;

        handler.post(new Runnable() {
            long elapsed;
            float t;
            float v;

            @Override
            public void run() {
                // Calculate progress using interpolator
                elapsed = SystemClock.uptimeMillis() - start;
                t = elapsed / durationInMs;
                v = interpolator.getInterpolation(t);

                LatLng currentPosition = new LatLng(
                        startPosition.latitude * (1 - t) + finalPosition.latitude * t,
                        startPosition.longitude * (1 - t) + finalPosition.longitude * t);


                marker.setPosition(currentPosition);
                if (flag=="0")
                {
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                }
                // Repeat till progress is complete.
                if (t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }


                }
            }
        });
    }

}
