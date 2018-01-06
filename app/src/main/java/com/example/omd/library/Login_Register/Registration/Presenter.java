package com.example.omd.library.Login_Register.Registration;

/**
 * Created by Delta on 24/12/2017.
 */

public interface Presenter {

    void NormalUserRegistration(String userType,String first_name,String last_name,String email,String country,String password,String phone,String job,String interests);
    void PublisherRegistration(String userType,String first_name,String last_name,String email,String country,String password,String phone,String expertise,String website_url);
    void LibraryRegistration (String userType,String libName,String libEmail,String libCommission,String libCountry,String libExpertise,String libType,String libOtherType,String libPassword);
}
