package com.example.omd.library.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.omd.library.Models.User;
import com.example.omd.library.R;
import com.example.omd.library.Services.NetworkConnection;
import com.example.omd.library.Services.Service;
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
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooserSingin extends AppCompatActivity implements View.OnClickListener,FacebookCallback<LoginResult>,GoogleApiClient.OnConnectionFailedListener{

    private ShimmerTextView shimmerTextView;
    private RippleView ripple_signinBtn;
    private LoginButton facebook_lognBtn;
    private SignInButton googleSininBtn;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    public GoogleApiClient apiClient;
    private GoogleSignInOptions signInOptions;
    private final int RC_GOOGLE_SIGNIN=1000;
    private GoogleSignInAccount account;
    private NetworkConnection connection;
    private boolean isConnected;
    private ProgressDialog dialog;
    private AccessTokenTracker tokenTracker;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_chooser_singin);
        connection = new NetworkConnection(this);
        //////////////////////////////////////////////////////////////
        initView();
        setUpShimmer();
        setUpProgressdialog();
        setUpUserProfile();
        CheckedUserLoginWithGoogle();

    }

    private void initView()
    {

        ///////////////////////////////////////////////////////////////
        shimmerTextView = (ShimmerTextView) findViewById(R.id.shimmer);
        ///////////////////////////////////////////////////////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.chooser_toolbar);
        setSupportActionBar(toolbar);
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
        facebook_lognBtn.setReadPermissions(Arrays.asList("email","public_profile","user_friends"));
        facebook_lognBtn.registerCallback(callbackManager,this);

        ////////////////////////////////////////////////////////////////
        googleSininBtn = (SignInButton) findViewById(R.id.googleSigninBtn);
        googleSininBtn.setOnClickListener(this);

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        apiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(ChooserSingin.this,ChooserSingin.this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();



    }
    private void setUpUserProfile()
    {

         tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
         profileTracker =new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                getUserData(currentProfile);
            }
        };
         tokenTracker.startTracking();
         profileTracker.startTracking();


    }
    private void setUpProgressdialog()
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Sign in....");
        ProgressBar progressBar =new ProgressBar(this);
        Drawable drawable = progressBar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        dialog.setIndeterminateDrawable(drawable);
        dialog.setCancelable(false);
    }
    private void CheckedUserLoginWithGoogle()
    {
        OptionalPendingResult<GoogleSignInResult> result = Auth.GoogleSignInApi.silentSignIn(apiClient);
        if (result.isDone())
        {
            GoogleSignInAccount account = result.get().getSignInAccount();
            if (account!=null)
            {

                getUserDataFromGoogle(account);

            }
        }

    }
    private void setUpShimmer()
    {
        Shimmer shimmer = new Shimmer();
        shimmer.setDuration(3000).setDirection(Shimmer.ANIMATION_DIRECTION_RTL);
        shimmer.start(shimmerTextView);
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.facebook_signinBtn:
                loginManager.logInWithReadPermissions(ChooserSingin.this,Arrays.asList("public_profile","email"));
                break;
            case R.id.googleSigninBtn:
                isConnected = connection.CheckConnection();
                if (isConnected==true)
                {

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                signinWithGoogle();

                            }
                        });
                        thread.start();

                }
                else
                    {
                        Toast.makeText(this, "Check network connection", Toast.LENGTH_LONG).show();

                    }

                break;


        }

    }
    private void signinWithGoogle()
    {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(intent,RC_GOOGLE_SIGNIN);

    }
    private void NavigateTOActivity_Login()
    {
        Intent intent = new Intent(ChooserSingin.this,LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public void onSuccess(LoginResult loginResult)
    {
       if (loginResult.getAccessToken()!=null)
       {
           Profile profile = Profile.getCurrentProfile();
           getUserData(profile);
       }


        tokenTracker.startTracking();
       profileTracker.startTracking();
    }
    private void getUserData(Profile profile)
    {
        dialog.show();
        if (profile!=null)
        {
            String userID = profile.getId();
            String userName = profile.getName();
            String userPhoto = "https://graph.facebook.com/" + userID+"/picture?type=large";
            User userData = new User(userName,"",userPhoto);
            userData.setUserId(userID);

            Retrofit retrofit = setUpRetrofit(getString(R.string.facebook_url));
            Service client = retrofit.create(Service.class);
            if (userData!=null)
            {
                Map<String,String> map = new HashMap<>();
                map.put("user_username",userData.getUserId());
                map.put("user_name",userData.getUserName());
                map.put("photo_link",userData.getUserPhotoUrl());
                Call<User> UserData = client.UploadUserDataWithFacebook(map);
                UserData.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful())
                        {
                            final User UserData = response.body();
                            if (UserData!=null)
                            {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(ChooserSingin.this,HomeActivity.class);
                                        intent.putExtra("userData",UserData);
                                        startActivity(intent);
                                    }
                                },2000);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(ChooserSingin.this, "Error ", Toast.LENGTH_SHORT).show();
                    }
                });
            }



        }
        else
            {
                dialog.dismiss();
            }

    }
    @Override
    public void onCancel()
    {
        dialog.dismiss();
    }
    @Override
    public void onError(FacebookException error)
    {
        dialog.dismiss();
        Toast.makeText(this, "Error "+error.getMessage(), Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        if (requestCode==RC_GOOGLE_SIGNIN&&data!=null)
        {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess())
            {

                account = result.getSignInAccount();
                if (account!=null)
                {

                    getUserDataFromGoogle(account);

                }

            }

        }
    }

    private void getUserDataFromGoogle(GoogleSignInAccount account)
    {
        dialog.show();
        this.account=account;

            if (account!=null)
            {
                final User userData= new User(account.getDisplayName().toString(),account.getEmail().toString(),account.getPhotoUrl().toString());
                userData.setUserId(account.getId());
                if (userData!=null)
                {


                    Retrofit retrofit = setUpRetrofit(getString(R.string.gmail_url));
                    Service client = retrofit.create(Service.class);
                    Map<String,String> map = new HashMap<>();
                    map.put("user_username",userData.getUserId());
                    map.put("user_name",userData.getUserName());
                    map.put("photo_link",userData.getUserPhotoUrl());
                    map.put("user_email",userData.getUserEmail());
                    Call<User> UserData = client.UploadUserDataWithGoogle(map);
                    UserData.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            final User UserData = response.body();
                            if (UserData!=null)
                            {

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(ChooserSingin.this,HomeActivity.class);
                                        intent.putExtra("userData",UserData);
                                        startActivity(intent);
                                    }
                                },2000);
                            }
                            else
                            {
                                Auth.GoogleSignInApi.signOut(apiClient);
                                apiClient.disconnect();
                                dialog.dismiss();
                                Toast.makeText(ChooserSingin.this, "no data", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }else
                    {
                        dialog.dismiss();

                    }



                }else
                    {
                        apiClient.disconnect();
                        dialog.dismiss();

                    }





    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Toast.makeText(this, "Error "+connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        getUserData(profile);
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        profileTracker.stopTracking();
        tokenTracker.stopTracking();

    }
    private Retrofit setUpRetrofit(String baseUrl)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
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
