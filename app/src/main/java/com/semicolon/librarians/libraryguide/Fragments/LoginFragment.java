package com.semicolon.librarians.libraryguide.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.semicolon.librarians.libraryguide.Activities.HomeActivity;
import com.semicolon.librarians.libraryguide.MVP.Login_RegisterMVP.Login.Login_PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.Login_RegisterMVP.Login.Login_ViewData;
import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Preferences;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Delta on 08/12/2017.
 */

public class LoginFragment extends Fragment implements Login_ViewData{
    //
    private TextView signupTv,forget_password;
    private LinearLayout signUp;
    private ShimmerTextView shimmerTextView;
    private ExpandableRelativeLayout login_expandlayout;
    private FragmentTransactionExtended fragmentTransactionExtended;
    private EditText userName,password;
    //private RippleView rippleView,ripple_et_forget_password,signinBtn_ripple;
    private Button signinBtn;
    Context mContext;
    Handler handler;
    private Login_PresenterImp presenterImp;
    private ProgressDialog  progressDialog;
    private CheckBox remember_me;
    private Preferences pref;
    //public static String userType=null;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), Tags.font,true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment,container,false);
        initView(view);
        Calligrapher calligrapher = new Calligrapher(getActivity().getApplicationContext());
        calligrapher.setFont(view, Tags.font);

