package com.example.omd.library.Login_Register.Registration;

import com.example.omd.library.Models.NormalUserData;

/**
 * Created by Delta on 24/12/2017.
 */

public interface ViewData {
    void setNormalUserFirstName_Error();
    void setNormalUserLastName_Error();
    void setNormalUserEmail_Error();
    void setNormalUserCountry_Error();
    void setNormalUserPassword_Error();

    void setPublisherFirstName_Error();
    void setPublisherLastName_Error();
    void setPublisherEmail_Error();
    void setPublisherCountry_Error();
    void setPublisherPassword_Error();

    void setLibraryName_Error();
    void setLibraryCommission_Error();
    void setLibraryOtherType_Error();
    void setLibraryCountry_Error();
    void setLibraryPassword_Error();

    void showProgressDialog();
    void hideProgressDialog();
    void navigateTO_HomeActivity();
    void setError(String error);
    void onNormalUserDataSuccess(NormalUserData normalUserData);
    void onPublisherDataSuccess();
    void onLibraryDataSuccess();

}
