package com.semicolon.librarians.libraryguide.Activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.Presenter;
import com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.ViewData;
import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.LocalDataBase;
import com.semicolon.librarians.libraryguide.Services.NetworkConnection;
import com.semicolon.librarians.libraryguide.Services.Preferences;
import com.semicolon.librarians.libraryguide.Services.Service;
import com.semicolon.librarians.libraryguide.Services.Tags;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooserSingin extends AppCompatActivity implements View.OnClickListener, FacebookCallback<LoginResult>, GoogleApiClient.OnConnectionFailedListener, ViewData {

    private ShimmerTextView shimmerTextView;
    //private RippleView ripple_signinBtn;
    private Button chooser_signinBtn;
    private LoginButton facebook_lognBtn;
    private SignInButton googleSininBtn;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    public GoogleApiClient apiClient;
    private GoogleSignInOptions signInOptions;
    private final int RC_GOOGLE_SIGNIN = 1000;
    private GoogleSignInAccount account;
    private NetworkConnection connection;
    private boolean isConnected;
    private ProgressDialog dialog;
    private AccessTokenTracker tokenTracker;
    private ProfileTracker profileTracker;
    private LocalDataBase dataBase;
    private AccessToken token;
    private FusedLocationProviderClient fusedLocationProviderClient;
    // private RelativeLayout progressBar_container;
    private Presenter presenter;
    private LocationManager locationManager;
    private Location location;
    private boolean isGPS;
    private boolean isNetwork;
    private Dialog setting_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_chooser_singin);
        //EventBus.getDefault().register(this);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, Tags.font, true);
        presenter = new PresenterImp(this, this);
        connection = new NetworkConnection(this);
        //////////////////////////////////////////////////////////////
        initView();
        setUpShimmer();
        setUpProgressdialog();
        setUpUserProfile();
        ///////////////////////////////////////////////////////////////////////////////
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        apiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(ChooserSingin.this, ChooserSingin.this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
        apiClient.connect();

        CheckedUserLoginWithGoogle();

    }


    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.chooser_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        //progressBar_container = (RelativeLayout) findViewById(R.id.progressBar_container);
        ///////////////////////////////////////////////////////////////
        shimmerTextView = (ShimmerTextView) findViewById(R.id.shimmer);
        ///////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////
        chooser_signinBtn = (Button) findViewById(R.id.chooser_signinBtn);
        chooser_signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigateTOActivity_Login();

            }
        });
        ///////////////////////////////////////////////////////////////
        facebook_lognBtn = (LoginButton) findViewById(R.id.facebook_signinBtn);
        facebook_lognBtn.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends"));
        facebook_lognBtn.registerCallback(callbackManager, this);

        ////////////////////////////////////////////////////////////////
        googleSininBtn = (SignInButton) findViewById(R.id.googleSigninBtn);
        googleSininBtn.setOnClickListener(this);


    }


    private void setUpUserProfile() {

        tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    // CheckedUserLoginWithGoogle();
                }

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                getUserData(currentProfile);

            }
        };
        tokenTracker.startTracking();
        profileTracker.startTracking();


    }

    private void setUpProgressdialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.sign_in));
        ProgressBar progressBar = new ProgressBar(this);
        Drawable drawable = progressBar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this, R.color.centercolor), PorterDuff.Mode.SRC_IN);
        dialog.setIndeterminateDrawable(drawable);
        dialog.setCancelable(false);
    }

    private void CheckedUserLoginWithGoogle() {
        if (connection.CheckConnection() == true) {
            OptionalPendingResult<GoogleSignInResult> result = Auth.GoogleSignInApi.silentSignIn(apiClient);
            if (result.isDone()) {
                dialog.show();
                GoogleSignInAccount account = result.get().getSignInAccount();
                if (account != null) {

                    getUserDataFromGoogle(account);


                } else {
                    dialog.dismiss();

                    apiClient.disconnect();


                }
            } else {
                //progressBar_container.setVisibility(View.VISIBLE);
                Log.e("1", "1");
                SharedPreferences preferences = getSharedPreferences("userType", MODE_PRIVATE);
                String uType = preferences.getString("userType", "");
                String u_id = preferences.getString("id", "");

                if (connection.CheckConnection() == true) {
                    Log.e("2", "1");


                    if (!TextUtils.isEmpty(uType) || !TextUtils.isEmpty(u_id)) {
                        Log.e("3", "1");

                        switch (uType) {
                            case "user":
                                presenter.getNormalUserData(uType, u_id);
                                break;
                            case "publisher":
                                presenter.getPublisherData(uType, u_id);
                                break;
                            case "library":
                                presenter.getLibraryData(uType, u_id);
                                break;
                            case "university":
                                presenter.getUniversityData(uType, u_id);
                                break;
                            case "company":
                                presenter.getCompanyData(uType, u_id);
                                break;
                        }
                    }
                } else {
                    dialog.show();
                    //progressBar_container.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(uType) || !TextUtils.isEmpty(u_id)) {
                        Check_OfflineData(uType);
                    }
                }
            }


        } else {

            Log.e("1", "1");
            SharedPreferences preferences = getSharedPreferences("userType", MODE_PRIVATE);
            String uType = preferences.getString("userType", "");
            String u_id = preferences.getString("id", "");

            if (connection.CheckConnection() == true) {
                Log.e("2", "1");


                if (!TextUtils.isEmpty(uType) || !TextUtils.isEmpty(u_id)) {
                    Log.e("3", "1");

                    switch (uType) {
                        case "user":
                            presenter.getNormalUserData(uType, u_id);
                            break;
                        case "publisher":
                            presenter.getPublisherData(uType, u_id);
                            break;
                        case "library":
                            presenter.getLibraryData(uType, u_id);
                            break;
                        case "university":
                            presenter.getUniversityData(uType, u_id);
                            break;
                        case "company":
                            presenter.getCompanyData(uType, u_id);
                            break;
                    }
                }
            } else {
                if (!TextUtils.isEmpty(uType) || !TextUtils.isEmpty(u_id)) {
                    Check_OfflineData(uType);
                }
            }


        }

    }

    private void Check_OfflineData(String current_user) {
        Log.e("offline", "offline");

        switch (current_user) {
            case "user":
                Log.e("user", "user");

                SharedPreferences sharedPreferences_user = getSharedPreferences("user_pref", MODE_PRIVATE);
                String u_id = sharedPreferences_user.getString("id", "");
                String u_name = sharedPreferences_user.getString("name", "");
                String u_email = sharedPreferences_user.getString("email", "");
                String u_phone = sharedPreferences_user.getString("phone", "");
                String u_country = sharedPreferences_user.getString("country", "");
                String u_type = sharedPreferences_user.getString("type", "");
                String u_photo = sharedPreferences_user.getString("photo", "");
                String u_photo_link = sharedPreferences_user.getString("photo_link", "");


                NormalUserData userData = new NormalUserData();
                userData.setUserId(u_id);
                userData.setUserName(u_name);
                userData.setUserEmail(u_email);
                userData.setUserPhone(u_phone);
                userData.setUserCountry(u_country);
                userData.setUserType(u_type);
                userData.setUser_photo(u_photo);
                userData.setUserPhoto(u_photo_link);


                if (u_photo_link.equals("null")) {
                    userData.setUserPhoto(null);
                } else {
                    userData.setUserPhoto(u_photo_link);
                }
                Intent intent = new Intent(ChooserSingin.this, HomeActivity.class);
                intent.putExtra("userData", userData);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                // progressBar_container.setVisibility(View.GONE);

                break;
            case "publisher":

                SharedPreferences sharedPreferences_pub = getSharedPreferences("pub_pref", MODE_PRIVATE);
                String p_id = sharedPreferences_pub.getString("id", "");
                String p_type = sharedPreferences_pub.getString("type", "");
                String p_photo = sharedPreferences_pub.getString("photo", "");
                String p_name = sharedPreferences_pub.getString("name", "");
                String p_email = sharedPreferences_pub.getString("email", "");
                String p_phone = sharedPreferences_pub.getString("phone", "");
                String p_country = sharedPreferences_pub.getString("country", "");
                String p_address = sharedPreferences_pub.getString("address", "");
                String p_town = sharedPreferences_pub.getString("town", "");
                String p_site = sharedPreferences_pub.getString("site", "");

                PublisherModel publisherModel = new PublisherModel();
                publisherModel.setPub_username(p_id);
                publisherModel.setUser_type(p_type);
                publisherModel.setUser_photo(p_photo);
                publisherModel.setPub_name(p_name);
                publisherModel.setPub_email(p_email);
                publisherModel.setPub_phone(p_phone);
                publisherModel.setPub_country(p_country);
                publisherModel.setPub_address(p_address);
                publisherModel.setPub_town(p_town);
                publisherModel.setPub_website(p_site);

                Intent intent2 = new Intent(ChooserSingin.this, HomeActivity.class);
                intent2.putExtra("publisherData", publisherModel);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                // progressBar_container.setVisibility(View.GONE);

                break;
            case "library":
                SharedPreferences sharedPreferences_lib = getSharedPreferences("lib_pref", MODE_PRIVATE);

                String l_id = sharedPreferences_lib.getString("id", "");
                String l_type = sharedPreferences_lib.getString("type", "");
                String l_photo = sharedPreferences_lib.getString("photo", "");
                String l_name = sharedPreferences_lib.getString("name", "");
                String l_libType = sharedPreferences_lib.getString("libType", "");
                String l_email = sharedPreferences_lib.getString("email", "");
                String l_phone = sharedPreferences_lib.getString("phone", "");
                String l_country = sharedPreferences_lib.getString("country", "");
                String l_address = sharedPreferences_lib.getString("address", "");

                LibraryModel libraryModel = new LibraryModel();
                libraryModel.setLib_username(l_id);
                libraryModel.setUser_type(l_type);
                libraryModel.setUser_photo(l_photo);
                libraryModel.setLib_name(l_name);
                libraryModel.setLib_type(l_libType);
                libraryModel.setLib_email(l_email);
                libraryModel.setLib_phone(l_phone);
                libraryModel.setLib_country(l_country);
                libraryModel.setLib_address(l_address);

                Intent intent3 = new Intent(ChooserSingin.this, HomeActivity.class);
                intent3.putExtra("libraryData", libraryModel);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent3);
                //  progressBar_container.setVisibility(View.GONE);

                break;
            case "university":

                SharedPreferences sharedPreferences_uni = getSharedPreferences("uni_pref", MODE_PRIVATE);


                String un_id = sharedPreferences_uni.getString("id", "");
                String un_type = sharedPreferences_uni.getString("type", "");
                String un_photo = sharedPreferences_uni.getString("photo", "");
                String un_name = sharedPreferences_uni.getString("name", "");
                String un_email = sharedPreferences_uni.getString("email", "");
                String un_phone = sharedPreferences_uni.getString("phone", "");
                String un_country = sharedPreferences_uni.getString("country", "");
                String un_address = sharedPreferences_uni.getString("address", "");
                String un_major = sharedPreferences_uni.getString("major", "");
                String un_site = sharedPreferences_uni.getString("site", "");

                UniversityModel universityModel = new UniversityModel();
                universityModel.setUni_username(un_id);
                universityModel.setUser_type(un_type);
                universityModel.setUser_photo(un_photo);
                universityModel.setUni_name(un_name);
                universityModel.setUni_email(un_email);
                universityModel.setUni_phone(un_phone);
                universityModel.setUni_country(un_country);
                universityModel.setUni_address(un_address);
                universityModel.setUni_major(un_major);
                universityModel.setUni_site(un_site);

                Intent intent4 = new Intent(ChooserSingin.this, HomeActivity.class);
                intent4.putExtra("universityData", universityModel);
                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent4);
                // progressBar_container.setVisibility(View.GONE);


                break;
            case "company":
                SharedPreferences sharedPreferences_comp = getSharedPreferences("comp_pref", MODE_PRIVATE);

                String c_id = sharedPreferences_comp.getString("id", "");
                String c_type = sharedPreferences_comp.getString("type", "");
                String c_photo = sharedPreferences_comp.getString("photo", "");
                String c_name = sharedPreferences_comp.getString("name", "");
                String c_email = sharedPreferences_comp.getString("email", "");
                String c_phone = sharedPreferences_comp.getString("phone", "");
                String c_country = sharedPreferences_comp.getString("country", "");
                String c_address = sharedPreferences_comp.getString("address", "");
                String c_town = sharedPreferences_comp.getString("town", "");
                String c_site = sharedPreferences_comp.getString("site", "");

                CompanyModel companyModel = new CompanyModel();
                companyModel.setComp_username(c_id);
                companyModel.setUser_type(c_type);
                companyModel.setUser_photo(c_photo);
                companyModel.setComp_name(c_name);
                companyModel.setComp_email(c_email);
                companyModel.setComp_phone(c_phone);
                companyModel.setComp_country(c_country);
                companyModel.setComp_address(c_address);
                companyModel.setComp_town(c_town);
                companyModel.setComp_site(c_site);

                Intent intent5 = new Intent(ChooserSingin.this, HomeActivity.class);
                intent5.putExtra("companyData", companyModel);
                intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent5);
                // progressBar_container.setVisibility(View.GONE);


                break;
            default: {

            }
        }


    }


    private void setUpShimmer() {
        Shimmer shimmer = new Shimmer();
        shimmer.setDuration(3000).setDirection(Shimmer.ANIMATION_DIRECTION_RTL);
        shimmer.start(shimmerTextView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.facebook_signinBtn:
                loginManager.logInWithReadPermissions(ChooserSingin.this, Arrays.asList("public_profile", "email"));
                break;
            case R.id.googleSigninBtn:
                isConnected = connection.CheckConnection();
                if (isConnected == true) {

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            signinWithGoogle();

                        }
                    });
                    thread.start();

                } else {
                    Toast.makeText(this, R.string.network_connection, Toast.LENGTH_LONG).show();

                }

                break;


        }

    }

    private void signinWithGoogle() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(intent, RC_GOOGLE_SIGNIN);

    }

    private void NavigateTOActivity_Login() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ChooserSingin.this, LoginActivity.class);
                startActivity(intent);
            }
        }, 2000);

    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        this.token = loginResult.getAccessToken();
        if (loginResult.getAccessToken() != null) {
            Profile profile = Profile.getCurrentProfile();
            getUserData(profile);


        } else {
            // CheckedUserLoginWithGoogle();
        }


        tokenTracker.startTracking();
        profileTracker.startTracking();
    }

    private void getUserData(Profile profile) {
        if (profile != null) {
            dialog.show();
            //progressBar_container.setVisibility(View.VISIBLE);
            getDeviceLocationFB(profile);

        } else {
            dialog.dismiss();
            //progressBar_container.setVisibility(View.GONE);

        }

    }

    @Override
    public void onCancel() {
        dialog.dismiss();
    }

    @Override
    public void onError(FacebookException error) {
        dialog.dismiss();
        // progressBar_container.setVisibility(View.GONE);

        Toast.makeText(this, getString(R.string.error) + error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGNIN && data != null) {

            dialog.show();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                dialog.show();
                account = result.getSignInAccount();
                if (account != null) {

                    getUserDataFromGoogle(account);

                } else {
                    dialog.dismiss();
                }


            } else {
                dialog.dismiss();
            }

        }
    }

    private void getUserDataFromGoogle(GoogleSignInAccount account) {
        //progressBar_container.setVisibility(View.VISIBLE);
        dialog.show();
        this.account = account;
        if (account != null) {
            getDeviceLocationGm(account);

        } else {
            apiClient.disconnect();
            dialog.dismiss();

        }


    }

    private void getDeviceLocationFB(final Profile profile) {


        dialog.show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // progressBar_container.setVisibility(View.VISIBLE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();
        lastLocation.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    if (location != null) {
                        String userID = profile.getId();
                        String userName = profile.getName();
                        String userPhoto = "https://graph.facebook.com/" + userID + "/picture?type=large";
                        String user_lat = String.valueOf(location.getLatitude());
                        String user_lng = String.valueOf(location.getLongitude());
                        final String user_token = FirebaseInstanceId.getInstance().getToken();

                        Map<String, String> map = new HashMap<>();
                        map.put("user_username", userID);
                        map.put("user_name", userName);
                        map.put("photo_link", userPhoto);
                        map.put("user_google_lat", user_lat);
                        map.put("user_google_lng", user_lng);
                        map.put("user_token", user_token);

                        Retrofit retrofit = setUpRetrofit("http://librarians.liboasis.com/");
                        Service service = retrofit.create(Service.class);

                        Call<NormalUserData> call = service.UploadUserDataWithFacebook(map);

                        call.enqueue(new Callback<NormalUserData>() {
                            @Override
                            public void onResponse(Call<NormalUserData> call, Response<NormalUserData> response) {
                                if (response.isSuccessful()) {
                                    final NormalUserData userData = response.body();
                                    Log.e("faaaaaaaaacelogin", userData.getUserEmail() + "   " + userData.getUserCountry() + "   " + userData.getUserPhone());
                                    if (userData.getUserCountry() == null || userData.getUserPhone() == null || userData.getUserEmail() == null) {

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(ChooserSingin.this, FB_Gmail_UpdateProfile.class);
                                                intent.putExtra("userData", userData);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                                startActivity(intent);
                                                //progressBar_container.setVisibility(View.GONE);
                                                dialog.dismiss();
                                            }
                                        }, 5000);
                                    } else {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                Intent intent = new Intent(ChooserSingin.this, HomeActivity.class);
                                                intent.putExtra("userData", userData);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                Preferences pref = new Preferences(ChooserSingin.this);
                                                pref.Session("loggedin");
                                                startActivity(intent);
                                                //progressBar_container.setVisibility(View.GONE);
                                                dialog.dismiss();
                                            }
                                        }, 10000);
                                    }

                                } else {

                                    dialog.dismiss();
                                    //progressBar_container.setVisibility(View.GONE);

                                    Toast.makeText(ChooserSingin.this, R.string.something_haywire, Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onFailure(Call<NormalUserData> call, Throwable t) {
                                dialog.dismiss();
                                // progressBar_container.setVisibility(View.GONE);
                                Log.e("error", t.getMessage());
                                Toast.makeText(ChooserSingin.this, R.string.something_haywire, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }
            }
        });







      /*  */

    }

    private void getDeviceLocationGm(final GoogleSignInAccount account) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();
        lastLocation.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful())
                {
                    //progressBar_container.setVisibility(View.VISIBLE);
                    Location location = task.getResult();
                    if (location!=null)
                    {
                        if (account!=null)
                        {
                            String userID = account.getId();
                            String userName = account.getDisplayName();
                            String userEmail = account.getEmail();
                            String userPhoto="";
                            if (account.getPhotoUrl()!=null)
                            {
                                userPhoto = account.getPhotoUrl().toString();

                            }
                            String user_lat  = String.valueOf(location.getLatitude());
                            String user_lng = String.valueOf(location.getLongitude());
                            String user_token = FirebaseInstanceId.getInstance().getToken();

                            Map<String, String> map = new HashMap<>();
                            map.put("user_username", userID);
                            map.put("user_name", userName);
                            map.put("user_email", userEmail);
                            map.put("photo_link", userPhoto);
                            map.put("user_google_lat", user_lat);
                            map.put("user_google_lng", user_lng);
                            map.put("user_token", user_token);

                            Retrofit retrofit = setUpRetrofit("http://librarians.liboasis.com/");
                            Service service = retrofit.create(Service.class);

                            Call<NormalUserData> call = service.UploadUserDataWithGoogle(map);

                            call.enqueue(new Callback<NormalUserData>() {
                                @Override
                                public void onResponse(Call<NormalUserData> call, Response<NormalUserData> response) {
                                    if (response.isSuccessful()) {
                                        final NormalUserData userData = response.body();

                                        if (userData.getUserCountry() == null || userData.getUserPhone() == null || userData.getUserEmail() == null) {
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(ChooserSingin.this, FB_Gmail_UpdateProfile.class);
                                                    intent.putExtra("userData", userData);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                                    Log.e("mmmm", "" + userData.getUserEmail());

                                                    startActivity(intent);
                                                    apiClient.disconnect();
                                                    dialog.dismiss();
                                                    // progressBar_container.setVisibility(View.GONE);

                                                }
                                            }, 10000);
                                        } else {
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    Intent intent = new Intent(ChooserSingin.this, HomeActivity.class);
                                                    intent.putExtra("userData", userData);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    Preferences pref = new Preferences(ChooserSingin.this);
                                                    pref.Session("loggedin");

                                                    startActivity(intent);
                                                    dialog.dismiss();
                                                    // progressBar_container.setVisibility(View.GONE);

                                                }
                                            }, 2000);
                                        }

                                    } else {
                                        dialog.dismiss();
                                        // progressBar_container.setVisibility(View.GONE);

                                        Toast.makeText(ChooserSingin.this, R.string.something_haywire, Toast.LENGTH_SHORT).show();

                                    }

                                }

                                @Override
                                public void onFailure(Call<NormalUserData> call, Throwable t) {

                                    dialog.dismiss();
                                    // progressBar_container.setVisibility(View.GONE);
                                    Log.e("error", t.getMessage());

                                    Toast.makeText(ChooserSingin.this, R.string.something_haywire, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }


                }
            }
        });



    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Error " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
            getUserData(profile);

    }

    @Override
    protected void onStop() {
        super.onStop();
        profileTracker.stopTracking();
        tokenTracker.stopTracking();
        apiClient.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
      //  progressBar_container.setVisibility(View.VISIBLE);
    }


    private Retrofit setUpRetrofit(String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chooser_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.help)
        {
            Toast.makeText(this, R.string.help, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onNormalUserDataSuccess(NormalUserData normalUserData) {
        Intent intent = new Intent(ChooserSingin.this,HomeActivity.class);
        intent.putExtra("userData",normalUserData);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        dialog.dismiss();

        // progressBar_container.setVisibility(View.GONE);


    }

    @Override
    public void onPublisherDataSuccess(PublisherModel publisherModel) {
        Intent intent = new Intent(ChooserSingin.this,HomeActivity.class);
        intent.putExtra("publisherData",publisherModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        dialog.dismiss();

        //progressBar_container.setVisibility(View.GONE);

    }

    @Override
    public void onLibraryDataSuccess(LibraryModel libraryModel) {
        Intent intent = new Intent(ChooserSingin.this,HomeActivity.class);
        intent.putExtra("libraryData",libraryModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        dialog.dismiss();

        // progressBar_container.setVisibility(View.GONE);

    }

    @Override
    public void onCompanyDataSuccess(CompanyModel companyModel) {
        Intent intent = new Intent(ChooserSingin.this,HomeActivity.class);
        intent.putExtra("companyData",companyModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        dialog.dismiss();

        //  progressBar_container.setVisibility(View.GONE);

    }

    @Override
    public void onUniversityDataSuccess(UniversityModel universityModel) {
        Intent intent = new Intent(ChooserSingin.this,HomeActivity.class);
        intent.putExtra("universityData",universityModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        dialog.dismiss();
      //  progressBar_container.setVisibility(View.GONE);

    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(this,getString(R.string.something_haywire), Toast.LENGTH_SHORT).show();
    }


}
