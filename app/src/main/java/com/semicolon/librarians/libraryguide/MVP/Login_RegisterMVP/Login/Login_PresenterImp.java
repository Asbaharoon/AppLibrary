package com.semicolon.librarians.libraryguide.MVP.Login_RegisterMVP.Login;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;

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
    public void Validate_Credential(String userType,String email, String password) {
        interactor.Login(userType,email,password,this,context);
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
            viewData.onSuccess_NormalUserData(normalUserModel);
        }
    }

    @Override
    public void onSuccess_LibraryData(LibraryModel libraryModel) {
        if (viewData!=null)
        {
            viewData.onSuccess_LibraryData(libraryModel);
        }
    }

    @Override
    public void onSuccess_PublisherData(PublisherModel publisherModel) {
        if (viewData!=null)
        {
            viewData.onSuccess_PublisherData(publisherModel);
        }
    }

    @Override
    public void onSuccess_UniversityData(UniversityModel universityModel) {
        if (viewData!=null)
        {
            viewData.onSuccess_UniversityData(universityModel);
        }
    }

    @Override
    public void onSuccess_CompanyData(CompanyModel companyModel) {
        if (viewData!=null)
        {
            viewData.onSuccess_CompanyData(companyModel);
        }
    }

    @Override
    public void onFailed(String error) {
        if (viewData!=null)
        {
            viewData.onFailed(error);
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
    public void hideProgressDialog() {
        if (viewData!=null)
        {
            viewData.hideProgressDialog();
        }

    }

    @Override
    public void showUserTypeDialog() {
        if (viewData!=null)
        {
            viewData.showUserTypeDialog();
        }
    }
}
