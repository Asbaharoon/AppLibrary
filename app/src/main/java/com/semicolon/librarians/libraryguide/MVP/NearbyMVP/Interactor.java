package com.semicolon.librarians.libraryguide.MVP.NearbyMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
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

    void getNearbyUsers(String currUserType, String currUserId, String filteredUserType, LatLng currLatLng, Context context,Interactor.onCompleteListener listener);

}
