package com.semicolon.librarians.libraryguide.MVP.CountriesMVP;

import com.semicolon.librarians.libraryguide.Models.CountriesModel;

import java.util.List;


public interface ViewData {
    void onCountryDataSuccess(List<CountriesModel>countriesModelList);
    void onCountryDataFailed(String error);


}
