package com.semicolon.librarians.libraryguide.MVP.UpdateFB_GM_Signin_Profile_MVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.NormalUserData;

/**
 * Created by Delta on 22/01/2018.
 */

public class PresenterImp implements Presenter,Interactor.onCompleteListener {
    ViewData viewData;
    Context context;
    Interactor interactor;
    public PresenterImp(ViewData viewData, Context context) {
        this.viewData = viewData;
        this.context = context;
        interactor = new InteractorImp();


    }


    @Override
    public void UpdateUserData(String user_photo, String user_userName, String user_name, String user_email, String user_phone, String user_country) {
        interactor.UpdateUserData(user_photo,user_userName,user_name,user_email,user_phone,user_country,context,this);
    }

    @Override
    public void setNormalUserPhone_Error() {

        if (viewData!=null)
        {
            viewData.setNormalUserPhone_Error();
        }
    }

    @Override
    public void setNormalUserEmail_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUserEmail_Error();
        }
    }

    @Override
    public void setNormalUser_invalidEmail_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUser_invalidEmail_Error();
        }
    }

    @Override
    public void setNormalUserCountry_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUserCountry_Error();
        }
    }

    @Override
    public void onNormalUserDataSuccess(NormalUserData normalUserData) {
        if (viewData!=null)
        {
            viewData.onNormalUserDataSuccess(normalUserData);
        }
    }

    @Override
    public void showProgressDialog() {
        if (viewData!=null)
        {
            viewData.showProgressDialog();
        }
    }

    @Override
    public void onFailed(String error) {

        if (viewData!=null)
        {
            viewData.onFailed(error);
        }
    }
}
