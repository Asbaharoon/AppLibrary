package com.example.omd.library.Login_Register.Registration;

import android.text.TextUtils;

import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.Services.Tags;

/**
 * Created by Delta on 24/12/2017.
 */

public class Model_InteractorImp implements Model_Interactor {


    @Override
    public void NormalUserRegistration(String userType,String first_name, String last_name, String email, String country, String password, String phone, String job, String interests, onCompleteListener listener) {
        if (TextUtils.isEmpty(first_name))
        {
            listener.setNormalUserFirstName_Error();
        }
        else if (TextUtils.isEmpty(last_name))
        {
            listener.setNormalUserLastName_Error();
        }
        else if (TextUtils.isEmpty(email))
        {
            listener.setNormalUserEmail_Error();
        }
        else if (!email.matches(Tags.email_Regex))
        {
            listener.setNormalUser_invalidEmail_Error();
        }
        else if (TextUtils.isEmpty(country))
        {
            listener.setNormalUserCountry_Error();
        }
        else if (TextUtils.isEmpty(password))
        {
            listener.setNormalUserPassword_Error();
        }
        else
            {
                Registration_NormalUser(first_name,last_name, email,country, password,phone, job, interests, listener);
            }
    }

    @Override
    public void PublisherRegistration(String userType,String first_name, String last_name, String email, String country, String password, String phone, String expertise, String website_url, onCompleteListener listener) {
        if (TextUtils.isEmpty(first_name))
        {
            listener.setPublisherFirstName_Error();
        }
        else if (TextUtils.isEmpty(last_name))
        {
            listener.setPublisherLastName_Error();
        }
        else if (TextUtils.isEmpty(email))
        {
            listener.setPublisherEmail_Error();
        }
        else if (!email.matches(Tags.email_Regex))
        {
            listener.setPublisher_invalidEmail_Error();
        }
        else if (TextUtils.isEmpty(country))
        {
            listener.setPublisherCountry_Error();
        }
        else if (TextUtils.isEmpty(password))
        {
            listener.setPublisherPassword_Error();
        }
        else
        {

            Registration_PublisherData(first_name, last_name,email,country,password,phone,expertise,website_url,listener);

        }
    }

    @Override
    public void LibraryRegistration(String userType,String libName,String libEmail, String libCommission, String libCountry, String libExpertise, String libType, String libOtherType, String libPassword, onCompleteListener listener) {
        if (TextUtils.isEmpty(libName))
        {
            listener.setLibraryName_Error();
        }
        else if (TextUtils.isEmpty(libEmail))
        {
            listener.setLibraryEmail_Error();
        }
        else if (!libEmail.matches(Tags.email_Regex))
        {
            listener.setLibraryEmail_Error();
        }
        else if (TextUtils.isEmpty(libCommission))
        {
            listener.setLibraryCommission_Error();
        }
        else if (TextUtils.isEmpty(libCountry))
        {
            listener.setLibraryCountry_Error();
        }

        else if (libType.equals("Other"))
        {
            if (TextUtils.isEmpty(libOtherType))
            {
                listener.setLibraryOtherType_Error();
            }
            else if (TextUtils.isEmpty(libType))
            {
            listener.setLibraryOtherType_Error();
            }
            else if (TextUtils.isEmpty(libPassword))
            {
            listener.setLibraryPassword_Error();
            }
            else
                {
                Registration_LibraryData(libName,libEmail,libCommission,libCountry,libExpertise,libType,libOtherType,libPassword,listener);

                }
        }
        else if (TextUtils.isEmpty(libType))
        {
            listener.setLibraryOtherType_Error();

        }
        else if (TextUtils.isEmpty(libPassword))
        {
            listener.setLibraryPassword_Error();
        }
        else
        {
            Registration_LibraryData(libName,libEmail, libCommission,libCountry,libExpertise,libType,libOtherType,libPassword,listener);
        }
    }

    private void Registration_NormalUser(String first_name, String last_name, String email, String country, String password, String phone, String job, String interests, onCompleteListener listener)
    {
        //write the code here
        //inside Response write this line after initialize normaluser object
        listener.onNormalUserDataSuccess(new NormalUserData());
    }
    private void Registration_PublisherData(String first_name, String last_name, String email, String country, String password, String phone, String expertise, String website_url, onCompleteListener listener)
    {
        //write the code here
        //inside Response write this line after initialize publisher object
        listener.onPublisherDataSuccess(new PublisherModel());
    }
    private void Registration_LibraryData(String libName,String libEmail, String libCommission, String libCountry, String libExpertise, String libType, String libOtherType, String libPassword, onCompleteListener listener)
    {
        //
        //write the code here
        //inside Response write this line after initialize library object
        listener.onLibraryDataSuccess(new LibraryModel());
    }
}
