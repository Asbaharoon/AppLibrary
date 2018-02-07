package com.semicolon.librarians.library.MVP.UniversityMVP;

import com.semicolon.librarians.library.Models.UniversityModel;

import java.util.List;


public interface ViewData {
    void onUniversityDataSuccess(List<UniversityModel> universityModels);
    void onUniversityDataFailed(String error);
    void hideProgressBar();
}
