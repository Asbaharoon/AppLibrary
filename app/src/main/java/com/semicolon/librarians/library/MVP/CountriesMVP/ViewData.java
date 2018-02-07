package com.semicolon.librarians.library.MVP.CountriesMVP;

import com.semicolon.librarians.library.Models.CountriesModel;

import java.util.List;


public interface ViewData {
    void onCountryDataSuccess(List<CountriesModel>countriesModelList);
    void onCountryDataFailed(String error);


}
