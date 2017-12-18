package com.example.omd.library.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.R;
import com.example.omd.library.Services.GoogleUserData.ModelDataImp;
import com.example.omd.library.Services.NetworkConnection;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ChooserSingin extends AppCompatActivity implements View.OnClickListener,FacebookCallback<LoginResult>,GoogleApiClient.OnConnectionFailedListener{

    private ShimmerTextView shimmerTextView;
    private Button singin_with_account_btn;
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

    }

    private void setUpProgressdialog() {
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
    private boolean CheckedUserLogin()
    {
        if (apiClient!=null&&apiClient.isConnected())
        {
            return true;
        }
        return false;
    }

    private void setUpShimmer()
    {
        Shimmer shimmer = new Shimmer();
        shimmer.setDuration(5000).setDirection(Shimmer.ANIMATION_DIRECTION_RTL);
        shimmer.start(shimmerTextView);
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
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

       /* Profile profile = Profile.getCurrentProfile();
        Toast.makeText(this, "profile"+profile.getName(), Toast.LENGTH_SHORT).show();*/
       Bundle bundle = new Bundle();
       bundle.putString("fields","email, name, id");
       if (loginResult.getAccessToken()!=null)
       {
           dialog.show();
           GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
               @Override
               public void onCompleted(JSONObject object, GraphResponse response) {
                   dialog.dismiss();
                   getUserData(object);
                   navigateToHomeActivity();


               }
           });
           request.setParameters(bundle);
           request.executeAsync();

       }


    }

    private void getUserData(JSONObject object) {
        try {

            if (object!=null)
            {
                String userID = object.getString("id");
                String userName = object.getString("name");
                String userPhoto = "https://graph.facebook.com/" + userID+"/picture?type=large";
                NormalUserData userData = new NormalUserData(userName,"","","","","",userPhoto);
                userData.setUserId(userID);
                if (userData!=null)
                {
                    modelDataImp.setUserData(userData);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
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
        Toast.makeText(this, "Error "+"check network connection", Toast.LENGTH_LONG).show();
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
        if (apiClient!=null &&apiClient.isConnected())
        {
            if (account!=null)
            {
                NormalUserData userData= new NormalUserData(account.getDisplayName(),account.getEmail(),"","","","",account.getPhotoUrl().toString());
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
        else
            {
                apiClient.disconnect();
                Toast.makeText(this, "Check network connection", Toast.LENGTH_LONG).show();

            }



    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Toast.makeText(this, "Error "+connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onStart() {
        super.onStart();
        isLoggedin = CheckedUserLogin();
        isConnected = connection.CheckConnection();


        if (isConnected==true)
        {
            if (isLoggedin==true)
            {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (account!=null)
                        {
                            NormalUserData userData= new NormalUserData(account.getDisplayName(),account.getEmail(),"","","","",account.getPhotoUrl().toString());
                            if (userData!=null)
                            {
                                modelDataImp.setUserData(userData);
                                navigateToHomeActivity();

                            }
                        }else
                        {
                            apiClient.disconnect();
                        }


                    }
                });
                thread.start();
            }





        }else
        {
            dialog.dismiss();
            Toast.makeText(this, "Check network connection", Toast.LENGTH_LONG).show();
        }
    }
}
