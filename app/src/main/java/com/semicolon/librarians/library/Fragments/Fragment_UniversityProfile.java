package com.semicolon.librarians.library.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.semicolon.librarians.library.Models.UniversityModel;
import com.semicolon.librarians.library.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Delta on 06/02/2018.
 */

public class Fragment_UniversityProfile extends Fragment {
    private UniversityModel universityModel=null;
    private CircleImageView uni_image;
    private TextView uni_name,uni_email,uni_phone,uni_country,uni_major,uni_address,uni_website;
    private Button msgBtn,locBtn,updBtn;
    private String who_visit_myProfile="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_universty,container,false);
        initView(view);
        getDataFromBundle();
        return view;
    }

    private void initView(View view)
    {
        uni_image  = (CircleImageView) view.findViewById(R.id.profile_universityImage);
        uni_name   = (TextView) view.findViewById(R.id.profile_universityName);
        uni_email  = (TextView) view.findViewById(R.id.profile_universityEmail);
        uni_phone  = (TextView) view.findViewById(R.id.profile_universityPhone);
        uni_country= (TextView) view.findViewById(R.id.profile_universityCountry);
        uni_major  = (TextView) view.findViewById(R.id.profile_universityMajor);
        uni_address= (TextView) view.findViewById(R.id.profile_universityAddress);
        uni_website= (TextView) view.findViewById(R.id.profile_universityWebsite);
        msgBtn      = (Button) view.findViewById(R.id.profile_uni_msgBtn);
        locBtn      = (Button) view.findViewById(R.id.profile_uni_locBtn);
        updBtn      = (Button) view.findViewById(R.id.profile_uni_updBtn);

    }
    private void getDataFromBundle() {
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            if (bundle.getSerializable("universityData")!=null)
            {
                universityModel = (UniversityModel) bundle.getSerializable("universityData");
                who_visit_myProfile = bundle.getString("who_visit_myProfile");

                updateUI(universityModel);
            }
        }
    }

    private void updateUI(UniversityModel universityModel) {
        uni_name.setText(universityModel.getUni_name().toString());
        uni_email.setText(universityModel.getUni_email().toString());
        uni_phone.setText(universityModel.getUni_phone().toString());
        uni_country.setText(universityModel.getUni_country().toString());
        uni_address.setText(universityModel.getUni_address().toString());
        uni_major.setText(universityModel.getUni_major().toString());
//        uni_website.setText(universityModel.getUni_site().toString());
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
