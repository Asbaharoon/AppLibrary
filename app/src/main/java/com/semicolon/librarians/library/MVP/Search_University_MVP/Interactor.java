package com.semicolon.librarians.library.MVP.Search_University_MVP;


import com.semicolon.librarians.library.Models.UniversityModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Interactor {
    interface onCompleteListener
    {
        void onUniversityDataSuccess(List<UniversityModel> universityModelList);
        void onUniversityDataFailed(String error);
        void showNoResults();

    }
    void getUniversityData(String uni_name, String country, onCompleteListener listener);
}
