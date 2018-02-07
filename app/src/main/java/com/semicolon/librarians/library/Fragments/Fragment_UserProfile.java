package com.semicolon.librarians.library.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Delta on 06/02/2018.
 */

public class Fragment_UserProfile extends Fragment {
    private NormalUserData userData=null;
    private CircleImageView userImage;
    private TextView userName,userEmail,userPhone,userCountry;
    private Button msgBtn,locBtn,updBtn;
    private String who_visit_myProfile="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_user,container,false);
        initView(view);
        getDataFromBundle();
        return view;
    }

    private void initView(View view)
    {
        userImage   = (CircleImageView) view.findViewById(R.id.profile_userImage);
        userName    = (TextView) view.findViewById(R.id.profile_userName);
        userEmail   = (TextView) view.findViewById(R.id.profile_userEmail);
        userPhone   = (TextView) view.findViewById(R.id.profile_userPhone);
        userCountry = (TextView) view.findViewById(R.id.profile_userCountry);
        msgBtn      = (Button) view.findViewById(R.id.profile_user_msgBtn);
        locBtn      = (Button) view.findViewById(R.id.profile_user_locBtn);
        updBtn      = (Button) view.findViewById(R.id.profile_user_updBtn);

    }
    private void getDataFromBundle()
    {
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            if (bundle.getSerializable("userData")!=null)
            {
                who_visit_myProfile = bundle.getString("who_visit_myProfile");
                userData = (NormalUserData) bundle.getSerializable("userData");
                updateUI(userData);
            }
        }
    }

    private void updateUI(NormalUserData userData) {
        userName.setText(userData.getUserName().toString());
        userEmail.setText(userData.getUserEmail().toString());
        userPhone.setText(userData.getUserPhone().toString());
        userCountry.setText(userData.getUserCountry().toString());
        if (who_visit_myProfile.equals("me"))
        {
            msgBtn.setVisibility(View.GONE);
            locBtn.setVisibility(View.GONE);
            updBtn.setVisibility(View.VISIBLE);
        }
        else
            {
                msgBtn.setVisibility(View.VISIBLE);
                locBtn.setVisibility(View.VISIBLE);
                updBtn.setVisibility(View.GONE);
            }
    }
}
