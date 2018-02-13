package com.semicolon.librarians.library.MVP.DisplayUsersDataMVP;

import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;


public interface ViewData {
    void onNormalUserDataSuccess(NormalUserData normalUserData);
    void onPublisherDataSuccess(PublisherModel publisherModel);
    void onLibraryDataSuccess(LibraryModel libraryModel);
    void onCompanyDataSuccess(CompanyModel companyModel);
    void onUniversityDataSuccess(UniversityModel universityModel);
    void onFailed(String error);
}
