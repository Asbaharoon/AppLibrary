package com.semicolon.librarians.libraryguide.MVP.UpdateUsersProfile_MVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.NormalUserData;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Interactor {
    interface onCompleteListener
    {
        void setNormalUserPhone_Error();
        void setNormalUserEmail_Error();
        void setNormalUser_invalidEmail_Error();
        void setNormalUserCountry_Error();
        void onNormalUserDataSuccess(NormalUserData normalUserData);
        void showProgressDialog();
        void onFailed(String error);
    }
    void UpdateUserData(String user_photo, String user_userName, String user_name, String user_email, String user_phone, String user_country, Context context,Interactor.onCompleteListener listener);

}
