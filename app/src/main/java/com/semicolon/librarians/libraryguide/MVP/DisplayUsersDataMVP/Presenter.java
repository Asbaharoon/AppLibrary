package com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Presenter {

    void getNormalUserData(String userType,String userId);
    void getPublisherData(String userType,String userId);
    void getLibraryData(String userType,String userId);
    void getUniversityData(String userType,String userId);
    void getCompanyData(String userType,String userId);
}
