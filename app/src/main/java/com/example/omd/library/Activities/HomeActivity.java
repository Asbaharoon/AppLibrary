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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
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

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.example.omd.library.Adapters.ViewPagerAdapter;
import com.example.omd.library.Fragments.Chat_Fragment;
import com.example.omd.library.Fragments.Library_Fragment;
import com.example.omd.library.Fragments.Publisher_Fragment;
import com.example.omd.library.Fragments.University_Fragment;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.R;
import com.example.omd.library.Services.GoogleUserData.PresenterImp;
import com.example.omd.library.Services.GoogleUserData.ViewData;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ViewData,GoogleApiClient.OnConnectionFailedListener{

    private Toolbar toolbar;
    private ArcNavigationView arcNavigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private AHBottomNavigation navBar;
    private ViewPager pager;
    private List<Fragment> fragmentList;
    private PresenterImp presenterImp;
    private CircleImageView im_userImage;
    private TextView tv_userName,tv_userEmail;
    private GoogleApiClient apiClient;
    private AlertDialog.Builder alerDialog;
    private ProgressDialog progressDialog;
    private CallbackManager callbackManager;
    private LoginManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_home);
        manager = LoginManager.getInstance();
        initView();
        setUpDrawer();
        setUpnavBar();
        setUpViewPager();
        setUpSigninWithGoogle();
        setUpAlertDialog();
        setUpProgressDialog();






    }

    private void setUpProgressDialog()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sign Out.....");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        ProgressBar progressBar = new ProgressBar(this);
        Drawable drawable = progressBar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        progressDialog.setIndeterminateDrawable(drawable);
    }
    private void initView()
    {
        alerDialog = new AlertDialog.Builder(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /////////////////////////////////////////////////////////////
        presenterImp =new PresenterImp(this,this);
        presenterImp.getUserData();
        /////////////////////////////////////////////////////////////
        pager = (ViewPager) findViewById(R.id.pager);
        /////////////////////////////////////////////////////////////
        arcNavigationView = (ArcNavigationView) findViewById(R.id.arcDrawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //////////////////////////////////////////////////////////////
        navBar = (AHBottomNavigation) findViewById(R.id.bottom_navBar);

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
    private void setUpViewPager()
    {
        fragmentList = new ArrayList<>();
        fragmentList.add(new Chat_Fragment());
        fragmentList.add(new University_Fragment());
        fragmentList.add(new Library_Fragment());
        fragmentList.add(new Publisher_Fragment());

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(fragmentList);
        pager.setAdapter(adapter);
        pager.beginFakeDrag();



    }
    private void setUpnavBar()
    {
        AHBottomNavigationAdapter adapter = new AHBottomNavigationAdapter(this,R.menu.navbar_menu);
        navBar.setDefaultBackgroundColor(ContextCompat.getColor(this,R.color.base));
        navBar.setInactiveColor(ContextCompat.getColor(this,R.color.dark_gray));
        navBar.setAccentColor(ContextCompat.getColor(this, R.color.darkblue));
        adapter.setupWithBottomNavigation(navBar);
        navBar.setCurrentItem(0);
        navBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        if (navBar.getCurrentItem()==0)
        {
            pager.setCurrentItem(0);
        }
        navBar.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (position==0)
                {
                    pager.setCurrentItem(0);
                    navBar.setCurrentItem(position,false);
                    return true;
                }
                else if (position==1)
                {

                    pager.setCurrentItem(1);

                    navBar.setCurrentItem(position,false);
                    return true;

                }
                else if (position==2)
                {
                    pager.setCurrentItem(2);

                    navBar.setCurrentItem(position,false);
                    return true;

                }
                else if (position==3)
                {

                    pager.setCurrentItem(3);
                    navBar.setCurrentItem(position,false);
                    return true;

                }
                return false;
            }
        });

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
                },3000);

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
                manager.unregisterCallback(callbackManager);
                manager.logOut();
                finish();
            }
        });
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
            Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
            {
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        }
        else if (item.getItemId()==R.id.chat)
        {
            Toast.makeText(this, "chat", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
            {
                drawerLayout.closeDrawer(GravityCompat.START);
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
                return true;
            }
        }
        else if (item.getItemId()==R.id.settings)
        {
            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
            {
                drawerLayout.closeDrawer(GravityCompat.START);
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
    public void OnSuccess(final NormalUserData userData) {
        if (userData!=null)
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Picasso.with(HomeActivity.this).load(Uri.parse(userData.getUserPhoto())).fit().into(im_userImage);
                    tv_userName.setText(userData.getUserName().toString().isEmpty()?"":userData.getUserName().toString());
                    tv_userEmail.setText(userData.getUserEmail().toString().isEmpty()?"":userData.getUserEmail().toString());

                }
            },5000);

        }else
            {
                NavigateToChooserActivity();
            }
    }

    private void NavigateToChooserActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                manager.unregisterCallback(callbackManager);
                manager.logOut();
                apiClient.disconnect();
                Auth.GoogleSignInApi.signOut(apiClient);
                finish();
            }
        },5000);




    }

    @Override
    public void OnFailed(String message) {
        Toast.makeText(this, "Error "+message, Toast.LENGTH_LONG).show();
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
            },1500);

        }


    }

}
