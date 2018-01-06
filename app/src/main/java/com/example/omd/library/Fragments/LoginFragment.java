package com.example.omd.library.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.example.omd.library.Activities.HomeActivity;
import com.example.omd.library.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

/**
 * Created by Delta on 08/12/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener{
    private TextView signupTv;
    private ShimmerTextView shimmerTextView;
    private ExpandableRelativeLayout login_expandlayout;
    private FragmentTransactionExtended fragmentTransactionExtended;
    private Button singninBtn;
    private RippleView rippleView,ripple_et_forget_password;
    Context mContext;
    Handler handler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment,container,false);
        initView(view);
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
        setUpShimmer();
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
        singninBtn = (Button) view.findViewById(R.id.signinBtn);
        singninBtn.setOnClickListener(this);
        signupTv = (TextView) view.findViewById(R.id.login_fragment_signuptv);
        signupTv.setOnClickListener(this);

    }
    private void setUpShimmer() {
        Shimmer shimmer = new Shimmer();
        shimmer.setDuration(3000).setDirection(Shimmer.ANIMATION_DIRECTION_RTL);
        shimmer.start(shimmerTextView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signinBtn:
                //Signin("","");
        }

    }

    private void Signin(String userName,String password) {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();

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
}
