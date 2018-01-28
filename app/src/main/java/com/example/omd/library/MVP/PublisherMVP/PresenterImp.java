package com.example.omd.library.MVP.PublisherMVP;

import android.content.Context;

import com.example.omd.library.Models.PublisherModel;

import java.util.List;


public class PresenterImp implements com.example.omd.library.MVP.PublisherMVP.Presenter, com.example.omd.library.MVP.PublisherMVP.Interactor.onCompleteListener {
    com.example.omd.library.MVP.PublisherMVP.ViewData viewData;
    Context context;
    Interactor interactor;

    public PresenterImp(ViewData viewData, Context context) {
        this.viewData = viewData;
        this.context = context;
        interactor = new InteractorImp();


    }

    @Override
    public void getPublisherData() {
        interactor.getPublisherData(this);
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
