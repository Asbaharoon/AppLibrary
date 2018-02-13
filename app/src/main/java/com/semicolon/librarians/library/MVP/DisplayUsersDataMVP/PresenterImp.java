package com.semicolon.librarians.library.MVP.DisplayUsersDataMVP;

import android.content.Context;

import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;


public class PresenterImp implements Presenter, Interactor.onCompleteListener {
    ViewData viewData;
    Context context;
    Interactor interactor;

    public PresenterImp(ViewData viewData, Context context) {
        this.viewData = viewData;
        this.context = context;
        interactor = new InteractorImp();


    }

    @Override
    public void getNormalUserData(String userType, String userId) {
        interactor.getNormalUserData(userType,userId,context,this);
    }

    @Override
    public void getPublisherData(String userType, String userId) {
        interactor.getPublisherData(userType,userId,context,this);
    }

    @Override
    public void getLibraryData(String userType, String userId) {
        interactor.getLibraryData(userType,userId,context,this);
    }

    @Override
    public void getUniversityData(String userType, String userId) {
        interactor.getUniversityData(userType,userId,context,this);
    }

    @Override
    public void getCompanyData(String userType, String userId) {
        interactor.getCompanyData(userType,userId,context,this);
    }

    @Override
    public void onNormalUserDataSuccess(NormalUserData normalUserDataList) {
        if (viewData!=null)
        {
            viewData.onNormalUserDataSuccess(normalUserDataList);
        }
    }

    @Override
    public void onPublisherDataSuccess(PublisherModel publisherModelList) {
        if (viewData!=null)
        {
            viewData.onPublisherDataSuccess(publisherModelList);
        }
    }

    @Override
    public void onLibraryDataSuccess(LibraryModel libraryModelList) {
        if (viewData!=null)
        {
            viewData.onLibraryDataSuccess(libraryModelList);
        }
    }

    @Override
    public void onCompanyDataSuccess(CompanyModel companyModelList) {
        if (viewData!=null)
        {
            viewData.onCompanyDataSuccess(companyModelList);
        }
    }

    @Override
    public void onUniversityDataSuccess(UniversityModel universityModels) {
        if (viewData!=null)
        {
            viewData.onUniversityDataSuccess(universityModels);
        }
    }

    @Override
    public void onFailed(String error) {
        if (viewData!=null)
        {
            viewData.onFailed(error);
        }
    }
}
