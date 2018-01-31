package com.example.omd.library.MVP.Search_University_MVP;

import com.example.omd.library.Models.UniversityModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onUniversityDataSuccess(List<UniversityModel> universityModelList);
    void onUniversityDataFailed(String error);
    void showNoResults();

}
