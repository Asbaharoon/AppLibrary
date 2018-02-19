package com.semicolon.librarians.libraryguide.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.semicolon.librarians.libraryguide.Fragments.Ads_Fragment;
import com.semicolon.librarians.libraryguide.Fragments.Company_Fragment;
import com.semicolon.librarians.libraryguide.Fragments.Home_Fragment;
import com.semicolon.librarians.libraryguide.Fragments.Jobs_Fragment;
import com.semicolon.librarians.libraryguide.Fragments.News_Fragment;
import com.semicolon.librarians.libraryguide.Fragments.Settings_Fragment;
import com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.Presenter;
import com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.ViewData;
import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.Location_Model;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.RefreshToken;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Locationservices_Update;
import com.semicolon.librarians.libraryguide.Services.NetworkConnection;
import com.semicolon.librarians.libraryguide.Services.Preferences;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;
import me.anwarshahriar.calligrapher.Calligrapher;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, com.semicolon.librarians.libraryguide.MVP.Create_ChatRoom_MVP.ViewData,ViewData{

    private Toolbar toolbar;
    //private ArcNavigationView arcNavigationView;
    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private CircleImageView im_userImage;
    private TextView tv_userName, tv_userEmail;
    private GoogleApiClient apiClient;

    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;
    private CallbackManager callbackManager;
    private LoginManager manager;

    public NormalUserData user_Data = null;
    public PublisherModel publisher_Model = null;
    public LibraryModel library_Model = null;
    public UniversityModel university_Model = null;
    public CompanyModel company_Model = null;

    private Preferences preferences;
    private Presenter presenter;
    public com.semicolon.librarians.libraryguide.MVP.Create_ChatRoom_MVP.Presenter chatRoomPresenter;
    private Target target;
    private Intent  intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, Tags.font,true);

        EventBus.getDefault().register(this);

        apiClient = new GoogleApiClient.Builder(HomeActivity.this).addApi(Auth.GOOGLE_SIGN_IN_API).enableAutoManage(HomeActivity.this,this).addApi(Auth.GOOGLE_SIGN_IN_API).build();
        apiClient.connect();
        callbackManager = CallbackManager.Factory.create();
        manager = LoginManager.getInstance();
        presenter = new PresenterImp(this,this);
        chatRoomPresenter = new com.semicolon.librarians.libraryguide.MVP.Create_ChatRoom_MVP.PresenterImp(this,this);

        initView();
        setUpDrawer();
        setUpSigninWithGoogle();
        setUpAlertDialog();
        setUpProgressDialog();

        getDataFrom_Intent();


        if (user_Data!=null)
        {

            NetworkConnection connection = new NetworkConnection(this);
            if (connection.CheckConnection()==true)
            {
                intent = new Intent(this, Locationservices_Update.class);
                startService(intent);
            }

        }



    }

    private void initView()
    {
        alertDialog = new AlertDialog.Builder(this);
        View home_View = findViewById(R.id.HomeContent);
        toolbar = (Toolbar) home_View.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navView = (NavigationView) findViewById(R.id.navView);
        //arcNavigationView = (ArcNavigationView) findViewById(R.id.arcDrawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //////////////////////////////////////////////////////////////
        //View headerView = arcNavigationView.getHeaderView(0);
        View headerView = navView.getHeaderView(0);
        im_userImage = (CircleImageView) headerView.findViewById(R.id.userPhoto);
        tv_userName = (TextView) headerView.findViewById(R.id.userName);
        tv_userEmail = (TextView) headerView.findViewById(R.id.userEmail);



        /////////////////////////////////////////////////////////////
        Log.e("token", FirebaseInstanceId.getInstance().getToken());

    }
    private void getDataFrom_Intent()
    {
        Intent intent = getIntent();
        if (intent.hasExtra("userData")) {
            final NormalUserData UserData = (NormalUserData) intent.getSerializableExtra("userData");

            if (UserData != null) {
                user_Data = UserData;
                Check_CurrentUser_ForFragment();


                if (user_Data.getUserPhoto()==null)
                {
                   /* Calligrapher calligrapher = new Calligrapher(getApplicationContext());
                    calligrapher.setFont(this, Tags.font,true);
             */       if (!user_Data.getUser_photo().equals("0"))
                    {


                        preferences = new Preferences(this);
                        preferences.Create_SharedPreference_User(user_Data);
                        preferences.LoggedUserPref("user",user_Data.getUserId());
                        UpdateUI(Tags.image_path+user_Data.getUser_photo(),user_Data.getUserName(),user_Data.getUserEmail());

                    }
                }else
                    {

                        preferences = new Preferences(this);
                        preferences.Create_SharedPreference_User(user_Data);
                        preferences.LoggedUserPref("user",user_Data.getUserId());

                        UpdateUI(user_Data.getUserPhoto(),user_Data.getUserName(),user_Data.getUserEmail());

                    }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.getNormalUserData("user",user_Data.getUserId());

                    }
                },100);
            }
        } else if (intent.hasExtra("publisherData")) {

            final PublisherModel publisherModel = (PublisherModel) intent.getSerializableExtra("publisherData");
            if (publisherModel != null) {
                publisher_Model = publisherModel;
              /*  Calligrapher calligrapher = new Calligrapher(getApplicationContext());
                calligrapher.setFont(this, Tags.font,true);
            */


                Check_CurrentUser_ForFragment();

                if (!publisher_Model.getUser_photo().equals("0"))
                {

                    preferences = new Preferences(this);
                    preferences.Create_SharedPreference_Publisher(publisher_Model);
                    preferences.LoggedUserPref("publisher",publisher_Model.getPub_username());

                    UpdateUI(Tags.image_path+publisher_Model.getUser_photo(),publisher_Model.getPub_name(),publisher_Model.getPub_email());

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        presenter.getPublisherData("publisher",publisher_Model.getPub_username());

                    }
                },100);
            }


        } else if (intent.hasExtra("libraryData")) {
            final LibraryModel libraryModel = (LibraryModel) intent.getSerializableExtra("libraryData");

            if (libraryModel != null) {
                library_Model = libraryModel;
              /*  Calligrapher calligrapher = new Calligrapher(getApplicationContext());
                calligrapher.setFont(this, Tags.font,true);
            */


                Check_CurrentUser_ForFragment();

                if (!library_Model.getUser_photo().equals("0"))
                {

                    preferences = new Preferences(this);
                    preferences.Create_SharedPreference_Library(library_Model);
                    preferences.LoggedUserPref("library",library_Model.getLib_username());

                    UpdateUI(Tags.image_path+library_Model.getUser_photo(),library_Model.getLib_name(),library_Model.getLib_email());

                }
                //EventBus.getDefault().post(libraryModel);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.getLibraryData("library",library_Model.getLib_username());

                    }
                },100);
            }


        } else if (intent.hasExtra("universityData")) {
            final UniversityModel universityModel = (UniversityModel) intent.getSerializableExtra("universityData");
            if (universityModel != null) {
                university_Model = universityModel;
               /* Calligrapher calligrapher = new Calligrapher(getApplicationContext());
                calligrapher.setFont(this, Tags.font,true);
*/

               Check_CurrentUser_ForFragment();
                if (!university_Model.getUser_photo().equals("0"))
                {

                    preferences = new Preferences(this);
                    preferences.Create_SharedPreference_University(university_Model);
                    preferences.LoggedUserPref("university",university_Model.getUni_username());

                    UpdateUI(Tags.image_path+university_Model.getUser_photo(),university_Model.getUni_name(),university_Model.getUni_email());

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.getUniversityData("university",university_Model.getUni_username());

                    }
                },100);
            }


        } else if (intent.hasExtra("companyData")) {
            final CompanyModel companyModel = (CompanyModel) intent.getSerializableExtra("companyData");

            if (companyModel != null) {
                company_Model = companyModel;
                /*Calligrapher calligrapher = new Calligrapher(getApplicationContext());
                calligrapher.setFont(this, Tags.font,true);
*/

                Check_CurrentUser_ForFragment();
                if (!company_Model.getUser_photo().equals("0"))
                {

                    preferences = new Preferences(this);
                    preferences.Create_SharedPreference_Company(company_Model);
                    preferences.LoggedUserPref("company",companyModel.getComp_username());

                    UpdateUI(Tags.image_path+company_Model.getUser_photo(),company_Model.getComp_name(),company_Model.getComp_email());

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.getCompanyData("company",company_Model.getComp_username());

                    }
                },100);
            }


        }
        /*else
                {

                    NetworkConnection connection = new NetworkConnection(HomeActivity.this);
                    if (connection.CheckConnection()==true)
                    {
                        SharedPreferences preferences = getSharedPreferences("userType",MODE_PRIVATE);
                        String uType = preferences.getString("userType","");
                        String u_id  = preferences.getString("id","");

                        if (TextUtils.isEmpty(uType)||TextUtils.isEmpty(u_id))
                        {
                            SignOut();
                        }else
                            {
                                switch (uType)
                                {
                                    case "user":
                                        NetworkConnection connection2 = new NetworkConnection(this);
                                        if (connection2.CheckConnection()==true)
                                        {
                                            intent = new Intent(this, Locationservices_Update.class);
                                            startService(intent);
                                        }

                                        presenter.getNormalUserData("user",u_id);
                                        break;
                                    case "publisher":
                                        presenter.getPublisherData("publisher",u_id);
                                        break;
                                    case "library":
                                        presenter.getLibraryData("library",u_id);
                                        break;
                                    case "university":
                                        presenter.getUniversityData("university",u_id);
                                        break;
                                    case "company":
                                        presenter.getCompanyData("company",u_id);
                                        break;

                                }
                            }

                    }else
                        {
                            SharedPreferences preferences = getSharedPreferences("userType",MODE_PRIVATE);
                            String uType = preferences.getString("userType","");
                            if (TextUtils.isEmpty(uType))
                            {
                                SignOut();
                            }else
                                {
                                    Check_OfflineData(uType);

                                }
                        }*/

                    /*SharedPreferences pref = getSharedPreferences("logged_user",MODE_PRIVATE);
                    String logged_user = pref.getString("current_user","");
                    switch (logged_user)
                    {
                        case "user":
                            Check_OfflineData("user");

                            break;
                        case "publisher":
                            Check_OfflineData("publisher");

                            break;
                        case "library":
                            Check_OfflineData("library");

                            break;
                        case "university":
                            Check_OfflineData("university");

                            break;
                        case "company":
                            Check_OfflineData("company");

                            break;
                        case "nobody":
                            SignOut();
                            Log.e("logout","logout");
                            break;
                        default:
                            {
                                SignOut();
                            }

                    }*/

                }




    private void UpdateUI(String imageUrl, String name, String email) {
        if (imageUrl == null) {

            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    im_userImage.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(HomeActivity.this).load(R.drawable.user_profile).placeholder(R.drawable.user_profile).into(target);
        } else {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    im_userImage.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };

            Log.e("immmmmmmmmm",imageUrl);
            Picasso.with(HomeActivity.this).load(Uri.parse(imageUrl)).placeholder(R.drawable.user_profile).into(im_userImage);


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

        navView.setNavigationItemSelectedListener(this);
        //arcNavigationView.setNavigationItemSelectedListener(this);


    }

    private void setUpAlertDialog() {
        alertDialog.setTitle(R.string.signout).setPositiveButton(getString(R.string.out), new DialogInterface.OnClickListener() {
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
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog Dialog = alertDialog.create();
                Dialog.dismiss();
            }
        }).create();

    }

    private void SignOut() {

        Auth.GoogleSignInApi.signOut(apiClient);
            Log.e("google","signout");
            apiClient.disconnect();
        manager.unregisterCallback(callbackManager);
        manager.logOut();
        if (user_Data!=null)
        {
            Preferences preference = new Preferences(HomeActivity.this);
            preference.Clear_SharedPrefrence("user");
            preference.LoggedUserPref("nobody","0");
            preference.Session("loggedout");

            startActivity(new Intent(HomeActivity.this,ChooserSingin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();

        }else if (publisher_Model!=null)
        {
            Preferences preference = new Preferences(HomeActivity.this);
            preference.Clear_SharedPrefrence("publisher");
            preference.LoggedUserPref("nobody","0");
            preference.Session("loggedout");

            startActivity(new Intent(HomeActivity.this,ChooserSingin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();

        }
        else if (library_Model!=null)
        {
            Preferences preference = new Preferences(HomeActivity.this);
            preference.Clear_SharedPrefrence("library");
            preference.LoggedUserPref("nobody","0");
            preference.Session("loggedout");


            startActivity(new Intent(HomeActivity.this,ChooserSingin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();

        }
        else if (university_Model!=null)
        {
            Preferences preference = new Preferences(HomeActivity.this);
            preference.Clear_SharedPrefrence("university");
            preference.LoggedUserPref("nobody","0");
            preference.Session("loggedout");

            startActivity(new Intent(HomeActivity.this,ChooserSingin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();

        }
        else if (company_Model!=null)
        {
            Preferences preference = new Preferences(HomeActivity.this);
            preference.Clear_SharedPrefrence("company");
            preference.LoggedUserPref("nobody","0");
            preference.Session("loggedout");


            startActivity(new Intent(HomeActivity.this,ChooserSingin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();

        }else
            {
                Preferences preference = new Preferences(HomeActivity.this);
                preference.Session("loggedout");

                startActivity(new Intent(HomeActivity.this,ChooserSingin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    Check_CurrentUser_ForFragment();
                    }
                }, 1000);
                return true;
            }
        } else if (item.getItemId() == R.id.profile) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (user_Data!=null)
                        {
                            Intent intent = new Intent(HomeActivity.this,Activity_Profile.class);
                            intent.putExtra("who_visit_myProfile","me");
                            intent.putExtra("userData",user_Data);
                            startActivity(intent);
                            Log.e("user_dataaaaa", "run: udataaaaa" );


                        }else if (publisher_Model!=null)
                        {
                            Intent intent = new Intent(HomeActivity.this,Activity_Profile.class);
                            intent.putExtra("who_visit_myProfile","me");
                            intent.putExtra("publisherData",publisher_Model);
                            startActivity(intent);


                        }else if (library_Model!=null)
                        {
                            Intent intent = new Intent(HomeActivity.this,Activity_Profile.class);
                            intent.putExtra("who_visit_myProfile","me");
                            intent.putExtra("libraryData",library_Model);
                            startActivity(intent);

                        }else if (university_Model!=null)
                        {
                            Intent intent = new Intent(HomeActivity.this,Activity_Profile.class);
                            intent.putExtra("who_visit_myProfile","me");
                            intent.putExtra("universityData", university_Model);
                            startActivity(intent);

                        }
                        else if (company_Model!=null)
                        {
                            Intent intent = new Intent(HomeActivity.this,Activity_Profile.class);
                            intent.putExtra("who_visit_myProfile","me");
                            intent.putExtra("companyData",company_Model);
                            startActivity(intent);
                        }

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
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (user_Data != null) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user_data",user_Data);
                            Company_Fragment company_fragment = new Company_Fragment();
                            company_fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer,company_fragment ,"company_fragment").addToBackStack("company_fragment").commit();

                        } else if (publisher_Model != null) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("publisherData",publisher_Model);
                            Company_Fragment company_fragment = new Company_Fragment();
                            company_fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer, company_fragment,"company_fragment").addToBackStack("company_fragment").commit();


                        } else if (university_Model != null) {

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("universityData",university_Model);
                            Company_Fragment company_fragment = new Company_Fragment();
                            company_fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer, company_fragment,"company_fragment").addToBackStack("company_fragment").commit();

                        } else if (library_Model != null) {

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("libraryData",library_Model);
                            Company_Fragment company_fragment = new Company_Fragment();
                            company_fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer, company_fragment,"company_fragment").addToBackStack("company_fragment").commit();


                        } else if (company_Model != null) {

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("companyData",company_Model);
                            Company_Fragment company_fragment = new Company_Fragment();
                            company_fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer,company_fragment ,"company_fragment").addToBackStack("company_fragment").commit();




                        }
                    }
                },500);

                return true;
            }


        }
        else if (item.getItemId() == R.id.ads) {
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
                alertDialog.show();

                return true;
            }
        }
        return false;
    }

    private void Check_CurrentUser_ForFragment()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user_Data != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user_data",user_Data);
                            Home_Fragment home_fragment =new Home_Fragment();
                            home_fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer, home_fragment,"home_fragment").addToBackStack("home_fragment").commitAllowingStateLoss();

                        }
                    },500);

                }
                else if (publisher_Model != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("publisherData",publisher_Model);
                            Home_Fragment home_fragment =new Home_Fragment();
                            home_fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer, home_fragment,"home_fragment").addToBackStack("home_fragment").commitAllowingStateLoss();

                        }
                    },500);


                } else if (university_Model != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("universityData",university_Model);
                            Home_Fragment home_fragment =new Home_Fragment();
                            home_fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer, home_fragment,"home_fragment").addToBackStack("home_fragment").commitAllowingStateLoss();

                        }
                    },500);

                } else if (library_Model != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("libraryData",library_Model);
                            Home_Fragment home_fragment =new Home_Fragment();
                            home_fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer, home_fragment,"home_fragment").addToBackStack("home_fragment").commitAllowingStateLoss();

                        }
                    },500);

                } else if (company_Model != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("companyData",company_Model);
                            Home_Fragment home_fragment =new Home_Fragment();
                            home_fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer, home_fragment,"home_fragment").addToBackStack("home_fragment").commitAllowingStateLoss();

                        }
                    },500);



                }
            }
        },500);

    }

    private void setUpProgressDialog()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.signout));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        ProgressBar progressBar = new ProgressBar(this);
        Drawable drawable = progressBar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this, R.color.centercolor), PorterDuff.Mode.SRC_IN);
        progressDialog.setIndeterminateDrawable(drawable);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

    public Toolbar getToolBar()
    {
        if (toolbar!=null){
        return toolbar;

        }
        return null;
    }


    @Override
    public void onNormalUserDataSuccess(final NormalUserData normalUserData)
    {
        user_Data = normalUserData;

        if (normalUserData.getUserPhoto()==null)
        {
            if (!normalUserData.getUser_photo().equals("0"))
            {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Check_CurrentUser_ForFragment();
                        preferences = new Preferences(HomeActivity.this);
                        preferences.Create_SharedPreference_User(user_Data);
                        preferences.LoggedUserPref("user",user_Data.getUserId());

                        UpdateUI(Tags.image_path+normalUserData.getUser_photo(), normalUserData.getUserName(), normalUserData.getUserEmail());
                    }
                }, 100);
            }
            else
                {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Check_CurrentUser_ForFragment();
                            preferences = new Preferences(HomeActivity.this);
                            preferences.Create_SharedPreference_User(user_Data);
                            preferences.LoggedUserPref("user",user_Data.getUserId());

                            UpdateUI("", normalUserData.getUserName(), normalUserData.getUserEmail());
                        }
                    }, 100);
                }
        }else
            {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Check_CurrentUser_ForFragment();
                        preferences = new Preferences(HomeActivity.this);
                        preferences.Create_SharedPreference_User(user_Data);
                        preferences.LoggedUserPref("user",user_Data.getUserId());

                        UpdateUI(normalUserData.getUserPhoto(), normalUserData.getUserName(), normalUserData.getUserEmail());
                    }
                }, 100);
            }
    }

    @Override
    public void onPublisherDataSuccess(final PublisherModel publisherModel)
    {
        publisher_Model = publisherModel;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Check_CurrentUser_ForFragment();
                preferences = new Preferences(HomeActivity.this);
                preferences.Create_SharedPreference_Publisher(publisher_Model);
                preferences.LoggedUserPref("publisher",publisher_Model.getPub_username());

                Log.e("pubphoto",Tags.image_path+publisherModel.getUser_photo());
                UpdateUI(Tags.image_path+publisherModel.getUser_photo(), publisherModel.getPub_name(), publisherModel.getPub_email());
            }
        }, 100);
    }

    @Override
    public void onLibraryDataSuccess(final LibraryModel libraryModel)
    {
        library_Model = libraryModel;


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Check_CurrentUser_ForFragment();
                preferences = new Preferences(HomeActivity.this);
                preferences.Create_SharedPreference_Library(library_Model);
                preferences.LoggedUserPref("library",library_Model.getLib_username());

                UpdateUI(Tags.image_path+libraryModel.getUser_photo(), libraryModel.getLib_name(), libraryModel.getLib_email());
            }
        }, 100);

    }

    @Override
    public void onCompanyDataSuccess(final CompanyModel companyModel)
    {
        company_Model = companyModel;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Check_CurrentUser_ForFragment();
                preferences = new Preferences(HomeActivity.this);
                preferences.Create_SharedPreference_Company(company_Model);
                preferences.LoggedUserPref("company",company_Model.getComp_username());

                UpdateUI(Tags.image_path+companyModel.getUser_photo(), companyModel.getComp_name(), companyModel.getComp_email());
            }
        }, 100);
    }

    @Override
    public void onUniversityDataSuccess(final UniversityModel universityModel)
    {
        university_Model = universityModel;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Check_CurrentUser_ForFragment();
                preferences = new Preferences(HomeActivity.this);
                preferences.Create_SharedPreference_University(university_Model);
                preferences.LoggedUserPref("university",university_Model.getUni_username());

                UpdateUI(Tags.image_path+universityModel.getUser_photo(), universityModel.getUni_name(), universityModel.getUni_email());
            }
        }, 100);
    }

    @Override
    public void onChatRoom_CreatedSuccessfully(String response) {
        Log.e("chat created ",response);
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(this,error, Toast.LENGTH_SHORT).show();
    }

    public void GetToken(String token)
    {
        Log.e("token ",token);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (apiClient!=null)
        {
            apiClient.connect();

        }
        preferences = new Preferences(this);
        String session = preferences.getSession();
        if (session.equals("loggedout"))
        {
            SignOut();


        }

    }

    @Override
    protected void onDestroy() {
        if (apiClient.isConnected())
        {
            apiClient.disconnect();
        }
        super.onDestroy();
        Picasso.with(this).cancelRequest(target);
        EventBus.getDefault().unregister(this);
        if (user_Data!=null)
        {
            intent = new Intent(this, Locationservices_Update.class);

            stopService(intent);

        }
        Log.e("home activity ","Destroy");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnLocationUpdated(Location_Model location_model )
    {
        Toast.makeText(this, ""+location_model.getLocation().getLatitude(), Toast.LENGTH_SHORT).show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenRefresh(RefreshToken token)
    {
        updateToken(token);
    }

    private void updateToken(RefreshToken token) {

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (user_Data!=null)
        {
            Log.e("home_post_user"," "+user_Data.getUserId());
            intent = new Intent(this, Locationservices_Update.class);

            stopService(intent);

            EventBus.getDefault().post(user_Data);


        }else if (publisher_Model!=null)
        {
            Log.e("home_post_pub"," "+publisher_Model.getPub_username());
            EventBus.getDefault().post(publisher_Model);

        }
        else if (library_Model!=null)
        {
            Log.e("home_post_lib"," "+library_Model.getLib_username());

            EventBus.getDefault().post(library_Model);

        }
        else if(university_Model!=null)
        {
            Log.e("home_post_uni"," "+university_Model.getUni_username());

            EventBus.getDefault().post(university_Model);

        }
        else if (company_Model!=null)
        {
            Log.e("home_post_comp"," "+company_Model.getComp_username());

            EventBus.getDefault().post(company_Model);

        }
    }



    @Override
    public void onBackPressed()
    {



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
}

