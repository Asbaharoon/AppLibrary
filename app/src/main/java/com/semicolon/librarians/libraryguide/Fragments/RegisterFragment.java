package com.semicolon.librarians.libraryguide.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;
import com.semicolon.librarians.libraryguide.Activities.Activity_PhoneNumber;
import com.semicolon.librarians.libraryguide.Activities.Activity_Search_Results;
import com.semicolon.librarians.libraryguide.Activities.HomeActivity;
import com.semicolon.librarians.libraryguide.Activities.MapsActivity;
import com.semicolon.librarians.libraryguide.MVP.Login_RegisterMVP.Registration.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.Login_RegisterMVP.Registration.ViewData;
import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.CountriesModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.LibraryType_Model;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Preferences;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;
import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Delta on 08/12/2017.
 */

public class RegisterFragment extends Fragment implements ViewData, View.OnClickListener {

    private ExpandableRelativeLayout layout_normal_user, layout_pub, layout_lib,layout_university,layout_company;
    private AppCompatSpinner spinner;
    private FrameLayout n_userPhoto_container,pubPhoto_container,libPhoto_container,uniPhoto_container,compPhoto_container;
    private CircleImageView n_circleImageView,pub_circleImageView,lib_circleImageView,uni_circleImageView,comp_circleImageView;
    private MaterialEditText n_userFirstName, n_userLastName,n_user_userName, n_userEmail, n_userPhone, n_userCountry, n_userPassword;
    private MaterialEditText publisher_firstName, publisher_lastName, publisherEmail, publisherCountry, publisherPhone, publisherTown,publisherUsername,publisherAddress, publisher_webSite, publisherPassword;
    private MaterialEditText libraryName, libraryEmail, libraryPhone, libraryCountry,libraryAddress, libraryUsername, libraryPassword, library_type;
    private MaterialEditText universityName, universityEmail, universityPhone, universityCountry,universityAddress, universityMajor,universityUsername, universityPassword,universitySite;
    private MaterialEditText companyName,companyUsername,companyEmail, companyPhone,companyCountry, companyPassword,companySite,companyTown,companyAddress;

    private Button n_SignInBtn, lib_SignInBtn,pub_SignInBtn, uni_SignInBtn,comp_SignInBtn;
    Context mContext;
    Handler handler;
    PresenterImp presenter;
    public Uri userImage_URI,pubImage_URI,libImage_URI,uniImage_URI,compImage_URI = null;
    public Bitmap userBitmap_image,pubBitmap_image,libBitmap_image,uniBitmap_image,compBitmap_image = null;
    public String user_image,pub_image,lib_image,uni_image,comp_image="";
    private final int IMAGE_REQUEST_USER    = 201;
    private final int IMAGE_REQUEST_PUB     = 202;
    private final int IMAGE_REQUEST_LIB     = 203;
    private final int IMAGE_REQUEST_UNI     = 204;
    private final int IMAGE_REQUEST_COMP    = 205;
    private final int REQUEST_CODE          = 300;

    private final int COUNTRY_REQUEST_USER  = 301;
    private final int COUNTRY_REQUEST_PUB   = 302;
    private final int COUNTRY_REQUEST_LIB   = 303;
    private final int COUNTRY_REQUEST_UNI   = 304;
    private final int COUNTRY_REQUEST_COMP  = 305;

    private final int PHONE_REQUEST_USER    = 307;
    private final int PHONE_REQUEST_PUB     = 308;
    private final int PHONE_REQUEST_LIB     = 309;
    private final int PHONE_REQUEST_UNI     = 310;
    private final int PHONE_REQUEST_COMP    = 311;

    private String n_country_id="";
    private String p_country_id="";
    private String l_country_id="";
    private String u_country_id="";
    private String c_country_id="";
    private String libType ="";
    private final int LIBRARY_TYPE_REQUEST  = 306;


