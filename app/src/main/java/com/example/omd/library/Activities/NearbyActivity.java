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
import android.os.Handler;
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
import com.example.omd.library.NearbyMVP.Presenter;
import com.example.omd.library.NearbyMVP.PresenterImp;
import com.example.omd.library.NearbyMVP.ViewData;
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
import com.google.android.gms.maps.model.Marker;
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

public class NearbyActivity extends AppCompatActivity implements OnMapReadyCallback,ViewData {
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
    private FusedLocationProviderClient fusedLocationProviderClient;
    private View  customMarkerView;
    private CircleImageView circleImageView;
    private Target target;
    private LatLng latLng;
    private Presenter presenter;
    private NormalUserData UserData=null;
    private PublisherModel publisherModel=null;
    private LibraryModel libraryModel=null;
    private UniversityModel universityModel=null;
    private CompanyModel companyModel=null;
    private int position;
    private List<LibraryModel> library_ModelList;

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
        presenter = new PresenterImp(this,this);
        filtered_fab = (FabSpeedDial) findViewById(R.id.filtered_fab);

        filtered_fab.setMenuListener(new SimpleMenuListenerAdapter()
        {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.user_menu_item:
                        /////////////////////////////////////////
                       // getCurrentUserLocation("user");
                        Toast.makeText(NearbyActivity.this, "user", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.publisher_menu_item:
                        Toast.makeText(NearbyActivity.this, "publisher", Toast.LENGTH_SHORT).show();
                        getCurrentUserLocation("publisher");
                        break;
                    case R.id.library_menu_item:
                        getCurrentUserLocation("library");

                        Toast.makeText(NearbyActivity.this, "library", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.university_menu_item:
                        getCurrentUserLocation("university");

                        Toast.makeText(NearbyActivity.this, "university", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.company_menu_item:
                        //getCurrentUserLocation("company");

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
                Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);
            }
            else if (library_Model!=null)
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
                Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);

            }
            else if (university_Model!=null)
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
                Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);

            }
            else if (company_Model!=null)
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
                Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);

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

                        latLng = new LatLng(location.getLatitude(), location.getLongitude());

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
              UserData = (NormalUserData) intent.getSerializableExtra("userData");

            if (UserData!=null)
            {
              user_Data = UserData;
                Toast.makeText(this, ""+user_Data.getUserType(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (intent.hasExtra("publisherData"))
        {

             publisherModel = (PublisherModel) intent.getSerializableExtra("publisherData");
            if (publisherModel!=null)
            {
               publisher_Model=publisherModel;
                Toast.makeText(this, ""+publisher_Model.getUser_type(), Toast.LENGTH_SHORT).show();

            }


        }
        else if (intent.hasExtra("libraryData"))
        {
            libraryModel = (LibraryModel) intent.getSerializableExtra("libraryData");

            if (libraryModel!=null)
            {

                library_Model=libraryModel;
                Toast.makeText(this, library_Model.getLib_name(), Toast.LENGTH_SHORT).show();


            }


        }
        else if (intent.hasExtra("universityData"))
        {
             universityModel = (UniversityModel) intent.getSerializableExtra("universityData");
            if (universityModel!=null)
            {
               university_Model = universityModel;
            }


        }
        else if (intent.hasExtra("companyData"))
        {
             companyModel = (CompanyModel) intent.getSerializableExtra("companyData");

            if (companyModel!=null)
            {
               company_Model = companyModel;
            }


        }
    }


    @Override
    public void onUsersDataSuccess(List<NormalUserData> normalUserDataList) {
        mMap.clear();
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
            for (NormalUserData normalUserData :normalUserDataList)
            {
                Toast.makeText(this, normalUserData.getUserName()+"\n"+normalUserData.getUserType(), Toast.LENGTH_SHORT).show();
                /*if (!normalUserData.getUserName().equals(user_Data.getUserName()))
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            // AddMarker(new LatLng(10.333333,30.222222),bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                    Picasso.with(this).load(Uri.parse(normalUserData.getUserPhoto())).noFade().into(target);
                }*/

            }
        }





    }

    @Override
    public void onPublisherDataSuccess(List<PublisherModel> publisherModelList) {
        mMap.clear();

        if (publisher_Model!=null)
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
            Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);
            position=0;
            for (final PublisherModel publisherModel :publisherModelList)
            {
                Toast.makeText(this, publisherModel.getPub_name()+"\n"+publisherModel.getUser_type(), Toast.LENGTH_SHORT).show();
                if (!publisherModel.getPub_username().equals(publisher_Model.getPub_username()))
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                           AddMarker(position,new LatLng(Double.parseDouble(publisherModel.getPub_lat()),Double.parseDouble(publisherModel.getPub_lng())),drawCustomMarker(customMarkerView,bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                }
                position++;

            }
        }






    }

    @Override
    public void onLibraryDataSuccess(List<LibraryModel> libraryModelList) {
        library_ModelList = libraryModelList;
        mMap.clear();

        if (library_Model!=null)
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
            Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);

            position =0;
            for (final LibraryModel libraryModel :libraryModelList)
            {
                Toast.makeText(this, libraryModel.getLib_name()+"\n"+libraryModel.getLib_username()+"\n"+library_Model.getLib_username()+"\n"+position, Toast.LENGTH_SHORT).show();

                target = new Target() {
                    @Override
                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AddMarker(position,new LatLng(Double.parseDouble(libraryModel.getLat()),Double.parseDouble(libraryModel.getLng())),drawCustomMarker(customMarkerView,bitmap));
                                position++;

                            }
                        },1000);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }

                };
               /* if (!libraryModel.getLib_username().equals(library_Model.getLib_username()))
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            AddMarker(new LatLng(Double.parseDouble(libraryModel.getLat()),Double.parseDouble(libraryModel.getLng())),drawCustomMarker(customMarkerView,bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                }*/

               Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);
            }
        }




    }

    @Override
    public void onUniversityDataSuccess(List<UniversityModel> universityModelList) {
        mMap.clear();

        if (university_Model!=null)
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
            Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);
            position=0;
            for (final UniversityModel universityModel :universityModelList)
            {
                Toast.makeText(this, universityModel.getUni_name()+"\n"+universityModel.getUser_type(), Toast.LENGTH_SHORT).show();

                if (!universityModel.getUni_username().equals(university_Model.getUni_username()))
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            AddMarker(position,new LatLng(Double.parseDouble(universityModel.getUni_lat()),Double.parseDouble(universityModel.getUni_lng())),drawCustomMarker(customMarkerView,bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                }
                position++;
            }
        }




    }

    @Override
    public void onCompanyDataSuccess(List<CompanyModel> companyModelList) {

       // mMap.clear();

        if (company_Model!=null)
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
            Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);
            for (final CompanyModel companyModel :companyModelList)
            {
               /* if (!companyModel.getComp_username().equals(company_Model.getComp_username()))
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            AddMarker(new LatLng(Double.parseDouble(""),Double.parseDouble("")),drawCustomMarker(customMarkerView,bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                }*/
                Toast.makeText(this, companyModel.getComp_name()+"\n"+companyModel.getUser_type(), Toast.LENGTH_SHORT).show();


            }
        }




    }

    @Override
    public void onFailed(String error) {

    }
    private void AddMarker(final int position, LatLng latLng, Bitmap bitmap)
    {
        Geocoder geocoder = new Geocoder(NearbyActivity.this);
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (bitmap!=null)
            {
               Marker marker= mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getLocality())
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                marker.setTag(position);
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (user_Data!=null)
                        {
                            Toast.makeText(NearbyActivity.this, ""+marker.getTag()+user_Data.getUserName(), Toast.LENGTH_SHORT).show();

                        }else if(publisher_Model!=null)
                        {
                            Toast.makeText(NearbyActivity.this, ""+marker.getTag()+publisher_Model.getPub_username(), Toast.LENGTH_SHORT).show();

                        }
                        else if(library_Model!=null)
                        {
                            int markerpos= (int) marker.getTag();
                            LibraryModel libraryData = library_ModelList.get(markerpos);
                            Toast.makeText(NearbyActivity.this, ""+libraryData.getLib_name(), Toast.LENGTH_SHORT).show();

                        }
                        else if(university_Model!=null)
                        {
                            Toast.makeText(NearbyActivity.this, ""+marker.getTag()+university_Model.getUni_username(), Toast.LENGTH_SHORT).show();

                        }
                        else if(company_Model!=null)
                        {
                            Toast.makeText(NearbyActivity.this, ""+marker.getTag()+company_Model.getComp_username(), Toast.LENGTH_SHORT).show();

                        }

                        return false;
                    }
                });

            }else
            {
               Marker marker= mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getLocality())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_profile)));
               marker.setTag(position);
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Toast.makeText(NearbyActivity.this, ""+marker.getTag(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getCurrentUserLocation(final String userType)
    {
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
                         switch (userType)
                         {
                             case "user":
                                 presenter.getNearbyUsers(userType,latLng);
                                 break;
                             case "publisher":
                                 presenter.getNearbyPublishers(userType,latLng);
                                 break;
                             case "library":
                                 presenter.getNearbyLibraries(userType,latLng);
                                 break;
                             case "university":
                                 presenter.getNearbyUniversities(userType,latLng);
                                 break;
                             case "company":
                                 presenter.getNearbyCompanies(userType,latLng);
                                 break;
                         }

                    }
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Picasso.with(this).cancelRequest(target);

    }

}
