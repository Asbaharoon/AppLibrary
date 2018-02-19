package com.semicolon.librarians.libraryguide.MVP.DisplayUsersDataMVP;

import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;


public interface ViewData {
    void onNormalUserDataSuccess(NormalUserData normalUserData);
    void onPublisherDataSuccess(PublisherModel publisherModel);
    void onLibraryDataSuccess(LibraryModel libraryModel);
    void onCompanyDataSuccess(CompanyModel companyModel);
    void onUniversityDataSuccess(UniversityModel universityModel);
    void onFailed(String error);
}
