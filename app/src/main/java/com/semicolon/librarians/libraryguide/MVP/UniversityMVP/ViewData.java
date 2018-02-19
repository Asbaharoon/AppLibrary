package com.semicolon.librarians.libraryguide.MVP.UniversityMVP;

import com.semicolon.librarians.libraryguide.Models.UniversityModel;

import java.util.List;


public interface ViewData {
    void onUniversityDataSuccess(List<UniversityModel> universityModels);
    void onUniversityDataFailed(String error);
    void hideProgressBar();
}
