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
import com.semicolon.librarians.libraryguide.Activities.UpdateProfiles;
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

public class Fragment_UserProfile extends Fragment implements View.OnClickListener , com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.ViewData,ViewData{
    private NormalUserData userData=null;
    private CircleImageView userImage;
    private ImageView back;
    private TextView userName,userEmail,userPhone,userCountry;
    private Button msgBtn,locBtn,updBtn;
    private String who_visit_myProfile="";
    private DiagonalView diagonalView;
    private Target target;
    private Activity_Profile activity_profile;
    private Presenter presenter;
    private com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.Presenter userPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), Tags.font,true);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_user,container,false);
        initView(view);
        presenter = new PresenterImp(this,getActivity());
        userPresenter = new com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP.PresenterImp(this,getActivity());
        activity_profile = (Activity_Profile) getActivity();
        getDataFromBundle();
        return view;
    }

    private void initView(View view)
    {
        diagonalView= (DiagonalView) view.findViewById(R.id.secondImage);
        userImage   = (CircleImageView) view.findViewById(R.id.profile_userImage);
        userName    = (TextView) view.findViewById(R.id.profile_userName);
        userEmail   = (TextView) view.findViewById(R.id.profile_userEmail);
        userPhone   = (TextView) view.findViewById(R.id.profile_userPhone);
        userCountry = (TextView) view.findViewById(R.id.profile_userCountry);
        msgBtn      = (Button) view.findViewById(R.id.profile_user_msgBtn);
        locBtn      = (Button) view.findViewById(R.id.profile_user_locBtn);
        updBtn      = (Button) view.findViewById(R.id.profile_user_updBtn);
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
            if (bundle.getSerializable("userData")!=null)
            {
                who_visit_myProfile = bundle.getString("who_visit_myProfile");
                userData = (NormalUserData) bundle.getSerializable("userData");

                updateUI(userData);
            }else if (bundle.getString("userId")!=null)
            {
                String userId = bundle.getString("userId");
                userPresenter.getNormalUserData("user",userId);
            }
        }
    }

    private void updateUI(NormalUserData userData) {
        userName.setText(userData.getUserName());
        userEmail.setText(userData.getUserEmail());
        userPhone.setText(userData.getUserPhone());
        if (userData.getTitle()==null)
        {
            userCountry.setText(userData.getUserCountry());

        }else
        {
            userCountry.setText(userData.getTitle());

        }

        if (userData.getUserPhoto()!=null)
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    userImage.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(getActivity()).load(Uri.parse(userData.getUserPhoto())).placeholder(R.drawable.user_profile).into(target);
        }else
            {
                if (!userData.getUser_photo().equals("0"))
                {
                    target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            userImage.setImageBitmap(bitmap);
                            diagonalView.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                    Picasso.with(getActivity()).load(Uri.parse(Tags.image_path+userData.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

                }
                else
                    {
                        target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                userImage.setImageBitmap(bitmap);
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
            }

        if (who_visit_myProfile.equals("me"))
        {
            msgBtn.setVisibility(View.GONE);
            locBtn.setVisibility(View.GONE);
            //updBtn.setVisibility(View.VISIBLE);
        }
        else
            {
                msgBtn.setVisibility(View.VISIBLE);
                locBtn.setVisibility(View.VISIBLE);
                //updBtn.setVisibility(View.GONE);
            }
    }

    private void updateUI2(NormalUserData userData)
    {
        userName.setText(userData.getUserName().toString());
        userEmail.setText(userData.getUserEmail().toString());
        userPhone.setText(userData.getUserPhone().toString());
        userCountry.setText(userData.getUserCountry().toString());

        if (userData.getUserPhoto()!=null)
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    userImage.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(getActivity()).load(Uri.parse(userData.getUserPhoto())).placeholder(R.drawable.user_profile).into(target);
        }else
        {
            if (!userData.getUser_photo().equals("0"))
            {
                target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        userImage.setImageBitmap(bitmap);
                        diagonalView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                Picasso.with(getActivity()).load(Uri.parse(Tags.image_path+userData.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

            }
            else
            {
                target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        userImage.setImageBitmap(bitmap);
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
            case R.id.profile_user_msgBtn:
                Create_ChatRoom();
                NavigateToChatActivity();
                break;
            case R.id.profile_user_locBtn:
                DisplayUserLocationOnMap();
                break;
            case R.id.profile_user_updBtn:
                UpdateProfile();
                break;
        }
    }

    private void UpdateProfile() {
        Intent intent = new Intent(getActivity(), UpdateProfiles.class);
        intent.putExtra("userType","user");
        intent.putExtra("userData",userData);
        startActivity(intent);
    }

    private void Create_ChatRoom() {
        if (activity_profile.logged_user_Data!=null)
        {
            String currUserId = activity_profile.logged_user_Data.getUserId();
            String chatUserId = userData.getUserId();
            presenter.Create_ChatRoom(currUserId,chatUserId);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            String currUserId = activity_profile.logged_publisher_Model.getPub_username();
            String chatUserId = userData.getUserId();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
        else if (activity_profile.logged_library_Model!=null)
        {
            String currUserId = activity_profile.logged_library_Model.getLib_username();
            String chatUserId = userData.getUserId();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
        else if (activity_profile.logged_university_Model!=null)
        {
            String currUserId = activity_profile.logged_university_Model.getUni_username();
            String chatUserId = userData.getUserId();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
        else if (activity_profile.logged_company_Model!=null)
        {
            String currUserId = activity_profile.logged_company_Model.getComp_username();
            String chatUserId = userData.getUserId();
            presenter.Create_ChatRoom(currUserId,chatUserId);

        }
    }


    private void NavigateToChatActivity()
    {
        if (activity_profile.logged_user_Data!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","user");
            intent.putExtra("chat_userType","user");
            intent.putExtra("curr_user",activity_profile.logged_user_Data);
            intent.putExtra("chat_user",userData);
            getActivity().startActivity(intent);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","publisher");
            intent.putExtra("chat_userType","user");
            intent.putExtra("curr_user",activity_profile.logged_publisher_Model);
            intent.putExtra("chat_user",userData);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_library_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","library");
            intent.putExtra("chat_userType","user");
            intent.putExtra("curr_user",activity_profile.logged_library_Model);
            intent.putExtra("chat_user",userData);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_university_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","university");
            intent.putExtra("chat_userType","user");
            intent.putExtra("curr_user",activity_profile.logged_university_Model);
            intent.putExtra("chat_user",userData);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_company_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","company");
            intent.putExtra("chat_userType","user");
            intent.putExtra("curr_user",activity_profile.logged_company_Model);
            intent.putExtra("chat_user",userData);
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
            intent.putExtra("userData",userData);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            String lat = activity_profile.logged_publisher_Model.getPub_lat();
            String lng =activity_profile.logged_publisher_Model.getPub_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("userData",userData);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_library_Model!=null)
        {
            String lat = activity_profile.logged_library_Model.getLat();
            String lng =activity_profile.logged_library_Model.getLng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("userData",userData);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_university_Model!=null)
        {
            String lat = activity_profile.logged_university_Model.getUni_lat();
            String lng =activity_profile.logged_university_Model.getUni_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("userData",userData);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_company_Model!=null)
        {
            String lat = activity_profile.logged_company_Model.getComp_lat();
            String lng =activity_profile.logged_company_Model.getComp_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("userData",userData);
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

        updateUI2(normalUserData);
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

    }

    @Override
    public void onFailed(String error) {
        Log.e("chatcreated",error);

    }
}
