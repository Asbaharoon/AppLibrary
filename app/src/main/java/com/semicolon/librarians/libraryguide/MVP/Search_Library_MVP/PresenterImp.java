package com.semicolon.librarians.libraryguide.MVP.Search_Library_MVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.LibraryModel;

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
    public void getLibraryData(String lib_name,String lib_type, String country_id) {
        interactor.getLibraryData(lib_name,lib_type,country_id,context,this);
    }

    @Override
    public void onLibraryDataSuccess(List<LibraryModel> libraryModelList) {
        if (viewData!=null)
        {
            viewData.onLibraryDataSuccess(libraryModelList);
        }
    }

    @Override
    public void onLibraryDataFailed(String error) {
        if (viewData!=null)
        {
            viewData.onLibraryDataFailed(error);
        }
    }

    @Override
    public void hideProgress() {
        if (viewData!=null)
        {
            viewData.hideProgress();
        }
    }
}
