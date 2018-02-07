package com.semicolon.librarians.library.MVP.NearbyMVP;

import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;

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
