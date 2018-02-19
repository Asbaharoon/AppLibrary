package com.semicolon.librarians.libraryguide.MVP.Login_RegisterMVP.Login;

import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;

/**
 * Created by Delta on 10/01/2018.
 */

public interface Login_ViewData {
    void setUserNameError();
    void setUserName_invalidError();
    void setPasswordError();
    void setPassword_invalidError();
    void showProgressDialog();
    void hideProgressDialog();
    void showUserTypeDialog();
    void onSuccess_NormalUserData(NormalUserData normalUserModel);
    void onSuccess_LibraryData(LibraryModel libraryModel);
    void onSuccess_PublisherData(PublisherModel publisherModel);
    void onSuccess_UniversityData(UniversityModel universityModel);
    void onSuccess_CompanyData(CompanyModel companyModel);
    void onFailed(String error);
}
