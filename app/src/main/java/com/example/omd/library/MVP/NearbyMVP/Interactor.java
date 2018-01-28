package com.example.omd.library.MVP.NearbyMVP;

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

public interface Interactor {
    interface onCompleteListener
    {
        void onUsersDataSuccess(List<NormalUserData> normalUserDataList);
        void onPublisherDataSuccess(List<PublisherModel> publisherModelList);
        void onLibraryDataSuccess(List<LibraryModel> libraryModelList);
        void onUniversityDataSuccess(List<UniversityModel> universityModelList);
        void onCompanyDataSuccess(List<CompanyModel> companyModelList);
        void onFailed(String error);
    }

    void getNearbyUsers(String currUserType,String filteredUserType, LatLng currLatLng,Interactor.onCompleteListener listener);

}
