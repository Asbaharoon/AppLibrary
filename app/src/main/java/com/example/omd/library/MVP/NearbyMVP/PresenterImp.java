package com.example.omd.library.MVP.NearbyMVP;

import android.content.Context;

import com.example.omd.library.Models.CompanyModel;
import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.Models.UniversityModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Delta on 17/01/2018.
 */

public class PresenterImp implements Presenter,Interactor.onCompleteListener {

    Context context;
    ViewData viewData;
    Interactor interactor;

    public PresenterImp(Context context, ViewData viewData) {
        this.context = context;
        this.viewData = viewData;
        interactor = new InteractorImp();
    }

   /* @Override
    public void getNearbyUsers(String userType, LatLng latLng) {
        interactor.getNearbyUsers(userType,latLng,this);
    }

    @Override
    public void getNearbyPublishers(String userType, LatLng latLng) {
        interactor.getNearbyPublishers(userType,latLng,this);
    }

    @Override
    public void getNearbyLibraries(String userType, LatLng latLng) {
        interactor.getNearbyLibraries(userType,latLng,this);
    }

    @Override
    public void getNearbyUniversities(String userType, LatLng latLng) {
        interactor.getNearbyUniversities(userType,latLng,this);
    }

    @Override
    public void getNearbyCompanies(String userType, LatLng latLng) {
        interactor.getNearbyCompanies(userType,latLng,this);
    }*/

    @Override
    public void onUsersDataSuccess(List<NormalUserData> normalUserDataList) {
        if (viewData!=null)
        {
            viewData.onUsersDataSuccess(normalUserDataList);
        }
    }

    @Override
    public void onPublisherDataSuccess(List<PublisherModel> publisherModelList) {
        if (viewData!=null)
        {
            viewData.onPublisherDataSuccess(publisherModelList);
        }
    }

    @Override
    public void onLibraryDataSuccess(List<LibraryModel> libraryModelList) {

        if (viewData!=null)
        {
            viewData.onLibraryDataSuccess(libraryModelList);
        }
    }

    @Override
    public void onUniversityDataSuccess(List<UniversityModel> universityModelList) {

        if (viewData!=null)
        {
            viewData.onUniversityDataSuccess(universityModelList);
        }
    }

    @Override
    public void onCompanyDataSuccess(List<CompanyModel> companyModelList) {
        if (viewData!=null)
        {
            viewData.onCompanyDataSuccess(companyModelList);
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
    public void getNearbyUsers(String currUserType, String filteredUserType, LatLng currLatLng) {
        interactor.getNearbyUsers(currUserType,filteredUserType,currLatLng,this);
    }
}
