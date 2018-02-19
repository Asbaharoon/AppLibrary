package com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;


public interface Interactor {
    interface onCompleteListener
    {
        void onNormalUserDataSuccess(NormalUserData normalUserData);
        void onPublisherDataSuccess(PublisherModel publisherModel);
        void onLibraryDataSuccess(LibraryModel libraryModel);
        void onCompanyDataSuccess(CompanyModel companyModel);
        void onUniversityDataSuccess(UniversityModel universityModel);
        void onFailed(String error);
    }
    void getNormalUserData(String userType, String userId, Context context, Interactor.onCompleteListener listener);
    void getPublisherData(String userType,String userId,Context context,Interactor.onCompleteListener listener);
    void getLibraryData(String userType,String userId,Context context,Interactor.onCompleteListener listener);
    void getUniversityData(String userType,String userId,Context context,Interactor.onCompleteListener listener);
    void getCompanyData(String userType,String userId,Context context,Interactor.onCompleteListener listener);
}
