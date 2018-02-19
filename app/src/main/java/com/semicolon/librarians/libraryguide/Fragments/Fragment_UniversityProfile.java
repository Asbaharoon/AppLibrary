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

public class Fragment_UniversityProfile extends Fragment implements View.OnClickListener, com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.ViewData,ViewData{
    private UniversityModel universityModel=null;
    private CircleImageView uni_image;
    private TextView uni_name,uni_email,uni_phone,uni_country,uni_major,uni_address,uni_website;
    private Button msgBtn,locBtn,updBtn;
    private String who_visit_myProfile="";
    private DiagonalView diagonalView;
    private Target target;
    private Activity_Profile activity_profile;
    private Presenter presenter;
    private com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.Presenter uniPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_universty,container,false);
        initView(view);
        presenter = new PresenterImp(this,getActivity());
        uniPresenter = new com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.PresenterImp(this,getActivity());

        activity_profile = (Activity_Profile) getActivity();
        getDataFromBundle();
        return view;
    }

    private void initView(View view)
    {
        diagonalView= (DiagonalView) view.findViewById(R.id.secondImage);
        uni_image   = (CircleImageView) view.findViewById(R.id.profile_universityImage);
        uni_name    = (TextView) view.findViewById(R.id.profile_universityName);
        uni_email   = (TextView) view.findViewById(R.id.profile_universityEmail);
        uni_phone   = (TextView) view.findViewById(R.id.profile_universityPhone);
        uni_country = (TextView) view.findViewById(R.id.profile_universityCountry);
        uni_major   = (TextView) view.findViewById(R.id.profile_universityMajor);
        uni_address = (TextView) view.findViewById(R.id.profile_universityAddress);
        uni_website = (TextView) view.findViewById(R.id.profile_universityWebsite);
        msgBtn      = (Button) view.findViewById(R.id.profile_uni_msgBtn);
        locBtn      = (Button) view.findViewById(R.id.profile_uni_locBtn);
        updBtn      = (Button) view.findViewById(R.id.profile_uni_updBtn);
        msgBtn.setOnClickListener(this);
        locBtn.setOnClickListener(this);
        updBtn.setOnClickListener(this);
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
            }else if (bundle.getString("uniId")!=null)
            {
                String uniId = bundle.getString("uniId");
                uniPresenter.getUniversityData("university",uniId);
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
        uni_website.setText(universityModel.getUni_site().toString());


        if (!universityModel.getUser_photo().equals("0"))
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    uni_image.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(getActivity()).load(Uri.parse(Tags.image_path+universityModel.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

        }
        else
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    uni_image.setImageBitmap(bitmap);
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
    private void updateUI2(UniversityModel universityModel) {
        uni_name.setText(universityModel.getUni_name().toString());
        uni_email.setText(universityModel.getUni_email().toString());
        uni_phone.setText(universityModel.getUni_phone().toString());
        uni_country.setText(universityModel.getUni_country().toString());
        uni_address.setText(universityModel.getUni_address().toString());
        uni_major.setText(universityModel.getUni_major().toString());
        uni_website.setText(universityModel.getUni_site().toString());


        if (!universityModel.getUser_photo().equals("0"))
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    uni_image.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(getActivity()).load(Uri.parse(Tags.image_path+universityModel.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

        }
        else
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    uni_image.setImageBitmap(bitmap);
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
            case R.id.profile_uni_msgBtn:
                Create_ChatRoom();
                NavigateToChatActivity();
                break;
            case R.id.profile_uni_locBtn:
                DisplayUserLocationOnMap();
                break;
            case R.id.profile_uni_updBtn:
                break;
        }
    }
    private void Create_ChatRoom() {
        if (activity_profile.logged_user_Data!=null)
        {
            String currUserId = activity_profile.logged_user_Data.getUserId();
            String chatUserId = universityModel.getUni_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            String currUserId = activity_profile.logged_publisher_Model.getPub_username();
            String chatUserId = universityModel.getUni_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
        else if (activity_profile.logged_library_Model!=null)
        {
            String currUserId = activity_profile.logged_library_Model.getLib_username();
            String chatUserId = universityModel.getUni_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
        else if (activity_profile.logged_university_Model!=null)
        {
            String currUserId = activity_profile.logged_university_Model.getUni_username();
            String chatUserId =universityModel.getUni_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
        else if (activity_profile.logged_company_Model!=null)
        {
            String currUserId = activity_profile.logged_company_Model.getComp_username();
            String chatUserId = universityModel.getUni_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
    }
    private void NavigateToChatActivity()
    {
        if (activity_profile.logged_user_Data!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","user");
            intent.putExtra("chat_userType","university");
            intent.putExtra("curr_user",activity_profile.logged_user_Data);
            intent.putExtra("chat_user",universityModel);
            getActivity().startActivity(intent);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","publisher");
            intent.putExtra("chat_userType","university");
            intent.putExtra("curr_user",activity_profile.logged_publisher_Model);
            intent.putExtra("chat_user",universityModel);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_library_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","library");
            intent.putExtra("chat_userType","university");
            intent.putExtra("curr_user",activity_profile.logged_library_Model);
            intent.putExtra("chat_user",universityModel);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_university_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","university");
            intent.putExtra("chat_userType","university");
            intent.putExtra("curr_user",activity_profile.logged_university_Model);
            intent.putExtra("chat_user",universityModel);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_company_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","company");
            intent.putExtra("chat_userType","university");
            intent.putExtra("curr_user",activity_profile.logged_company_Model);
            intent.putExtra("chat_user",universityModel);
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
            intent.putExtra("universityData",universityModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            String lat = activity_profile.logged_publisher_Model.getPub_lat();
            String lng =activity_profile.logged_publisher_Model.getPub_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("universityData",universityModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_library_Model!=null)
        {
            String lat = activity_profile.logged_library_Model.getLat();
            String lng =activity_profile.logged_library_Model.getLng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("universityData",universityModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_university_Model!=null)
        {
            String lat = activity_profile.logged_university_Model.getUni_lat();
            String lng =activity_profile.logged_university_Model.getUni_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("universityData",universityModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_company_Model!=null)
        {
            String lat = activity_profile.logged_company_Model.getComp_lat();
            String lng =activity_profile.logged_company_Model.getComp_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("universityData",universityModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
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

    }

    @Override
    public void onUniversityDataSuccess(UniversityModel universityModel) {

        updateUI2(universityModel);
    }

    @Override
    public void onFailed(String error) {
        Log.e("chatcreated",error);

    }
}
