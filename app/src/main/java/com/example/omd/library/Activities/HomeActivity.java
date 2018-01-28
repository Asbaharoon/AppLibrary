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
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omd.library.Fragments.Ads_Fragment;
import com.example.omd.library.Fragments.Company_Fragment;
import com.example.omd.library.Fragments.Home_Fragment;
import com.example.omd.library.Fragments.Jobs_Fragment;
import com.example.omd.library.Fragments.News_Fragment;
import com.example.omd.library.Fragments.Settings_Fragment;
import com.example.omd.library.Models.CompanyModel;
import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.Models.UniversityModel;
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
    private TextView tv_userName, tv_userEmail;

    private GoogleApiClient apiClient;

    private AlertDialog.Builder alerDialog;
    private ProgressDialog progressDialog;
    private CallbackManager callbackManager;
    private LoginManager manager;
    private NormalUserData user_Data = null;
    private PublisherModel publisher_Model = null;
    private LibraryModel library_Model = null;
    private UniversityModel university_Model = null;
    private CompanyModel company_Model = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        callbackManager = CallbackManager.Factory.create();
        manager = LoginManager.getInstance();
        initView();

        setUpDrawer();
        setUpSigninWithGoogle();
        setUpAlertDialog();
        setUpProgressDialog();
        getSupportFragmentManager().beginTransaction().add(R.id.home_fragmentsContainer, new Home_Fragment(),"home_fragment").addToBackStack("home_fragment").commit();
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
        if (intent.hasExtra("userData")) {
            final NormalUserData UserData = (NormalUserData) intent.getSerializableExtra("userData");

            if (UserData != null) {
                user_Data = UserData;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UpdateUI(UserData.getUserPhoto(), UserData.getUserName(), UserData.getUserEmail());
                    }
                }, 500);
            }
        } else if (intent.hasExtra("publisherData")) {

            final PublisherModel publisherModel = (PublisherModel) intent.getSerializableExtra("publisherData");
            if (publisherModel != null) {
                publisher_Model = publisherModel;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UpdateUI("", publisherModel.getPub_name(), publisherModel.getPub_email());
                    }
                }, 500);
            }


        } else if (intent.hasExtra("libraryData")) {
            final LibraryModel libraryModel = (LibraryModel) intent.getSerializableExtra("libraryData");

            if (libraryModel != null) {
                library_Model = libraryModel;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UpdateUI("", libraryModel.getLib_name(), libraryModel.getLib_email());
                    }
                }, 500);

            }


        } else if (intent.hasExtra("universityData")) {
            final UniversityModel universityModel = (UniversityModel) intent.getSerializableExtra("universityData");
            if (universityModel != null) {
                university_Model = universityModel;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UpdateUI("", universityModel.getUni_name(), universityModel.getUni_email());
                    }
                }, 500);
            }


        } else if (intent.hasExtra("companyData")) {
            final CompanyModel companyModel = (CompanyModel) intent.getSerializableExtra("companyData");

            if (companyModel != null) {
                company_Model = companyModel;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UpdateUI("", companyModel.getComp_name(), companyModel.getComp_email());
                    }
                }, 500);
            }


        }
    }

    private void initView() {
        alerDialog = new AlertDialog.Builder(this);

        View home_View = findViewById(R.id.HomeContent);
        toolbar = (Toolbar) home_View.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);/////////////////////////////////////////////////////////////
        arcNavigationView = (ArcNavigationView) findViewById(R.id.arcDrawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //////////////////////////////////////////////////////////////
        View headerView = arcNavigationView.getHeaderView(0);

        im_userImage = (CircleImageView) headerView.findViewById(R.id.userPhoto);
        tv_userName = (TextView) headerView.findViewById(R.id.userName);
        tv_userEmail = (TextView) headerView.findViewById(R.id.userEmail);



        /////////////////////////////////////////////////////////////


    }




    private void UpdateUI(String imageUrl, String name, String email) {
        if (TextUtils.isEmpty(imageUrl) || imageUrl == null) {
            Picasso.with(HomeActivity.this).load(R.drawable.user_profile).fit().into(im_userImage);
        } else {
            Picasso.with(HomeActivity.this).load(Uri.parse(imageUrl)).fit().into(im_userImage);

        }
        tv_userName.setText(TextUtils.isEmpty(name) || name == null ? "" : name.toString());
        tv_userEmail.setText(email == null || TextUtils.isEmpty(email) ? "" : email);

    }

    private void setUpSigninWithGoogle() {
        apiClient = new GoogleApiClient.Builder(HomeActivity.this).addApi(Auth.GOOGLE_SIGN_IN_API).build();
        apiClient.connect();

    }

    private void setUpDrawer() {
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        arcNavigationView.setNavigationItemSelectedListener(this);


    }

    private void setUpAlertDialog() {
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
                }, 5000);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog alertDialog = alerDialog.create();
                alertDialog.dismiss();
            }
        }).create();

    }

    private void SignOut() {
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

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer, new Home_Fragment(),"home_fragment").addToBackStack("home_fragment").commit();
                    }
                }, 500);
                return true;
            }
        } else if (item.getItemId() == R.id.profile) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 500);

                return true;
            }
        } else if (item.getItemId() == R.id.nearby) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (user_Data != null) {
                            Intent intent = new Intent(HomeActivity.this, NearbyActivity.class);
                            intent.putExtra("userData", user_Data);
                            startActivity(intent);
                        } else if (publisher_Model != null) {
                            Intent intent = new Intent(HomeActivity.this, NearbyActivity.class);
                            intent.putExtra("publisherData", publisher_Model);
                            startActivity(intent);
                        } else if (university_Model != null) {
                            Intent intent = new Intent(HomeActivity.this, NearbyActivity.class);
                            intent.putExtra("universityData", university_Model);
                            startActivity(intent);
                        } else if (library_Model != null) {
                            Intent intent = new Intent(HomeActivity.this, NearbyActivity.class);
                            intent.putExtra("libraryData", library_Model);
                            startActivity(intent);
                        } else if (company_Model != null) {
                            Intent intent = new Intent(HomeActivity.this, NearbyActivity.class);
                            intent.putExtra("companyData", company_Model);
                            startActivity(intent);
                        }

                    }
                }, 500);

                return true;
            }
        } else if (item.getItemId() == R.id.news) {
            Toast.makeText(this, "news", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer, new News_Fragment(),"news_fragment").addToBackStack("news_fragment").commit();
                    }
                }, 500);

                return true;
            }


        }
        else if (item.getItemId() == R.id.jobs) {
            Toast.makeText(this, "jobs", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer, new Jobs_Fragment(),"jobs_fragment").addToBackStack("jobs_fragment").commit();
                    }
                }, 500);

                return true;
            }


        }
        else if (item.getItemId() == R.id.company) {
            Toast.makeText(this, "company", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer, new Company_Fragment(),"company_fragment").addToBackStack("company_fragment").commit();
                    }
                }, 500);

                return true;
            }


        }
        else if (item.getItemId() == R.id.ads) {
            Toast.makeText(this, "ads", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer, new Ads_Fragment(),"ads_fragment").addToBackStack("ads_fragment").commit();
                    }
                }, 500);

                return true;
            }


        }
        else if (item.getItemId() == R.id.settings) {

            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer, new Settings_Fragment()).commit();
                    }
                }, 500);
                return true;
            }
        } else if (item.getItemId() == R.id.logout) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                alerDialog.show();

                return true;
            }
        }
        return false;
    }



    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sign Out.....");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        ProgressBar progressBar = new ProgressBar(this);
        Drawable drawable = progressBar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this, R.color.centercolor), PorterDuff.Mode.SRC_IN);
        progressDialog.setIndeterminateDrawable(drawable);
    }
    @Override
    public void onBackPressed() {



        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                    }
                }, 500);







            }

    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public Toolbar getToolBar()
    {
        if (toolbar!=null){
        return toolbar;

        }
        return null;
    }
}

