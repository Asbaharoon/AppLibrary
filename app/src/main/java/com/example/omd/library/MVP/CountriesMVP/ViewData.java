package com.example.omd.library.MVP.CountriesMVP;

import com.example.omd.library.Models.CountriesModel;

import java.util.List;


public interface ViewData {
    void onCountryDataSuccess(List<CountriesModel>countriesModelList);
    void onCountryDataFailed(String error);


}
