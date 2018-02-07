package com.semicolon.librarians.library.Activities;

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
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.semicolon.librarians.library.MVP.NearbyMVP.Presenter;
import com.semicolon.librarians.library.MVP.NearbyMVP.PresenterImp;
import com.semicolon.librarians.library.MVP.NearbyMVP.ViewData;
import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.MarkerModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;
import com.semicolon.librarians.library.R;
import com.semicolon.librarians.library.Services.NetworkConnection;
import com.semicolon.librarians.library.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import me.anwarshahriar.calligrapher.Calligrapher;

public class NearbyActivity extends AppCompatActivity implements OnMapReadyCallback,ViewData {
    private FabSpeedDial filtered_fab;
    private GoogleMap mMap;
    private  NormalUserData user_Data=null;
    private  PublisherModel publisher_Model=null;
    private  LibraryModel library_Model=null;
    private  UniversityModel university_Model=null;
    private  CompanyModel company_Model=null;
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
    private List<NormalUserData> normalUserData_List;
    private List<PublisherModel> publisherModel_List;
    private List<LibraryModel> library_ModelList;
    private List<UniversityModel> universityModel_List;
    private List<CompanyModel> companyModel_List;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        Calligrapher calligrapher = new Calligrapher(getApplicationContext());
        calligrapher.setFont(this, Tags.font,true);

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
                        if (user_Data!=null)
                        {
                            getNearbyUsers("user","user");
                        }else if (publisher_Model!=null)
                        {
                            getNearbyUsers("publisher","user");
                        }
                        else if (library_Model!=null)
                        {
                            getNearbyUsers("library","user");
                        }
                        else if (university_Model!=null)
                        {
                            getNearbyUsers("university","user");
                        }
                        else if (company_Model!=null)
                        {
                            getNearbyUsers("company","user");
                        }
                        //getCurrentUserLocation("user");
                        break;
                    case R.id.publisher_menu_item:
                        if (user_Data!=null)
                        {
                            getNearbyUsers("user","publisher");
                        }else if (publisher_Model!=null)
                        {
                            getNearbyUsers("publisher","publisher");
                        }
                        else if (library_Model!=null)
                        {
                            getNearbyUsers("library","publisher");
                        }
                        else if (university_Model!=null)
                        {
                            getNearbyUsers("university","publisher");
                        }
                        else if (company_Model!=null)
                        {
                            getNearbyUsers("company","publisher");
                        }
                        //getCurrentUserLocation("publisher");
                        break;
                    case R.id.library_menu_item:
                        if (user_Data!=null)
                        {
                            getNearbyUsers("user","library");
                        }else if (publisher_Model!=null)
                        {
                            getNearbyUsers("publisher","library");
                        }
                        else if (library_Model!=null)
                        {
                            getNearbyUsers("library","library");
                        }
                        else if (university_Model!=null)
                        {
                            getNearbyUsers("university","library");
                        }
                        else if (company_Model!=null)
                        {
                            getNearbyUsers("company","library");
                        }
                        //getCurrentUserLocation("library");
                        break;
                    case R.id.university_menu_item:
                        if (user_Data!=null)
                        {
                            getNearbyUsers("user","university");
                        }else if (publisher_Model!=null)
                        {
                            getNearbyUsers("publisher","university");
                        }
                        else if (library_Model!=null)
                        {
                            getNearbyUsers("library","university");
                        }
                        else if (university_Model!=null)
                        {
                            getNearbyUsers("university","university");
                        }
                        else if (company_Model!=null)
                        {
                            getNearbyUsers("company","university");
                        }
                        //getCurrentUserLocation("university");

