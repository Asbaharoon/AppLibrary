package com.example.omd.library.Login_Register.Login;

import android.content.Context;

import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;

/**
 * Created by Delta on 10/01/2018.
 */

public interface Login_ModelInteractor {
    interface onCompleteListener
    {
        void setEmailError(String error);
        void setPasswordError(String error);
        void onSuccess_NormalUserData(NormalUserData normalUserModel);
        void onSuccess_LibraryData(LibraryModel libraryModel);
        void onSuccess_PublisherData(PublisherModel publisherModel);
        void onFailed(String error);
    }

   void Login(String email, String password, Login_ModelInteractor.onCompleteListener listener, Context context);
}