        CreateProgressDialog();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate_to_signup_fragment();

            }
        });
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate_to_ForgetPassword_fragment();

            }
        });
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_Name = userName.getText().toString().trim();
                String Password = password.getText().toString().trim();
                presenterImp.Validate_Credential("",user_Name,Password);

            }
        });

        setUpShimmer();

        presenterImp = new Login_PresenterImp(getActivity(),this);

        SharedPreferences pRef = getActivity().getSharedPreferences("remember_me",getActivity().MODE_PRIVATE);

        String uname = pRef.getString("user_name","");
        String upass = pRef.getString("pass","");
        pref = new Preferences(getActivity());
        if (!TextUtils.isEmpty(uname)||!TextUtils.isEmpty(upass))
        {
            userName.setText(uname);
            password.setText(upass);
            remember_me.setChecked(true);
        }

        remember_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (remember_me.isChecked())
                {


                    if (!TextUtils.isEmpty(userName.getText().toString())&&!TextUtils.isEmpty(password.getText().toString()))
                    {
                        pref = new Preferences(getActivity());
                        pref.createPrfRemmember_me(userName.getText().toString(),password.getText().toString());

                    }else
                        {
                            pref.clearRefRemember_me();
                        }
                }else
                    {
                        pref.clearRefRemember_me();

                    }
            }
        });

        return view;
    }



    private void initView(View view) {
        mContext = view.getContext();
        shimmerTextView = (ShimmerTextView) view.findViewById(R.id.shimmer);
        login_expandlayout = (ExpandableRelativeLayout) view.findViewById(R.id.login_expandlayout);
        login_expandlayout.collapse();
        handler= new Handler();
        forget_password = (TextView) view.findViewById(R.id.forget_password);
        signUp = (LinearLayout) view.findViewById(R.id.signUp);
        signinBtn = (Button) view.findViewById(R.id.signinBtn);
         userName = (EditText) view.findViewById(R.id.userName);
        password = (EditText) view.findViewById(R.id.password);

        remember_me = (CheckBox) view.findViewById(R.id.remember_me);

    }
    private void setUpShimmer() {
        Shimmer shimmer = new Shimmer();
        shimmer.setDuration(3000).setDirection(Shimmer.ANIMATION_DIRECTION_RTL);
        shimmer.start(shimmerTextView);
    }




    private void navigate_to_signup_fragment() {

        if (getActivity().getFragmentManager().getBackStackEntryCount()==0)
        {
            FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
            fragmentTransactionExtended = new FragmentTransactionExtended(mContext,transaction,new LoginFragment(),new RegisterFragment(),R.id.fragmentContainer);
            fragmentTransactionExtended.commit();
            fragmentTransactionExtended.addTransition(FragmentTransactionExtended.ACCORDION);
        }
        else
            {
                getActivity().getFragmentManager().popBackStack();
            }
    }

    private void navigate_to_ForgetPassword_fragment() {

        if (getActivity().getFragmentManager().getBackStackEntryCount()==0)
        {
            FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
            fragmentTransactionExtended = new FragmentTransactionExtended(mContext,transaction,new LoginFragment(),new ForgetPassword_Fragment(),R.id.fragmentContainer);
            fragmentTransactionExtended.commit();
            fragmentTransactionExtended.addTransition(FragmentTransactionExtended.ZOOM_SLIDE_VERTICAL);
        }
        else
        {
            getActivity().getFragmentManager().popBackStack();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                login_expandlayout.expand();
            }
        },1100);
    }


    @Override
    public void setUserNameError() {
        userName.setError(getString(R.string.empty_field));
    }

    @Override
    public void setUserName_invalidError() {
        userName.setError(getString(R.string.invalid_username));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.empty_field));

    }

    @Override
    public void setPassword_invalidError() {
        password.setError(getString(R.string.invalid_password));
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
    public void showUserTypeDialog() {
        CreateSelectUserTypeDialog();


    }

    @Override
    public void onSuccess_NormalUserData(final NormalUserData normalUserModel) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(),HomeActivity.class);
                intent.putExtra("userData",normalUserModel);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Preferences pref = new Preferences(getActivity());
                pref.Session("loggedin");
                Log.e("userToken",normalUserModel.getUser_token());

                startActivity(intent);
            }
        },500);
        userName.setText(null);
        password.setText(null);
    }

    @Override
    public void onSuccess_LibraryData(final LibraryModel libraryModel) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(),HomeActivity.class);
                intent.putExtra("libraryData",libraryModel);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Preferences pref = new Preferences(getActivity());
                pref.Session("loggedin");
                Log.e("libToken",libraryModel.getUser_token());

                startActivity(intent);
            }
        },500);

        userName.setText(null);
        password.setText(null);
    }

    @Override
    public void onSuccess_PublisherData(final PublisherModel publisherModel) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(),HomeActivity.class);
                intent.putExtra("publisherData",publisherModel);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Preferences pref = new Preferences(getActivity());
                pref.Session("loggedin");
                Log.e("pubToken",publisherModel.getUser_token());

                startActivity(intent);
            }
        },500);

        userName.setText(null);
        password.setText(null);
    }

    @Override
    public void onSuccess_UniversityData(final UniversityModel universityModel) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 Intent intent = new Intent(getActivity(),HomeActivity.class);
                intent.putExtra("universityData",universityModel);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Preferences pref = new Preferences(getActivity());

                pref.Session("loggedin");
                startActivity(intent);
            }
        },500);

        userName.setText(null);
        password.setText(null);
    }

    @Override
    public void onSuccess_CompanyData(final CompanyModel companyModel) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getActivity(),HomeActivity.class);
                intent.putExtra("companyData",companyModel);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Preferences pref = new Preferences(getActivity());
                pref.Session("loggedin");
                startActivity(intent);
            }
        },500);

        userName.setText(null);
        password.setText(null);
    }


    @Override
    public void onFailed(String error) {
        CreateAlertDialog(error);
    }
    private void CreateProgressDialog()
    {
        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.sin_in));
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
    private void CreateSelectUserTypeDialog()
    {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.alertdialog_choose_usertype,null);

        Button confirm = (Button) view.findViewById(R.id.confirmBtn);
        final RadioButton rb_normaluser = (RadioButton) view.findViewById(R.id.rb_normaluser);
        final RadioButton rb_library = (RadioButton) view.findViewById(R.id.rb_library);
        final RadioButton rb_publisher = (RadioButton) view.findViewById(R.id.rb_publisher);
        final RadioButton rb_university = (RadioButton) view.findViewById(R.id.rb_university);
        final RadioButton rb_company = (RadioButton) view.findViewById(R.id.rb_company);

        final LovelyCustomDialog customDialog = new LovelyCustomDialog(getActivity())
                .setCancelable(true)
                .setTopColor(ContextCompat.getColor(getActivity(), R.color.centercolor))
                .setIcon(R.drawable.commession_icon)
                .setIconTintColor(ContextCompat.getColor(getActivity(), R.color.base));
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rb_normaluser.isChecked())
                {
                    String user = "user";
                    String user_Name = userName.getText().toString().trim();
                    String Password = password.getText().toString().trim();
                    presenterImp.Validate_Credential(user,user_Name,Password);
                    customDialog.dismiss();
                }else if (rb_publisher.isChecked())
                {
                    String publisher = "publisher";
                    String user_Name = userName.getText().toString().trim();
                    String Password = password.getText().toString().trim();
                    presenterImp.Validate_Credential(publisher,user_Name,Password);

                    customDialog.dismiss();
                }
                else if (rb_library.isChecked())
                {
                    String library = "library";
                    String user_Name = userName.getText().toString().trim();
                    String Password = password.getText().toString().trim();
                    presenterImp.Validate_Credential(library,user_Name,Password);

                    customDialog.dismiss();
                }else if (rb_university.isChecked())
                {
                    String university = "university";
                    String user_Name = userName.getText().toString().trim();
                    String Password = password.getText().toString().trim();
                    presenterImp.Validate_Credential(university,user_Name,Password);

                    customDialog.dismiss();
                }else if (rb_company.isChecked())
                {
                    String company = "company";
                    String user_Name = userName.getText().toString().trim();
                    String Password = password.getText().toString().trim();
                    presenterImp.Validate_Credential(company,user_Name,Password);
                    customDialog.dismiss();
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                customDialog.create();
                customDialog.setView(view);
                customDialog.show();
            }
        },500);




    }

}
