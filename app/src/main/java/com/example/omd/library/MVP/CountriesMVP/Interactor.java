package com.example.omd.library.MVP.CountriesMVP;

import com.example.omd.library.Models.CountriesModel;

import java.util.List;


public interface Interactor {
    interface onCompleteListener
    {
        void onCountryDataSuccess(List<CountriesModel>countriesModelList);
        void onCountryDataFailed(String error);

    }
    void getCountryData(onCompleteListener listener);
}
