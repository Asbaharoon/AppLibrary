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

public class Activity_Profile extends AppCompatActivity {

    private NormalUserData user_Data = null;
    private PublisherModel publisher_Model = null;
    private LibraryModel library_Model = null;
    private UniversityModel university_Model = null;
    private CompanyModel company_Model = null;
    private String who_visit_myProfile="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__profile);
        getDataFrom_Intent();
    }
    private void getDataFrom_Intent() {
        Intent intent = getIntent();
        if (intent.hasExtra("userData")) {
            final NormalUserData UserData = (NormalUserData) intent.getSerializableExtra("userData");

            if (UserData != null) {
                who_visit_myProfile = intent.getStringExtra("who_visit_myProfile");
                user_Data = UserData;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("userdataaaaaaaaa","userdataaaaaaaaaa");
                        Bundle bundle = new Bundle();
                        bundle.putString("who_visit_myProfile",who_visit_myProfile);
                        bundle.putSerializable("userData",user_Data);
                        Fragment_UserProfile fragment_userProfile  = new Fragment_UserProfile();
                        fragment_userProfile.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container,fragment_userProfile).commit();
                    }
                }, 500);
            }
        } else if (intent.hasExtra("publisherData")) {

            final PublisherModel publisherModel = (PublisherModel) intent.getSerializableExtra("publisherData");
            if (publisherModel != null) {
                who_visit_myProfile = intent.getStringExtra("who_visit_myProfile");

                publisher_Model = publisherModel;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("publisherData",publisher_Model);
                        bundle.putString("who_visit_myProfile",who_visit_myProfile);
                        Fragment_PublisherProfile fragment_publisherProfile  = new Fragment_PublisherProfile();
                        fragment_publisherProfile.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container,fragment_publisherProfile).commit();

                    }
                }, 500);
            }


        } else if (intent.hasExtra("libraryData")) {
            final LibraryModel libraryModel = (LibraryModel) intent.getSerializableExtra("libraryData");

            if (libraryModel != null) {
                who_visit_myProfile = intent.getStringExtra("who_visit_myProfile");

                library_Model = libraryModel;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("libraryData",library_Model);
                        bundle.putString("who_visit_myProfile",who_visit_myProfile);
                        Fragment_LibraryProfile fragment_libraryProfile  = new Fragment_LibraryProfile();
                        fragment_libraryProfile.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container,fragment_libraryProfile).commit();

                    }
                }, 500);

            }


        } else if (intent.hasExtra("universityData")) {
            final UniversityModel universityModel = (UniversityModel) intent.getSerializableExtra("universityData");
            if (universityModel != null) {
                who_visit_myProfile = intent.getStringExtra("who_visit_myProfile");

                university_Model = universityModel;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("universityData",university_Model);
                        bundle.putString("who_visit_myProfile",who_visit_myProfile);
                        Fragment_UniversityProfile fragment_universityProfile  = new Fragment_UniversityProfile();
                        fragment_universityProfile.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container,fragment_universityProfile).commit();

                    }
                }, 500);
            }


        } else if (intent.hasExtra("companyData")) {
            final CompanyModel companyModel = (CompanyModel) intent.getSerializableExtra("companyData");

            if (companyModel != null) {
                who_visit_myProfile = intent.getStringExtra("who_visit_myProfile");

                company_Model = companyModel;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("companyData",company_Model);
                        bundle.putString("who_visit_myProfile",who_visit_myProfile);
                        Fragment_CompanyProfile fragment_companyProfile  = new Fragment_CompanyProfile();
                        fragment_companyProfile.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_profile_container,fragment_companyProfile).commit();

                    }
                }, 500);
            }


        }
    }
}
