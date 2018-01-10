package com.example.omd.library.Login_Register.Login;

import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;

/**
 * Created by Delta on 10/01/2018.
 */

public interface Login_ViewData {
    void setEmailError(String error);
    void setPasswordError(String error);
    void showProgressDialog();
    void hideProgressDialog();
    void onSuccess_NormalUserData(NormalUserData normalUserModel);
    void onSuccess_LibraryData(LibraryModel libraryModel);
    void onSuccess_PublisherData(PublisherModel publisherModel);
    void onFailed(String error);
}
