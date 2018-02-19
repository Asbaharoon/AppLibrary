package com.semicolon.librarians.libraryguide.MVP.CountriesMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.CountriesModel;

import java.util.List;


public interface Interactor {
    interface onCompleteListener
    {
        void onCountryDataSuccess(List<CountriesModel>countriesModelList);
        void onCountryDataFailed(String error);

    }
    void getCountryData(Context context,onCompleteListener listener);
}
