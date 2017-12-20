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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.R;
import com.example.omd.library.Services.GoogleUserData.ModelDataImp;
import com.example.omd.library.Services.NetworkConnection;
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
    private ModelDataImp modelDataImp;
    private boolean isLoggedin;
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
    private void initView()
    {
        modelDataImp = new ModelDataImp(this);

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
        facebook_lognBtn.setReadPermissions(Arrays.asList("email","public_profile"));
        facebook_lognBtn.registerCallback(callbackManager,this);

        ////////////////////////////////////////////////////////////////
        googleSininBtn = (SignInButton) findViewById(R.id.googleSigninBtn);
        googleSininBtn.setOnClickListener(this);

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        apiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(ChooserSingin.this,ChooserSingin.this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();



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
                dialog.show();
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
           dialog.dismiss();

       }
       else{
           dialog.dismiss();
       }

        tokenTracker.startTracking();
       profileTracker.startTracking();
    }

    private void getUserData(Profile profile) {

        if (profile!=null)
        {
            dialog.show();
            String userID = profile.getId();
            String userName = profile.getName();
            String userPhoto = "https://graph.facebook.com/" + userID+"/picture?type=large";
            NormalUserData userData = new NormalUserData(userName,"","","","","",userPhoto);
            userData.setUserId(userID);
            if (userData!=null)
            {
                modelDataImp.setUserData(userData);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        navigateToHomeActivity();
                    }
                },2000);



            }
            else
                {
                    dialog.dismiss();

                }

        }else
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

    private void navigateToHomeActivity() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

    private void getUserDataFromGoogle(GoogleSignInAccount account) {
        this.account=account;

            if (account!=null)
            {
                final NormalUserData userData= new NormalUserData(account.getDisplayName(),account.getEmail(),"","","","",account.getPhotoUrl().toString());
                userData.setUserId(account.getId());
                if (userData!=null)
                {

                    modelDataImp.setUserData(userData);
                    navigateToHomeActivity();


                }else
                    {
                        apiClient.disconnect();

                    }
            }
            else
                {

                    apiClient.disconnect();

                }





    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Toast.makeText(this, "Error "+connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
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
}
