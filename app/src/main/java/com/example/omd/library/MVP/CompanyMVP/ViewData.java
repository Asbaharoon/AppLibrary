package com.example.omd.library.MVP.CompanyMVP;

import com.example.omd.library.Models.CompanyModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onCompanyDataSuccess(List<CompanyModel> companyModelList);
    void onCompanyDataFailed(String error);
    void hideProgressBar();
}
