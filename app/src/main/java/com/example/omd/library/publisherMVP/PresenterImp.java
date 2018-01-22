package com.example.omd.library.publisherMVP;

import android.content.Context;

import com.example.omd.library.Models.PublishersModel;
import java.util.List;


public class PresenterImp implements com.example.omd.library.publisherMVP.Presenter, com.example.omd.library.publisherMVP.Interactor.onCompleteListener {
    com.example.omd.library.publisherMVP.ViewData viewData;
    Context context;
    Interactor interactor;

    public PresenterImp(ViewData viewData, Context context) {
        this.viewData = viewData;
        this.context = context;
        interactor = new InteractorImp();


    }

    @Override
    public void getpublisherData() {
        interactor.getpublisherData(this);
    }






    @Override
    public void onpublisherDataSuccess(List<PublishersModel> publishersModelList) {
        if (viewData!=null)
        {
            viewData.onpublisherDataSuccess(publishersModelList);
        }
    }

    @Override
    public void onpublisherDataFailed(String error) {
        if (viewData!=null)
        {
            viewData.onpublisherDataFailed(error);
        }
    }
}
