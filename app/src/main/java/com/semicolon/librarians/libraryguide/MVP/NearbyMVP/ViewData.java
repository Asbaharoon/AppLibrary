package com.semicolon.librarians.libraryguide.MVP.NearbyMVP;

import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;

import java.util.List;

/**
 * Created by Delta on 17/01/2018.
 */

public interface ViewData {
    void onUsersDataSuccess(List<NormalUserData> normalUserDataList);
    void onPublisherDataSuccess(List<PublisherModel> publisherModelList);
    void onLibraryDataSuccess(List<LibraryModel> libraryModelList);
    void onUniversityDataSuccess(List<UniversityModel> universityModelList);
    void onCompanyDataSuccess(List<CompanyModel> companyModelList);
    void onFailed(String error);
}
