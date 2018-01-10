package com.example.omd.library.Fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omd.library.Activities.MapsActivity;
import com.example.omd.library.Login_Register.Registration.PresenterImp;
import com.example.omd.library.Login_Register.Registration.ViewData;
import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.R;
import com.example.omd.library.Services.Tags;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Delta on 08/12/2017.
 */

public class RegisterFragment extends Fragment implements ViewData, View.OnClickListener {

    private ExpandableRelativeLayout layout_normal_user, layout_pub, layout_lib, library_spinner_expanded;
    private AppCompatSpinner spinner, lib_spinner;
    private FrameLayout n_userPhoto_container;
    private CircleImageView n_circleImageView;
    private MaterialEditText n_userFirstName, n_userLastName, n_userEmail, n_userPhone, n_userCountry, n_userJob, n_userInterests, n_userPassword;
    private MaterialEditText publisher_firstName, publisher_lastName, publisherEmail, publisherCountry, publisherPhone, publisherExpertise, publisher_webSite, publisherPassword;
    private MaterialEditText libraryName, libraryEmail, libraryCommission, libraryCountry, libraryExpertise, libraryPassword, library_otherType;
    private Button n_SignInBtn, lib_SignInBtn, pub_SignInBtn;
    private ScrollView lib_scrollView;
    Context mContext;
    Handler handler;
    PresenterImp presenter;
    public Uri userImage_URI = null;
    public Bitmap userBitmap_image = null;
    private static final int IMAGE_REQUEST = 200;
    private static final int REQUEST_CODE = 300;
    private TextView addPhoto_tv;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ProgressDialog  progressDialog;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_fragment, container, false);
        initView(view);
        init_normalUserView(view);
        init_publisherView(view);
        init_libraryView(view);

        presenter = new PresenterImp(mContext,this);
        return view;
    }

    private void initView(View view) {

        handler = new Handler();
        mContext = view.getContext();
        layout_normal_user = (ExpandableRelativeLayout) view.findViewById(R.id.expandable_normal_user);
        layout_pub = (ExpandableRelativeLayout) view.findViewById(R.id.expandable_publisher);
        layout_lib = (ExpandableRelativeLayout) view.findViewById(R.id.expandable_lib);
        library_spinner_expanded = (ExpandableRelativeLayout) view.findViewById(R.id.library_spinner_expanded);
        library_spinner_expanded.collapse();
        layout_normal_user.collapse();
        layout_lib.collapse();
        spinner = (AppCompatSpinner) view.findViewById(R.id.spinner);

        lib_spinner = (AppCompatSpinner) view.findViewById(R.id.lib_type);
        lib_spinner.setSelection(0);
        final ArrayAdapter<String> adapter_type = new ArrayAdapter<String>(mContext, R.layout.spinner_row, getResources().getStringArray(R.array.libType));
        lib_spinner.setAdapter(adapter_type);


        spinner.setSelection(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_row, getResources().getStringArray(R.array.spinnerArray));
        spinner.setAdapter(adapter);

        if (spinner.getSelectedItem().toString().equals("Normal user")) {
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

        lib_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getSelectedView()).setTextColor(ContextCompat.getColor(mContext, R.color.label));
                ((TextView) adapterView.getSelectedView()).setGravity(Gravity.CENTER);
                if (adapterView.getSelectedItem().toString().equals("Other")) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            library_spinner_expanded.expand();

                        }
                    }, 1000);

                } else {
                    library_spinner_expanded.collapse();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getSelectedView()).setGravity(Gravity.CENTER);
                ((TextView) adapterView.getSelectedView()).setTextColor(ContextCompat.getColor(mContext, R.color.label));
                if (adapterView.getSelectedItem().toString().equals("Normal user")) {
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
                } else if (adapterView.getSelectedItem().toString().equals("Publisher")) {
                    if (layout_normal_user.isExpanded() || layout_lib.isExpanded()) {

                        layout_normal_user.collapse();
                        layout_lib.collapse();
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

                } else if (adapterView.getSelectedItem().toString().equals("Library")) {
                    if (layout_normal_user.isExpanded() || layout_pub.isExpanded()) {

                        layout_normal_user.collapse();
                        layout_pub.collapse();
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
            addPhoto_tv = (TextView) userView.findViewById(R.id.addPhoto_tv);
            n_circleImageView = (CircleImageView) userView.findViewById(R.id.user_photo);

            n_userFirstName = (MaterialEditText) userView.findViewById(R.id.user_firstName);
            n_userFirstName.setAutoValidate(true);
            n_userFirstName.addValidator(new RegexpValidator("invalid first name", "[^0-9@!#$%^&*()-\\*\\+<>/\\+~\\s]+"));
            n_userFirstName.setShowClearButton(true);

            n_userLastName = (MaterialEditText) userView.findViewById(R.id.user_lastName);
            n_userLastName.setAutoValidate(true);
            n_userLastName.setShowClearButton(true);
            n_userLastName.addValidator(new RegexpValidator("invalid last name", "[^0-9@!#$%^&*()-\\*\\+<>/\\+~\\s]+"));


            n_userEmail = (MaterialEditText) userView.findViewById(R.id.user_email);
            n_userEmail.setAutoValidate(true);
            n_userEmail.setShowClearButton(true);
            n_userEmail.addValidator(new RegexpValidator(getString(R.string.invalidEmail), Tags.email_Regex));

            n_userPhone = (MaterialEditText) userView.findViewById(R.id.user_phone);
            n_userPhone.setAutoValidate(true);
            n_userPhone.setShowClearButton(true);

            n_userCountry = (MaterialEditText) userView.findViewById(R.id.user_country);
            n_userCountry.setAutoValidate(true);
            n_userCountry.setShowClearButton(true);
            n_userCountry.addValidator(new RegexpValidator("invalid country name", ".+"));


            n_userJob = (MaterialEditText) userView.findViewById(R.id.user_job);
            n_userJob.setAutoValidate(true);
            n_userJob.setShowClearButton(true);


            n_userInterests = (MaterialEditText) userView.findViewById(R.id.user_interests);
            n_userInterests.setAutoValidate(true);
            n_userInterests.setShowClearButton(true);


            n_userPassword = (MaterialEditText) userView.findViewById(R.id.user_password);
            n_userPassword.setAutoValidate(true);
            n_userPassword.setShowClearButton(true);
            n_userPassword.addValidator(new RegexpValidator("invalid password", "[A-Za-z0-9_.-]+"));


            n_SignInBtn = (Button) userView.findViewById(R.id.n_SignInBtn);
            n_SignInBtn.setOnClickListener(this);
        }


    }

    private void init_publisherView(View view) {
        View publisherView = view.findViewById(R.id.publisher_layout);
        if (publisherView != null) {
            publisher_firstName = (MaterialEditText) publisherView.findViewById(R.id.publisher_firstName);
            publisher_firstName.setAutoValidate(true);
            publisher_firstName.setShowClearButton(true);
            publisher_firstName.addValidator(new RegexpValidator("invalid first name", "[^0-9@!#$%^&*()-\\*\\+<>/\\+~\\s]+"));


            publisher_lastName = (MaterialEditText) publisherView.findViewById(R.id.publisher_lasttName);
            publisher_lastName.setAutoValidate(true);
            publisher_lastName.setShowClearButton(true);
            publisher_lastName.addValidator(new RegexpValidator("invalid last name", "[^0-9@!#$%^&*()-\\*\\+<>/\\+~\\s]+"));


            publisherEmail = (MaterialEditText) publisherView.findViewById(R.id.publisher_email);
            publisherEmail.setAutoValidate(true);
            publisherEmail.setShowClearButton(true);
            publisherEmail.addValidator(new RegexpValidator(getString(R.string.invalidEmail), Tags.email_Regex));

            publisherCountry = (MaterialEditText) publisherView.findViewById(R.id.publisher_country);
            publisherCountry.setAutoValidate(true);
            publisherCountry.setShowClearButton(true);
            publisherCountry.addValidator(new RegexpValidator("invalid country", ".+"));


            publisherPhone = (MaterialEditText) publisherView.findViewById(R.id.publisher_phone);
            publisherPhone.setAutoValidate(true);
            publisherPhone.setShowClearButton(true);

            publisherExpertise = (MaterialEditText) publisherView.findViewById(R.id.publisher_expertise);
            publisherExpertise.setAutoValidate(true);
            publisherExpertise.setShowClearButton(true);

            publisher_webSite = (MaterialEditText) publisherView.findViewById(R.id.publisher_website);
            publisher_webSite.setAutoValidate(true);
            publisher_webSite.setShowClearButton(true);
            publisher_webSite.addValidator(new RegexpValidator(getString(R.string.invalid_url), Tags.url_Regex));

            publisherPassword = (MaterialEditText) publisherView.findViewById(R.id.publisher_password);
            publisherPassword.setAutoValidate(true);
            publisherPassword.setShowClearButton(true);
            publisherPassword.addValidator(new RegexpValidator("invalid password", "[A-Za-z0-9_.-]+"));

            pub_SignInBtn = (Button) publisherView.findViewById(R.id.pub_SignInBtn);
            pub_SignInBtn.setOnClickListener(this);

        }


    }

    private void init_libraryView(View view) {
        View libraryView = view.findViewById(R.id.library_layout);
        if (libraryView != null) {

            lib_scrollView = (ScrollView) libraryView.findViewById(R.id.lib_scrollView);

            libraryName = (MaterialEditText) libraryView.findViewById(R.id.library_Name);
            libraryName.setAutoValidate(true);
            libraryName.setShowClearButton(true);
            libraryName.addValidator(new RegexpValidator("invalid library name", "[^0-9@!#$%^&*()-\\*\\+<>/\\+~\\s]+"));

            libraryEmail = (MaterialEditText) libraryView.findViewById(R.id.library_email);
            libraryEmail.setAutoValidate(true);
            libraryEmail.setShowClearButton(true);
            libraryEmail.addValidator(new RegexpValidator(getString(R.string.invalidEmail), Tags.email_Regex));

            libraryCommission = (MaterialEditText) libraryView.findViewById(R.id.library_commission);
            libraryCommission.setAutoValidate(true);
            libraryCommission.setShowClearButton(true);
            libraryCommission.addValidator(new RegexpValidator("invalid commission", ".+"));


            libraryCountry = (MaterialEditText) libraryView.findViewById(R.id.library_country);
            libraryCountry.setAutoValidate(true);
            libraryCountry.setShowClearButton(true);
            libraryCountry.addValidator(new RegexpValidator("invalid country", ".+"));


            libraryExpertise = (MaterialEditText) libraryView.findViewById(R.id.library_expertise);
            libraryExpertise.setAutoValidate(true);
            libraryExpertise.setShowClearButton(true);


            libraryPassword = (MaterialEditText) libraryView.findViewById(R.id.library_password);
            libraryPassword.setAutoValidate(true);
            libraryPassword.setShowClearButton(true);
            libraryPassword.addValidator(new RegexpValidator("invalid password", "[A-Za-z0-9_.-]+"));


            library_otherType = (MaterialEditText) libraryView.findViewById(R.id.library_otherType);
            library_otherType.setAutoValidate(true);
            library_otherType.setShowClearButton(true);
            library_otherType.addValidator(new RegexpValidator("invalid library type", ".+"));


            lib_SignInBtn = (Button) libraryView.findViewById(R.id.lib_SignInBtn);
            lib_SignInBtn.setOnClickListener(this);
        }


    }


    @Override
    public void setNormalUserFirstName_Error() {
        n_userFirstName.setError("empty field");

    }

    @Override
    public void setNormalUserLastName_Error() {
        n_userLastName.setError("empty field");

    }

    @Override
    public void setNormalUserEmail_Error() {
        n_userEmail.setError("empty field");


    }

    @Override
    public void setNormalUser_invalidEmail_Error() {
        n_userEmail.setError("invalid email");

    }

    @Override
    public void setNormalUserCountry_Error() {
        n_userCountry.setError("empty field");


    }

    @Override
    public void setNormalUserPassword_Error() {
        n_userPassword.setError("empty field");


    }

    @Override
    public void setPublisherFirstName_Error() {
        publisher_firstName.setError("empty field");
    }

    @Override
    public void setPublisherLastName_Error() {
        publisher_lastName.setError("empty field");

    }

    @Override
    public void setPublisherEmail_Error() {

        publisherEmail.setError("empty field");

    }

    @Override
    public void setPublisher_invalidEmail_Error() {
        publisherEmail.setError("invalid email");

    }

    @Override
    public void setPublisherCountry_Error() {
        publisherCountry.setError("empty field");


    }

    @Override
    public void setPublisherPassword_Error() {

        publisherPassword.setError("empty field");

    }

    @Override
    public void setLibraryName_Error() {
        libraryName.setError("empty field");

    }

    @Override
    public void setLibraryEmail_Error() {
        libraryEmail.setError("invalid email");
    }

    @Override
    public void setLibraryCommission_Error() {
        libraryCommission.setError("empty field");


    }

    @Override
    public void setLibraryOtherType_Error() {
        library_otherType.setError("empty field");


    }

    @Override
    public void setLibraryCountry_Error() {

    }

    @Override
    public void setLibraryPassword_Error() {
        libraryPassword.setError("empty field");

    }

    @Override
    public void setLibraryLat_Lng_Error() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CreateDialog();

            }
        },1000);

    }


    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

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
    public void onNormalUserDataSuccess(NormalUserData normalUserData) {

    }

    @Override
    public void onPublisherDataSuccess(PublisherModel publisherModel) {
        Toast.makeText(mContext, "pub success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLibraryDataSuccess(LibraryModel libraryModel) {
        Toast.makeText(mContext, "lib success", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.n_SignInBtn:
                String userType1 = spinner.getSelectedItem().toString();
                String n_fname = n_userFirstName.getText().toString();
                String n_lname = n_userLastName.getText().toString();
                String n_email = n_userEmail.getText().toString();
                String n_country = n_userCountry.getText().toString();
                String n_password = n_userPassword.getText().toString();
                String n_phone = n_userPhone.getText().toString();
                String n_job = n_userJob.getText().toString();
                String n_interests = n_userInterests.getText().toString();
                presenter.NormalUserRegistration(userType1, n_fname, n_lname, n_email, n_country, n_password, n_phone, n_job, n_interests);
                break;

            case R.id.pub_SignInBtn:
                String userType2 = spinner.getSelectedItem().toString();
                String pub_fname = publisher_firstName.getText().toString();
                String pub_lname = publisher_lastName.getText().toString();
                String pub_email = publisherEmail.getText().toString();
                String pub_country = publisherCountry.getText().toString();
                String pub_password = publisherPassword.getText().toString();
                String pub_phone = publisherPhone.getText().toString();
                String pub_expertise = publisherExpertise.getText().toString();
                String pub_weburl = publisher_webSite.getText().toString();
                presenter.PublisherRegistration(userType2, pub_fname, pub_lname, pub_email, pub_country, pub_password, pub_phone, pub_expertise, pub_weburl);
                break;
            case R.id.lib_SignInBtn:
                String userType3 = spinner.getSelectedItem().toString();
                String lib_name = libraryName.getText().toString();
                String lib_email = libraryEmail.getText().toString();
                String lib_commission = libraryCommission.getText().toString();
                String lib_country = libraryCountry.getText().toString();
                String lib_expertise = libraryExpertise.getText().toString();
                String lib_password = libraryPassword.getText().toString();
                String lib_type = lib_spinner.getSelectedItem().toString();
                String lib_otherType = "";
                if (lib_type.equals("Other")) {
                    lib_otherType = library_otherType.getText().toString();
                }
                presenter.LibraryRegistration(userType3, lib_name, lib_email, lib_commission, lib_country, lib_expertise, lib_type, lib_otherType, lib_password, "", "");
                break;

            case R.id.userPhoto_container:
                SelectUserPhoto();
                break;


        }

    }

    private void SelectUserPhoto() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        getActivity().startActivityForResult(intent.createChooser(intent, "Choose your photo"), IMAGE_REQUEST);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_REQUEST) {

            if (data != null) {


                try {
                    userImage_URI = data.getData();
                    Cursor cursor = getActivity().getContentResolver().query(userImage_URI, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        String imageName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                        Toast.makeText(mContext, "" + imageName, Toast.LENGTH_SHORT).show();

                    }
                    userBitmap_image = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(userImage_URI));
                    n_circleImageView.setImageBitmap(userBitmap_image);
                    addPhoto_tv.setVisibility(View.GONE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
            }
        } else if (REQUEST_CODE == requestCode && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                if (data.hasExtra("lat") && data.hasExtra("lng")) {
                    double lat = data.getExtras().getDouble("lat");
                    double lng = data.getExtras().getDouble("lng");
                    String userType = spinner.getSelectedItem().toString();
                    String lib_name = libraryName.getText().toString();
                    String lib_email = libraryEmail.getText().toString();
                    String lib_commission = libraryCommission.getText().toString();
                    String lib_country = libraryCountry.getText().toString();
                    String lib_expertise = libraryExpertise.getText().toString();
                    String lib_password = libraryPassword.getText().toString();
                    String lib_type = lib_spinner.getSelectedItem().toString();
                    String lib_otherType = "";
                    if (lib_type.equals("Other")) {
                        lib_otherType = library_otherType.getText().toString();
                    }
                    presenter.LibraryRegistration(userType, lib_name, lib_email, lib_commission, lib_country, lib_expertise, lib_type, lib_otherType, lib_password, String.valueOf(lat), String.valueOf(lng));
                }

            }
        }
    }

    private void CreateDialog() {
        LovelyStandardDialog dialog = new LovelyStandardDialog(getActivity())
                .setTitle("Select Library Location...")
                .setMessage("Your current location is the library location ?")
                .setTopColor(ActivityCompat.getColor(getActivity(), R.color.centercolor))
                .setIcon(R.drawable.help_menu_icon)
                .setCancelable(false)
                .setPositiveButton("yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getDeviceLocation();
                    }
                })
                .setPositiveButtonColor(ContextCompat.getColor(getActivity(), R.color.centercolor))
                .setNegativeButton("Select Location", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getActivity(), MapsActivity.class);
                        getActivity().startActivityForResult(intent, REQUEST_CODE);


                    }
                });
        dialog.create();
        dialog.show();
    }

    private void getDeviceLocation() {
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
                        String userType = spinner.getSelectedItem().toString();
                        String lib_name = libraryName.getText().toString();
                        String lib_email = libraryEmail.getText().toString();
                        String lib_commission = libraryCommission.getText().toString();
                        String lib_country = libraryCountry.getText().toString();
                        String lib_expertise = libraryExpertise.getText().toString();
                        String lib_password = libraryPassword.getText().toString();
                        String lib_type = lib_spinner.getSelectedItem().toString();
                        String lib_otherType = "";
                        Toast.makeText(mContext, "lat "+lat+"\n"+"lng"+lng, Toast.LENGTH_SHORT).show();
                        if (lib_type.equals("Other")) {
                            lib_otherType = library_otherType.getText().toString();
                        }
                        presenter.LibraryRegistration(userType, lib_name, lib_email, lib_commission, lib_country, lib_expertise, lib_type, lib_otherType, lib_password, String.valueOf(lat), String.valueOf(lng));

                    }
                }
            }
        });
    }

    private void CreateProgressDialog()
    {
        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage("SignIn....");
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
                .setTopTitle("Error")
                .setTopTitleColor(ContextCompat.getColor(getActivity(),R.color.base))
                .setPositiveButtonColorRes(R.color.centercolor)
                .setMessage(msg);
        dialog.setPositiveButton("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

}
