package com.semicolon.librarians.library.MVP.Search_University_MVP;

import com.semicolon.librarians.library.Models.UniversityModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onUniversityDataSuccess(List<UniversityModel> universityModelList);
    void onUniversityDataFailed(String error);
    void showNoResults();

}
