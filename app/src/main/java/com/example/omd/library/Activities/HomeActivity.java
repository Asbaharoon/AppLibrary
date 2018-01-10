package com.example.omd.library.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omd.library.Fragments.Home_Fragment;
import com.example.omd.library.Fragments.News_Fragment;
import com.example.omd.library.Fragments.Settings_Fragment;
import com.example.omd.library.Models.User;
import com.example.omd.library.R;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.OnConnectionFailedListener{

    private Toolbar toolbar;
    private ArcNavigationView arcNavigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private CircleImageView im_userImage;
    private TextView tv_userName,tv_userEmail;
    private GoogleApiClient apiClient;
    private AlertDialog.Builder alerDialog;
    private ProgressDialog progressDialog;
    private CallbackManager callbackManager;
    private LoginManager manager;
    private static User user_Data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_home);
        manager = LoginManager.getInstance();
        initView();
        setUpDrawer();
        setUpSigninWithGoogle();
        setUpAlertDialog();
        setUpProgressDialog();
        getSupportFragmentManager().beginTransaction().add(R.id.home_fragmentsContainer,new Home_Fragment()).commit();
        getDataFrom_Intent();
       // CheckuserType();
    }

    /*private void CheckuserType() {
        final LovelyCustomDialog dialog = new LovelyCustomDialog(HomeActivity.this)
                .setCancelable(false)
                .setIcon(R.drawable.commession_icon)
                .setIconTintColor(ActivityCompat.getColor(HomeActivity.this,R.color.base))
                .setTopColor(ActivityCompat.getColor(HomeActivity.this,R.color.colorPrimary));
        if (user_Data.getUserCountry()==null)
        {



            final View view = getLayoutInflater().inflate(R.layout.alertdialog_choose_usertype,null);
            final BubbleImageView bubbleImageView = (BubbleImageView) view.findViewById(R.id.bubble_userImage);
            Button confirm = (Button) view.findViewById(R.id.confirmBtn);
            Picasso.with(HomeActivity.this).load(user_Data.getUserPhotoUrl().toString()).into(bubbleImageView);
            final RadioButton rb_normaluser = (RadioButton) view.findViewById(R.id.rb_normaluser);
            final RadioButton rb_library = (RadioButton) view.findViewById(R.id.rb_library);
            final RadioButton rb_publisher = (RadioButton) view.findViewById(R.id.rb_publisher);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (rb_normaluser.isChecked())
                    {
                        String userType = rb_normaluser.getText().toString();
                        Toast.makeText(HomeActivity.this, "user is "+userType, Toast.LENGTH_SHORT).show();

                    }
                    else if (rb_library.isChecked())
                    {
                        String userType = rb_library.getText().toString();
                        Toast.makeText(HomeActivity.this, "user is "+userType, Toast.LENGTH_SHORT).show();

                    }
                    else if (rb_publisher.isChecked())
                    {
                        String userType = rb_library.getText().toString();
                        Toast.makeText(HomeActivity.this, "user is "+userType, Toast.LENGTH_SHORT).show();

                    }

                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    dialog.create();
                    dialog.setView(view);
                    dialog.show();

                }
            },1000);


        }
        else
        {
            dialog.dismiss();
        }
    }*/

    private void getDataFrom_Intent() {
        Intent intent = getIntent();
        if (intent.hasExtra("userData"))
        {
            User UserData = (User) intent.getSerializableExtra("userData");

            if (UserData!=null)
            {
                HomeActivity.user_Data= UserData;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.with(HomeActivity.this).load(Uri.parse(user_Data.getUserPhotoUrl())).fit().into(im_userImage);
                        tv_userName.setText(user_Data.getUserName().toString().isEmpty()?"":user_Data.getUserName().toString());
                        tv_userEmail.setText(user_Data.getUserEmail()==null?"":user_Data.getUserEmail().toString());

                    }
                },500);
            }
        }
    }

    private void setUpProgressDialog()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sign Out.....");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        ProgressBar progressBar = new ProgressBar(this);
        Drawable drawable = progressBar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.centercolor), PorterDuff.Mode.SRC_IN);
        progressDialog.setIndeterminateDrawable(drawable);
    }
    private void initView()
    {
        alerDialog = new AlertDialog.Builder(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /////////////////////////////////////////////////////////////
       /* presenterImp =new Login_PresenterImp(this,this);
        presenterImp.getUserData();*/
        /////////////////////////////////////////////////////////////
       /* pager = (ViewPager) findViewById(R.id.pager);*/
        /////////////////////////////////////////////////////////////
        arcNavigationView = (ArcNavigationView) findViewById(R.id.arcDrawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //////////////////////////////////////////////////////////////
       // navBar = (AHBottomNavigation) findViewById(R.id.bottom_navBar);

        //////////////////////////////////////////////////////////////

        View headerView =arcNavigationView.getHeaderView(0);

        im_userImage = (CircleImageView) headerView.findViewById(R.id.userPhoto);
        tv_userName  = (TextView) headerView.findViewById(R.id.userName);
        tv_userEmail = (TextView) headerView.findViewById(R.id.userEmail);


    }
    private void setUpSigninWithGoogle()
    {
        apiClient = new GoogleApiClient.Builder(HomeActivity.this).addApi(Auth.GOOGLE_SIGN_IN_API).build();
        apiClient.connect();

    }

    private void setUpDrawer()
    {
        mToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        arcNavigationView.setNavigationItemSelectedListener(this);


    }
    private void setUpAlertDialog()
    {
        alerDialog.setTitle("Sign out ?").setPositiveButton("Sign out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressDialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SignOut();
                        progressDialog.dismiss();
                    }
                },4000);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog alertDialog = alerDialog.create();
                alertDialog.dismiss();
            }
        }).create();
    }
    private void SignOut()
    {
        Auth.GoogleSignInApi.signOut(apiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                apiClient.disconnect();

            }
        });
        manager.unregisterCallback(callbackManager);
        manager.logOut();
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item))
        {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         if (item.getItemId()==R.id.home)
        {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
            {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer,new Home_Fragment()).commit();
                    }
                },500);
                return true;
            }
        }
        else if (item.getItemId()==R.id.chat)
        {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
            {
                drawerLayout.closeDrawer(GravityCompat.START);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer,new Home_Fragment()).commit();
                    }
                },500);

                return true;
            }
        }
        else if (item.getItemId()==R.id.nearby)
        {
            Toast.makeText(this, "nearby", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
            {
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        }
        else if (item.getItemId()==R.id.news)
        {
            Toast.makeText(this, "news", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
            {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer,new News_Fragment()).commit();
                    }
                },500);

                return true;
            }
        }
        else if (item.getItemId()==R.id.settings)
        {

            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
            {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer,new Settings_Fragment()).commit();
                    }
                },500);
                return true;
            }
        }
        else if (item.getItemId()==R.id.logout)
        {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
            {
                drawerLayout.closeDrawer(GravityCompat.START);
                alerDialog.show();

                return true;
            }
        }
        return false;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(Gravity.START))
        {
            drawerLayout.closeDrawer(Gravity.START);

        }
        else
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                }
            },500);

        }


    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
