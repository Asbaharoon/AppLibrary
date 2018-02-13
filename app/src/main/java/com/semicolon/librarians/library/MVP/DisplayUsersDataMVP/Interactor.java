package com.semicolon.librarians.library.MVP.DisplayUsersDataMVP;

import android.content.Context;

import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;


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
