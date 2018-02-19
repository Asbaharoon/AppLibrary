package com.semicolon.librarians.libraryguide.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.intrusoft.squint.DiagonalView;
import com.semicolon.librarians.libraryguide.Activities.Activity_Profile;
import com.semicolon.librarians.libraryguide.Activities.Chat_Activity;
import com.semicolon.librarians.libraryguide.Activities.DisplayUserLocation_OnMap;
import com.semicolon.librarians.libraryguide.MVP.Create_ChatRoom_MVP.Presenter;
import com.semicolon.librarians.libraryguide.MVP.Create_ChatRoom_MVP.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.Create_ChatRoom_MVP.ViewData;
import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Delta on 06/02/2018.
 */

public class Fragment_CompanyProfile extends Fragment implements View.OnClickListener, com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.ViewData,ViewData{

    private CompanyModel companyModel=null;
    private CircleImageView comp_image;
    private TextView comp_name,comp_email,comp_phone,comp_country,comp_address,comp_town,comp_website;
    private Button msgBtn,locBtn,updBtn;
    private String who_visit_myProfile="";
    private DiagonalView diagonalView;
    private Target target;
    private Activity_Profile activity_profile;
    private Presenter presenter;
    private com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.Presenter compPresenter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_company,container,false);
        initView(view);
        activity_profile = (Activity_Profile) getActivity();
        presenter = new PresenterImp(this,getActivity());
        compPresenter = new com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.PresenterImp(this,getActivity());

        getDataFromBundle();
        return view;
    }

    private void initView(View view)
    {
        diagonalView= (DiagonalView) view.findViewById(R.id.secondImage);
        comp_image  = (CircleImageView) view.findViewById(R.id.profile_companyImage);
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
        msgBtn.setOnClickListener(this);
        locBtn.setOnClickListener(this);
        updBtn.setOnClickListener(this);
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
            }else if (bundle.getString("compId")!=null)
            {
                String compId = bundle.getString("compId");
                compPresenter.getCompanyData("company",compId);
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

        if (!companyModel.getUser_photo().equals("0"))
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    comp_image.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(getActivity()).load(Uri.parse(Tags.image_path+companyModel.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

        }
        else
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    comp_image.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(getActivity()).load(R.drawable.user_profile).into(target);

        }
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
    private void updateUI2(CompanyModel companyModel) {
        comp_name.setText(companyModel.getComp_name().toString());
        comp_email.setText(companyModel.getComp_email().toString());
        comp_phone.setText(companyModel.getComp_phone().toString());
        comp_country.setText(companyModel.getComp_country().toString());
        comp_address.setText(companyModel.getComp_address().toString());
        comp_town.setText(companyModel.getComp_town().toString());
        comp_website.setText(companyModel.getComp_site().toString());

        if (!companyModel.getUser_photo().equals("0"))
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    comp_image.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(getActivity()).load(Uri.parse(Tags.image_path+companyModel.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

        }
        else
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    comp_image.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(getActivity()).load(R.drawable.user_profile).into(target);

        }

            msgBtn.setVisibility(View.GONE);
            locBtn.setVisibility(View.GONE);
            updBtn.setVisibility(View.GONE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Picasso.with(getActivity()).cancelRequest(target);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.profile_comp_msgBtn:
                Create_ChatRoom();
                NavigateToChatActivity();
                break;
            case R.id.profile_comp_locBtn:
                DisplayUserLocationOnMap();
                break;
            case R.id.profile_comp_updBtn:

                break;
        }
    }
    private void NavigateToChatActivity()
    {
        if (activity_profile.logged_user_Data!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","user");
            intent.putExtra("chat_userType","company");
            intent.putExtra("curr_user",activity_profile.logged_user_Data);
            intent.putExtra("chat_user",companyModel);
            getActivity().startActivity(intent);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","publisher");
            intent.putExtra("chat_userType","company");
            intent.putExtra("curr_user",activity_profile.logged_publisher_Model);
            intent.putExtra("chat_user",companyModel);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_library_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","library");
            intent.putExtra("chat_userType","company");
            intent.putExtra("curr_user",activity_profile.logged_library_Model);
            intent.putExtra("chat_user",companyModel);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_university_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","university");
            intent.putExtra("chat_userType","company");
            intent.putExtra("curr_user",activity_profile.logged_university_Model);
            intent.putExtra("chat_user",companyModel);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_company_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","company");
            intent.putExtra("chat_userType","company");
            intent.putExtra("curr_user",activity_profile.logged_company_Model);
            intent.putExtra("chat_user",companyModel);
            getActivity().startActivity(intent);

        }
    }
    private void DisplayUserLocationOnMap()
    {
        if (activity_profile.logged_user_Data!=null)
        {
            String lat = activity_profile.logged_user_Data.getUser_google_lat();
            String lng =activity_profile.logged_user_Data.getUser_google_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("companyData",companyModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            String lat = activity_profile.logged_publisher_Model.getPub_lat();
            String lng =activity_profile.logged_publisher_Model.getPub_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("companyData",companyModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_library_Model!=null)
        {
            String lat = activity_profile.logged_library_Model.getLat();
            String lng =activity_profile.logged_library_Model.getLng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("companyData",companyModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_university_Model!=null)
        {
            String lat = activity_profile.logged_university_Model.getUni_lat();
            String lng =activity_profile.logged_university_Model.getUni_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("companyData",companyModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_company_Model!=null)
        {
            String lat = activity_profile.logged_company_Model.getComp_lat();
            String lng =activity_profile.logged_company_Model.getComp_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("companyData",companyModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }
    }
    private void Create_ChatRoom() {
        if (activity_profile.logged_user_Data!=null)
        {
            String currUserId = activity_profile.logged_user_Data.getUserId();
            String chatUserId = companyModel.getComp_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            String currUserId = activity_profile.logged_publisher_Model.getPub_username();
            String chatUserId = companyModel.getComp_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
        else if (activity_profile.logged_library_Model!=null)
        {
            String currUserId = activity_profile.logged_library_Model.getLib_username();
            String chatUserId = companyModel.getComp_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
        else if (activity_profile.logged_university_Model!=null)
        {
            String currUserId = activity_profile.logged_university_Model.getUni_username();
            String chatUserId = companyModel.getComp_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
        else if (activity_profile.logged_company_Model!=null)
        {
            String currUserId = activity_profile.logged_company_Model.getComp_username();
            String chatUserId = companyModel.getComp_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
    }
    @Override
    public void onChatRoom_CreatedSuccessfully(String response) {
        Log.e("chatcreated",response);

    }

    @Override
    public void onNormalUserDataSuccess(NormalUserData normalUserData) {

    }

    @Override
    public void onPublisherDataSuccess(PublisherModel publisherModel) {

    }

    @Override
    public void onLibraryDataSuccess(LibraryModel libraryModel) {

    }

    @Override
    public void onCompanyDataSuccess(CompanyModel companyModel) {

        updateUI2(companyModel);
    }

    @Override
    public void onUniversityDataSuccess(UniversityModel universityModel) {

    }

    @Override
    public void onFailed(String error) {
        Log.e("chatcreated",error);

    }
}
