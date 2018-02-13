package com.semicolon.librarians.library.MVP.UpdateFB_GM_Signin_Profile_MVP;

import com.semicolon.librarians.library.Models.NormalUserData;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void setNormalUserPhone_Error();
    void setNormalUserEmail_Error();
    void setNormalUser_invalidEmail_Error();
    void setNormalUserCountry_Error();
    void showProgressDialog();
    void onNormalUserDataSuccess(NormalUserData normalUserData);
    void onFailed(String error);
}
