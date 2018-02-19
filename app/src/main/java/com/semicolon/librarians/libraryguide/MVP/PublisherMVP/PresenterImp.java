package com.semicolon.librarians.libraryguide.MVP.PublisherMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.PublisherModel;

import java.util.List;


public class PresenterImp implements com.semicolon.librarians.libraryguide.MVP.PublisherMVP.Presenter, com.semicolon.librarians.libraryguide.MVP.PublisherMVP.Interactor.onCompleteListener {
    com.semicolon.librarians.libraryguide.MVP.PublisherMVP.ViewData viewData;
    Context context;
    Interactor interactor;

    public PresenterImp(ViewData viewData, Context context) {
        this.viewData = viewData;
        this.context = context;
        interactor = new InteractorImp();


    }

    @Override
    public void getPublisherData(String pub_userName) {

        interactor.getPublisherData(pub_userName,context,this);
    }






    @Override
    public void onPublisherDataSuccess(List<PublisherModel> publishersModelList) {
        if (viewData!=null)
        {
            viewData.onPublisherDataSuccess(publishersModelList);
        }
    }

    @Override
    public void onPublisherDataFailed(String error) {
        if (viewData!=null)
        {
            viewData.onPublisherDataFailed(error);
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
