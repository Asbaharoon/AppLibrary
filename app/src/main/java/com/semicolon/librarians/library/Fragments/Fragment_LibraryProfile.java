package com.semicolon.librarians.library.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Delta on 06/02/2018.
 */

public class Fragment_LibraryProfile extends Fragment {

    private LibraryModel libraryModel=null;
    private CircleImageView lib_image;
    private TextView lib_name,lib_type,lib_email,lib_phone,lib_country,lib_address;
    private Button msgBtn,locBtn,updBtn;
    private String who_visit_myProfile="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_library,container,false);
        initView(view);
        getDataFromBundle();
        return view;
    }

    private void initView(View view)
    {
        lib_image  = (CircleImageView) view.findViewById(R.id.profile_libraryImage);
        lib_name   = (TextView) view.findViewById(R.id.profile_libraryName);
        lib_type   = (TextView) view.findViewById(R.id.profile_libraryType);
        lib_email  = (TextView) view.findViewById(R.id.profile_libraryEmail);
        lib_country= (TextView) view.findViewById(R.id.profile_libraryCountry);
        lib_phone  = (TextView) view.findViewById(R.id.profile_libraryPhone);
        lib_address= (TextView) view.findViewById(R.id.profile_libraryAddress);
        msgBtn      = (Button) view.findViewById(R.id.profile_lib_msgBtn);
        locBtn      = (Button) view.findViewById(R.id.profile_lib_locBtn);
        updBtn      = (Button) view.findViewById(R.id.profile_lib_updBtn);

    }
    private void getDataFromBundle() {

        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            if (bundle.getSerializable("libraryData")!=null)
            {
                who_visit_myProfile = bundle.getString("who_visit_myProfile");
                libraryModel = (LibraryModel) bundle.getSerializable("libraryData");
                updateUI(libraryModel);
            }
        }
    }

    private void updateUI(LibraryModel libraryModel) {
        lib_name.setText(libraryModel.getLib_name().toString());
        lib_type.setText(libraryModel.getLib_type().toString());
        lib_email.setText(libraryModel.getLib_email().toString());
        lib_phone.setText(libraryModel.getLib_phone().toString());
        lib_country.setText(libraryModel.getLib_country().toString());
        lib_address.setText(libraryModel.getLib_address().toString());

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
