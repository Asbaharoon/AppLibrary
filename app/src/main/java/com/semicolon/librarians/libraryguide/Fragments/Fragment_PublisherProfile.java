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
import android.widget.ImageView;
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
import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Delta on 06/02/2018.
 */

public class Fragment_PublisherProfile extends Fragment implements View.OnClickListener , com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.ViewData,ViewData{

    private PublisherModel publisherModel=null;
    private CircleImageView pub_image;
    private ImageView back;
    private TextView pub_name,pub_email,pub_phone,pub_country,pub_address,pub_town,pub_website;
    private Button msgBtn,locBtn,updBtn;
    private String who_visit_myProfile="";
    private DiagonalView diagonalView;
    private Target target;
    private Activity_Profile activity_profile;
    private Presenter presenter;
    private com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.Presenter pubPresenter;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), Tags.font,true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_publisher,container,false);
        activity_profile = (Activity_Profile) getActivity();

        presenter = new PresenterImp(this,getActivity());
        pubPresenter = new com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.PresenterImp(this,getActivity());

        initView(view);
        getDataFromBundle();
        return view;
    }

    private void initView(View view)
    {
        diagonalView= (DiagonalView) view.findViewById(R.id.secondImage);
        pub_image   = (CircleImageView) view.findViewById(R.id.profile_publisherImage);
        pub_name    = (TextView) view.findViewById(R.id.profile_publisherName);
        pub_email   = (TextView) view.findViewById(R.id.profile_publisherEmail);
        pub_phone   = (TextView) view.findViewById(R.id.profile_publisherPhone);
        pub_country = (TextView) view.findViewById(R.id.profile_publisherCountry);
        pub_address = (TextView) view.findViewById(R.id.profile_publisherAddress);
        pub_town    = (TextView) view.findViewById(R.id.profile_publisherTown);
        pub_website = (TextView) view.findViewById(R.id.profile_publisherWebsite);
        msgBtn      = (Button) view.findViewById(R.id.profile_pub_msgBtn);
        locBtn      = (Button) view.findViewById(R.id.profile_pub_locBtn);
        updBtn      = (Button) view.findViewById(R.id.profile_pub_updBtn);
        back        = (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        msgBtn.setOnClickListener(this);
        locBtn.setOnClickListener(this);
        updBtn.setOnClickListener(this);
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
            }else if (bundle.getString("pubId")!=null)
            {
                String pubId = bundle.getString("pubId");
                pubPresenter.getPublisherData("publisher",pubId);
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
        if (!publisherModel.getUser_photo().equals("0"))
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    pub_image.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(getActivity()).load(Uri.parse(Tags.image_path+publisherModel.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

        }
        else
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    pub_image.setImageBitmap(bitmap);
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
    private void updateUI2(PublisherModel publisherModel) {
        pub_name.setText(publisherModel.getPub_name().toString());
        pub_email.setText(publisherModel.getPub_email().toString());
        pub_phone.setText(publisherModel.getPub_phone().toString());
        pub_country.setText(publisherModel.getPub_country().toString());
        pub_address.setText(publisherModel.getPub_address().toString());
        pub_town.setText(publisherModel.getPub_town().toString());
        pub_website.setText(publisherModel.getPub_website().toString());
        if (!publisherModel.getUser_photo().equals("0"))
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    pub_image.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(getActivity()).load(Uri.parse(Tags.image_path+publisherModel.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

        }
        else
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    pub_image.setImageBitmap(bitmap);
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

    private void Create_ChatRoom() {
        if (activity_profile.logged_user_Data!=null)
        {
            String currUserId = activity_profile.logged_user_Data.getUserId();
            String chatUserId = publisherModel.getPub_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            String currUserId = activity_profile.logged_publisher_Model.getPub_username();
            String chatUserId = publisherModel.getPub_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
        else if (activity_profile.logged_library_Model!=null)
        {
            String currUserId = activity_profile.logged_library_Model.getLib_username();
            String chatUserId = publisherModel.getPub_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
        else if (activity_profile.logged_university_Model!=null)
        {
            String currUserId = activity_profile.logged_university_Model.getUni_username();
            String chatUserId = publisherModel.getPub_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
        else if (activity_profile.logged_company_Model!=null)
        {
            String currUserId = activity_profile.logged_company_Model.getComp_username();
            String chatUserId = publisherModel.getPub_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
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
            case R.id.profile_pub_msgBtn:
                Create_ChatRoom();
                NavigateToChatActivity();
                Log.e("msgbtn", "msgbtn");

                break;
            case R.id.profile_pub_locBtn:
                Log.e("locbtn", "locbtn");

                DisplayUserLocationOnMap();
                break;
            case R.id.profile_pub_updBtn:
                break;
        }
    }
    private void NavigateToChatActivity()
    {
        if (activity_profile.logged_user_Data!=null)
        {
            Log.e("curuser", "navigat");

            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","user");
            intent.putExtra("chat_userType","publisher");
            intent.putExtra("curr_user",activity_profile.logged_user_Data);
            intent.putExtra("chat_user",publisherModel);
            getActivity().startActivity(intent);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            Log.e("curpuv", "navigat");

            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","publisher");
            intent.putExtra("chat_userType","publisher");
            intent.putExtra("curr_user",activity_profile.logged_publisher_Model);
            intent.putExtra("chat_user",publisherModel);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_library_Model!=null)
        {
            Log.e("curlib", "navigat");

            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","library");
            intent.putExtra("chat_userType","publisher");
            intent.putExtra("curr_user",activity_profile.logged_library_Model);
            intent.putExtra("chat_user",publisherModel);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_university_Model!=null)
        {
            Log.e("curuni", "navigat");

            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","university");
            intent.putExtra("chat_userType","publisher");
            intent.putExtra("curr_user",activity_profile.logged_university_Model);
            intent.putExtra("chat_user",publisherModel);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_company_Model!=null)
        {
            Log.e("curcomp", "navigat");

            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","company");
            intent.putExtra("chat_userType","publisher");
            intent.putExtra("curr_user",activity_profile.logged_company_Model);
            intent.putExtra("chat_user",publisherModel);
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
            intent.putExtra("publisherData",publisherModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            String lat = activity_profile.logged_publisher_Model.getPub_lat();
            String lng =activity_profile.logged_publisher_Model.getPub_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("publisherData",publisherModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_library_Model!=null)
        {
            String lat = activity_profile.logged_library_Model.getLat();
            String lng =activity_profile.logged_library_Model.getLng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("publisherData",publisherModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_university_Model!=null)
        {
            String lat = activity_profile.logged_university_Model.getUni_lat();
            String lng =activity_profile.logged_university_Model.getUni_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("publisherData",publisherModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_company_Model!=null)
        {
            String lat = activity_profile.logged_company_Model.getComp_lat();
            String lng =activity_profile.logged_company_Model.getComp_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("publisherData",publisherModel);
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

        updateUI2(publisherModel);
    }

    @Override
    public void onLibraryDataSuccess(LibraryModel libraryModel) {

    }

    @Override
    public void onCompanyDataSuccess(CompanyModel companyModel) {

    }

    @Override
    public void onUniversityDataSuccess(UniversityModel universityModel) {

    }

    @Override
    public void onFailed(String error) {
        Log.e("chatcreated",error);

    }
}