                        break;
                    case R.id.company_menu_item:
                        if (user_Data!=null)
                        {
                            getNearbyUsers("user","company");
                        }else if (publisher_Model!=null)
                        {
                            getNearbyUsers("publisher","company");
                        }
                        else if (library_Model!=null)
                        {
                            getNearbyUsers("library","company");
                        }
                        else if (university_Model!=null)
                        {
                            getNearbyUsers("university","company");
                        }
                        else if (company_Model!=null)
                        {
                            getNearbyUsers("company","company");
                        }
                        //getCurrentUserLocation("company");

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
                    filtered_fab.show();
                } else {
                    Toast.makeText(this, "service not available", Toast.LENGTH_SHORT).show();
                    filtered_fab.hide();
                }

            } else {
                Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
                filtered_fab.hide();

            }
        } else {
            Toast.makeText(this, "check network connection", Toast.LENGTH_SHORT).show();
            filtered_fab.hide();

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
                Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);
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
                Toast.makeText(this, ""+user_Data.getUserType()+user_Data.getUserName(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (intent.hasExtra("publisherData"))
        {

             publisherModel = (PublisherModel) intent.getSerializableExtra("publisherData");
            if (publisherModel!=null)
            {
               publisher_Model=publisherModel;
                Toast.makeText(this, ""+publisher_Model.getUser_type()+"\n"+publisher_Model.getPub_name(), Toast.LENGTH_SHORT).show();

            }


        }
        else if (intent.hasExtra("libraryData"))
        {
            libraryModel = (LibraryModel) intent.getSerializableExtra("libraryData");

            if (libraryModel!=null)
            {

                library_Model=libraryModel;
                Toast.makeText(this, library_Model.getLib_name()+library_Model.getLib_name(), Toast.LENGTH_SHORT).show();


            }


        }
        else if (intent.hasExtra("universityData"))
        {
             universityModel = (UniversityModel) intent.getSerializableExtra("universityData");
            if (universityModel!=null)
            {
               university_Model = universityModel;
                Toast.makeText(this, universityModel.getUni_name()+"\n"+universityModel.getUser_type(), Toast.LENGTH_SHORT).show();

            }


        }
        else if (intent.hasExtra("companyData"))
        {
             companyModel = (CompanyModel) intent.getSerializableExtra("companyData");

            if (companyModel!=null)
            {
               company_Model = companyModel;
                Toast.makeText(this, companyModel.getComp_name()+"\n"+companyModel.getUser_type(), Toast.LENGTH_SHORT).show();
            }


        }
    }


    @Override
    public void onUsersDataSuccess(List<NormalUserData> normalUserDataList) {
        if (normalUserDataList.size()>0)
        {
            normalUserData_List=normalUserDataList;
            mMap.clear();
            if (user_Data!=null||publisher_Model!=null||library_Model!=null||university_Model!=null||company_Model!=null)
            {
                position=0;
                for (final NormalUserData normalUser_Data :normalUserDataList)
                {

                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            AddMarker(new MarkerModel(normalUser_Data.getUserType(),position),new LatLng(Double.parseDouble(normalUser_Data.getUser_google_lat()),Double.parseDouble(normalUser_Data.getUser_google_lng())),drawCustomMarker(customMarkerView,bitmap));
                            position++;
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

            }else
                {
                    mMap.clear();
                }
        }







    }

    @Override
    public void onPublisherDataSuccess(List<PublisherModel> publisherModelList) {
        if (publisherModelList.size()>0)
        {
            publisherModel_List=publisherModelList;
            mMap.clear();
            if (user_Data!=null||publisher_Model!=null||library_Model!=null||university_Model!=null||company_Model!=null)
            {
                position=0;
                for (final PublisherModel publisher_Data :publisherModelList)
                {

                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            AddMarker(new MarkerModel(publisher_Data.getUser_type(),position),new LatLng(Double.parseDouble(publisher_Data.getPub_lat()),Double.parseDouble(publisher_Data.getPub_lng())),drawCustomMarker(customMarkerView,bitmap));
                            position++;

                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                }
                Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);

            }
        }else
            {
                mMap.clear();
            }








    }

    @Override
    public void onLibraryDataSuccess(List<LibraryModel> libraryModelList) {
        if (libraryModelList.size()>0)
        {
            library_ModelList = libraryModelList;
            mMap.clear();

            if (user_Data!=null||publisher_Model!=null||library_Model!=null||university_Model!=null||company_Model!=null)
            {

                position =0;
                for (final LibraryModel libraryModel :libraryModelList)
                {

                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    AddMarker(new MarkerModel(libraryModel.getUser_type(),position),new LatLng(Double.parseDouble(libraryModel.getLat()),Double.parseDouble(libraryModel.getLng())),drawCustomMarker(customMarkerView,bitmap));
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




                    Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);
                }
            }

        }else
            {
                mMap.clear();
            }





    }

    @Override
    public void onUniversityDataSuccess(List<UniversityModel> universityModelList) {

        if (universityModelList.size()>0)
        {
            universityModel_List = universityModelList;
            mMap.clear();

            if (user_Data!=null||publisher_Model!=null||library_Model!=null||university_Model!=null||company_Model!=null)
            {
                position=0;
                for (final UniversityModel universityModel :universityModelList)
                {


                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            /////Log.e("uni web",universityModel.getUni_site());
                            AddMarker(new MarkerModel(universityModel.getUser_type(),position),new LatLng(Double.parseDouble(universityModel.getUni_lat()),Double.parseDouble(universityModel.getUni_lng())),drawCustomMarker(customMarkerView,bitmap));
                            position++;

                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                }
                Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);

            }
        }
        else
            {
                mMap.clear();
            }






    }

    @Override
    public void onCompanyDataSuccess(List<CompanyModel> companyModelList) {
        if (companyModelList.size()>0)
        {
            companyModel_List =companyModelList;
            mMap.clear();

            if (user_Data!=null||publisher_Model!=null||library_Model!=null||university_Model!=null||company_Model!=null)
            {
                position=0;
                for (final CompanyModel companyModel :companyModelList)
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            AddMarker(new MarkerModel(companyModel.getUser_type(),position),new LatLng(Double.parseDouble(companyModel.getComp_lat()),Double.parseDouble(companyModel.getComp_lng())),drawCustomMarker(customMarkerView,bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                }
                //Toast.makeText(this, companyModel.getComp_name()+"\n"+companyModel.getUser_type(), Toast.LENGTH_SHORT).show();

                Picasso.with(this).load(R.drawable.user_profile).noFade().into(target);

            }
        }else
            {
                mMap.clear();
            }







    }

    @Override
    public void onFailed(String error) {

    }
    private void AddMarker(MarkerModel markerModel, LatLng latLng, Bitmap bitmap)
    {
        Geocoder geocoder = new Geocoder(NearbyActivity.this);
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (bitmap!=null)
            {
               Marker marker= mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getLocality())
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                marker.setTag(markerModel);
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        MarkerModel markerTag = (MarkerModel) marker.getTag();
                        switch (markerTag.getUser_type())
                        {
                            case "user":
                                int u_pos = markerTag.getPosition();
                                final NormalUserData u_data=normalUserData_List.get(u_pos);
                                View view = LayoutInflater.from(NearbyActivity.this).inflate(R.layout.custom_alert_msg_profile,null);
                                TextView open_profile = (TextView) view.findViewById(R.id.open_profile_tv);
                                TextView send_msg = (TextView) view.findViewById(R.id.send_msg_tv);
                                Button cancelBtn = (Button) view.findViewById(R.id.cancelBtn);

                                final LovelyCustomDialog dialog = new LovelyCustomDialog(NearbyActivity.this);
                                dialog.setCancelable(true);
                                dialog.setTopTitle("Select Options");
                                dialog.setTopColor(ActivityCompat.getColor(NearbyActivity.this,R.color.centercolor));
                                dialog.setTopTitleColor(ActivityCompat.getColor(NearbyActivity.this,R.color.base));
                                open_profile.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            Intent intent = new Intent(NearbyActivity.this,Activity_Profile.class);
                                            intent.putExtra("who_visit_myProfile","visitor");
                                            intent.putExtra("userData",u_data);
                                            startActivity(intent);
                                            dialog.dismiss();


                                    }
                                });

                                send_msg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (user_Data!=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","user");
                                            intent.putExtra("chat_userType","user");
                                            intent.putExtra("curr_user",user_Data);
                                            intent.putExtra("chat_user",u_data);
                                            startActivity(intent);
                                            dialog.dismiss();

                                        }
                                        else if (publisher_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","publisher");
                                            intent.putExtra("chat_userType","user");
                                            intent.putExtra("curr_user",publisher_Model);
                                            intent.putExtra("chat_user",u_data);
                                            startActivity(intent);
                                            dialog.dismiss();

                                        }
                                        else if (library_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","library");
                                            intent.putExtra("chat_userType","user");
                                            intent.putExtra("curr_user",library_Model);
                                            intent.putExtra("chat_user",u_data);
                                            startActivity(intent);
                                            dialog.dismiss();

                                        }
                                        else if (university_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","university");
                                            intent.putExtra("chat_userType","user");
                                            intent.putExtra("curr_user",university_Model);
                                            intent.putExtra("chat_user",u_data);
                                            startActivity(intent);
                                            dialog.dismiss();

                                        }
                                        else if (company_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","company");
                                            intent.putExtra("chat_userType","user");
                                            intent.putExtra("curr_user",company_Model);
                                            intent.putExtra("chat_user",u_data);
                                            startActivity(intent);
                                            dialog.dismiss();

                                        }
                                    }
                                });
                                cancelBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.setView(view);
                                dialog.create();
                                dialog.show();
                                Toast.makeText(NearbyActivity.this, "userData: "+u_data.getUserName()+"\n"+u_data.getUserType()+"\n"+u_data.getUser_google_lat(), Toast.LENGTH_SHORT).show();
                                break;
                            case  "publisher":
                                int pub_pos = markerTag.getPosition();
                                final PublisherModel pub_data=publisherModel_List.get(pub_pos);

                                View view2 = LayoutInflater.from(NearbyActivity.this).inflate(R.layout.custom_alert_msg_profile,null);
                                TextView open_profile2 = (TextView) view2.findViewById(R.id.open_profile_tv);
                                TextView send_msg2 = (TextView) view2.findViewById(R.id.send_msg_tv);
                                Button cancelBtn2 = (Button) view2.findViewById(R.id.cancelBtn);

                                 final LovelyCustomDialog dialog2 = new LovelyCustomDialog(NearbyActivity.this);
                                dialog2.setCancelable(true);
                                dialog2.setTopTitle("Select Options");
                                dialog2.setTopColor(ActivityCompat.getColor(NearbyActivity.this,R.color.centercolor));
                                dialog2.setTopTitleColor(ActivityCompat.getColor(NearbyActivity.this,R.color.base));
                                open_profile2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(NearbyActivity.this,Activity_Profile.class);
                                        intent.putExtra("who_visit_myProfile","visitor");
                                        intent.putExtra("publisherData",pub_data);
                                        startActivity(intent);
                                        dialog2.dismiss();                                        dialog2.dismiss();
                                    }
                                });

                                send_msg2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (user_Data!=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","user");
                                            intent.putExtra("chat_userType","publisher");
                                            intent.putExtra("curr_user",user_Data);
                                            intent.putExtra("chat_user",pub_data);
                                            startActivity(intent);
                                            dialog2.dismiss();

                                        }
                                        else if (publisher_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","publisher");
                                            intent.putExtra("chat_userType","publisher");
                                            intent.putExtra("curr_user",publisher_Model);
                                            intent.putExtra("chat_user",pub_data);
                                            startActivity(intent);
                                            dialog2.dismiss();

                                        }
                                        else if (library_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","library");
                                            intent.putExtra("chat_userType","publisher");
                                            intent.putExtra("curr_user",library_Model);
                                            intent.putExtra("chat_user",pub_data);
                                            startActivity(intent);
                                            dialog2.dismiss();

                                        }
                                        else if (university_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","university");
                                            intent.putExtra("chat_userType","publisher");
                                            intent.putExtra("curr_user",university_Model);
                                            intent.putExtra("chat_user",pub_data);
                                            startActivity(intent);
                                            dialog2.dismiss();

                                        }
                                        else if (company_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","company");
                                            intent.putExtra("chat_userType","publisher");
                                            intent.putExtra("curr_user",company_Model);
                                            intent.putExtra("chat_user",pub_data);
                                            startActivity(intent);
                                            dialog2.dismiss();

                                        }
                                    }
                                });
                                cancelBtn2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog2.dismiss();
                                    }
                                });
                                dialog2.setView(view2);
                                dialog2.create();
                                dialog2.show();
                                Toast.makeText(NearbyActivity.this, "pubData"+pub_data.getPub_username()+"\n"+pub_data.getPub_lat()+"\n"+pub_data.getUser_type(), Toast.LENGTH_SHORT).show();
                                break;
                            case "library":
                                int lib_pos = markerTag.getPosition();
                                final LibraryModel lib_data = library_ModelList.get(lib_pos);
                                View view3 = LayoutInflater.from(NearbyActivity.this).inflate(R.layout.custom_alert_msg_profile,null);
                                TextView open_profile3 = (TextView) view3.findViewById(R.id.open_profile_tv);
                                TextView send_msg3 = (TextView) view3.findViewById(R.id.send_msg_tv);
                                Button cancelBtn3 = (Button) view3.findViewById(R.id.cancelBtn);

                                final LovelyCustomDialog dialog3 = new LovelyCustomDialog(NearbyActivity.this);
                                dialog3.setCancelable(true);
                                dialog3.setTopTitle("Select Options");
                                dialog3.setTopColor(ActivityCompat.getColor(NearbyActivity.this,R.color.centercolor));
                                dialog3.setTopTitleColor(ActivityCompat.getColor(NearbyActivity.this,R.color.base));
                                open_profile3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(NearbyActivity.this,Activity_Profile.class);
                                        intent.putExtra("who_visit_myProfile","visitor");
                                        intent.putExtra("libraryData",lib_data);
                                        startActivity(intent);
                                        dialog3.dismiss();
                                    }
                                });

                                send_msg3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (user_Data!=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","user");
                                            intent.putExtra("chat_userType","library");
                                            intent.putExtra("curr_user",user_Data);
                                            intent.putExtra("chat_user",lib_data);
                                            startActivity(intent);
                                            dialog3.dismiss();

                                        }
                                        else if (publisher_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","publisher");
                                            intent.putExtra("chat_userType","library");
                                            intent.putExtra("curr_user",publisher_Model);
                                            intent.putExtra("chat_user",lib_data);
                                            startActivity(intent);
                                            dialog3.dismiss();

                                        }
                                        else if (library_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","library");
                                            intent.putExtra("chat_userType","library");
                                            intent.putExtra("curr_user",library_Model);
                                            intent.putExtra("chat_user",lib_data);
                                            startActivity(intent);
                                            dialog3.dismiss();

                                        }
                                        else if (university_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","university");
                                            intent.putExtra("chat_userType","library");
                                            intent.putExtra("curr_user",university_Model);
                                            intent.putExtra("chat_user",lib_data);
                                            startActivity(intent);
                                            dialog3.dismiss();

                                        }
                                        else if (company_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","company");
                                            intent.putExtra("chat_userType","library");
                                            intent.putExtra("curr_user",company_Model);
                                            intent.putExtra("chat_user",lib_data);
                                            startActivity(intent);
                                            dialog3.dismiss();

                                        }
                                    }
                                });
                                cancelBtn3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog3.dismiss();
                                    }
                                });
                                dialog3.setView(view3);
                                dialog3.create();
                                dialog3.show();
                                Toast.makeText(NearbyActivity.this, "libData"+lib_data.getLib_username()+"\n"+lib_data.getUser_type()+"\n"+lib_data.getLat(), Toast.LENGTH_SHORT).show();
                                break;
                            case "university":
                                int uni_pos = markerTag.getPosition();
                                final UniversityModel uni_data = universityModel_List.get(uni_pos);
                                View view4 = LayoutInflater.from(NearbyActivity.this).inflate(R.layout.custom_alert_msg_profile,null);
                                TextView open_profile4 = (TextView) view4.findViewById(R.id.open_profile_tv);
                                TextView send_msg4 = (TextView) view4.findViewById(R.id.send_msg_tv);
                                Button cancelBtn4 = (Button) view4.findViewById(R.id.cancelBtn);

                                final LovelyCustomDialog dialog4 = new LovelyCustomDialog(NearbyActivity.this);
                                dialog4.setCancelable(true);
                                dialog4.setTopTitle("Select Options");
                                dialog4.setTopColor(ActivityCompat.getColor(NearbyActivity.this,R.color.centercolor));
                                dialog4.setTopTitleColor(ActivityCompat.getColor(NearbyActivity.this,R.color.base));
                                open_profile4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(NearbyActivity.this,Activity_Profile.class);
                                        intent.putExtra("who_visit_myProfile","visitor");
                                        intent.putExtra("universityData",uni_data);
                                        startActivity(intent);
                                        dialog4.dismiss();
                                    }
                                });

                                send_msg4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (user_Data!=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","user");
                                            intent.putExtra("chat_userType","university");
                                            intent.putExtra("curr_user",user_Data);
                                            intent.putExtra("chat_user",uni_data);
                                            startActivity(intent);
                                            dialog4.dismiss();

                                        }
                                        else if (publisher_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","publisher");
                                            intent.putExtra("chat_userType","university");
                                            intent.putExtra("curr_user",publisher_Model);
                                            intent.putExtra("chat_user",uni_data);
                                            startActivity(intent);
                                            dialog4.dismiss();

                                        }
                                        else if (library_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","library");
                                            intent.putExtra("chat_userType","university");
                                            intent.putExtra("curr_user",library_Model);
                                            intent.putExtra("chat_user",uni_data);
                                            startActivity(intent);
                                            dialog4.dismiss();

                                        }
                                        else if (university_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","university");
                                            intent.putExtra("chat_userType","university");
                                            intent.putExtra("curr_user",university_Model);
                                            intent.putExtra("chat_user",uni_data);
                                            startActivity(intent);
                                            dialog4.dismiss();

                                        }
                                        else if (company_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","company");
                                            intent.putExtra("chat_userType","university");
                                            intent.putExtra("curr_user",company_Model);
                                            intent.putExtra("chat_user",uni_data);
                                            startActivity(intent);
                                            dialog4.dismiss();

                                        }
                                    }
                                });
                                cancelBtn4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog4.dismiss();
                                    }
                                });
                                dialog4.setView(view4);
                                dialog4.create();
                                dialog4.show();
                                Toast.makeText(NearbyActivity.this, "uniData"+uni_data.getUni_username()+"\n"+uni_data.getUser_type()+"\n"+uni_data.getUni_lat(), Toast.LENGTH_SHORT).show();
                                break;
                            case "company":
                                int comp_pos = markerTag.getPosition();
                                final CompanyModel comp_data = companyModel_List.get(comp_pos);
                                View view5 = LayoutInflater.from(NearbyActivity.this).inflate(R.layout.custom_alert_msg_profile,null);
                                TextView open_profile5 = (TextView) view5.findViewById(R.id.open_profile_tv);
                                TextView send_msg5 = (TextView) view5.findViewById(R.id.send_msg_tv);
                                Button cancelBtn5 = (Button) view5.findViewById(R.id.cancelBtn);

                                final LovelyCustomDialog dialog5 = new LovelyCustomDialog(NearbyActivity.this);
                                dialog5.setCancelable(true);
                                dialog5.setTopTitle("Select Options");
                                dialog5.setTopColor(ActivityCompat.getColor(NearbyActivity.this,R.color.centercolor));
                                dialog5.setTopTitleColor(ActivityCompat.getColor(NearbyActivity.this,R.color.base));
                                open_profile5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(NearbyActivity.this,Activity_Profile.class);
                                        intent.putExtra("who_visit_myProfile","visitor");
                                        intent.putExtra("companyData",comp_data);
                                        startActivity(intent);
                                        dialog5.dismiss();
                                    }
                                });

                                send_msg5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (user_Data!=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","user");
                                            intent.putExtra("chat_userType","company");
                                            intent.putExtra("curr_user",user_Data);
                                            intent.putExtra("chat_user",comp_data);
                                            startActivity(intent);
                                            dialog5.dismiss();

                                        }
                                        else if (publisher_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","publisher");
                                            intent.putExtra("chat_userType","company");
                                            intent.putExtra("curr_user",publisher_Model);
                                            intent.putExtra("chat_user",comp_data);
                                            startActivity(intent);
                                            dialog5.dismiss();

                                        }
                                        else if (library_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","library");
                                            intent.putExtra("chat_userType","company");
                                            intent.putExtra("curr_user",library_Model);
                                            intent.putExtra("chat_user",comp_data);
                                            startActivity(intent);
                                            dialog5.dismiss();

                                        }
                                        else if (university_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","university");
                                            intent.putExtra("chat_userType","company");
                                            intent.putExtra("curr_user",university_Model);
                                            intent.putExtra("chat_user",comp_data);
                                            startActivity(intent);
                                            dialog5.dismiss();

                                        }
                                        else if (company_Model !=null)
                                        {
                                            Intent intent = new Intent(NearbyActivity.this,Chat_Activity.class);
                                            intent.putExtra("curr_userType","company");
                                            intent.putExtra("chat_userType","company");
                                            intent.putExtra("curr_user",company_Model);
                                            intent.putExtra("chat_user",comp_data);
                                            startActivity(intent);
                                            dialog5.dismiss();

                                        }
                                    }
                                });
                                cancelBtn5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog5.dismiss();
                                    }
                                });
                                dialog5.setView(view5);
                                dialog5.create();
                                dialog5.show();
                                Toast.makeText(NearbyActivity.this, "compData"+comp_data.getUser_type()+"\n"+comp_data.getComp_username()+"\n"+comp_data.getComp_lat(), Toast.LENGTH_SHORT).show();

                                break;
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

    private void getNearbyUsers(String currUserType,String filteredUserType)
    {
        switch (currUserType)
        {
            case "user":
                getDeviceLocation(filteredUserType);
                break;
            case "publisher":
                presenter.getNearbyUsers("publisher",filteredUserType,new LatLng(Double.parseDouble(publisher_Model.getPub_lat()),Double.parseDouble(publisher_Model.getPub_lng())));
                break;
            case "library":
                presenter.getNearbyUsers("library",filteredUserType,new LatLng(Double.parseDouble(library_Model.getLat()),Double.parseDouble(library_Model.getLng())));

                break;
            case "university":
                presenter.getNearbyUsers("university",filteredUserType,new LatLng(Double.parseDouble(university_Model.getUni_lat()),Double.parseDouble(university_Model.getUni_lng())));

                break;
            case "company":
                presenter.getNearbyUsers("company",filteredUserType,new LatLng(Double.parseDouble(company_Model.getComp_lat()),Double.parseDouble(company_Model.getComp_lng())));

                break;
        }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Picasso.with(this).cancelRequest(target);

    }
    private void getDeviceLocation(final String filteredUserType)
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

                        Toast.makeText(NearbyActivity.this, "location"+location.getLatitude(), Toast.LENGTH_SHORT).show();
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        presenter.getNearbyUsers("user",filteredUserType,latLng);

                    }
                }
            }
        });
    }


}
