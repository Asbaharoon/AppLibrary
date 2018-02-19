package com.semicolon.librarians.libraryguide.MVP.CompanyMVP;

import com.semicolon.librarians.libraryguide.Models.CompanyModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onCompanyDataSuccess(List<CompanyModel> companyModelList);
    void onCompanyDataFailed(String error);
    void hideProgressBar();
}
