package com.example.omd.library.MVP.CompanyMVP;


import com.example.omd.library.Models.CompanyModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Interactor {
    interface onCompleteListener
    {
        void onCompanyDataSuccess(List<CompanyModel> companyModelList);
        void onCompanyDataFailed(String error);
        void hideProgressBar();

    }
    void getCompanyData(onCompleteListener listener);
}
