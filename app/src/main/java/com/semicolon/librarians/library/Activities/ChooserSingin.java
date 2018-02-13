package com.semicolon.librarians.library.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.andexert.library.RippleView;
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
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.R;
import com.semicolon.librarians.library.Services.LocalDataBase;
import com.semicolon.librarians.library.Services.NetworkConnection;
import com.semicolon.librarians.library.Services.Preferences;
import com.semicolon.librarians.library.Services.Service;
import com.semicolon.librarians.library.Services.Tags;

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

public class ChooserSingin extends AppCompatActivity implements View.OnClickListener, FacebookCallback<LoginResult>, GoogleApiClient.OnConnectionFailedListener {

    private ShimmerTextView shimmerTextView;
    private RippleView ripple_signinBtn;
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
    private RelativeLayout progressBar_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_chooser_singin);
        Calligrapher calligrapher = new Calligrapher(getApplicationContext());
        calligrapher.setFont(this, Tags.font, true);

        connection = new NetworkConnection(this);
        //////////////////////////////////////////////////////////////
        initView();
        setUpShimmer();
        setUpProgressdialog();
        setUpUserProfile();

        //CheckedUserLoginWithGoogle();

    }

    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.chooser_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        progressBar_container = (RelativeLayout) findViewById(R.id.progressBar_container);
        ///////////////////////////////////////////////////////////////
        shimmerTextView = (ShimmerTextView) findViewById(R.id.shimmer);
        ///////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////
        ripple_signinBtn = (RippleView) findViewById(R.id.ripple_signinBtn);
        ripple_signinBtn.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
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

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        apiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(ChooserSingin.this, ChooserSingin.this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();


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
        dialog.setMessage("Sign in....");
        ProgressBar progressBar = new ProgressBar(this);
        Drawable drawable = progressBar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this, R.color.centercolor), PorterDuff.Mode.SRC_IN);
        dialog.setIndeterminateDrawable(drawable);
        dialog.setCancelable(false);
    }

    private void CheckedUserLoginWithGoogle() {
        OptionalPendingResult<GoogleSignInResult> result = Auth.GoogleSignInApi.silentSignIn(apiClient);
        if (result.isDone()) {

            GoogleSignInAccount account = result.get().getSignInAccount();
            if (account != null) {

                getUserDataFromGoogle(account);


            }
        }else
            {
               // Check_OfflineData();
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
                            if (apiClient.isConnected())
                            {
                                apiClient.clearDefaultAccountAndReconnect().setResultCallback(new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(@NonNull Status status) {
                                        apiClient.disconnect();
                                    }
                                });
                                Auth.GoogleSignInApi.signOut(apiClient);
                            }

                            signinWithGoogle();

                        }
                    });
                    thread.start();

                } else {
                    Toast.makeText(this, "Check network connection", Toast.LENGTH_LONG).show();

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
            progressBar_container.setVisibility(View.VISIBLE);
            getDeviceLocationFB(profile);

        } else {
            dialog.dismiss();
            progressBar_container.setVisibility(View.GONE);

        }

    }

    @Override
    public void onCancel() {
        dialog.dismiss();
    }

    @Override
    public void onError(FacebookException error) {
        dialog.dismiss();
        progressBar_container.setVisibility(View.GONE);

        Toast.makeText(this, "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGNIN && data != null) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {

                account = result.getSignInAccount();
                if (account != null) {

                    getUserDataFromGoogle(account);

                }


            } else {
                dialog.dismiss();
            }

        }
    }

    private void getUserDataFromGoogle(GoogleSignInAccount account) {
        progressBar_container.setVisibility(View.VISIBLE);
        this.account = account;

        if (account != null) {

            getDeviceLocationGm(account);
        } else {
            apiClient.disconnect();
            dialog.dismiss();

        }


    }

    private void getDeviceLocationFB(final Profile profile)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        progressBar_container.setVisibility(View.VISIBLE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();
        lastLocation.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful())
                {
                    Location location = task.getResult();
                    String userID = profile.getId();
                    String userName = profile.getName();
                    String userPhoto = "https://graph.facebook.com/" + userID + "/picture?type=large";
                    String user_lat  = String.valueOf(location.getLatitude());
                    String user_lng = String.valueOf(location.getLongitude());
                    String user_token = FirebaseInstanceId.getInstance().getToken();

                    Map<String,String> map = new HashMap<>();
                    map.put("user_username",userID);
                    map.put("user_name",userName);
                    map.put("photo_link",userPhoto);
                    map.put("user_google_lat",user_lat);
                    map.put("user_google_lng",user_lng);
                    map.put("user_token",user_token);

                    Retrofit retrofit = setUpRetrofit("http://librarians.liboasis.com/");
                    Service service = retrofit.create(Service.class);

                    Call<NormalUserData> call = service.UploadUserDataWithFacebook(map);

                    call.enqueue(new Callback<NormalUserData>() {
                        @Override
                        public void onResponse(Call<NormalUserData> call, Response<NormalUserData> response) {
                            if (response.isSuccessful())
                            {
                                final NormalUserData userData = response.body();

                                if (userData.getUserCountry()==null||userData.getUserPhone()==null||userData.getUserEmail()==null)
                                {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(ChooserSingin.this,FB_Gmail_UpdateProfile.class);
                                            intent.putExtra("userData",userData);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                            startActivity(intent);
                                            progressBar_container.setVisibility(View.GONE);
                                            dialog.dismiss();
                                        }
                                    },5000);
                                }else
                                {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            Intent intent = new Intent(ChooserSingin.this,HomeActivity.class);
                                            intent.putExtra("userData",userData);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            Preferences pref = new Preferences(ChooserSingin.this);
                                            pref.Session("loggedin");
                                            startActivity(intent);
                                            progressBar_container.setVisibility(View.GONE);
                                            dialog.dismiss();
                                        }
                                    },10000);
                                }

                            }else
                                {

                                    dialog.dismiss();
                                    progressBar_container.setVisibility(View.GONE);

                                    Toast.makeText(ChooserSingin.this, "Something went haywire", Toast.LENGTH_SHORT).show();

                                }

                        }

                        @Override
                        public void onFailure(Call<NormalUserData> call, Throwable t) {
                            dialog.dismiss();
                            progressBar_container.setVisibility(View.GONE);
                            Log.e("error",t.getMessage());
                            Toast.makeText(ChooserSingin.this, "Something went haywire", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private void getDeviceLocationGm(final GoogleSignInAccount account)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();
        lastLocation.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful())
                {
                    progressBar_container.setVisibility(View.VISIBLE);
                    Location location = task.getResult();
                    String userID = account.getId();
                    String userName = account.getDisplayName();
                    String userEmail = account.getEmail();
                    String userPhoto = account.getPhotoUrl().toString();
                    String user_lat  = String.valueOf(location.getLatitude());
                    String user_lng = String.valueOf(location.getLongitude());
                    String user_token = FirebaseInstanceId.getInstance().getToken();

                    Map<String,String> map = new HashMap<>();
                    map.put("user_username",userID);
                    map.put("user_name",userName);
                    map.put("user_email",userEmail);
                    map.put("photo_link",userPhoto);
                    map.put("user_google_lat",user_lat);
                    map.put("user_google_lng",user_lng);
                    map.put("user_token",user_token);

                    Retrofit retrofit = setUpRetrofit("http://librarians.liboasis.com/");
                    Service service = retrofit.create(Service.class);

                    Call<NormalUserData> call = service.UploadUserDataWithGoogle(map);

                    call.enqueue(new Callback<NormalUserData>() {
                        @Override
                        public void onResponse(Call<NormalUserData> call, Response<NormalUserData> response) {
                            if (response.isSuccessful())
                            {
                                final NormalUserData userData = response.body();

                                if (userData.getUserCountry()==null||userData.getUserPhone()==null||userData.getUserEmail()==null)
                                {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(ChooserSingin.this,FB_Gmail_UpdateProfile.class);
                                            intent.putExtra("userData",userData);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            Log.e("mmmm",""+userData.getUserEmail());

                                            startActivity(intent);
                                            dialog.dismiss();
                                            progressBar_container.setVisibility(View.GONE);

                                        }
                                    },10000);
                                }else
                                {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            Intent intent = new Intent(ChooserSingin.this,HomeActivity.class);
                                            intent.putExtra("userData",userData);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            Preferences pref = new Preferences(ChooserSingin.this);
                                            pref.Session("loggedin");

                                            startActivity(intent);
                                            dialog.dismiss();
                                            progressBar_container.setVisibility(View.GONE);

                                        }
                                    },2000);
                                }

                            }else
                                {
                                    dialog.dismiss();
                                    progressBar_container.setVisibility(View.GONE);

                                    Toast.makeText(ChooserSingin.this, "Something went haywire", Toast.LENGTH_SHORT).show();

                                }

                        }

                        @Override
                        public void onFailure(Call<NormalUserData> call, Throwable t) {

                            dialog.dismiss();
                            progressBar_container.setVisibility(View.GONE);
                            Log.e("error",t.getMessage());

                            Toast.makeText(ChooserSingin.this, "Something went haywire", Toast.LENGTH_SHORT).show();
                        }
                    });

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

    }

    @Override
    protected void onStart() {
        super.onStart();
        apiClient.connect();
        if (apiClient.isConnected())
        {
            Auth.GoogleSignInApi.signOut(apiClient);
            apiClient.clearDefaultAccountAndReconnect().setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    apiClient.disconnect();
                }
            });
            apiClient.disconnect();
            apiClient.connect();
        }

        progressBar_container.setVisibility(View.VISIBLE);
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
            Toast.makeText(this, "help", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


}
