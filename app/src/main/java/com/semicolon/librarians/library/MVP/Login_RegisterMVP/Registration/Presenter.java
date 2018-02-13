package com.semicolon.librarians.library.MVP.Login_RegisterMVP.Registration;

/**
 * Created by Delta on 24/12/2017.
 */

public interface Presenter {
//
    void NormalUserRegistration(String userType,String photo,String first_name,String last_name,String email,String country,String phone,String user_username,String password,String lat,String lng,String token);
    void PublisherRegistration(String userType,String photo,String first_name,String last_name,String email,String country,String password,String phone,String username,String address,String town,String website_url,String lat,String lng,String token);
    void LibraryRegistration (String userType,String photo,String libName,String libEmail,String libPhone,String libCountry,String libAddress ,String libType,String libUsername,String libPassword,String lat,String lng,String token);
    void UniversityRegistration(String userType,String photo,String name,String email,String country,String phone,String username,String password ,String major,String address,String site,String lat,String lng,String token);
    void CompanyRegistration(String userType,String photo,String name,String email,String country,String phone,String username,String password,String address,String town,String site,String lat,String lng,String token);


}
