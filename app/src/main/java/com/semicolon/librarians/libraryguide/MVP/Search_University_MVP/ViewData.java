package com.semicolon.librarians.libraryguide.MVP.Search_University_MVP;

import com.semicolon.librarians.libraryguide.Models.UniversityModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onUniversityDataSuccess(List<UniversityModel> universityModelList);
    void onUniversityDataFailed(String error);
    void showNoResults();

}
