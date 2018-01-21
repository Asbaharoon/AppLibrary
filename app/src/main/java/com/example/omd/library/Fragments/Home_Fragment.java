package com.example.omd.library.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.example.omd.library.Activities.NearbyActivity;
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

/**
 * Created by Delta on 15/12/2017.
 */

public class Home_Fragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.OnConnectionFailedListener {
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


    private Context mContext;
    private AHBottomNavigation navBar;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);


        initView(view);

        setUpnavBar();
        setUpDrawer();
        setUpSigninWithGoogle();
        setUpAlertDialog();
        setUpProgressDialog();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void initView(View view) {
        mContext = view.getContext();
        callbackManager = CallbackManager.Factory.create();
        manager = LoginManager.getInstance();
        alerDialog = new AlertDialog.Builder(getActivity());
        View home_View = view.findViewById(R.id.HomeContent);
        toolbar = (Toolbar) home_View.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        navBar = (AHBottomNavigation)home_View.findViewById(R.id.bottom_navBar);

        /////////////////////////////////////////////////////////////
        arcNavigationView = (ArcNavigationView) view.findViewById(R.id.arcDrawer);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        //////////////////////////////////////////////////////////////
        View headerView = arcNavigationView.getHeaderView(0);

        im_userImage = (CircleImageView) headerView.findViewById(R.id.userPhoto);
        tv_userName = (TextView) headerView.findViewById(R.id.userName);
        tv_userEmail = (TextView) headerView.findViewById(R.id.userEmail);


        //getSupportFragmentManager().beginTransaction().add(R.id.home_fragmentsContainer, new Home_Fragment()).commit();
        //getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_Fragments, new Home_Fragment()).commit();

        getDataFrom_Intent();
    }
    private void setUpnavBar()
    {
        AHBottomNavigationAdapter adapter = new AHBottomNavigationAdapter(getActivity(),R.menu.navbar_menu);
        navBar.setDefaultBackgroundColor(ContextCompat.getColor(mContext,R.color.base));
        navBar.setInactiveColor(ContextCompat.getColor(mContext,R.color.dark_gray));
        navBar.setAccentColor(ContextCompat.getColor(mContext, R.color.centercolor));
        adapter.setupWithBottomNavigation(navBar);
        //navBar.setCurrentItem(0);
        navBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        if (navBar.getCurrentItem()==0)
        {
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.home_fragmentsContainer,new Chat_Fragment(),"chat_fragment").commit();

        }
        navBar.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (position==0)
                {

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer,new Chat_Fragment(),"chat_fragment").commit();

                    navBar.setCurrentItem(position,false);


                    return true;
                }
                else if (position==1)
                {

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer,new University_Fragment(),"uni_fragment").commit();
                    navBar.setCurrentItem(position,false);


                    return true;

                }
                else if (position==2)
                {

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer,new Library_Fragment(),"lib_fragment").commit();
                    navBar.setCurrentItem(position,false);


                    return true;

                }
                else if (position==3)
                {

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragmentsContainer,new Publisher_Fragment()).commit();
                    navBar.setCurrentItem(position,false);


                    return true;

                }
                return false;
            }
        });
    }

    private void getDataFrom_Intent() {
        Intent intent = getActivity().getIntent();
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



    private void UpdateUI(String imageUrl, String name, String email) {
        if (TextUtils.isEmpty(imageUrl) || imageUrl == null) {
            Picasso.with(getActivity()).load(R.drawable.user_profile).fit().into(im_userImage);
        } else {
            Picasso.with(getActivity()).load(Uri.parse(imageUrl)).fit().into(im_userImage);

        }
        tv_userName.setText(TextUtils.isEmpty(name) || name == null ? "" : name.toString());
        tv_userEmail.setText(email == null || TextUtils.isEmpty(email) ? "" : email);

    }

    private void setUpSigninWithGoogle() {
        apiClient = new GoogleApiClient.Builder(getActivity()).addApi(Auth.GOOGLE_SIGN_IN_API).build();
        apiClient.connect();

    }

    private void setUpDrawer() {
        mToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close);
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
        getActivity().finish();
    }

    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Sign Out.....");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        ProgressBar progressBar = new ProgressBar(getActivity());
        Drawable drawable = progressBar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(getActivity(), R.color.centercolor), PorterDuff.Mode.SRC_IN);
        progressDialog.setIndeterminateDrawable(drawable);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer, new Home_Fragment()).commit();
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
                            Intent intent = new Intent(getActivity(), NearbyActivity.class);
                            intent.putExtra("userData", user_Data);
                            startActivity(intent);
                        } else if (publisher_Model != null) {
                            Intent intent = new Intent(getActivity(), NearbyActivity.class);
                            intent.putExtra("publisherData", publisher_Model);
                            startActivity(intent);
                        } else if (university_Model != null) {
                            Intent intent = new Intent(getActivity(), NearbyActivity.class);
                            intent.putExtra("universityData", university_Model);
                            startActivity(intent);
                        } else if (library_Model != null) {
                            Intent intent = new Intent(getActivity(), NearbyActivity.class);
                            intent.putExtra("libraryData", library_Model);
                            startActivity(intent);
                        } else if (company_Model != null) {
                            Intent intent = new Intent(getActivity(), NearbyActivity.class);
                            intent.putExtra("companyData", company_Model);
                            startActivity(intent);
                        }

                    }
                }, 500);

                return true;
            }
        } else if (item.getItemId() == R.id.news) {
            Toast.makeText(getActivity(), "news", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer, new News_Fragment()).commit();
                    }
                }, 500);

                return true;
            }


        }
        else if (item.getItemId() == R.id.jobs) {
            Toast.makeText(getActivity(), "jobs", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer, new Jobs_Fragment()).commit();
                    }
                }, 500);

                return true;
            }


        }
        else if (item.getItemId() == R.id.company) {
            Toast.makeText(getActivity(), "company", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer, new Company_Fragment()).commit();
                    }
                }, 500);

                return true;
            }


        }
        else if (item.getItemId() == R.id.ads) {
            Toast.makeText(getActivity(), "ads", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.home_fragmentsContainer, new Ads_Fragment()).commit();
                    }
                }, 500);

                return true;
            }


        }
        else if (item.getItemId() == R.id.settings) {

            Toast.makeText(getActivity(), "settings", Toast.LENGTH_SHORT).show();
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
