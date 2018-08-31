package com.semicolon.librarians.libraryguide.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.anwarshahriar.calligrapher.Calligrapher;

public class DisplayUserLocation_OnMap extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean  PERMISSION_GRANTED =false;
    private static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    private static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    private final int  PERMISSION_REQUST=1;
    private NormalUserData userData=null;
    private PublisherModel publisherModel=null;
    private LibraryModel libraryModel =null;
    private UniversityModel universityModel=null;
    private CompanyModel companyModel =null;
    private LatLng curLatlng;
    private View customMarkerView;
    private CircleImageView circleImageView;
    private Target target;
    private LocationManager manager;
    private AlertDialog gpsDialog;
    private final int gps_req = 2330;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_location__on_map);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, Tags.font,true);

        customMarkerView = LayoutInflater.from(this).inflate(R.layout.custom_marker, null);
        circleImageView = (CircleImageView) customMarkerView.findViewById(R.id.custom_userImage);

        if (isServiceOk())
        {
            CheckPermissions();

        }
        getDataFromIntent();


    }


    private void getDataFromIntent()
    {
        Intent intent = getIntent();

        if (intent.hasExtra("userData"))
        {
            userData = (NormalUserData) intent.getSerializableExtra("userData");
            String lat = intent.getStringExtra("currLat");
            String lng = intent.getStringExtra("currLng");

            curLatlng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
        }
        else if (intent.hasExtra("publisherData"))
        {
            publisherModel = (PublisherModel) intent.getSerializableExtra("publisherData");
            String lat = intent.getStringExtra("currLat");
            String lng = intent.getStringExtra("currLng");

            curLatlng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
        }
        else if (intent.hasExtra("libraryData"))
        {
            libraryModel = (LibraryModel) intent.getSerializableExtra("libraryData");
            String lat = intent.getStringExtra("currLat");
            String lng = intent.getStringExtra("currLng");

            curLatlng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
        }else if (intent.hasExtra("universityData"))
        {
            universityModel = (UniversityModel) intent.getSerializableExtra("universityData");
            String lat = intent.getStringExtra("currLat");
            String lng = intent.getStringExtra("currLng");

            curLatlng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));

        }else if (intent.hasExtra("companyData"))
        {
            companyModel = (CompanyModel) intent.getSerializableExtra("companyData");
            String lat = intent.getStringExtra("currLat");
            String lng = intent.getStringExtra("currLng");

            curLatlng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));

        }
    }

    private void initMap()
    {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        if (googleMap!=null)
        {
            mMap = googleMap;
            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.setBuildingsEnabled(true);
            mMap.setTrafficEnabled(false);
            mMap.setIndoorEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.maps));


            if (userData!=null)
            {
                if (userData.getUserPhoto()==null)
                {
                    if (!userData.getUser_photo().equals("0"))
                    {
                        target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                AddMarker(curLatlng,drawCustomMarker(customMarkerView,bitmap));
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        };
                        Picasso.with(this).load(Uri.parse(Tags.image_path+userData.getUser_photo())).placeholder(R.drawable.user_profile).into(target);
                    }else
                        {
                            target = new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    AddMarker(curLatlng,drawCustomMarker(customMarkerView,bitmap));
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            };
                            Picasso.with(this).load(R.drawable.user_profile).placeholder(R.drawable.user_profile).into(target);

                        }
                }else
                    {
                        target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                AddMarker(curLatlng,drawCustomMarker(customMarkerView,bitmap));
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        };
                        Picasso.with(this).load(Uri.parse(userData.getUserPhoto())).placeholder(R.drawable.user_profile).into(target);

                    }
            }else if (publisherModel!=null)
            {
                if (!publisherModel.getUser_photo().equals("0"))
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            AddMarker(curLatlng,drawCustomMarker(customMarkerView,bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                    Picasso.with(this).load(Uri.parse(Tags.image_path+publisherModel.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

                }else
                    {
                        target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                AddMarker(curLatlng,drawCustomMarker(customMarkerView,bitmap));
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        };
                        Picasso.with(this).load(R.drawable.user_profile).placeholder(R.drawable.user_profile).into(target);

                    }

            }else if (libraryModel!=null)
            {
                if (!libraryModel.getUser_photo().equals("0"))
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            AddMarker(curLatlng,drawCustomMarker(customMarkerView,bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                    Picasso.with(this).load(Uri.parse(Tags.image_path+libraryModel.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

                }else
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            AddMarker(curLatlng,drawCustomMarker(customMarkerView,bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                    Picasso.with(this).load(R.drawable.user_profile).placeholder(R.drawable.user_profile).into(target);

                }
            }else if (universityModel!=null)
            {
                if (!universityModel.getUser_photo().equals("0"))
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            AddMarker(curLatlng,drawCustomMarker(customMarkerView,bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                    Picasso.with(this).load(Uri.parse(Tags.image_path+universityModel.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

                }else
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            AddMarker(curLatlng,drawCustomMarker(customMarkerView,bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                    Picasso.with(this).load(R.drawable.user_profile).placeholder(R.drawable.user_profile).into(target);

                }
            }else if (companyModel!=null)
            {
                if (!companyModel.getUser_photo().equals("0"))
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            AddMarker(curLatlng,drawCustomMarker(customMarkerView,bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                    Picasso.with(this).load(Uri.parse(Tags.image_path+companyModel.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

                }else
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            AddMarker(curLatlng,drawCustomMarker(customMarkerView,bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                    Picasso.with(this).load(R.drawable.user_profile).placeholder(R.drawable.user_profile).into(target);

                }
            }
        }
    }
    private void CheckPermissions()
    {
        String [] permissions = new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)

        {

           if (ContextCompat.checkSelfPermission(this,ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
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
                   ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQUST);

               }
        }else
        {
            ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQUST);
        }
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

    private boolean isServiceOk()
    {
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
        int available = availability.isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS)
        {
            return true;
        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this,available,9001);
            dialog.show();
        }
        return false;
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

    private Bitmap drawCustomMarker(View view, Bitmap bitmap)
    {

        circleImageView.setImageBitmap(bitmap);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);

        canvas.drawColor(ContextCompat.getColor(DisplayUserLocation_OnMap.this, R.color.base), PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);

        return returnedBitmap;

    }

    private void AddMarker(final LatLng latLng , Bitmap bitmap)
    {
        if (userData!=null)
        {
            LatLng latlng = new LatLng(Double.parseDouble(userData.getUser_google_lat()),Double.parseDouble(userData.getUser_google_lng()));
            mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.7f));

        }else if (publisherModel!=null)
        {
            LatLng latlng = new LatLng(Double.parseDouble(publisherModel.getPub_lat()),Double.parseDouble(publisherModel.getPub_lng()));
            mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.7f));
        }
        else if (libraryModel!=null)
        {

            LatLng latlng = new LatLng(Double.parseDouble(libraryModel.getLat()),Double.parseDouble(libraryModel.getLng()));
            mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.7f));
        }
        else if (universityModel!=null)
        {
            LatLng latlng = new LatLng(Double.parseDouble(universityModel.getUni_lat()),Double.parseDouble(universityModel.getUni_lng()));
            mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.7f));
        }
        else if (companyModel!=null)
        {
            LatLng latlng = new LatLng(Double.parseDouble(companyModel.getComp_lat()),Double.parseDouble(companyModel.getComp_lng()));
            mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.7f));


        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Geocoder geocoder = new Geocoder(DisplayUserLocation_OnMap.this);
                try {

                    if (userData!=null)
                    {
                        List<Address> addressList = geocoder.getFromLocation(Double.parseDouble(userData.getUser_google_lat()),Double.parseDouble(userData.getUser_google_lng()),1);
                        String address = addressList.get(0).getLocality();
                        String name = userData.getUserName();
                        double dis = distance(Double.parseDouble(userData.getUser_google_lat()),Double.parseDouble(userData.getUser_google_lng()),curLatlng.latitude,curLatlng.longitude);
                        String photo ="";

                        if (userData.getUserPhoto()!=null)
                        {
                            photo = userData.getUserPhoto();
                        }else if (!userData.getUser_photo().equals("0"))
                        {
                            photo = Tags.image_path+userData.getUser_photo();
                        }
                        CreateAlertDialog(photo,name,address,String.valueOf(dis));

                    }else if (publisherModel!=null)
                    {

                        if (!publisherModel.getUser_photo().equals("0"))
                        {
                            String photo = Tags.image_path+publisherModel.getUser_photo();
                            List<Address> addressList = geocoder.getFromLocation(Double.parseDouble(publisherModel.getPub_lat()),Double.parseDouble(publisherModel.getPub_lng()),1);
                            String address = addressList.get(0).getLocality();
                            String name = publisherModel.getPub_name();
                            double dis = distance(Double.parseDouble(publisherModel.getPub_lat()),Double.parseDouble(publisherModel.getPub_lng()),curLatlng.latitude,curLatlng.longitude);
                            CreateAlertDialog(photo,name,address,String.valueOf(dis));

                        }

                    }
                    else if (libraryModel!=null)
                    {

                        if (!libraryModel.getUser_photo().equals("0"))
                        {
                            String photo = Tags.image_path+libraryModel.getUser_photo();
                            List<Address> addressList = geocoder.getFromLocation(Double.parseDouble(libraryModel.getLat()),Double.parseDouble(libraryModel.getLng()),1);
                            String address = addressList.get(0).getLocality();
                            String name = libraryModel.getLib_name();
                            double dis = distance(Double.parseDouble(libraryModel.getLat()),Double.parseDouble(libraryModel.getLng()),curLatlng.latitude,curLatlng.longitude);
                            CreateAlertDialog(photo,name,address,String.valueOf(dis));

                        }

                    }
                    else if (universityModel!=null)
                    {

                        if (!universityModel.getUser_photo().equals("0"))
                        {
                            String photo =Tags.image_path+ universityModel.getUser_photo();
                            List<Address> addressList = geocoder.getFromLocation(Double.parseDouble(universityModel.getUni_lat()),Double.parseDouble(universityModel.getUni_lng()),1);
                            String address = addressList.get(0).getLocality();
                            String name = universityModel.getUni_name();
                            double dis = distance(Double.parseDouble(universityModel.getUni_lat()),Double.parseDouble(universityModel.getUni_lng()),curLatlng.latitude,curLatlng.longitude);
                            CreateAlertDialog(photo,name,address,String.valueOf(dis));

                        }

                    }
                    else if (companyModel!=null)
                    {

                        if (!companyModel.getUser_photo().equals("0"))
                        {
                            String photo = Tags.image_path+companyModel.getUser_photo();
                            List<Address> addressList = geocoder.getFromLocation(Double.parseDouble(companyModel.getComp_lat()),Double.parseDouble(companyModel.getComp_lng()),1);
                            String address = addressList.get(0).getLocality();
                            String name = companyModel.getComp_name();
                            double dis = distance(Double.parseDouble(companyModel.getComp_lat()),Double.parseDouble(companyModel.getComp_lng()),curLatlng.latitude,curLatlng.longitude);
                            CreateAlertDialog(photo,name,address,String.valueOf(dis));

                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(DisplayUserLocation_OnMap.this, "clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void CreateAlertDialog(String image,String name,String address,String distance)
    {
        Log.e("image",image);
        View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog_map_info,null);
        final CircleImageView userImage  = (CircleImageView) view.findViewById(R.id.image);
        TextView  userName = (TextView) view.findViewById(R.id.name);
        TextView  userAddress = (TextView) view.findViewById(R.id.address);
        TextView  userDistance = (TextView) view.findViewById(R.id.distance);
        Button button = (Button) view.findViewById(R.id.cancelBtn);


        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                userImage.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        if (TextUtils.isEmpty(image))
        {
            Picasso.with(this).load(R.drawable.user_profile).placeholder(R.drawable.user_profile).into(target);
        }else
            {
                Picasso.with(this).load(Uri.parse(image)).placeholder(R.drawable.user_profile).into(target);

            }
        userName.setText(name);
        userAddress.setText(address);
        String dis = String.valueOf(Math.round(Double.parseDouble(distance)));
        userDistance.setText(dis+getString(R.string.kilo_m));
        final LovelyCustomDialog dialog = new LovelyCustomDialog(this);
        dialog.setCancelable(true);
        dialog.setTopTitle(R.string.distance_);
        dialog.setTopColor(ActivityCompat.getColor(this, R.color.centercolor));
        dialog.setTopTitleColor(ActivityCompat.getColor(this, R.color.base));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.create();
        dialog.show();


    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_REQUST)
        {
            if (grantResults.length>0)
            {
                for (int i=0;i<grantResults.length;i++)
                {
                    if (grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                    {
                        PERMISSION_GRANTED = false;
                    }else
                        {
                            PERMISSION_GRANTED =true;
                        }
                }
            }
        }
    }




}
