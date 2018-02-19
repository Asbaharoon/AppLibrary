package com.semicolon.librarians.libraryguide.MVP.Search_University_MVP;


import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.UniversityModel;

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
    void getUniversityData(String uni_name, String country, Context context, onCompleteListener listener);
}
