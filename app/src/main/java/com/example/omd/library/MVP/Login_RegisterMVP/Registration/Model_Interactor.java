package com.example.omd.library.MVP.Login_RegisterMVP.Registration;

import android.content.Context;

import com.example.omd.library.Models.CompanyModel;
import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.Models.UniversityModel;

/**
 * Created by Delta on 24/12/2017.
 */

public interface Model_Interactor {
    interface onCompleteListener
    {
        void setNormalUserFirstName_Error();
        void setNormalUser_invalidFirstName_Error();
        void setNormalUserLastName_Error();
        void setNormalUser_invalidLastName_Error();
        void setNormalUserEmail_Error();
        void setNormalUser_invalidEmail_Error();
        void setNormalUserCountry_Error();
        void setNormalUserPhone_Error();
        void setNormalUser_username_Error();
        void setNormalUser_invalidUsername_Error();
        void setNormalUserPassword_Error();
        void setNormalUser_invalidPassword_Error();

        void setPublisherFirstName_Error();
        void setPublisher_invalidFirstName_Error();
        void setPublisherLastName_Error();
        void setPublisher_invalidLastName_Error();
        void setPublisherEmail_Error();
        void setPublisher_invalidEmail_Error();
        void setPublisherPhone_Error();
        void setPublisherCountry_Error();
        void setPublisherAddress_Error();
        void setPublisherTown_Error();
        void setPublisher_site_Error();
        void setPublisher_invalidSite_Error();
        void setPublisher_username_Error();
        void setPublisher_invalidUsername_Error();
        void setPublisherPassword_Error();
        void setPublisher_invalidPassword_Error();
        void setPublisherLat_LngError();


        void setLibraryName_Error();
        void setLibrary_invalidName_Error();
        void setLibraryEmail_Error();
        void setLibrary_invalidEmail_Error();
        void setLibraryPhone_Error();
        void setLibraryOtherType_Error();
        void setLibraryCountry_Error();
        void setLibraryAddress_Error();
        void setLibraryUsername_Error();
        void setLibrary_invalidUsername_Error();
        void setLibraryPassword_Error();
        void setLibrary_invalidPassword_Error();
        void setLibraryLat_lng_Error();

        void setUniversityName_Error();
        void setUniversity_invalidName_Error();
        void setUniversityEmail_Error();
        void setUniversity_invalidEmail_Error();
        void setUniversityPhone_Error();
        void setUniversityCountry_Error();
        void setUniversityAddress_Error();
        void setUniversitySite_Error();
        void setUniversity_invalidSite_Error();
        void setUniversityMajor_Error();
        void setUniversityUsername_Error();
        void setUniversity_invalidUsername_Error();
        void setUniversityPassword_Error();
        void setUniversity_invalidPassword_Error();
        void setUniversityLat_Lng_Error();

        void setCompanyName_Error();
        void setCompany_invalidName_Error();
        void setCompanyEmail_Error();
        void setCompany_invalidEmail_Error();
        void setCompanyPhone_Error();
        void setCompanyCountry_Error();
        void setCompanyAddress_Error();
        void setCompanyTown_Error();
        void setCompanySite_Error();
        void setCompany_invalidSite_Error();
        void setCompanyUsername_Error();
        void setCompany_invalidUsername_Error();
        void setCompanyPassword_Error();
        void setCompany_invalidPassword_Error();
        void setCompanyLat_Lng_Error();


        void showProgress_Dialog();
        void hideProgress_Dialog();
        void onNormalUserDataSuccess(NormalUserData normalUserData);
        void onPublisherDataSuccess(PublisherModel publisherModel);
        void onLibraryDataSuccess(LibraryModel libraryModel);
        void onUniversityDataSuccess(UniversityModel universityModel);
        void onCompanyDataSuccess(CompanyModel companyModel);
        void onFailed(String error);
    }
    //
    void NormalUserRegistration(String userType,String photoName,String photo,String first_name, String last_name, String email, String country, String phone, String username, String password ,String lat,String lng,Model_Interactor.onCompleteListener listener, Context context);
    void PublisherRegistration(String userType,String first_name,String last_name,String email,String country,String password,String phone,String username,String address,String town,String website_url,String lat,String lng,Model_Interactor.onCompleteListener listener, Context context);
    void LibraryRegistration (String userType, String libName, String libEmail, String libPhone, String libCountry, String libAddress, String libType, String libOtherType, String libUsername, String libPassword, String lat, String lng,Model_Interactor.onCompleteListener listener,Context context);
    void UniversityRegistration(String userType,String name,String email,String country,String phone,String username,String password ,String major,String address,String site,String lat,String lng,Model_Interactor.onCompleteListener listener, Context context);
    void CompanyRegistration(String userType,String name,String email,String country,String phone,String username,String password,String address,String town,String site,String lat,String lng,Model_Interactor.onCompleteListener listener, Context context);

}
