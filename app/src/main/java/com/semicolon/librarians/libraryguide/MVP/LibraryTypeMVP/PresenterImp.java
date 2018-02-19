package com.semicolon.librarians.libraryguide.MVP.LibraryTypeMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.LibraryType_Model;

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
    public void onLibraryTypeDataSuccess(List<LibraryType_Model> libraryTypeModelList) {
        if (viewData!=null)
        {
            viewData.onLibraryTypeDataSuccess(libraryTypeModelList);
        }
    }

    @Override
    public void onLibraryTypeDataFailed(String error) {
        if (viewData!=null)
        {
            viewData.onLibraryTypeDataFailed(error);
        }
    }

    @Override
    public void hideProgressBar() {
        if (viewData!=null)
        {
            viewData.hideProgressBar();
        }
    }

    @Override
    public void getLibraryTypeData() {
        interactor.getLibraryTypeData(context,this);
    }
}
