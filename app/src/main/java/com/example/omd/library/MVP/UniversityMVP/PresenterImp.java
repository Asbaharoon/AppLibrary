package com.example.omd.library.MVP.UniversityMVP;

import android.content.Context;

import com.example.omd.library.Models.NewsModel;
import com.example.omd.library.Models.UniversityModel;

import java.util.List;


public class PresenterImp implements Presenter, Interactor.onCompleteListener {
    ViewData viewData;
    Context context;
    Interactor interactor;

    public PresenterImp(ViewData viewData, Context context) {
        this.viewData = viewData;
        this.context = context;
        interactor = new InteractorImp();


    }





    @Override
    public void getUniversityData() {
        interactor.getUniversityData(this);
    }

    @Override
    public void onUniversityDataSuccess(List<UniversityModel> universityModels) {
        if (viewData!=null)
        {
            viewData.onUniversityDataSuccess(universityModels);
        }
    }

    @Override
    public void onUniversityDataFailed(String error) {
        if (viewData!=null)
        {
            viewData.onUniversityDataFailed(error);
        }

    }



    @Override
    public void hideProgressBar() {
        if (viewData!=null)
        {
            viewData.hideProgressBar();
        }
    }
}
