package com.semicolon.librarians.library.MVP.CountriesMVP;

import android.content.Context;

import com.semicolon.librarians.library.Models.CountriesModel;

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
    public void getCountryData() {
        interactor.getCountryData(this);
    }

    @Override
    public void onCountryDataSuccess(List<CountriesModel> countriesModelList) {
        if (viewData!=null)
        {
            viewData.onCountryDataSuccess(countriesModelList);
        }
    }

    @Override
    public void onCountryDataFailed(String error) {
        if (viewData!=null)
        {
            viewData.onCountryDataFailed(error);
        }
    }



}
