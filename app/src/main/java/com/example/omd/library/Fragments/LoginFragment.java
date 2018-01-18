package com.example.omd.library.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.example.omd.library.Activities.HomeActivity;
import com.example.omd.library.Login_RegisterMVP.Login.Login_PresenterImp;
import com.example.omd.library.Login_RegisterMVP.Login.Login_ViewData;
import com.example.omd.library.Models.CompanyModel;
import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.Models.UniversityModel;
import com.example.omd.library.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.siyamed.shapeimageview.BubbleImageView;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.squareup.picasso.Picasso;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

/**
 * Created by Delta on 08/12/2017.
 */

public class LoginFragment extends Fragment implements Login_ViewData{
    //
    private TextView signupTv;
    private ShimmerTextView shimmerTextView;
    private ExpandableRelativeLayout login_expandlayout;
    private FragmentTransactionExtended fragmentTransactionExtended;
    private EditText userName,password;
    private RippleView rippleView,ripple_et_forget_password,signinBtn_ripple;
    Context mContext;
    Handler handler;
    private Login_PresenterImp presenterImp;
    private ProgressDialog  progressDialog;
    //public static String userType=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment,container,false);
        initView(view);
        CreateProgressDialog();

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                navigate_to_signup_fragment();
            }
        });
        ripple_et_forget_password.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                navigate_to_ForgetPassword_fragment();
            }
        });
        signinBtn_ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                String user_Name = userName.getText().toString().trim();
                String Password = password.getText().toString().trim();
                presenterImp.Validate_Credential("",user_Name,Password);
            }
        });
        setUpShimmer();

        presenterImp = new Login_PresenterImp(getActivity(),this);
        return view;
    }



    private void initView(View view) {
        mContext = view.getContext();
        shimmerTextView = (ShimmerTextView) view.findViewById(R.id.shimmer);
        login_expandlayout = (ExpandableRelativeLayout) view.findViewById(R.id.login_expandlayout);
        login_expandlayout.collapse();
        handler= new Handler();
        rippleView = (RippleView) view.findViewById(R.id.ripple_et);
        ripple_et_forget_password = (RippleView) view.findViewById(R.id.ripple_et_forget_password);
        signinBtn_ripple = (RippleView) view.findViewById(R.id.signinBtn_ripple);
        userName = (EditText) view.findViewById(R.id.userName);
        password = (EditText) view.findViewById(R.id.password);


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
        userName.setError("empty field");
    }

    @Override
    public void setUserName_invalidError() {
        userName.setError("invalid username");
    }

    @Override
    public void setPasswordError() {
        password.setError("empty field");

    }

    @Override
    public void setPassword_invalidError() {
        password.setError("invalid password");
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
                startActivity(intent);
            }
        },500);
        Toast.makeText(mContext, "user", Toast.LENGTH_SHORT).show();
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
                startActivity(intent);
                Toast.makeText(mContext, ""+libraryModel.getUser_type()+"\n"+libraryModel.getLib_username(), Toast.LENGTH_SHORT).show();
            }
        },500);
        Toast.makeText(mContext, "library", Toast.LENGTH_SHORT).show();

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
                startActivity(intent);
            }
        },500);
        Toast.makeText(mContext, "publisher", Toast.LENGTH_SHORT).show();

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
                startActivity(intent);
            }
        },500);
        Toast.makeText(mContext, "university", Toast.LENGTH_SHORT).show();

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
                startActivity(intent);
            }
        },500);
        Toast.makeText(mContext, "company", Toast.LENGTH_SHORT).show();

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
    private void CreateSelectUserTypeDialog()
    {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.alertdialog_choose_usertype,null);
        final BubbleImageView bubbleImageView = (BubbleImageView) view.findViewById(R.id.bubble_userImage);

        Button confirm = (Button) view.findViewById(R.id.confirmBtn);
        Picasso.with(getActivity()).load(R.drawable.user_profile).into(bubbleImageView);
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
                    Toast.makeText(getActivity(), user, Toast.LENGTH_SHORT).show();
                }else if (rb_publisher.isChecked())
                {
                    String publisher = "publisher";
                    String user_Name = userName.getText().toString().trim();
                    String Password = password.getText().toString().trim();
                    presenterImp.Validate_Credential(publisher,user_Name,Password);
                    Toast.makeText(getActivity(), publisher, Toast.LENGTH_SHORT).show();

                    customDialog.dismiss();
                }
                else if (rb_library.isChecked())
                {
                    String library = "library";
                    String user_Name = userName.getText().toString().trim();
                    String Password = password.getText().toString().trim();
                    presenterImp.Validate_Credential(library,user_Name,Password);
                    Toast.makeText(getActivity(), library.toLowerCase(), Toast.LENGTH_SHORT).show();

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
