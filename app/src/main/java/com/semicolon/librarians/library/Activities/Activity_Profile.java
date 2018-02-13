package com.semicolon.librarians.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.semicolon.librarians.library.Fragments.Fragment_CompanyProfile;
import com.semicolon.librarians.library.Fragments.Fragment_LibraryProfile;
import com.semicolon.librarians.library.Fragments.Fragment_PublisherProfile;
import com.semicolon.librarians.library.Fragments.Fragment_UniversityProfile;
import com.semicolon.librarians.library.Fragments.Fragment_UserProfile;
import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;
import com.semicolon.librarians.library.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Activity_Profile extends AppCompatActivity {

    public NormalUserData user_Data = null;
    public PublisherModel publisher_Model = null;
    public LibraryModel library_Model = null;
    public UniversityModel university_Model = null;
    public CompanyModel company_Model = null;
    public String who_visit_myProfile = "";
    public NormalUserData logged_user_Data = null;
    public PublisherModel logged_publisher_Model = null;
    public LibraryModel logged_library_Model = null;
    public UniversityModel logged_university_Model = null;
    public CompanyModel logged_company_Model = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__profile);
        EventBus.getDefault().register(this);
        getDataFrom_Intent();
    }

    private void getDataFrom_Intent() {
        Intent intent = getIntent();
        if (intent.hasExtra("userData")) {
            user_Data = (NormalUserData) intent.getSerializableExtra("userData");

            if (user_Data != null) {
                who_visit_myProfile = intent.getStringExtra("who_visit_myProfile");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("userdataaaaaaaaa", "userdataaaaaaaaaa");
                        Bundle bundle = new Bundle();
                        bundle.putString("who_visit_myProfile", who_visit_myProfile);
                        bundle.putSerializable("userData", user_Data);
                        Fragment_UserProfile fragment_userProfile = new Fragment_UserProfile();
                        fragment_userProfile.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container, fragment_userProfile).commit();
                    }
                }, 500);
            }
        } else if (intent.hasExtra("publisherData")) {
            publisher_Model = (PublisherModel) intent.getSerializableExtra("publisherData");
            who_visit_myProfile = intent.getStringExtra("who_visit_myProfile");

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("publisherData", publisher_Model);
                    bundle.putString("who_visit_myProfile", who_visit_myProfile);
                    Fragment_PublisherProfile fragment_publisherProfile = new Fragment_PublisherProfile();
                    fragment_publisherProfile.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container, fragment_publisherProfile).commit();

                }
            }, 500);



        } else if (intent.hasExtra("libraryData")) {
            library_Model = (LibraryModel) intent.getSerializableExtra("libraryData");

            if (library_Model != null) {
                who_visit_myProfile = intent.getStringExtra("who_visit_myProfile");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("libraryData", library_Model);
                        bundle.putString("who_visit_myProfile", who_visit_myProfile);
                        Fragment_LibraryProfile fragment_libraryProfile = new Fragment_LibraryProfile();
                        fragment_libraryProfile.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container, fragment_libraryProfile).commit();

                    }
                }, 500);

            }
        } else if (intent.hasExtra("universityData")) {
            university_Model = (UniversityModel) intent.getSerializableExtra("universityData");
            if (university_Model != null) {
                who_visit_myProfile = intent.getStringExtra("who_visit_myProfile");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("universityData", university_Model);
                        bundle.putString("who_visit_myProfile", who_visit_myProfile);
                        Fragment_UniversityProfile fragment_universityProfile = new Fragment_UniversityProfile();
                        fragment_universityProfile.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container, fragment_universityProfile).commit();

                    }
                }, 500);
            }
        } else if (intent.hasExtra("companyData")) {
            company_Model = (CompanyModel) intent.getSerializableExtra("companyData");

            if (company_Model != null) {
                who_visit_myProfile = intent.getStringExtra("who_visit_myProfile");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("companyData", company_Model);
                        bundle.putString("who_visit_myProfile", who_visit_myProfile);
                        Fragment_CompanyProfile fragment_companyProfile = new Fragment_CompanyProfile();
                        fragment_companyProfile.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container, fragment_companyProfile).commit();

                    }
                }, 500);
            }


        }else if (intent.hasExtra("userId"))
        {
            final String userId = intent.getStringExtra("userId");
            final String visitor=intent.getStringExtra("who_visit_myProfile");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("userdataaaaaaaaa", "userdataaaaaaaaaa");
                    Bundle bundle = new Bundle();
                    bundle.putString("who_visit_myProfile",visitor);
                    bundle.putSerializable("userId", userId);
                    Fragment_UserProfile fragment_userProfile = new Fragment_UserProfile();
                    fragment_userProfile.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container, fragment_userProfile).commit();
                }
            }, 500);


        }else if (intent.hasExtra("pubId"))
        {
            final String pubId = intent.getStringExtra("pubId");
            final String visitor=intent.getStringExtra("who_visit_myProfile");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pubId", pubId);
                    bundle.putString("who_visit_myProfile", visitor);
                    Fragment_PublisherProfile fragment_publisherProfile = new Fragment_PublisherProfile();
                    fragment_publisherProfile.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container, fragment_publisherProfile).commit();

                }
            }, 500);


        }else if (intent.hasExtra("libId"))
        {
            final String libId = intent.getStringExtra("libId");
            final String visitor=intent.getStringExtra("who_visit_myProfile");

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("libId", libId);
                    bundle.putString("who_visit_myProfile", visitor);
                    Fragment_LibraryProfile fragment_libraryProfile = new Fragment_LibraryProfile();
                    fragment_libraryProfile.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container, fragment_libraryProfile).commit();

                }
            }, 500);


        }else if (intent.hasExtra("uniId"))
        {
            final String uniId = intent.getStringExtra("uniId");
            final String visitor=intent.getStringExtra("who_visit_myProfile");

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("uniId", uniId);
                    bundle.putString("who_visit_myProfile", visitor);
                    Fragment_UniversityProfile fragment_universityProfile = new Fragment_UniversityProfile();
                    fragment_universityProfile.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container, fragment_universityProfile).commit();

                }
            }, 500);


        }else if (intent.hasExtra("compId"))
        {
            final String compId = intent.getStringExtra("compId");
            final String visitor=intent.getStringExtra("who_visit_myProfile");

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("compId", compId);
                    bundle.putString("who_visit_myProfile", visitor);
                    Fragment_CompanyProfile fragment_companyProfile = new Fragment_CompanyProfile();
                    fragment_companyProfile.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container, fragment_companyProfile).commit();

                }
            }, 500);

        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLoggedUserData(NormalUserData normalUserData) {
        this.logged_user_Data = normalUserData;
        Log.e("user", "" + logged_user_Data.getUserName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLoggedPublisherData(PublisherModel publisherModel) {
        this.logged_publisher_Model = publisherModel;
        Log.e("pub", "" + logged_publisher_Model.getPub_name());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLoggedLibraryData(LibraryModel libraryModel) {
        this.logged_library_Model = libraryModel;
        Log.e("uni", "" + logged_library_Model.getLib_name());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLoggedUniversityData(UniversityModel universityModel) {
        this.logged_university_Model = universityModel;
        Log.e("uni", "" + logged_university_Model.getUni_name());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLoggedCompanyData(CompanyModel companyModel) {
        this.logged_company_Model = companyModel;
        Log.e("comp", "" + logged_company_Model.getComp_name());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
