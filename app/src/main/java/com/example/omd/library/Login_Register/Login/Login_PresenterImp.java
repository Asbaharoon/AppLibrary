package com.example.omd.library.Login_Register.Login;

import android.content.Context;

import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;

/**
 * Created by Delta on 10/01/2018.
 */

public class Login_PresenterImp implements Login_Presenter,Login_ModelInteractor.onCompleteListener{
    Context context;
    Login_ViewData viewData;
    Login_ModelInteractor interactor;

    public Login_PresenterImp(Context context, Login_ViewData viewData) {
        this.context = context;
        this.viewData = viewData;
        interactor = new Login_ModelInteractorImp();
    }


    @Override
    public void Validate_Credential(String email, String password) {
        interactor.Login(email,password,this,context);
    }


    @Override
    public void setUserNameError() {
        if (viewData!=null)
        {
            viewData.setUserNameError();
        }
    }

    @Override
    public void setUserName_invalidError() {

        if (viewData!=null)
        {
           viewData.setUserName_invalidError();
        }
    }

    @Override
    public void setPasswordError() {
        if (viewData!=null)
        {
            viewData.setPasswordError();
        }
    }

    @Override
    public void setPassword_invalidError() {
        if (viewData!=null)
        {
            viewData.setPassword_invalidError();
        }
    }

    @Override
    public void onSuccess_NormalUserData(NormalUserData normalUserModel) {
        if (viewData!=null)
        {
            viewData.showProgressDialog();
            viewData.onSuccess_NormalUserData(normalUserModel);
        }
    }

    @Override
    public void onSuccess_LibraryData(LibraryModel libraryModel) {

    }

    @Override
    public void onSuccess_PublisherData(PublisherModel publisherModel) {

    }

    @Override
    public void onFailed(String error) {
        if (viewData!=null)
        {
            viewData.hideProgressDialog();
            viewData.onFailed(error);
        }
    }
}
