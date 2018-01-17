package com.example.omd.library.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.omd.library.Models.CompanyModel;
import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.Models.UniversityModel;
import com.example.omd.library.R;
import com.example.omd.library.Services.NetworkConnection;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class NearbyActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FabSpeedDial filtered_fab;
    private GoogleMap mMap;
    private static NormalUserData user_Data=null;
    private static PublisherModel publisher_Model=null;
    private static LibraryModel library_Model=null;
    private static UniversityModel university_Model=null;
    private static CompanyModel company_Model=null;
    private static final String INTERNET = "android.permission.INTERNET";
    private static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    private static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    private static final int PERMISSION_REQUEST = 400;
    private static boolean PERMISSION_GRANTED = false;
    private boolean isConnected = false;
    private int availability;
    private boolean isMapReady=false;
    FusedLocationProviderClient fusedLocationProviderClient;
    View  customMarkerView;
    CircleImageView circleImageView;
    private static String imageUrl=null;
    Target target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        initView();
        CheckPermission();
        CheckConnectivity();
        getDataFrom_Intent();

    }

    private void initView() {
        filtered_fab = (FabSpeedDial) findViewById(R.id.filtered_fab);

        filtered_fab.setMenuListener(new SimpleMenuListenerAdapter()
        {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.user_menu_item:
                        Toast.makeText(NearbyActivity.this, "user", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.publisher_menu_item:
                        Toast.makeText(NearbyActivity.this, "publisher", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.library_menu_item:
                        Toast.makeText(NearbyActivity.this, "library", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.university_menu_item:
                        Toast.makeText(NearbyActivity.this, "university", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.company_menu_item:
                        Toast.makeText(NearbyActivity.this, "Company", Toast.LENGTH_SHORT).show();
                        break;

                }
                return super.onMenuItemSelected(menuItem);
            }
        });

        customMarkerView = LayoutInflater.from(NearbyActivity.this).inflate(R.layout.custom_marker,null);
        circleImageView = (CircleImageView) customMarkerView.findViewById(R.id.custom_userImage);

    }

    private void CheckConnectivity() {
        NetworkConnection connection = new NetworkConnection(this);
        isConnected = connection.CheckConnection();
        if (isConnected) {
            if (PERMISSION_GRANTED == true) {
                if (isServiceOk()) {
                    initMap();
                } else {
                    Toast.makeText(this, "service not available", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(this, "check network connection", Toast.LENGTH_SHORT).show();

        }
    }

    private void CheckPermission() {

        String[] permissions = new String[]{INTERNET, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, INTERNET) == PackageManager.PERMISSION_GRANTED) {
            PERMISSION_GRANTED = true;
        } else {
            PERMISSION_GRANTED = false;
            ActivityCompat.requestPermissions(NearbyActivity.this, permissions, PERMISSION_REQUEST);
        }

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.nearby_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mMap = googleMap;
            if (user_Data!=null)
            {
                target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        deviceLocation(drawCustomMarker(customMarkerView,bitmap));

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                Picasso.with(this).load(Uri.parse(user_Data.getUserPhoto())).noFade().into(target);
            }
            else if (publisher_Model!=null)
            {
            }
            else if (library_Model!=null)
            {
            }
            else if (university_Model!=null)
            {
            }
            else if (company_Model!=null)
            {
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            for (int index = 0; index < grantResults.length; index++) {
                if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                    PERMISSION_GRANTED = false;
                } else {
                    PERMISSION_GRANTED = true;
                }
            }
        }
    }

    private boolean isServiceOk() {
        availability = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (availability == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(availability)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, availability, 9001);
            dialog.show();
        }
        return false;
    }

    private void deviceLocation(final Bitmap bitmap) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(NearbyActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();
        lastLocation.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    if (location != null) {

                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                        Geocoder geocoder = new Geocoder(NearbyActivity.this);
                        try {
                            if (ActivityCompat.checkSelfPermission(NearbyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NearbyActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                return;
                            }
                            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                            if (bitmap!=null)
                            {
                                mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getLocality())
                                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f));

                            }else
                                {
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getLocality())
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_profile)));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f));
                                }

                            mMap.setMyLocationEnabled(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }
            }
        });
    }

    private Bitmap drawCustomMarker(View view ,Bitmap bitmap)
    {

        circleImageView.setImageBitmap(bitmap);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0,0,customMarkerView.getMeasuredWidth(),customMarkerView.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(),customMarkerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);

        canvas.drawColor(ContextCompat.getColor(NearbyActivity.this,R.color.base), PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable!=null)
        drawable.draw(canvas);
        customMarkerView.draw(canvas);

        return  returnedBitmap;

    }
    private void getDataFrom_Intent()
    {
        Intent intent = getIntent();
        if (intent.hasExtra("userData"))
        {
            final NormalUserData UserData = (NormalUserData) intent.getSerializableExtra("userData");

            if (UserData!=null)
            {
              NearbyActivity.user_Data = UserData;
            }
        }
        else if (intent.hasExtra("publisherData"))
        {

            final PublisherModel publisherModel = (PublisherModel) intent.getSerializableExtra("publisherData");
            if (publisherModel!=null)
            {
               NearbyActivity.publisher_Model=publisherModel;
            }


        }
        else if (intent.hasExtra("libraryData"))
        {
            final LibraryModel libraryModel = (LibraryModel) intent.getSerializableExtra("libraryData");

            if (libraryModel!=null)
            {
               NearbyActivity.library_Model=libraryModel;

            }


        }
        else if (intent.hasExtra("universityData"))
        {
            final UniversityModel universityModel = (UniversityModel) intent.getSerializableExtra("universityData");
            if (universityModel!=null)
            {
               NearbyActivity.university_Model = universityModel;
            }


        }
        else if (intent.hasExtra("companyData"))
        {
            final CompanyModel companyModel = (CompanyModel) intent.getSerializableExtra("companyData");

            if (companyModel!=null)
            {
                NearbyActivity.company_Model = companyModel;
            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Picasso.with(this).cancelRequest(target);

    }
}
