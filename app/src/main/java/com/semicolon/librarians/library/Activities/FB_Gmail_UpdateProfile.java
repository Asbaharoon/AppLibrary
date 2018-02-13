package com.semicolon.librarians.library.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.intrusoft.squint.DiagonalView;
import com.semicolon.librarians.library.MVP.UpdateFB_GM_Signin_Profile_MVP.Presenter;
import com.semicolon.librarians.library.MVP.UpdateFB_GM_Signin_Profile_MVP.PresenterImp;
import com.semicolon.librarians.library.MVP.UpdateFB_GM_Signin_Profile_MVP.ViewData;
import com.semicolon.librarians.library.Models.CountriesModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.R;
import com.semicolon.librarians.library.Services.Preferences;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.hdodenhof.circleimageview.CircleImageView;

public class FB_Gmail_UpdateProfile extends AppCompatActivity implements View.OnClickListener,ViewData{

    private CircleImageView userImage;
    private TextView userId,userName,userEmail,userPhone,userCountry;
    private final int country_request_code=01;
    private final int phone_request_user =02;
    private String country_id="";
    private NormalUserData normalUserData;
    private DiagonalView diagonalView;
    private Presenter presenter;
    private Button updBtn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_gmail_update_profile);
        presenter = new PresenterImp(this,this);
        iniView();
        getDataFromIntent();
        CreateProgressDialog();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("userData"))
            {
                normalUserData = (NormalUserData) intent.getSerializableExtra("userData");
                Log.e("emaillll",""+normalUserData.getUserEmail());
                UpdateUI(normalUserData);
            }
        }
    }

    private void UpdateUI(NormalUserData normalUserData) {
        if (!TextUtils.isEmpty(normalUserData.getUserId())||normalUserData.getUserId()!=null)
        {
            userId.setText(normalUserData.getUserId());
        }
        if (!TextUtils.isEmpty(normalUserData.getUserPhoto())||normalUserData.getUserPhoto()!=null)
        {
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
        if (!TextUtils.isEmpty(normalUserData.getUserEmail())||normalUserData.getUserEmail()!=null)
        {
            userEmail.setText(normalUserData.getUserEmail());
        }
        if (!TextUtils.isEmpty(normalUserData.getUserPhone())||normalUserData.getUserPhone()!=null)
        {
            userPhone.setText(normalUserData.getUserPhone());
        }

        if (!TextUtils.isEmpty(normalUserData.getUserCountry())||normalUserData.getUserCountry()!=null)
        {
            userCountry.setText(normalUserData.getUserCountry());
        }
        if (normalUserData.getUserName()!=null||!TextUtils.isEmpty(normalUserData.getUserName()))
        {
            userName.setText(normalUserData.getUserName());
        }
    }

    private void iniView() {
        updBtn      = (Button) findViewById(R.id.user_updBtn);
        diagonalView= (DiagonalView) findViewById(R.id.secondImage);
        userImage   = (CircleImageView) findViewById(R.id.userImage);
        userId      = (TextView) findViewById(R.id.userId);
        userName    = (TextView) findViewById(R.id.userName);
        userEmail   = (TextView) findViewById(R.id.userEmail);
        userPhone   = (TextView) findViewById(R.id.userPhone);
        userCountry = (TextView) findViewById(R.id.userCountry);
        userCountry.setOnClickListener(this);
        userPhone.setOnClickListener(this);
        updBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.user_updBtn:
                updataData();
                break;
            case R.id.userPhone:
                getPhone();
                break;
                
            case R.id.userCountry:
                getCountry();
                break;
        }
    }

    private void updataData() {
        String userPhoto    = normalUserData.getUserPhoto();
        String user_id      = userId.getText().toString();
        String user_name    = userName.getText().toString();
        String user_phone   = userPhone.getText().toString();
        String user_email   = userEmail.getText().toString();
        String user_country = userCountry.getText().toString();

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
        Toast.makeText(this, "phone number shouldn't be empty ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setNormalUserEmail_Error() {
        Toast.makeText(this, "email address shouldn't be empty ", Toast.LENGTH_LONG).show();

    }

    @Override
    public void setNormalUser_invalidEmail_Error() {
        Toast.makeText(this, "invalid email address ", Toast.LENGTH_LONG).show();

    }

    @Override
    public void setNormalUserCountry_Error() {
        Toast.makeText(this, "country shouldn't be empty ", Toast.LENGTH_LONG).show();

    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void onNormalUserDataSuccess(NormalUserData normalUserData) {
        final NormalUserData userData = normalUserData;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Preferences preferences = new Preferences(FB_Gmail_UpdateProfile.this);
                preferences.Create_SharedPreference_User(userData);

                Intent intent = new Intent(FB_Gmail_UpdateProfile.this,HomeActivity.class);
                intent.putExtra("userData",userData);
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
        progressDialog.setMessage("Wait for updating profile ");
        ProgressBar progressBar = new ProgressBar(this);
        Drawable drawable =progressBar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ActivityCompat.getColor(this,R.color.centercolor), PorterDuff.Mode.SRC_IN);
        progressDialog.setIndeterminateDrawable(drawable);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }
}
