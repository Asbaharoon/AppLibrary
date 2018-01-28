package com.example.omd.library.MVP.UniversityMVP;

import com.example.omd.library.Models.UniversityModel;

import java.util.List;


public interface Interactor {
    interface onCompleteListener
    {
        void onUniversityDataSuccess(List<UniversityModel> universityModels);
        void onUniversityDataFailed(String error);
        void hideProgressBar();
    }
    void getUniversityData(onCompleteListener listener);
}
