package com.example.omd.library.Login_Register.Registration;

/**
 * Created by Delta on 24/12/2017.
 */

public interface Presenter {

    void NormalUserRegistration(String first_name,String last_name,String email,String country,String password,String phone,String job,String interests);
    void PublisherRegistration(String first_name,String last_name,String email,String country,String password,String phone,String expertise,String website_url);
    void LibraryRegistration (String libName,String libCommission,String libCountry,String libExpertise,String libType,String libOtherType,String libPassword);
}
