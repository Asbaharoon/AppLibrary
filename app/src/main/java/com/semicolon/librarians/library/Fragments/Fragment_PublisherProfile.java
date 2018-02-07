package com.semicolon.librarians.library.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Delta on 06/02/2018.
 */

public class Fragment_PublisherProfile extends Fragment {

    private PublisherModel publisherModel=null;
    private CircleImageView pub_image;
    private TextView pub_name,pub_email,pub_phone,pub_country,pub_address,pub_town,pub_website;
    private Button msgBtn,locBtn,updBtn;
    private String who_visit_myProfile="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_publisher,container,false);
        initView(view);
        getDataFromBundle();
        return view;
    }

    private void initView(View view)
    {
        pub_image  = (CircleImageView) view.findViewById(R.id.profile_publisherImage);
        pub_name   = (TextView) view.findViewById(R.id.profile_publisherName);
        pub_email  = (TextView) view.findViewById(R.id.profile_publisherEmail);
        pub_phone  = (TextView) view.findViewById(R.id.profile_publisherPhone);
        pub_country= (TextView) view.findViewById(R.id.profile_publisherCountry);
        pub_address= (TextView) view.findViewById(R.id.profile_publisherAddress);
        pub_town   = (TextView) view.findViewById(R.id.profile_publisherTown);
        pub_website= (TextView) view.findViewById(R.id.profile_publisherWebsite);
        msgBtn      = (Button) view.findViewById(R.id.profile_pub_msgBtn);
        locBtn      = (Button) view.findViewById(R.id.profile_pub_locBtn);
        updBtn      = (Button) view.findViewById(R.id.profile_pub_updBtn);

    }
    private void getDataFromBundle()
    {
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            if (bundle.getSerializable("publisherData")!=null)
            {
                who_visit_myProfile = bundle.getString("who_visit_myProfile");
                publisherModel = (PublisherModel) bundle.getSerializable("publisherData");
                updateUI(publisherModel);
            }
        }
    }

    private void updateUI(PublisherModel publisherModel) {
        pub_name.setText(publisherModel.getPub_name().toString());
        pub_email.setText(publisherModel.getPub_email().toString());
        pub_phone.setText(publisherModel.getPub_phone().toString());
        pub_country.setText(publisherModel.getPub_country().toString());
        pub_address.setText(publisherModel.getPub_address().toString());
        pub_town.setText(publisherModel.getPub_town().toString());
        pub_website.setText(publisherModel.getPub_website().toString());
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
