package com.semicolon.librarians.library.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Delta on 06/02/2018.
 */

public class Fragment_CompanyProfile extends Fragment {

    private CompanyModel companyModel=null;
    private CircleImageView comp_image;
    private TextView comp_name,comp_email,comp_phone,comp_country,comp_address,comp_town,comp_website;
    private Button msgBtn,locBtn,updBtn;
    private String who_visit_myProfile="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_company,container,false);
        initView(view);
        getDataFromBundle();
        return view;
    }

    private void initView(View view)
    {
        comp_image  = (CircleImageView) view.findViewById(R.id.profile_publisherImage);
        comp_name   = (TextView) view.findViewById(R.id.profile_companyName);
        comp_email  = (TextView) view.findViewById(R.id.profile_companyEmail);
        comp_phone  = (TextView) view.findViewById(R.id.profile_companyPhone);
        comp_country= (TextView) view.findViewById(R.id.profile_companyCountry);
        comp_address= (TextView) view.findViewById(R.id.profile_companyAddress);
        comp_town   = (TextView) view.findViewById(R.id.profile_companyTown);
        comp_website= (TextView) view.findViewById(R.id.profile_companyWebsite);
        msgBtn      = (Button) view.findViewById(R.id.profile_comp_msgBtn);
        locBtn      = (Button) view.findViewById(R.id.profile_comp_locBtn);
        updBtn      = (Button) view.findViewById(R.id.profile_comp_updBtn);

    }
    private void getDataFromBundle()
    {
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            if (bundle.getSerializable("companyData")!=null)
            {
                who_visit_myProfile = bundle.getString("who_visit_myProfile");
                companyModel = (CompanyModel) bundle.getSerializable("companyData");
                updateUI(companyModel);
            }
        }
    }

    private void updateUI(CompanyModel companyModel) {
        comp_name.setText(companyModel.getComp_name().toString());
        comp_email.setText(companyModel.getComp_email().toString());
        comp_phone.setText(companyModel.getComp_phone().toString());
        comp_country.setText(companyModel.getComp_country().toString());
        comp_address.setText(companyModel.getComp_address().toString());
        comp_town.setText(companyModel.getComp_town().toString());
        comp_website.setText(companyModel.getComp_site().toString());
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
