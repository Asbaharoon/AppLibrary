package com.example.omd.library.MVP.LibraryServicesMVP;

import android.content.Context;

import com.example.omd.library.Models.LibraryServices_Model;

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
    public void getLibraryServicesData() {
        interactor.getLibraryServicesData(this);
    }

    @Override
    public void onLibraryServicesDataSuccess(List<LibraryServices_Model> libraryServicesModelList) {
        if (viewData!=null)
        {
            viewData.onLibraryServicesDataSuccess(libraryServicesModelList);
        }
    }

    @Override
    public void onLibraryServicesDataFailed(String error) {
        if (viewData!=null)
        {
            viewData.onLibraryServicesDataFailed(error);
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