    private TextView addPhoto_tv_user,addPhoto_tv_pub,addPhoto_tv_lib,addPhoto_tv_uni,addPhoto_tv_comp;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ProgressDialog  progressDialog;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), Tags.font,true);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_fragment, container, false);
        initView(view);
        Calligrapher calligrapher = new Calligrapher(getActivity().getApplicationContext());
        calligrapher.setFont(view, Tags.font);

        init_normalUserView(view);
        init_publisherView(view);
        init_libraryView(view);
        init_universityView(view);
        init_companyView(view);
        initView(view);
        CreateProgressDialog();

        presenter = new PresenterImp(mContext,this);
        return view;
    }

    private void initView(View view) {

        handler = new Handler();
        mContext = view.getContext();
        spinner = (AppCompatSpinner) view.findViewById(R.id.spinner);
        layout_normal_user = (ExpandableRelativeLayout) view.findViewById(R.id.expandable_normal_user);
        layout_pub = (ExpandableRelativeLayout) view.findViewById(R.id.expandable_publisher);
        layout_lib = (ExpandableRelativeLayout) view.findViewById(R.id.expandable_lib);
        layout_university = (ExpandableRelativeLayout) view.findViewById(R.id.expandable_university);
        layout_company = (ExpandableRelativeLayout) view.findViewById(R.id.expandable_company);
        layout_normal_user.collapse();
        layout_lib.collapse();
        spinner = (AppCompatSpinner) view.findViewById(R.id.spinner);


        spinner.setSelection(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_row, getResources().getStringArray(R.array.spinnerArray));
        spinner.setAdapter(adapter);

        if (spinner.getSelectedItem().toString().equals(getString(R.string.normal_user))) {
               if (layout_pub.isExpanded() || layout_lib.isExpanded()) {
                layout_pub.collapse();
                layout_lib.collapse();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        layout_normal_user.expand();
                    }
                }, 1000);
            } else {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        layout_normal_user.expand();
                    }
                }, 1000);
            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getSelectedView()).setGravity(Gravity.CENTER);
                ((TextView) adapterView.getSelectedView()).setTextColor(ContextCompat.getColor(mContext, R.color.label));
                if (adapterView.getSelectedItem().toString().equals(getString(R.string.normal_user))) {

                              if (layout_pub.isExpanded() || layout_lib.isExpanded()||layout_university.isExpanded()||layout_company.isExpanded()) {

                        layout_pub.collapse();
                        layout_lib.collapse();
                        layout_company.collapse();
                        layout_university.collapse();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                layout_normal_user.expand();
                            }
                        }, 1000);
                    } else {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                layout_normal_user.expand();
                            }
                        }, 1000);

                    }
                } else if (adapterView.getSelectedItem().toString().equals(getString(R.string.spn_pub))) {
                    if (layout_normal_user.isExpanded() || layout_lib.isExpanded()||layout_university.isExpanded()||layout_company.isExpanded()) {

                        layout_normal_user.collapse();
                        layout_lib.collapse();
                        layout_company.collapse();
                        layout_university.collapse();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                layout_pub.expand();
                            }
                        }, 1000);
                    } else {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                layout_pub.expand();
                            }
                        }, 1000);
                    }

                } else if (adapterView.getSelectedItem().toString().equals(getString(R.string.spin_lib))) {
                    if (layout_normal_user.isExpanded() || layout_pub.isExpanded()||layout_university.isExpanded()||layout_company.isExpanded()) {

                        layout_normal_user.collapse();
                        layout_pub.collapse();
                        layout_company.collapse();
                        layout_university.collapse();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                layout_lib.expand();
                            }
                        }, 1000);
                    } else {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                layout_lib.expand();
                            }
                        }, 1000);
                    }

                }
                else if (spinner.getSelectedItem().toString().equals(getString(R.string.spin_uni)))
                {
                    if (layout_normal_user.isExpanded() || layout_pub.isExpanded()||layout_lib.isExpanded()||layout_company.isExpanded()) {

                        layout_normal_user.collapse();
                        layout_pub.collapse();
                        layout_lib.collapse();
                        layout_company.collapse();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                layout_university.expand();
                            }
                        }, 1000);
                    } else {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                layout_university.expand();
                            }
                        }, 1000);
                    }

                }else if (spinner.getSelectedItem().toString().equals(getString(R.string.spin_comp))) {
                    if (layout_normal_user.isExpanded() || layout_pub.isExpanded()||layout_lib.isExpanded()||layout_university.isExpanded()) {

                        layout_normal_user.collapse();
                        layout_pub.collapse();
                        layout_lib.collapse();
                        layout_university.collapse();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                layout_company.expand();
                            }
                        }, 1000);
                    } else {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                layout_company.expand();
                            }
                        }, 1000);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void init_normalUserView(View view) {
        View userView = view.findViewById(R.id.normal_user_layout);
        if (userView != null) {
            n_userPhoto_container = (FrameLayout) userView.findViewById(R.id.userPhoto_container);
            n_userPhoto_container.setOnClickListener(this);
            addPhoto_tv_user = (TextView) userView.findViewById(R.id.addPhoto_tv_user);
            n_circleImageView = (CircleImageView) userView.findViewById(R.id.user_photo);


            n_userFirstName = (MaterialEditText) userView.findViewById(R.id.user_firstName);
            n_userFirstName.setAutoValidate(true);
            n_userFirstName.addValidator(new RegexpValidator(getString(R.string.inv_fn), Tags.name_Regex));
            n_userFirstName.setShowClearButton(false);

            n_userLastName = (MaterialEditText) userView.findViewById(R.id.user_lastName);
            n_userLastName.setAutoValidate(true);
            n_userLastName.addValidator(new RegexpValidator(getString(R.string.inv_ln), Tags.name_Regex));
            n_userLastName.setShowClearButton(false);


            n_userEmail = (MaterialEditText) userView.findViewById(R.id.user_email);
            n_userEmail.setAutoValidate(true);
            n_userEmail.setShowClearButton(false);
            n_userEmail.addValidator(new RegexpValidator(getString(R.string.invalidEmail), Tags.email_Regex));

            n_userPhone = (MaterialEditText) userView.findViewById(R.id.user_phone);
            n_userPhone.setAutoValidate(true);
            n_userPhone.addValidator(new RegexpValidator(getString(R.string.inv_pn), Tags.phone_Regex));
            n_userPhone.setOnClickListener(this);


            n_userCountry = (MaterialEditText) userView.findViewById(R.id.user_country);
            n_userCountry.setAutoValidate(true);
            n_userCountry.addValidator(new RegexpValidator(getString(R.string.empty_field),".+"));
            n_userCountry.setOnClickListener(this);

            n_user_userName = (MaterialEditText) userView.findViewById(R.id.user_userName);
            n_user_userName.setAutoValidate(true);
            n_user_userName.addValidator(new RegexpValidator(getString(R.string.invalid_username), Tags.username_Regex));
            n_user_userName.setShowClearButton(false);


            n_userPassword = (MaterialEditText) userView.findViewById(R.id.user_password);
            n_userPassword.setAutoValidate(true);
            n_userPassword.setShowClearButton(false);
            n_userPassword.addValidator(new RegexpValidator(getString(R.string.invalid_password), Tags.pass_Regex));


            n_SignInBtn = (Button) userView.findViewById(R.id.n_SignInBtn);
            n_SignInBtn.setOnClickListener(this);
        }


    }

    private void init_publisherView(View view) {
        View publisherView = view.findViewById(R.id.publisher_layout);
        if (publisherView != null) {
            pubPhoto_container = (FrameLayout) publisherView.findViewById(R.id.publisherPhoto_container);
            pub_circleImageView = (CircleImageView) publisherView.findViewById(R.id.publisher_photo);
            pubPhoto_container.setOnClickListener(this);
            addPhoto_tv_pub = (TextView) publisherView.findViewById(R.id.addPhoto_tv_pub);

            publisher_firstName = (MaterialEditText) publisherView.findViewById(R.id.publisher_firstName);
            publisher_firstName.setAutoValidate(true);
            publisher_firstName.setShowClearButton(false);
            publisher_firstName.addValidator(new RegexpValidator(getString(R.string.inv_fn), Tags.name_Regex));


            publisher_lastName = (MaterialEditText) publisherView.findViewById(R.id.publisher_lasttName);
            publisher_lastName.setAutoValidate(true);
            publisher_lastName.setShowClearButton(false);
            publisher_lastName.addValidator(new RegexpValidator(getString(R.string.inv_ln), Tags.name_Regex));


            publisherEmail = (MaterialEditText) publisherView.findViewById(R.id.publisher_email);
            publisherEmail.setAutoValidate(true);
            publisherEmail.setShowClearButton(false);
            publisherEmail.addValidator(new RegexpValidator(getString(R.string.invalidEmail), Tags.email_Regex));

            publisherCountry = (MaterialEditText) publisherView.findViewById(R.id.publisher_country);
            publisherCountry.setAutoValidate(true);
            publisherCountry.addValidator(new RegexpValidator(getString(R.string.empty_field),".+"));

            publisherCountry.setOnClickListener(this);

            publisherPhone = (MaterialEditText) publisherView.findViewById(R.id.publisher_phone);
            publisherPhone.setAutoValidate(true);
            publisherPhone.addValidator(new RegexpValidator(getString(R.string.inv_country), Tags.phone_Regex));
            publisherPhone.setOnClickListener(this);

            publisherAddress = (MaterialEditText) publisherView.findViewById(R.id.publisher_address);
            publisherAddress.setAutoValidate(true);
            publisherAddress.setShowClearButton(false);
            publisherAddress.addValidator(new RegexpValidator(getString(R.string.inv_address), ".+"));


            publisherTown = (MaterialEditText) publisherView.findViewById(R.id.publisher_town);
            publisherTown.setAutoValidate(true);
            publisherTown.setShowClearButton(false);
            publisherTown.addValidator(new RegexpValidator(getString(R.string.inv_town), ".+"));


            publisherUsername = (MaterialEditText) publisherView.findViewById(R.id.publisher_userName);
            publisherUsername.setAutoValidate(true);
            publisherUsername.setShowClearButton(false);
            publisherUsername.addValidator(new RegexpValidator(getString(R.string.invalid_username), Tags.username_Regex));


            publisher_webSite = (MaterialEditText) publisherView.findViewById(R.id.publisher_website);
            publisher_webSite.setAutoValidate(true);
            publisher_webSite.setShowClearButton(false);
            publisher_webSite.addValidator(new RegexpValidator(getString(R.string.invalid_url), Tags.url_Regex));

            publisherPassword = (MaterialEditText) publisherView.findViewById(R.id.publisher_password);
            publisherPassword.setAutoValidate(true);
            publisherPassword.setShowClearButton(false);
            publisherPassword.addValidator(new RegexpValidator(getString(R.string.invalid_password), Tags.pass_Regex));

            pub_SignInBtn = (Button) publisherView.findViewById(R.id.pub_SignInBtn);
            pub_SignInBtn.setOnClickListener(this);

        }


    }

    private void init_libraryView(View view) {
        View libraryView = view.findViewById(R.id.library_layout);
        if (libraryView != null) {

            libPhoto_container = (FrameLayout) libraryView.findViewById(R.id.libraryPhoto_container);
            libPhoto_container.setOnClickListener(this);
            lib_circleImageView = (CircleImageView) libraryView.findViewById(R.id.library_photo);
            addPhoto_tv_lib = (TextView) libraryView.findViewById(R.id.addPhoto_tv_lib);

            libraryName = (MaterialEditText) libraryView.findViewById(R.id.library_Name);
            libraryName.setAutoValidate(true);
            libraryName.setShowClearButton(false);
            libraryName.addValidator(new RegexpValidator(getString(R.string.inv_lib_name), Tags.name_Regex));

            libraryEmail = (MaterialEditText) libraryView.findViewById(R.id.library_email);
            libraryEmail.setAutoValidate(true);
            libraryEmail.setShowClearButton(false);
            libraryEmail.addValidator(new RegexpValidator(getString(R.string.invalidEmail), Tags.email_Regex));

            libraryPhone = (MaterialEditText) libraryView.findViewById(R.id.library_phone);
            libraryPhone.addValidator(new RegexpValidator(getString(R.string.invalid_phone), Tags.phone_Regex));
            libraryPhone.setOnClickListener(this);


            libraryCountry = (MaterialEditText) libraryView.findViewById(R.id.library_country);
            libraryCountry.setAutoValidate(true);
            libraryCountry.setOnClickListener(this);

            libraryAddress = (MaterialEditText) libraryView.findViewById(R.id.library_address);
            libraryAddress.setAutoValidate(true);
            libraryAddress.setShowClearButton(false);
            libraryAddress.addValidator(new RegexpValidator(getString(R.string.inv_address), ".+"));


            libraryUsername = (MaterialEditText) libraryView.findViewById(R.id.library_username);
            libraryUsername.setAutoValidate(true);
            libraryUsername.setShowClearButton(false);
            libraryUsername.addValidator(new RegexpValidator(getString(R.string.invalid_username), Tags.username_Regex));



            libraryPassword = (MaterialEditText) libraryView.findViewById(R.id.library_password);
            libraryPassword.setAutoValidate(true);
            libraryPassword.setShowClearButton(false);
            libraryPassword.addValidator(new RegexpValidator(getString(R.string.invalid_password), Tags.pass_Regex));


            library_type = (MaterialEditText) libraryView.findViewById(R.id.library_type);
            library_type.setOnClickListener(this);

            lib_SignInBtn = (Button) libraryView.findViewById(R.id.lib_SignInBtn);
            lib_SignInBtn.setOnClickListener(this);
        }


    }

    private void init_universityView(View view)
    {
        View universityView = view.findViewById(R.id.university_layout);
        if (universityView != null) {

            uniPhoto_container = (FrameLayout) universityView.findViewById(R.id.universityPhoto_container);
            uniPhoto_container.setOnClickListener(this);
            uni_circleImageView = (CircleImageView) universityView.findViewById(R.id.university_photo);
            addPhoto_tv_uni = (TextView) universityView.findViewById(R.id.addPhoto_tv_uni);

            universityName = (MaterialEditText) universityView.findViewById(R.id.university_Name);
            universityName.setAutoValidate(true);
            universityName.addValidator(new RegexpValidator(getString(R.string.inv_name), Tags.name_Regex));
            universityName.setShowClearButton(false);



            universityEmail = (MaterialEditText) universityView.findViewById(R.id.university_email);
            universityEmail.setAutoValidate(true);
            universityEmail.setShowClearButton(false);
            universityEmail.addValidator(new RegexpValidator(getString(R.string.invalidEmail), Tags.email_Regex));

            universityPhone = (MaterialEditText) universityView.findViewById(R.id.university_phone);
            universityPhone.setOnClickListener(this);

            universityCountry = (MaterialEditText) universityView.findViewById(R.id.university_country);
            universityCountry.setAutoValidate(true);
            universityCountry.addValidator(new RegexpValidator(getString(R.string.empty_field),".+"));
            universityCountry.setOnClickListener(this);

            universityAddress = (MaterialEditText) universityView.findViewById(R.id.university_address);
            universityAddress.setAutoValidate(true);
            universityAddress.setShowClearButton(false);
            universityAddress.addValidator(new RegexpValidator(getString(R.string.inv_address), ".+"));


            universityMajor = (MaterialEditText) universityView.findViewById(R.id.university_major);
            universityMajor.setAutoValidate(true);
            universityMajor.setShowClearButton(false);
            universityMajor.addValidator(new RegexpValidator(getString(R.string.inv_major), ".+"));


            universitySite = (MaterialEditText) universityView.findViewById(R.id.university_website);
            universitySite.setAutoValidate(true);
            universitySite.setShowClearButton(false);
            universitySite.addValidator(new RegexpValidator(getString(R.string.invalid_url), Tags.url_Regex));


            universityUsername = (MaterialEditText) universityView.findViewById(R.id.university_username);
            universityUsername.setAutoValidate(true);
            universityUsername.setShowClearButton(false);
            universityUsername.addValidator(new RegexpValidator(getString(R.string.invalid_username), Tags.username_Regex));


            universityPassword = (MaterialEditText) universityView.findViewById(R.id.university_password);
            universityPassword.setAutoValidate(true);
            universityPassword.setShowClearButton(false);
            universityPassword.addValidator(new RegexpValidator(getString(R.string.invalid_password), Tags.pass_Regex));


            uni_SignInBtn = (Button) universityView.findViewById(R.id.university_SignInBtn);
            uni_SignInBtn.setOnClickListener(this);
    }
    }

    private void init_companyView(View view)
    {
        View companyView = view.findViewById(R.id.company_layout);
        if (companyView != null) {

            compPhoto_container = (FrameLayout) companyView.findViewById(R.id.companyPhoto_container);
            compPhoto_container.setOnClickListener(this);
            comp_circleImageView = (CircleImageView) companyView.findViewById(R.id.company_photo);
            addPhoto_tv_comp = (TextView) companyView.findViewById(R.id.addPhoto_tv_comp);


            companyName = (MaterialEditText) companyView.findViewById(R.id.company_Name);
            companyName.setAutoValidate(true);
            companyName.addValidator(new RegexpValidator(getString(R.string.inv_name), Tags.name_Regex));
            companyName.setShowClearButton(false);


            companyEmail = (MaterialEditText) companyView.findViewById(R.id.company_email);
            companyEmail.setAutoValidate(true);
            companyEmail.setShowClearButton(false);
            companyEmail.addValidator(new RegexpValidator(getString(R.string.invalidEmail), Tags.email_Regex));


            companyPhone = (MaterialEditText) companyView.findViewById(R.id.company_phone);
            companyPhone.setOnClickListener(this);

            companyCountry = (MaterialEditText) companyView.findViewById(R.id.company_country);
              companyCountry.setOnClickListener(this);

            companyAddress = (MaterialEditText) companyView.findViewById(R.id.company_address);
            companyAddress.setAutoValidate(true);
            companyAddress.setShowClearButton(false);
            companyAddress.addValidator(new RegexpValidator(getString(R.string.inv_address), ".+"));


            companyTown = (MaterialEditText) companyView.findViewById(R.id.company_town);
            companyTown.setAutoValidate(true);
            companyTown.setShowClearButton(false);
            companyTown.addValidator(new RegexpValidator(getString(R.string.inv_town), ".+"));


            companySite = (MaterialEditText) companyView.findViewById(R.id.company_website);
            companySite.setAutoValidate(true);
            companySite.setShowClearButton(false);
            companySite.addValidator(new RegexpValidator(getString(R.string.invalid_url), Tags.url_Regex));


            companyUsername = (MaterialEditText) companyView.findViewById(R.id.company_username);
            companyUsername.setAutoValidate(true);
            companyUsername.setShowClearButton(false);
            companyUsername.addValidator(new RegexpValidator(getString(R.string.invalid_username), Tags.username_Regex));



            companyPassword = (MaterialEditText) companyView.findViewById(R.id.company_password);
            companyPassword.setAutoValidate(true);
            companyPassword.setShowClearButton(false);
            companyPassword.addValidator(new RegexpValidator(getString(R.string.invalid_password), Tags.pass_Regex));


            comp_SignInBtn = (Button) companyView.findViewById(R.id.company_SignInBtn);
            comp_SignInBtn.setOnClickListener(this);
        }
    }


    @Override
    public void setNormalUserPhoto_Error() {
        Toast.makeText(mContext, R.string.ch_photo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNormalUserFirstName_Error() {
        n_userFirstName.setError(getString(R.string.empty_field));

    }

    @Override
    public void setNormalUser_invalidFirstName_Error() {
        n_userFirstName.setError(getString(R.string.inv_fn));

    }

    @Override
    public void setNormalUserLastName_Error() {
        n_userLastName.setError(getString(R.string.empty_field));

    }

    @Override
    public void setNormalUser_invalidLastName_Error() {

        n_userLastName.setError(getString(R.string.inv_ln));
    }

    @Override
    public void setNormalUserEmail_Error() {
        n_userEmail.setError(getString(R.string.empty_field));


    }

    @Override
    public void setNormalUser_invalidUsername_Error() {
        n_user_userName.setError(getString(R.string.invalid_username));
    }

    @Override
    public void setNormalUserPhone_Error() {
        n_userPhone.setError(getString(R.string.empty_field));
    }

    @Override
    public void setNormalUser_username_Error() {
        n_user_userName.setError(getString(R.string.empty_field));
    }

    @Override
    public void setNormalUser_invalidEmail_Error()
    {
        n_userEmail.setError(getString(R.string.invalidEmail));

    }

    @Override
    public void setNormalUserCountry_Error() {
        n_userCountry.setError(getString(R.string.empty_field));


    }

    @Override
    public void setNormalUserPassword_Error() {
        n_userPassword.setError(getString(R.string.empty_field));


    }

    @Override
    public void setNormalUser_invalidPassword_Error() {
        n_userPassword.setError(getString(R.string.invalid_password));
    }

    @Override
    public void setPublisherPhoto_Error() {
        Toast.makeText(mContext, R.string.ch_photo, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setPublisherFirstName_Error() {
        publisher_firstName.setError(getString(R.string.empty_field));
    }

    @Override
    public void setPublisher_invalidFirstName_Error() {

        publisher_firstName.setError(getString(R.string.inv_fn));
    }

    @Override
    public void setPublisherLastName_Error() {
        publisher_lastName.setError(getString(R.string.empty_field));

    }

    @Override
    public void setPublisher_invalidLastName_Error() {
        publisher_lastName.setError(getString(R.string.inv_ln));

    }

    @Override
    public void setPublisherEmail_Error() {

        publisherEmail.setError(getString(R.string.empty_field));

    }

    @Override
    public void setPublisher_invalidEmail_Error() {
        publisherEmail.setError(getString(R.string.invalidEmail));

    }

    @Override
    public void setPublisherPhone_Error() {
        publisherPhone.setError(getString(R.string.empty_field));
    }

    @Override
    public void setPublisherCountry_Error() {
        publisherCountry.setError(getString(R.string.empty_field));


    }

    @Override
    public void setPublisherAddress_Error() {
        publisherAddress.setError(getString(R.string.empty_field));
    }

    @Override
    public void setPublisherTown_Error() {
        publisherTown.setError(getString(R.string.empty_field));
    }

    @Override
    public void setPublisher_site_Error() {
        publisher_webSite.setError(getString(R.string.empty_field));
    }

    @Override
    public void setPublisher_invalidSite_Error() {
        publisher_webSite.setError(getString(R.string.inv_site));

    }

    @Override
    public void setPublisher_username_Error() {
        publisherUsername.setError(getString(R.string.invalid_username));
    }

    @Override
    public void setPublisher_invalidUsername_Error() {
        publisherUsername.setError(getString(R.string.invalid_username));
    }

    @Override
    public void setPublisherPassword_Error() {

        publisherPassword.setError(getString(R.string.empty_field));

    }
    @Override
    public void setPublisher_invalidPassword_Error() {
        publisherPassword.setError(getString(R.string.invalid_password));
    }

    @Override
    public void setPublisherLat_LngError() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CreateDialogForLocation();

            }
        },1000);

    }

    @Override
    public void setLibraryPhoto_Error() {
        Toast.makeText(mContext, R.string.ch_photo, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setLibraryName_Error() {
        libraryName.setError(getString(R.string.empty_field));

    }

    @Override
    public void setLibrary_invalidName_Error() {

        libraryName.setError(getString(R.string.inv_name));
    }

    @Override
    public void setLibraryEmail_Error() {
        libraryEmail.setError(getString(R.string.empty_field));
    }

    @Override
    public void setLibrary_invalidEmail_Error() {
        libraryEmail.setError(getString(R.string.invalidEmail));
    }

    @Override
    public void setLibraryPhone_Error() {
        libraryPhone.setError(getString(R.string.empty_field));
    }


    @Override
    public void setLibraryType_Error() {
        library_type.setError(getString(R.string.empty_field));


    }

    @Override
    public void setLibraryCountry_Error() {
        libraryCountry.setError(getString(R.string.empty_field));
    }

    @Override
    public void setLibraryAddress_Error() {
        libraryAddress.setError(getString(R.string.empty_field));
    }

    @Override
    public void setLibraryUsername_Error() {
        libraryUsername.setError(getString(R.string.empty_field));
    }

    @Override
    public void setLibrary_invalidUsername_Error() {
        libraryUsername.setError(getString(R.string.invalid_username));
    }

    @Override
    public void setLibraryPassword_Error() {
        libraryPassword.setError(getString(R.string.empty_field));

    }

    @Override
    public void setLibrary_invalidPassword_Error() {
        libraryPassword.setError(getString(R.string.invalid_password));
    }

    @Override
    public void setLibraryLat_Lng_Error() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CreateDialogForLocation();

            }
        },1000);

    }

    @Override
    public void setUniversityPhoto_Error() {
        Toast.makeText(mContext, R.string.ch_photo, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setUniversityName_Error() {
        universityName.setError(getString(R.string.empty_field));
    }

    @Override
    public void setUniversity_invalidName_Error() {
        universityName.setError(getString(R.string.inv_name));
    }

    @Override
    public void setUniversityEmail_Error() {
        universityEmail.setError(getString(R.string.empty_field));

    }

    @Override
    public void setUniversity_invalidEmail_Error() {
        universityEmail.setError(getString(R.string.empty_field));

    }

    @Override
    public void setUniversityPhone_Error() {
        universityPhone.setError(getString(R.string.empty_field));
    }

    @Override
    public void setUniversityCountry_Error() {
        universityCountry.setError(getString(R.string.empty_field));
    }

    @Override
    public void setUniversityAddress_Error() {
        universityAddress.setError(getString(R.string.empty_field));
    }

    @Override
    public void setUniversitySite_Error() {
        universitySite.setError(getString(R.string.empty_field));

    }

    @Override
    public void setUniversity_invalidSite_Error() {
        universitySite.setError(getString(R.string.inv_site));

    }

    @Override
    public void setUniversityMajor_Error() {
        universityMajor.setError(getString(R.string.empty_field));
    }

    @Override
    public void setUniversityUsername_Error() {
        universityUsername.setError(getString(R.string.empty_field));
    }

    @Override
    public void setUniversity_invalidUsername_Error()
    {
        universityUsername.setError(getString(R.string.invalid_username));
    }

    @Override
    public void setUniversityPassword_Error()
    {
        universityPassword.setError(getString(R.string.empty_field));

    }

    @Override
    public void setUniversity_invalidPassword_Error()
    {
        universityPassword.setError(getString(R.string.empty_field));

    }

    @Override
    public void setUniversityLat_Lng_Error()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CreateDialogForLocation();

            }
        },1000);
    }

    @Override
    public void setCompanyPhoto_Error() {
        Toast.makeText(mContext, R.string.ch_photo, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setCompanyName_Error() {
        companyName.setError(getString(R.string.empty_field));
    }

    @Override
    public void setCompany_invalidName_Error() {
        companyName.setError(getString(R.string.inv_name));
    }

    @Override
    public void setCompanyEmail_Error() {
        companyEmail.setError(getString(R.string.empty_field));

    }

    @Override
    public void setCompany_invalidEmail_Error() {
        companyEmail.setError(getString(R.string.invalidEmail));

    }

    @Override
    public void setCompanyPhone_Error() {
        companyPhone.setError(getString(R.string.empty_field));
    }

    @Override
    public void setCompanyCountry_Error() {
        companyCountry.setError(getString(R.string.empty_field));
    }

    @Override
    public void setCompanyAddress_Error() {
        companyAddress.setError(getString(R.string.empty_field));
    }

    @Override
    public void setCompanyTown_Error() {
        companyTown.setError(getString(R.string.empty_field));
    }



    @Override
    public void setCompanySite_Error() {

        companySite.setError(getString(R.string.empty_field));
    }

    @Override
    public void setCompany_invalidSite_Error() {
        companySite.setError(getString(R.string.inv_site));
    }

    @Override
    public void setCompanyUsername_Error() {
        companyUsername.setError(getString(R.string.empty_field));
    }

    @Override
    public void setCompany_invalidUsername_Error() {
        companyUsername.setError(getString(R.string.invalid_username));

    }

    @Override
    public void setCompanyPassword_Error() {
        companyPassword.setError(getString(R.string.empty_field));

    }

    @Override
    public void setCompany_invalidPassword_Error() {
        companyPassword.setError(getString(R.string.empty_field));

    }

    @Override
    public void setCompanyLat_Lng_Error() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CreateDialogForLocation();

            }
        },1000);

    }


    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }


    @Override
    public void onFailed(final String error) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CreateAlertDialog(error);

            }
        },1000);
    }

    @Override
    public void onNormalUserDataSuccess(final NormalUserData normalUserData) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getActivity(),HomeActivity.class);
                intent.putExtra("userData",normalUserData);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Preferences pref = new Preferences(getActivity());
                pref.Session("loggedin");
                startActivity(intent);
            }
        },500);
    }

    @Override
    public void onPublisherDataSuccess(final PublisherModel publisherModel) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getActivity(),HomeActivity.class);
                intent.putExtra("publisherData",publisherModel);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Preferences pref = new Preferences(getActivity());
                pref.Session("loggedin");
                startActivity(intent);
            }
        },500);

    }

    @Override
    public void onLibraryDataSuccess(final LibraryModel libraryModel) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getActivity(),HomeActivity.class);
                intent.putExtra("libraryData",libraryModel);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Preferences pref = new Preferences(getActivity());
                pref.Session("loggedin");
                startActivity(intent);
            }
        },500);

       // Toast.makeText(mContext, "lib success"+libraryModel.getLib_name()+"\n"+libraryModel.getLat()+"\n"+libraryModel.getUser_type(), Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onUniversityDataSuccess(final UniversityModel universityModel) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                Intent intent = new Intent(getActivity(),HomeActivity.class);
                intent.putExtra("universityData",universityModel);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Preferences pref = new Preferences(getActivity());
                pref.Session("loggedin");
                getActivity().startActivity(intent);
            }
        },500);

    }

    @Override
    public void onCompanyDataSuccess(final CompanyModel companyModel) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getActivity(),HomeActivity.class);
                intent.putExtra("companyData",companyModel);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Preferences pref = new Preferences(getActivity());
                pref.Session("loggedin");
                getActivity().startActivity(intent);
            }
        },500);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.n_SignInBtn:
                getDeviceLocation();
                break;
            case R.id.user_country:
                getCountry(COUNTRY_REQUEST_USER);
                break;

            case R.id.publisher_country:
                getCountry(COUNTRY_REQUEST_PUB);
                break;
            case R.id.library_country:
                getCountry(COUNTRY_REQUEST_LIB);
                break;
            case R.id.university_country:
                getCountry(COUNTRY_REQUEST_UNI);
                break;
            case R.id.company_country:
                getCountry(COUNTRY_REQUEST_COMP);
                break;
            case R.id.pub_SignInBtn:
                initPublisherData("","");
                break;
            case R.id.lib_SignInBtn:
                initLibraryData("","");
                break;

            case R.id.company_SignInBtn:
                initCompanyData("","");
                break;


            case R.id.university_SignInBtn:
                initUniversityData("","");
                  break;


            case R.id.userPhoto_container:
                SelectUserPhoto(IMAGE_REQUEST_USER);
                break;
            case R.id.publisherPhoto_container:
                SelectUserPhoto(IMAGE_REQUEST_PUB);
                break;

            case R.id.libraryPhoto_container:
                SelectUserPhoto(IMAGE_REQUEST_LIB);
                break;
            case R.id.universityPhoto_container:
                SelectUserPhoto(IMAGE_REQUEST_UNI);
                break;
            case R.id.companyPhoto_container:
                SelectUserPhoto(IMAGE_REQUEST_COMP);
                break;

            case R.id.library_type:
                getLibraryType(LIBRARY_TYPE_REQUEST);
                break;

            case R.id.user_phone:
                getPhoneNumber(PHONE_REQUEST_USER);
                break;

            case R.id.publisher_phone:
                getPhoneNumber(PHONE_REQUEST_PUB);

                break;
            case R.id.library_phone:
                getPhoneNumber(PHONE_REQUEST_LIB);

                break;
            case R.id.university_phone:
                getPhoneNumber(PHONE_REQUEST_UNI);

                break;
            case R.id.company_phone:
                getPhoneNumber(PHONE_REQUEST_COMP);

                break;

        }

    }

    private void getPhoneNumber(int phone_request_user) {

        Intent intent = new Intent(getActivity(), Activity_PhoneNumber.class);
        startActivityForResult(intent,phone_request_user);
    }

    private void getLibraryType(int library_type_request) {
        Intent intent = new Intent(getActivity(),Activity_Search_Results.class);
        intent.putExtra("searchType","libType");
        getActivity().startActivityForResult(intent,library_type_request);
    }

    private void getCountry(int country_request_code) {
        Intent intent = new Intent(getActivity(), Activity_Search_Results.class);
        intent.putExtra("searchType","country");
        getActivity().startActivityForResult(intent,country_request_code);
    }


    private void SelectUserPhoto(int REQ_CODE) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        getActivity().startActivityForResult(intent.createChooser(intent, "Choose your photo"), REQ_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == IMAGE_REQUEST_USER) {

            if (data != null) {


                try {
                    userImage_URI = data.getData();
                    userBitmap_image = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(userImage_URI));
                    n_circleImageView.setImageBitmap(userBitmap_image);
                    addPhoto_tv_user.setVisibility(View.GONE);
                    user_image = encodeUserPhoto(userBitmap_image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
            }
        }else if (requestCode==IMAGE_REQUEST_PUB)
        {
            if (data != null) {


                try {
                    pubImage_URI = data.getData();

                    pubBitmap_image = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(pubImage_URI));
                    pub_circleImageView.setImageBitmap(pubBitmap_image);
                    addPhoto_tv_pub.setVisibility(View.GONE);
                    pub_image = encodeUserPhoto(pubBitmap_image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
            }
        }
        else if (requestCode==IMAGE_REQUEST_LIB)
        {
            if (data != null) {


                try {
                    libImage_URI = data.getData();

                    libBitmap_image = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(libImage_URI));
                    lib_circleImageView.setImageBitmap(libBitmap_image);
                    addPhoto_tv_lib.setVisibility(View.GONE);
                    lib_image = encodeUserPhoto(libBitmap_image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
            }
        }
        else if (requestCode==IMAGE_REQUEST_UNI)
        {
            if (data != null) {


                try {
                    uniImage_URI = data.getData();

                    uniBitmap_image = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uniImage_URI));
                    uni_circleImageView.setImageBitmap(uniBitmap_image);
                    addPhoto_tv_uni.setVisibility(View.GONE);
                    uni_image = encodeUserPhoto(uniBitmap_image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
            }
        }
        else if (requestCode==IMAGE_REQUEST_COMP)
        {
            if (data != null) {


                try {
                    compImage_URI = data.getData();

                    compBitmap_image = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(compImage_URI));
                    comp_circleImageView.setImageBitmap(compBitmap_image);
                    addPhoto_tv_comp.setVisibility(View.GONE);
                    comp_image = encodeUserPhoto(compBitmap_image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
            }
        }
        else if (requestCode==COUNTRY_REQUEST_USER)
        {
            if (data!=null && resultCode== Activity.RESULT_OK)
            {
                CountriesModel countriesModel = (CountriesModel) data.getSerializableExtra("country_data");
                n_country_id = countriesModel.getCountry_id();
                n_userCountry.setText(countriesModel.getCountry_title()+"-"+countriesModel.getCountry_flag());

            }
              }
        else if (requestCode==COUNTRY_REQUEST_PUB)
        {
            if (data!=null && resultCode== Activity.RESULT_OK)
            {
                CountriesModel countriesModel = (CountriesModel) data.getSerializableExtra("country_data");
                p_country_id = countriesModel.getCountry_id();
                publisherCountry.setText(countriesModel.getCountry_title()+"-"+countriesModel.getCountry_flag());

            }

        }
        else if (requestCode==COUNTRY_REQUEST_LIB)
        {
            if (data!=null && resultCode== Activity.RESULT_OK)
            {
                CountriesModel countriesModel = (CountriesModel) data.getSerializableExtra("country_data");
                l_country_id = countriesModel.getCountry_id();
                libraryCountry.setText(countriesModel.getCountry_title()+"-"+countriesModel.getCountry_flag());

            }

        }
        else if (requestCode==COUNTRY_REQUEST_UNI)
        {
            if (data!=null && resultCode== Activity.RESULT_OK)
            {
                CountriesModel countriesModel = (CountriesModel) data.getSerializableExtra("country_data");
                u_country_id = countriesModel.getCountry_id();
                universityCountry.setText(countriesModel.getCountry_title()+"-"+countriesModel.getCountry_flag());

            }

        }
        else if (requestCode==COUNTRY_REQUEST_COMP)
        {
            if (data!=null && resultCode== Activity.RESULT_OK)
            {
                CountriesModel countriesModel = (CountriesModel) data.getSerializableExtra("country_data");
                c_country_id = countriesModel.getCountry_id();
                companyCountry.setText(countriesModel.getCountry_title()+"-"+countriesModel.getCountry_flag());

            }

        }
        else if (requestCode==LIBRARY_TYPE_REQUEST)
        {
            if (data!=null && resultCode== Activity.RESULT_OK)
            {
                LibraryType_Model libraryType_model = (LibraryType_Model) data.getSerializableExtra("libType_data");
                library_type.setText(libraryType_model.getLib_type_title());
                libType = libraryType_model.getLib_type_id();

            }

        }
        else if (requestCode==PHONE_REQUEST_USER)
        {
            if (data!=null && resultCode== Activity.RESULT_OK)
            {
                String phoneNumber =data.getStringExtra("phone_number");
                n_userPhone.setText(phoneNumber);
            }

        }
        else if (requestCode==PHONE_REQUEST_PUB)
        {
            if (data!=null && resultCode== Activity.RESULT_OK)
            {
                String phoneNumber =data.getStringExtra("phone_number");
                publisherPhone.setText(phoneNumber);
            }

        }
        else if (requestCode==PHONE_REQUEST_LIB)
        {
            if (data!=null && resultCode== Activity.RESULT_OK)
            {
                String phoneNumber =data.getStringExtra("phone_number");
                libraryPhone.setText(phoneNumber);
            }

        }
        else if (requestCode==PHONE_REQUEST_UNI)
        {
            if (data!=null && resultCode== Activity.RESULT_OK)
            {
                String phoneNumber =data.getStringExtra("phone_number");
                universityPhone.setText(phoneNumber);
            }

        }
        else if (requestCode==PHONE_REQUEST_COMP)
        {
            if (data!=null && resultCode== Activity.RESULT_OK)
            {
                String phoneNumber =data.getStringExtra("phone_number");
                companyPhone.setText(phoneNumber);
            }

        }

        else if (REQUEST_CODE == requestCode && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                if (data.hasExtra("lat") && data.hasExtra("lng")) {
                    double lat = data.getExtras().getDouble("lat");
                    double lng = data.getExtras().getDouble("lng");
                    int position = spinner.getSelectedItemPosition();
                    if (position==1)
                    {
                        initPublisherData(String.valueOf(lat),String.valueOf(lng));
                    }else if (position==2)
                    {
                        initLibraryData(String.valueOf(lat),String.valueOf(lng));

                    }
                    else if (position==3)
                    {
                        initUniversityData(String.valueOf(lat),String.valueOf(lng));

                    }else if (position==4)
                    {
                        initCompanyData(String.valueOf(lat),String.valueOf(lng));
                    }


                }

            }
        }
    }

    private void CreateDialogForLocation()
    {
        LovelyStandardDialog dialog = new LovelyStandardDialog(getActivity())
                .setTitle(R.string.sel_lib_loc)
                .setMessage(R.string.lib_loc)
                .setTopColor(ActivityCompat.getColor(getActivity(), R.color.centercolor))
                .setIcon(R.drawable.help_menu_icon)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.y), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getDeviceLocation();
                    }
                })
                .setPositiveButtonColor(ContextCompat.getColor(getActivity(), R.color.centercolor))
                .setNegativeButton(R.string.select_loc, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getActivity(), MapsActivity.class);
                        getActivity().startActivityForResult(intent, REQUEST_CODE);


                    }
                });
        dialog.create();
        dialog.show();
    }
    private void CreateProgressDialog()
    {
        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.sign_in));
        progressDialog.setCancelable(true);
        ProgressBar progressBar = new ProgressBar(getActivity());
        Drawable drawable =progressBar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ActivityCompat.getColor(getActivity(),R.color.centercolor), PorterDuff.Mode.SRC_IN);
        progressDialog.setIndeterminateDrawable(drawable);



    }
    private void CreateAlertDialog(String msg)
    {
        final LovelyStandardDialog dialog = new LovelyStandardDialog(getActivity())
                .setTopColor(ContextCompat.getColor(getActivity(),R.color.centercolor))
                .setTopTitle(getString(R.string.error))
                .setTopTitleColor(ContextCompat.getColor(getActivity(),R.color.base))
                .setPositiveButtonColorRes(R.color.centercolor)
                .setMessage(msg);
        dialog.setPositiveButton(getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }
    private String encodeUserPhoto(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte [] bytes = outputStream.toByteArray();

        String encodeImage = Base64.encodeToString(bytes,Base64.DEFAULT);
        return encodeImage;
    }
    private void getDeviceLocation()
    {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> locationTask = mFusedLocationProviderClient.getLastLocation();
        locationTask.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                if (task.isSuccessful())
                {
                    Location location = task.getResult();
                    if (location!=null)
                    {

                        double lat = location.getLatitude();
                        double lng = location.getLongitude();
                        int userType = spinner.getSelectedItemPosition();
                        if (userType==0)
                        {
                            initNormalUserData(String.valueOf(lat),String.valueOf(lng));
                        }
                        else if (userType==1)
                        {
                            initPublisherData(String.valueOf(lat),String.valueOf(lng));
                        }else if (userType==2)
                        {
                            initLibraryData(String.valueOf(lat),String.valueOf(lng));
                        }
                        else if (userType==3)
                        {
                            initUniversityData(String.valueOf(lat),String.valueOf(lng));
                        }
                        else if (userType==4)
                        {
                            initCompanyData(String.valueOf(lat),String.valueOf(lng));
                        }


                    }
                }
            }
        });
    }
    private void initNormalUserData(String lat,String lng)
    {
        String userType   = "user";
        String n_fname    = n_userFirstName .getText().toString();
        String n_lname    = n_userLastName  .getText().toString();
        String n_email    = n_userEmail     .getText().toString();
        String n_country  = n_country_id;
        String n_phone    = n_userPhone     .getText().toString();
        String n_username = n_user_userName .getText().toString();
        String n_password = n_userPassword  .getText().toString();
        String token = FirebaseInstanceId.getInstance().getToken();

        presenter.NormalUserRegistration(userType,user_image,n_fname,n_lname,n_email,n_country,n_phone,n_username,n_password,lat,lng,token);

    }
    private void initPublisherData(String lat,String lng)
    {
        String userType2    = "publisher";
        String pub_fname    = publisher_firstName.getText().toString();
        String pub_lname    = publisher_lastName .getText().toString();
        String pub_email    = publisherEmail     .getText().toString();
        String pub_country  = p_country_id;
        String pub_password = publisherPassword  .getText().toString();
        String pub_phone    = publisherPhone     .getText().toString();
        String pub_address  = publisherAddress   .getText().toString();
        String pub_town     = publisherTown      .getText().toString();
        String pub_username = publisherUsername  .getText().toString();
        String pub_site     = publisher_webSite  .getText().toString();
        String token = FirebaseInstanceId.getInstance().getToken();

        presenter.PublisherRegistration(userType2,pub_image,pub_fname,pub_lname,pub_email,pub_country,pub_password,pub_phone,pub_username,pub_address,pub_town,pub_site,lat,lng,token);

    }
    private void initLibraryData(String lat,String lng)
    {
        String userType = "library";
        String lib_name     = libraryName     .getText().toString();
        String lib_email    = libraryEmail    .getText().toString();
        String lib_phone    = libraryPhone    .getText().toString();
        String lib_country  = l_country_id;
        String lib_address  = libraryAddress  .getText().toString();
        String lib_userName = libraryUsername .getText().toString();
        String lib_password = libraryPassword .getText().toString();
        String lib_type     =  libType;
        String token = FirebaseInstanceId.getInstance().getToken();



        presenter.LibraryRegistration(userType,lib_image,lib_name,lib_email,lib_phone,lib_country,lib_address,lib_type,lib_userName,lib_password,lat,lng,token);

    }
    private void initUniversityData(String lat, String lng)
    {
        String userType = "university";
        String uni_name      = universityName     .getText().toString();
        String uni_email     = universityEmail    .getText().toString();
        String uni_phone     = universityPhone    .getText().toString();
        String uni_country   = u_country_id;
        String uni_address   = universityAddress  .getText().toString();
        String uni_major     = universityMajor    .getText().toString();
        String uni_site      = universitySite     .getText().toString();
        String uni_userName  = universityUsername .getText().toString();
        String uni_password  = universityPassword .getText().toString();
        String token = FirebaseInstanceId.getInstance().getToken();


        presenter.UniversityRegistration(userType,uni_image,uni_name,uni_email,uni_country,uni_phone,uni_userName,uni_password,uni_major,uni_address,uni_site,lat,lng,token);

    }
    private void initCompanyData(String lat,String lng)
    {
        String userType = "company";
        String comp_name     = companyName     .getText().toString();
        String comp_email    = companyEmail    .getText().toString();
        String comp_phone    = companyPhone    .getText().toString();
        String comp_country  = c_country_id;
        String comp_address  = companyAddress  .getText().toString();
        String comp_town     = companyTown     .getText().toString();
        String comp_site     = companySite     .getText().toString();
        String comp_userName = companyUsername .getText().toString();
        String comp_password = companyPassword .getText().toString();
        String token = FirebaseInstanceId.getInstance().getToken();

        presenter.CompanyRegistration(userType,comp_image,comp_name,comp_email,comp_country,comp_phone,comp_userName,comp_password,comp_address,comp_town,comp_site,lat,lng,token);

    }
}

