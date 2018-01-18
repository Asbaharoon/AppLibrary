package com.example.omd.library.Login_RegisterMVP.Registration;

import android.content.Context;

import com.example.omd.library.Models.CompanyModel;
import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.Models.UniversityModel;

/**
 * Created by Delta on 24/12/2017.
 */

public class PresenterImp implements Presenter,Model_Interactor.onCompleteListener {
    Context mContext;
    ViewData viewData;
    Model_Interactor interactor;
    public PresenterImp(Context mContext,ViewData viewData) {
        this.mContext = mContext;
        this.viewData=viewData;


    }


    @Override
    public void setNormalUserFirstName_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUserFirstName_Error();
        }
    }

    @Override
    public void setNormalUser_invalidFirstName_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUser_invalidFirstName_Error();
        }
    }

    @Override
    public void setNormalUserLastName_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUserLastName_Error();
        }
    }

    @Override
    public void setNormalUser_invalidLastName_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUser_invalidLastName_Error();
        }
    }

    @Override
    public void setNormalUserEmail_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUserEmail_Error();
        }
    }

    @Override
    public void setNormalUser_invalidEmail_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUser_invalidEmail_Error();
        }
    }

    @Override
    public void setNormalUserCountry_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUserCountry_Error();
        }
    }

    @Override
    public void setNormalUserPhone_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUserPhone_Error();
        }
    }

    @Override
    public void setNormalUser_username_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUser_username_Error();
        }
    }

    @Override
    public void setNormalUser_invalidUsername_Error() {

        if (viewData!=null)
        {
            viewData.setNormalUser_invalidUsername_Error();
        }
    }

    @Override
    public void setNormalUserPassword_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUserPassword_Error();
        }
    }

    @Override
    public void setNormalUser_invalidPassword_Error() {

        if (viewData!=null)
        {
            viewData.setNormalUser_invalidPassword_Error();
        }
    }

    @Override
    public void setPublisherFirstName_Error() {
        if (viewData!=null)
        {
            viewData.setPublisherFirstName_Error();
        }
    }

    @Override
    public void setPublisher_invalidFirstName_Error() {

        if (viewData!=null)
        {
            viewData.setPublisher_invalidFirstName_Error();
        }
    }

    @Override
    public void setPublisherLastName_Error() {
        if (viewData!=null)
        {
            viewData.setPublisherLastName_Error();
        }
    }

    @Override
    public void setPublisher_invalidLastName_Error() {
        if (viewData!=null)
        {
            viewData.setPublisher_invalidLastName_Error();
        }
    }

    @Override
    public void setPublisherEmail_Error() {
        if (viewData!=null)
        {
            viewData.setPublisherEmail_Error();
        }
    }

    @Override
    public void setPublisher_invalidEmail_Error() {
        if (viewData!=null)
        {
            viewData.setPublisher_invalidEmail_Error();
        }
    }

    @Override
    public void setPublisherPhone_Error() {
        if (viewData!=null)
        {
            viewData.setPublisherPhone_Error();
        }
    }


    @Override
    public void setPublisherCountry_Error() {
        if (viewData!=null)
        {
            viewData.setPublisherCountry_Error();
        }
    }

    @Override
    public void setPublisherAddress_Error() {
        if (viewData!=null)
        {
            viewData.setPublisherAddress_Error();
        }
    }

    @Override
    public void setPublisherTown_Error() {
        if (viewData!=null)
        {
            viewData.setPublisherTown_Error();
        }
    }

    @Override
    public void setPublisher_site_Error() {
        if (viewData!=null)
        {
            viewData.setPublisher_site_Error();
        }
    }

    @Override
    public void setPublisher_invalidSite_Error() {
        if (viewData!=null)
        {
            viewData.setPublisher_invalidSite_Error();
        }

    }

    @Override
    public void setPublisher_username_Error() {

        if (viewData!=null)
        {
            viewData.setPublisher_username_Error();
        }
    }

    @Override
    public void setPublisher_invalidUsername_Error() {
        if (viewData!=null)
        {
            viewData.setPublisher_invalidUsername_Error();
        }
    }

    @Override
    public void setPublisherPassword_Error() {
        if (viewData!=null)
        {
            viewData.setPublisherPassword_Error();
        }
    }

    @Override
    public void setPublisher_invalidPassword_Error() {
        if (viewData!=null)
        {
            viewData.setPublisher_invalidPassword_Error();
        }
    }

    @Override
    public void setPublisherLat_LngError() {
        if (viewData!=null)
        {
            viewData.setPublisherLat_LngError();
        }
    }

    @Override
    public void setLibraryName_Error() {
        if (viewData!=null)
        {
            viewData.setLibraryName_Error();
        }
    }

    @Override
    public void setLibrary_invalidName_Error() {

        if (viewData!=null)
        {
            viewData.setLibrary_invalidName_Error();
        }
    }

    @Override
    public void setLibraryEmail_Error() {
        if (viewData!=null)
        {
            viewData.setLibraryEmail_Error();
        }
    }

    @Override
    public void setLibrary_invalidEmail_Error() {
        if (viewData!=null)
        {
            viewData.setLibrary_invalidEmail_Error();
        }

    }

    @Override
    public void setLibraryPhone_Error() {
        if (viewData!=null)
        {
            viewData.setLibraryPhone_Error();
        }
    }


    @Override
    public void setLibraryOtherType_Error() {
        if (viewData!=null)
        {
            viewData.setLibraryOtherType_Error();
        }
    }

    @Override
    public void setLibraryCountry_Error() {
        if (viewData!=null)
        {
            viewData.setLibraryCountry_Error();
        }
    }

    @Override
    public void setLibraryAddress_Error() {
        if (viewData!=null)
        {
            viewData.setLibraryAddress_Error();
        }
    }

    @Override
    public void setLibraryUsername_Error() {
        if (viewData!=null)
        {
            viewData.setLibraryUsername_Error();
        }
    }

    @Override
    public void setLibrary_invalidUsername_Error() {
        if (viewData!=null)
        {
            viewData.setLibrary_invalidUsername_Error();
        }
    }

    @Override
    public void setLibraryPassword_Error() {
        if (viewData!=null)
        {
            viewData.setLibraryPassword_Error();
        }
    }

    @Override
    public void setLibrary_invalidPassword_Error() {
        if (viewData!=null)
        {
            viewData.setLibrary_invalidPassword_Error();
        }
    }

    @Override
    public void setLibraryLat_lng_Error() {
        if (viewData!=null)
        {
            viewData.setLibraryLat_Lng_Error();
        }
    }

    @Override
    public void setUniversityName_Error() {
        if (viewData!=null)
        {
            viewData.setUniversityName_Error();
        }
    }

    @Override
    public void setUniversity_invalidName_Error() {
        if (viewData!=null)
        {
            viewData.setUniversity_invalidName_Error();
        }

    }

    @Override
    public void setUniversityEmail_Error() {
        if (viewData!=null)
        {
            viewData.setUniversityEmail_Error();
        }
    }

    @Override
    public void setUniversity_invalidEmail_Error() {

        if (viewData!=null)
        {
            viewData.setUniversity_invalidEmail_Error();
        }
    }

    @Override
    public void setUniversityPhone_Error() {
        if (viewData!=null)
        {
            viewData.setUniversityPhone_Error();
        }

    }

    @Override
    public void setUniversityCountry_Error() {
        if (viewData!=null)
        {
            viewData.setUniversityCountry_Error();
        }
    }

    @Override
    public void setUniversityAddress_Error() {
        if (viewData!=null)
        {
            viewData.setUniversityAddress_Error();
        }
    }

    @Override
    public void setUniversitySite_Error() {
        if (viewData!=null)
        {
            viewData.setUniversitySite_Error();
        }
    }

    @Override
    public void setUniversity_invalidSite_Error() {
        if (viewData!=null)
        {
            viewData.setUniversity_invalidSite_Error();
        }
    }

    @Override
    public void setUniversityMajor_Error() {
        if (viewData!=null)
        {
            viewData.setUniversityMajor_Error();
        }
    }

    @Override
    public void setUniversityUsername_Error() {
        if (viewData!=null)
        {
            viewData.setUniversityUsername_Error();
        }
    }

    @Override
    public void setUniversity_invalidUsername_Error() {
        if (viewData!=null)
        {
            viewData.setUniversity_invalidUsername_Error();
        }
    }

    @Override
    public void setUniversityPassword_Error() {
        if (viewData!=null)
        {
            viewData.setUniversityPassword_Error();
        }
    }

    @Override
    public void setUniversity_invalidPassword_Error() {
        if (viewData!=null)
        {
            viewData.setUniversity_invalidPassword_Error();
        }
    }

    @Override
    public void setUniversityLat_Lng_Error() {
        if (viewData!=null)
        {
            viewData.setUniversityLat_Lng_Error();        }

    }

    @Override
    public void setCompanyName_Error() {
        if (viewData!=null)
        {
            viewData.setCompanyName_Error();
        }
    }

    @Override
    public void setCompany_invalidName_Error() {

        if (viewData!=null)
        {
            viewData.setCompany_invalidName_Error();
        }
    }

    @Override
    public void setCompanyEmail_Error() {
        if (viewData!=null)
        {
            viewData.setCompanyEmail_Error();
        }
    }

    @Override
    public void setCompany_invalidEmail_Error() {
        if (viewData!=null)
        {
            viewData.setCompany_invalidEmail_Error();
        }
    }

    @Override
    public void setCompanyPhone_Error() {
        if (viewData!=null)
        {
            viewData.setCompanyPhone_Error();
        }
    }

    @Override
    public void setCompanyCountry_Error() {
        if (viewData!=null)
        {
            viewData.setCompanyCountry_Error();
        }
    }

    @Override
    public void setCompanyAddress_Error() {
        if (viewData!=null)
        {
            viewData.setCompanyAddress_Error();
        }

    }

    @Override
    public void setCompanyTown_Error() {
        if (viewData!=null)
        {
            viewData.setCompanyTown_Error();
        }
    }

    @Override
    public void setCompanySite_Error() {
        if (viewData!=null)
        {
            viewData.setCompanySite_Error();
        }
    }

    @Override
    public void setCompany_invalidSite_Error() {
        if (viewData!=null)
        {
            viewData.setCompany_invalidSite_Error();
        }
    }

    @Override
    public void setCompanyUsername_Error() {
        if (viewData!=null)
        {
            viewData.setCompanyUsername_Error();
        }
    }

    @Override
    public void setCompany_invalidUsername_Error() {
        if (viewData!=null)
        {
            viewData.setCompany_invalidUsername_Error();
        }
    }

    @Override
    public void setCompanyPassword_Error() {
        if (viewData!=null)
        {
            viewData.setCompanyPassword_Error();
        }

    }

    @Override
    public void setCompany_invalidPassword_Error() {
        if (viewData!=null)
        {
            viewData.setCompany_invalidPassword_Error();
        }
    }

    @Override
    public void setCompanyLat_Lng_Error() {
        if (viewData!=null)
        {
            viewData.setCompanyLat_Lng_Error();
        }
    }


    @Override
    public void showProgress_Dialog() {
        if (viewData!=null)
        {
            viewData.showProgressDialog();

        }
    }

    @Override
    public void hideProgress_Dialog() {
        if (viewData!=null)
        {
            viewData.hideProgressDialog();

        }
    }


    @Override
    public void onNormalUserDataSuccess(NormalUserData normalUserData) {
        if (viewData!=null)
        {
            viewData.onNormalUserDataSuccess(normalUserData);

        }
    }

    @Override
    public void onPublisherDataSuccess(PublisherModel publisherModel) {

        if (viewData!=null)
        {
            viewData.onPublisherDataSuccess(publisherModel);
        }
    }

    @Override
    public void onLibraryDataSuccess(LibraryModel libraryModel) {
        if (viewData!=null)
        {
            viewData.onLibraryDataSuccess(libraryModel);
        }
    }

    @Override
    public void onUniversityDataSuccess(UniversityModel universityModel) {
        if (viewData!=null)
        {
            viewData.onUniversityDataSuccess( universityModel);
        }
    }

    @Override
    public void onCompanyDataSuccess(CompanyModel companyModel) {
        if (viewData!=null)
        {
            viewData.onCompanyDataSuccess(companyModel);
        }
    }


    @Override
    public void onFailed(String error) {
        if (viewData!=null)
        {
            viewData.onFailed(error);

        }
    }


    @Override
    public void NormalUserRegistration(String userType, String photoName, String photo, String first_name, String last_name, String email, String country, String phone, String user_username, String password) {
        interactor = new Model_InteractorImp();
        interactor.NormalUserRegistration(userType,photoName,photo,first_name,last_name,email,country,phone,user_username,password,this,mContext);

    }

    @Override
    public void PublisherRegistration(String userType, String first_name, String last_name, String email, String country, String password, String phone, String username, String address, String town, String website_url, String lat, String lng) {
        interactor = new Model_InteractorImp();
        interactor.PublisherRegistration(userType,first_name,last_name,email,country,password,phone,username,address,town,website_url,lat,lng,this,mContext);

    }


    @Override
    public void LibraryRegistration(String userType, String libName, String libEmail, String libPhone, String libCountry, String libAddress, String libType, String libOtherType, String libUsername, String libPassword, String lat, String lng) {
        interactor = new Model_InteractorImp();
        interactor.LibraryRegistration(userType,libName,libEmail,libPhone,libCountry,libAddress,libType,libOtherType,libUsername,libPassword,lat,lng,this,mContext);

    }

    @Override
    public void UniversityRegistration(String userType, String name, String email, String country, String phone, String username, String password, String major, String address, String site, String lat, String lng) {
        interactor = new Model_InteractorImp();
        interactor.UniversityRegistration(userType,name,email,country,phone,username,password,major,address,site,lat,lng,this,mContext);
    }

    @Override
    public void CompanyRegistration(String userType, String name, String email, String country, String phone, String username, String password, String address, String town, String site) {
        interactor = new Model_InteractorImp();
        interactor.CompanyRegistration(userType,name,email,country,phone,username,password,address,town,site,this,mContext);
    }


}
