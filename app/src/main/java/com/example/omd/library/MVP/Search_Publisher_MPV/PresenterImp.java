package com.example.omd.library.MVP.Search_Publisher_MPV;

import android.content.Context;

import com.example.omd.library.Models.PublisherModel;

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
    public void showNoResults() {
        if (viewData!=null)
        {
            viewData.showNoResults();
        }
    }

    @Override
    public void hideNoResults() {
        if (viewData!=null)
        {
            viewData.hideNoResults();
        }
    }


    @Override
    public void getPublisherData(String pub_name,String country) {
        interactor.getPublisherData(pub_name,country,this);
    }
}
