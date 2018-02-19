package com.semicolon.librarians.libraryguide.MVP.UniversityMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.UniversityModel;

import java.util.List;


public interface Interactor {
    interface onCompleteListener
    {
        void onUniversityDataSuccess(List<UniversityModel> universityModels);
        void onUniversityDataFailed(String error);
        void hideProgressBar();
    }
    void getUniversityData(String userName, Context context,onCompleteListener listener);
}
