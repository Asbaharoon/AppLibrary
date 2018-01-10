package com.example.omd.library.Login_Register.Registration;

import android.content.Context;

import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;

/**
 * Created by Delta on 24/12/2017.
 */

public interface Model_Interactor {
    interface onCompleteListener
    {
        void setNormalUserFirstName_Error();
        void setNormalUserLastName_Error();
        void setNormalUserEmail_Error();
        void setNormalUser_invalidEmail_Error();
        void setNormalUserCountry_Error();
        void setNormalUserPassword_Error();

        void setPublisherFirstName_Error();
        void setPublisherLastName_Error();
        void setPublisherEmail_Error();
        void setPublisher_invalidEmail_Error();
        void setPublisherCountry_Error();
        void setPublisherPassword_Error();

        void setLibraryName_Error();
        void setLibraryEmail_Error();
        void setLibraryCommission_Error();
        void setLibraryOtherType_Error();
        void setLibraryCountry_Error();
        void setLibraryPassword_Error();
        void setLibraryLat_lng_Error();


        void showProgress_Dialog();
        void hideProgress_Dialog();
        void onNormalUserDataSuccess(NormalUserData normalUserData);
        void onPublisherDataSuccess(PublisherModel publisherModel);
        void onLibraryDataSuccess(LibraryModel libraryModel);
        void onFailed(String error);
    }
    //
    void NormalUserRegistration(String userType, String first_name, String last_name, String email, String country, String password, String phone, String job, String interests, Model_Interactor.onCompleteListener listener, Context context);
    void PublisherRegistration(String userType,String first_name,String last_name,String email,String country,String password,String phone,String expertise,String website_url,Model_Interactor.onCompleteListener listener,Context context);
    void LibraryRegistration (String userType,String libName,String libEmail,String libCommession,String libCountry,String libExpertise,String libType,String libOtherType,String libPassword,String lat,String lng,Model_Interactor.onCompleteListener listener,Context context);
}
