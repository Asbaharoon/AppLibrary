package com.semicolon.librarians.library.MVP.Search_University_MVP;

import android.content.Context;

import com.semicolon.librarians.library.Models.UniversityModel;

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
    public void getUniversityData(String uni_name, String country) {
        interactor.getUniversityData(uni_name,country,this);
    }

    @Override
    public void onUniversityDataSuccess(List<UniversityModel> universityModelList) {
        if (viewData!=null)
        {
            viewData.onUniversityDataSuccess(universityModelList);
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
    public void showNoResults() {
        if (viewData!=null)
        {
            viewData.showNoResults();
        }
    }
}
