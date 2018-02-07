package com.semicolon.librarians.library.MVP.CountriesMVP;

import com.semicolon.librarians.library.Models.CountriesModel;

import java.util.List;


public interface Interactor {
    interface onCompleteListener
    {
        void onCountryDataSuccess(List<CountriesModel>countriesModelList);
        void onCountryDataFailed(String error);

    }
    void getCountryData(onCompleteListener listener);
}
