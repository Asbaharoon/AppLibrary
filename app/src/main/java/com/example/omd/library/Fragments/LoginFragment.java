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

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.example.omd.library.Activities.HomeActivity;
import com.example.omd.library.R;

/**
 * Created by Delta on 08/12/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener{
    private TextView signupTv;
    private FragmentTransactionExtended fragmentTransactionExtended;
    private Button singninBtn;
    Context mContext;
    Handler handler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mContext = view.getContext();
        handler= new Handler();
        singninBtn = (Button) view.findViewById(R.id.signinBtn);
        singninBtn.setOnClickListener(this);
        signupTv = (TextView) view.findViewById(R.id.login_fragment_signuptv);
        signupTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.login_fragment_signuptv:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        navigate_to_signup_fragment();

                    }
                },500);
                break;
            case R.id.signinBtn:
                Signin("","");
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



}
