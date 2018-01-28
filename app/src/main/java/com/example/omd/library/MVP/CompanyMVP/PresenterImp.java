package com.example.omd.library.MVP.CompanyMVP;

import android.content.Context;

import com.example.omd.library.Models.CompanyModel;

import java.util.List;


public class PresenterImp implements Presenter, Interactor.onCompleteListener {
    ViewData viewData;
    Context context;
    Interactor interactor;

    public PresenterImp(ViewData viewData, Context context) {
        this.viewData = viewData;
        this.context = context;
        interactor = new InteractorImp();


    }

    @Override
    public void getCompanyData() {
        interactor.getCompanyData(this);
    }


    @Override
    public void onCompanyDataSuccess(List<CompanyModel> companyModelList) {
        if (viewData!=null)
        {
            viewData.onCompanyDataSuccess(companyModelList);
        }
    }

    @Override
    public void onCompanyDataFailed(String error) {
        if (viewData!=null)
        {
            viewData.onCompanyDataFailed(error);
        }
    }

    @Override
    public void hideProgressBar() {
        if (viewData!=null)
        {
            viewData.hideProgressBar();
        }
    }
}
