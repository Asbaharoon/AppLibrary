package com.example.omd.library.Login_Register.Registration;

import com.example.omd.library.Fragments.RegisterFragment;
import com.example.omd.library.Models.NormalUserData;

/**
 * Created by Delta on 24/12/2017.
 */

public class PresenterImp implements Presenter,Model_Interactor.onCompleteListener {
    RegisterFragment mContext;
    ViewData viewData;
    Model_Interactor interactor;
    public PresenterImp(RegisterFragment mContext) {
        this.mContext = mContext;
        this.viewData =  mContext;

    }


    @Override
    public void setNormalUserFirstName_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUserFirstName_Error();
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
    public void setNormalUserEmail_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUserEmail_Error();
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
    public void setNormalUserPassword_Error() {
        if (viewData!=null)
        {
            viewData.setNormalUserPassword_Error();
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
    public void setPublisherLastName_Error() {
        if (viewData!=null)
        {
            viewData.setPublisherLastName_Error();
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
    public void setPublisherCountry_Error() {
        if (viewData!=null)
        {
            viewData.setPublisherCountry_Error();
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
    public void setLibraryName_Error() {
        if (viewData!=null)
        {
            viewData.setLibraryName_Error();
        }
    }

    @Override
    public void setLibraryCommission_Error() {
        if (viewData!=null)
        {
            viewData.setLibraryCommission_Error();
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
    public void setLibraryPassword_Error() {
        if (viewData!=null)
        {
            viewData.setLibraryPassword_Error();
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
    public void navigate_TO_HomeActivity() {
        if (viewData!=null)
        {
            viewData.navigateTO_HomeActivity();

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
    public void onPublisherDataSuccess() {

        if (viewData!=null)
        {
            viewData.onPublisherDataSuccess();
        }
    }

    @Override
    public void onLibraryDataSuccess() {
        if (viewData!=null)
        {
            viewData.onLibraryDataSuccess();
        }
    }


    @Override
    public void onFailed(String error) {
        if (viewData!=null)
        {
            viewData.setError(error);

        }
    }


    @Override
    public void NormalUserRegistration(String first_name, String last_name, String email, String country, String password, String phone, String job, String interests) {
        interactor = new Model_InteractorImp();
        interactor.NormalUserRegistration(first_name,last_name,email,country,password,phone,job,interests,this);
    }

    @Override
    public void PublisherRegistration(String first_name, String last_name, String email, String country, String password, String phone, String expertise, String website_url) {
        interactor = new Model_InteractorImp();
        interactor.PublisherRegistration(first_name,last_name,email,country,password,phone,expertise,website_url,this);

    }

    @Override
    public void LibraryRegistration(String libName, String libCommission, String libCountry, String libExpertise, String libType, String libOtherType, String libPassword) {
        interactor = new Model_InteractorImp();
        interactor.LibraryRegistration(libName,libCommission,libCountry,libExpertise,libType,libOtherType,libPassword,this);

    }
}
