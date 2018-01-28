package com.example.omd.library.MVP.UniversityMVP;

import com.example.omd.library.Models.UniversityModel;

import java.util.List;


public interface ViewData {
    void onUniversityDataSuccess(List<UniversityModel> universityModels);
    void onUniversityDataFailed(String error);
    void hideProgressBar();
}
