package com.semicolon.librarians.library.MVP.Login_RegisterMVP.Login;

import android.content.Context;

import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;

/**
 * Created by Delta on 10/01/2018.
 */

public interface Login_ModelInteractor {
    interface onCompleteListener
    {
        void setUserNameError();
        void setUserName_invalidError();
        void setPasswordError();
        void setPassword_invalidError();
        void onSuccess_NormalUserData(NormalUserData normalUserModel);
        void onSuccess_LibraryData(LibraryModel libraryModel);
        void onSuccess_PublisherData(PublisherModel publisherModel);
        void onSuccess_UniversityData(UniversityModel universityModel);
        void onSuccess_CompanyData(CompanyModel companyModel);
        void onFailed(String error);
        void showProgressDialog();
        void hideProgressDialog();
        void showUserTypeDialog();

    }

   void Login(String userType,String username, String password, Login_ModelInteractor.onCompleteListener listener, Context context);
}
