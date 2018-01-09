package com.example.omd.library.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omd.library.Adapters.PlaceAutocompleteAdapter;
import com.example.omd.library.R;
import com.example.omd.library.Services.NetworkConnection;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback ,GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{

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
    boolean permission_granted=false,is_connected=false;


    private static LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initView();
        CheckPermission();
        Check_Connectivity();

    }

    private void initView() {
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(this);
        geoDataClient = Places.getGeoDataClient(this,null);
        library_search_ed = (AutoCompleteTextView) findViewById(R.id.library_search_tv);
        autocompleteAdapter = new PlaceAutocompleteAdapter(this,geoDataClient,new LatLngBounds(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596)),null);
        library_search_ed.setAdapter(autocompleteAdapter);
        library_search_ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        ||actionId == EditorInfo.IME_ACTION_GO
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    String lib_loc = library_search_ed.getText().toString();
                    if (TextUtils.isEmpty(lib_loc))
                    {
                        Toast.makeText(MapsActivity.this, "Please enter library location", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        }
                        try {
                            Geocoder geocoder = new Geocoder(MapsActivity.this);

                            List<Address> addresses = geocoder.getFromLocationName(lib_loc,1);
                            if (addresses.size()>0)
                            {
                                latLng = new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
                                mMap.clear();

                                mMap.setMyLocationEnabled(false);
                                mMap.addMarker(new MarkerOptions()
                                        .icon(BitmapDescriptorFactory.defaultMarker())
                                        .title(TextUtils.isEmpty(addresses.get(0).getLocality().toString())?"unknown":addresses.get(0).getLocality().toString())
                                        .position(new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude())));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11f));
                                tv_ok.setVisibility(View.VISIBLE);


                                InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                methodManager.hideSoftInputFromWindow(library_search_ed.getWindowToken(),0);

                            }
                            else
                                {
                                    Toast.makeText(MapsActivity.this, "invalid address", Toast.LENGTH_SHORT).show();
                                }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }



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

            getDeviceLocation();
        }

    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = mFusedLocationProviderClient.getLastLocation();
        task.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    LatLng latLng = null;
                    if (location != null) {
                        latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        Geocoder geocoder = new Geocoder(MapsActivity.this);
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            mMap.addMarker(new MarkerOptions().title(addresses.get(0).getLocality().toString())
                                    .position(latLng).icon(BitmapDescriptorFactory.defaultMarker()));
                            if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                return;
                            }
                            mMap.setMyLocationEnabled(true);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11f));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
    }

    private void CheckPermission()
    {
        String [] permissions=new String[]{INTERNET,ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION};

        if (ActivityCompat.checkSelfPermission(this,INTERNET)== PackageManager.PERMISSION_GRANTED
                &&ActivityCompat.checkSelfPermission(this,ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED
                &&ActivityCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            permission_granted=true;
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
                        Toast.makeText(MapsActivity.this, "service not available", Toast.LENGTH_SHORT).show();
                        permission_granted =false;
                        break;

                    }
                    else
                    {
                        permission_granted =true;
                        //initMap();
                    }
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
            if (latLng!=null)
            {
                Intent intent = new Intent();
                intent.putExtra("lat",latLng.latitude);
                intent.putExtra("lng",latLng.longitude);
                setResult(MapsActivity.RESULT_OK,intent);

                finish();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Check_Connectivity();
    }





    private boolean isServiceOk() {

        int is_available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);
        if (is_available==ConnectionResult.SUCCESS)
        {
            Toast.makeText(MapsActivity.this, "service is available", Toast.LENGTH_SHORT).show();
            return  true;
        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(is_available))
        {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapsActivity.this,is_available,9001);
            dialog.show();
        }else if (is_available == ConnectionResult.NETWORK_ERROR)
        {
            Toast.makeText(MapsActivity.this, "Check network connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }
    private void Check_Connectivity()
    {
        is_connected = new NetworkConnection(this).CheckConnection();

        if (permission_granted)
        {
            if (is_connected)
            {
                if (isServiceOk())
                {
                    initMap();
                }
                else
                {
                    Toast.makeText(this, "service not available", Toast.LENGTH_SHORT).show();

                }
            }else
            {
                Toast.makeText(this, "check network connection", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();

        }
    }
}
