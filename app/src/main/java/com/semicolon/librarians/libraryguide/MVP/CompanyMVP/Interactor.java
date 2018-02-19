package com.semicolon.librarians.libraryguide.MVP.CompanyMVP;


import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.CompanyModel;

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
    void getCompanyData(String userName, Context context, onCompleteListener listener);
}
