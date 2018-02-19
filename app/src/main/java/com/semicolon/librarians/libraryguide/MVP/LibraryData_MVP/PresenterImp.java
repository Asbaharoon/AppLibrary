package com.semicolon.librarians.libraryguide.MVP.LibraryData_MVP;

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
    public void getLibraryData(String pub_userName) {

        interactor.getLibraryData(pub_userName,context,this);
    }






    @Override
    public void onLibraryDataSuccess(List<LibraryModel> libraryModelList) {
        if (viewData!=null)
        {
            viewData.onLibraryDataSuccess(libraryModelList);
        }
    }

    @Override
    public void onFailed(String error) {
        if (viewData!=null)
        {
            viewData.onFailed(error);
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
