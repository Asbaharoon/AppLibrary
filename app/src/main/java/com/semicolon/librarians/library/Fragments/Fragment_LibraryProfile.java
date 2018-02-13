package com.semicolon.librarians.library.Fragments;

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
import android.widget.Toast;

import com.intrusoft.squint.DiagonalView;
import com.semicolon.librarians.library.Activities.Activity_Profile;
import com.semicolon.librarians.library.Activities.Chat_Activity;
import com.semicolon.librarians.library.Activities.DisplayUserLocation_OnMap;
import com.semicolon.librarians.library.MVP.Create_ChatRoom_MVP.Presenter;
import com.semicolon.librarians.library.MVP.Create_ChatRoom_MVP.PresenterImp;
import com.semicolon.librarians.library.MVP.Create_ChatRoom_MVP.ViewData;
import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;
import com.semicolon.librarians.library.R;
import com.semicolon.librarians.library.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Delta on 06/02/2018.
 */

public class Fragment_LibraryProfile extends Fragment implements View.OnClickListener, com.semicolon.librarians.library.MVP.DisplayUsersDataMVP.ViewData,ViewData{

    private LibraryModel libraryModel=null;
    private CircleImageView lib_image;
    private TextView lib_name,lib_type,lib_email,lib_phone,lib_country,lib_address;
    private Button msgBtn,locBtn,updBtn;
    private String who_visit_myProfile="";
    private DiagonalView diagonalView;
    private Target target;
    private Activity_Profile activity_profile;
    private Presenter presenter;
    private com.semicolon.librarians.library.MVP.DisplayUsersDataMVP.Presenter libPresenter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_library,container,false);
        initView(view);
        activity_profile = (Activity_Profile) getActivity();
        presenter = new PresenterImp(this,getActivity());
        libPresenter = new com.semicolon.librarians.library.MVP.DisplayUsersDataMVP.PresenterImp(this,getActivity());

        getDataFromBundle();
        return view;
    }

    private void initView(View view)
    {
        diagonalView= (DiagonalView) view.findViewById(R.id.secondImage);
        lib_image   = (CircleImageView) view.findViewById(R.id.profile_libraryImage);
        lib_name    = (TextView) view.findViewById(R.id.profile_libraryName);
        lib_type    = (TextView) view.findViewById(R.id.profile_libraryType);
        lib_email   = (TextView) view.findViewById(R.id.profile_libraryEmail);
        lib_country = (TextView) view.findViewById(R.id.profile_libraryCountry);
        lib_phone   = (TextView) view.findViewById(R.id.profile_libraryPhone);
        lib_address = (TextView) view.findViewById(R.id.profile_libraryAddress);
        msgBtn      = (Button) view.findViewById(R.id.profile_lib_msgBtn);
        locBtn      = (Button) view.findViewById(R.id.profile_lib_locBtn);
        updBtn      = (Button) view.findViewById(R.id.profile_lib_updBtn);

        msgBtn.setOnClickListener(this);
        locBtn.setOnClickListener(this);
        updBtn.setOnClickListener(this);
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
            }else if (bundle.getString("libId")!=null)
            {
                String libId = bundle.getString("libId");
                libPresenter.getLibraryData("library",libId);
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

        if (!libraryModel.getUser_photo().equals("0"))
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    lib_image.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(getActivity()).load(Uri.parse(Tags.image_path+libraryModel.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

        }
        else
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    lib_image.setImageBitmap(bitmap);
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
    private void updateUI2(LibraryModel libraryModel) {
        lib_name.setText(libraryModel.getLib_name().toString());
        lib_type.setText(libraryModel.getLib_type().toString());
        lib_email.setText(libraryModel.getLib_email().toString());
        lib_phone.setText(libraryModel.getLib_phone().toString());
        lib_country.setText(libraryModel.getLib_country().toString());
        lib_address.setText(libraryModel.getLib_address().toString());

        if (!libraryModel.getUser_photo().equals("0"))
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    lib_image.setImageBitmap(bitmap);
                    diagonalView.setImageBitmap(bitmap);

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(getActivity()).load(Uri.parse(Tags.image_path+libraryModel.getUser_photo())).placeholder(R.drawable.user_profile).into(target);

        }
        else
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    lib_image.setImageBitmap(bitmap);
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
            case R.id.profile_lib_msgBtn:
                Create_ChatRoom();
                NavigateToChatActivity();
                break;
            case R.id.profile_lib_locBtn:
                DisplayUserLocationOnMap();
                break;
            case R.id.profile_lib_updBtn:
                break;
        }
    }
    private void NavigateToChatActivity()
    {
        if (activity_profile.logged_user_Data!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","user");
            intent.putExtra("chat_userType","library");
            intent.putExtra("curr_user",activity_profile.logged_user_Data);
            intent.putExtra("chat_user",libraryModel);
            getActivity().startActivity(intent);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","publisher");
            intent.putExtra("chat_userType","library");
            intent.putExtra("curr_user",activity_profile.logged_publisher_Model);
            intent.putExtra("chat_user",libraryModel);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_library_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","library");
            intent.putExtra("chat_userType","library");
            intent.putExtra("curr_user",activity_profile.logged_library_Model);
            intent.putExtra("chat_user",libraryModel);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_university_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","university");
            intent.putExtra("chat_userType","library");
            intent.putExtra("curr_user",activity_profile.logged_university_Model);
            intent.putExtra("chat_user",libraryModel);
            getActivity().startActivity(intent);

        }
        else if (activity_profile.logged_company_Model!=null)
        {
            Intent intent = new Intent(getActivity(), Chat_Activity.class);
            intent.putExtra("curr_userType","company");
            intent.putExtra("chat_userType","library");
            intent.putExtra("curr_user",activity_profile.logged_company_Model);
            intent.putExtra("chat_user",libraryModel);
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
            intent.putExtra("universityData",libraryModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            String lat = activity_profile.logged_publisher_Model.getPub_lat();
            String lng =activity_profile.logged_publisher_Model.getPub_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("libraryData",libraryModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_library_Model!=null)
        {
            String lat = activity_profile.logged_library_Model.getLat();
            String lng =activity_profile.logged_library_Model.getLng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("libraryData",libraryModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_university_Model!=null)
        {
            String lat = activity_profile.logged_university_Model.getUni_lat();
            String lng =activity_profile.logged_university_Model.getUni_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("libraryData",libraryModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }else if (activity_profile.logged_company_Model!=null)
        {
            String lat = activity_profile.logged_company_Model.getComp_lat();
            String lng =activity_profile.logged_company_Model.getComp_lng();
            Intent intent = new Intent(getActivity(), DisplayUserLocation_OnMap.class);
            intent.putExtra("libraryData",libraryModel);
            intent.putExtra("currLat",lat);
            intent.putExtra("currLng",lng);

            getActivity().startActivity(intent);
        }
    }
    private void Create_ChatRoom() {
        if (activity_profile.logged_user_Data!=null)
        {
            String currUserId = activity_profile.logged_user_Data.getUserId();
            String chatUserId = libraryModel.getLib_username();
            Toast.makeText(activity_profile, "userid"+chatUserId+"\n"+"chat"+chatUserId, Toast.LENGTH_SHORT).show();
            presenter.Create_ChatRoom(currUserId,chatUserId);
        }else if (activity_profile.logged_publisher_Model!=null)
        {
            String currUserId = activity_profile.logged_publisher_Model.getPub_username();
            String chatUserId = libraryModel.getLib_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);
            Toast.makeText(activity_profile, "userid"+chatUserId+"\n"+"chat"+chatUserId, Toast.LENGTH_SHORT).show();

        }
        else if (activity_profile.logged_library_Model!=null)
        {
            String currUserId = activity_profile.library_Model.getLib_username();
            String chatUserId = libraryModel.getLib_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);
            Toast.makeText(activity_profile, "userid"+chatUserId+"\n"+"chat"+chatUserId, Toast.LENGTH_SHORT).show();

        }
        else if (activity_profile.logged_university_Model!=null)
        {
            String currUserId = activity_profile.logged_university_Model.getUni_username();
            String chatUserId = libraryModel.getLib_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);
            Toast.makeText(activity_profile, "userid"+chatUserId+"\n"+"chat"+chatUserId, Toast.LENGTH_SHORT).show();

        }
        else if (activity_profile.logged_company_Model!=null)
        {
            String currUserId = activity_profile.logged_company_Model.getComp_username();
            String chatUserId = libraryModel.getLib_username();
            presenter.Create_ChatRoom(currUserId,chatUserId);
            Toast.makeText(activity_profile, "userid"+chatUserId+"\n"+"chat"+chatUserId, Toast.LENGTH_SHORT).show();

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
        updateUI2(libraryModel);
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
