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
        void setUserNameError();
        void setUserName_invalidError();
        void setPasswordError();
        void setPassword_invalidError();
        void onSuccess_NormalUserData(NormalUserData normalUserModel);
        void onSuccess_LibraryData(LibraryModel libraryModel);
        void onSuccess_PublisherData(PublisherModel publisherModel);
        void onFailed(String error);
    }

   void Login(String username, String password, Login_ModelInteractor.onCompleteListener listener, Context context);
}
