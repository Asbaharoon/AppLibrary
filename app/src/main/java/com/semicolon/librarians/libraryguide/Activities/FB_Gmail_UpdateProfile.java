package com.semicolon.librarians.libraryguide.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.intrusoft.squint.DiagonalView;
import com.semicolon.librarians.libraryguide.MVP.UpdateFB_GM_Signin_Profile_MVP.Presenter;
import com.semicolon.librarians.libraryguide.MVP.UpdateFB_GM_Signin_Profile_MVP.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.UpdateFB_GM_Signin_Profile_MVP.ViewData;
import com.semicolon.librarians.libraryguide.Models.CountriesModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Preferences;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.hdodenhof.circleimageview.CircleImageView;
import me.anwarshahriar.calligrapher.Calligrapher;

public class FB_Gmail_UpdateProfile extends AppCompatActivity implements View.OnClickListener, ViewData,GoogleApiClient.OnConnectionFailedListener {

    private CircleImageView userImage;
    private TextView userId, userName, userEmail, userPhone, userCountry;
    private final int country_request_code = 01;
    private final int phone_request_user = 02;
    private String country_id = "";
    private NormalUserData normalUserData;
    private DiagonalView diagonalView;
    private Presenter presenter;
    private Button updBtn;
    private ProgressDialog progressDialog;
    private LocationManager manager;
    private GoogleApiClient apiClient;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private String userPhoto;
    private String user_id;
    private String user_name;
    private String user_phone;
    private String user_email;
    private String user_country;
    private String token;
    private com.semicolon.librarians.libraryguide.MVP.Update_UserStatue_MVP.Presenter pr;
    private NormalUserData userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_gmail_update_profile);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, Tags.font, true);
        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        token = FirebaseInstanceId.getInstance().getToken();
        presenter = new PresenterImp(this, this);
        iniView();
        getDataFromIntent();
        CreateProgressDialog();

        apiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API).enableAutoManage(this,this).build();
        apiClient.connect();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("userData")) {
                normalUserData = (NormalUserData) intent.getSerializableExtra("userData");
                Log.e("emaillll", "" + normalUserData.getUserEmail());
                UpdateUI(normalUserData);
            }

        }
    }

    private void UpdateUI(NormalUserData normalUserData) {
        if (!TextUtils.isEmpty(normalUserData.getUserId()) || normalUserData.getUserId() != null) {
            userId.setText(normalUserData.getUserId());
        }
        if (!TextUtils.isEmpty(normalUserData.getUserPhoto()) || normalUserData.getUserPhoto() != null) {
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    userImage.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(this).load(Uri.parse(normalUserData.getUserPhoto())).placeholder(R.drawable.user_profile).into(target);
        }
        if (!TextUtils.isEmpty(normalUserData.getUserEmail()) || normalUserData.getUserEmail() != null) {
            userEmail.setText(normalUserData.getUserEmail());
        }
        if (!TextUtils.isEmpty(normalUserData.getUserPhone()) || normalUserData.getUserPhone() != null) {
            userPhone.setText(normalUserData.getUserPhone());
        }

        if (!TextUtils.isEmpty(normalUserData.getUserCountry()) || normalUserData.getUserCountry() != null) {
            userCountry.setText(normalUserData.getUserCountry());
        }
        if (normalUserData.getUserName() != null || !TextUtils.isEmpty(normalUserData.getUserName())) {
            userName.setText(normalUserData.getUserName());
        }
    }

    private void iniView() {
        updBtn = (Button) findViewById(R.id.user_updBtn);
        diagonalView = (DiagonalView) findViewById(R.id.secondImage);
        userImage = (CircleImageView) findViewById(R.id.userImage);
        userId = (TextView) findViewById(R.id.userId);
        userName = (TextView) findViewById(R.id.userName);
        userEmail = (TextView) findViewById(R.id.userEmail);
        userPhone = (TextView) findViewById(R.id.userPhone);
        userCountry = (TextView) findViewById(R.id.userCountry);
        userCountry.setOnClickListener(this);
        userPhone.setOnClickListener(this);
        updBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_updBtn:
                updateData();
                break;
            case R.id.userPhone:
                getPhone();
                break;

            case R.id.userCountry:
                getCountry();
                break;
        }
    }

    private void updateData() {
        userPhoto    = normalUserData.getUserPhoto();
        user_id      = userId.getText().toString();
        user_name    = userName.getText().toString();
        user_phone   = userPhone.getText().toString();
        user_email   = userEmail.getText().toString();
        user_country = userCountry.getText().toString();

        presenter.UpdateUserData(userPhoto,user_id,user_name,user_email,user_phone,user_country);


    }





    private void getCountry() {
        Intent intent = new Intent(this, Activity_Search_Results.class);
        intent.putExtra("searchType","country");
       startActivityForResult(intent,country_request_code);
    }

    private void getPhone() {
        Intent intent = new Intent(this, Activity_PhoneNumber.class);
        startActivityForResult(intent,phone_request_user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==phone_request_user&&resultCode==RESULT_OK&&data!=null)
        {
            String phoneNumber =data.getStringExtra("phone_number");
            userPhone.setText(phoneNumber);

        }else if (requestCode==country_request_code&&resultCode==RESULT_OK&&data!=null)
        {
            CountriesModel countriesModel = (CountriesModel) data.getSerializableExtra("country_data");
            country_id = countriesModel.getCountry_id();
            userCountry.setText(countriesModel.getCountry_title()+"-"+countriesModel.getCountry_flag());

        }
    }

    @Override
    public void setNormalUserPhone_Error() {
        Toast.makeText(this, R.string.phone_empty, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setNormalUserEmail_Error() {
        Toast.makeText(this, R.string.email_empty, Toast.LENGTH_LONG).show();

    }

    @Override
    public void setNormalUser_invalidEmail_Error() {
        Toast.makeText(this, getString(R.string.invalidEmail), Toast.LENGTH_LONG).show();

    }

    @Override
    public void setNormalUserCountry_Error() {
        Toast.makeText(this, R.string.country_empty, Toast.LENGTH_LONG).show();

    }


    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void onNormalUserDataSuccess(final NormalUserData normalUserData) {
        Log.e("fffffffff","fff");
        final NormalUserData userData = normalUserData;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Preferences preferences = new Preferences(FB_Gmail_UpdateProfile.this);
                preferences.Create_SharedPreference_User(normalUserData);

                Intent intent = new Intent(FB_Gmail_UpdateProfile.this,HomeActivity.class);
                intent.putExtra("userData",normalUserData);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Preferences pref = new Preferences(FB_Gmail_UpdateProfile.this);
                pref.Session("loggedin");
                startActivity(intent);
                progressDialog.dismiss();
            }
        },1000);

    }

    @Override
    public void onFailed(String error) {

        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        progressDialog.dismiss();

    }

    private void CreateProgressDialog()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.wait_update_profile));
        ProgressBar progressBar = new ProgressBar(this);
        Drawable drawable =progressBar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ActivityCompat.getColor(this,R.color.centercolor), PorterDuff.Mode.SRC_IN);
        progressDialog.setIndeterminateDrawable(drawable);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (apiClient!=null)
        {
            if (apiClient.isConnected())
            {
                apiClient.clearDefaultAccountAndReconnect();
                apiClient.disconnect();
            }
        }
        loginManager.unregisterCallback(callbackManager);
        loginManager.logOut();


    }
}
