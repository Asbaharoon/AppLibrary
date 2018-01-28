package com.example.omd.library.MVP.NewsMVP;

import android.content.Context;

import com.example.omd.library.Models.NewsModel;

import java.util.List;



public class PresenterImp implements com.example.omd.library.MVP.NewsMVP.Presenter, com.example.omd.library.MVP.NewsMVP.Interactor.onCompleteListener {
    com.example.omd.library.MVP.NewsMVP.ViewData viewData;
    Context context;
    Interactor interactor;

    public PresenterImp(ViewData viewData, Context context) {
        this.viewData = viewData;
        this.context = context;
        interactor = new InteractorImp();


    }





    @Override
    public void getNewsData() {
        interactor.getNewsData(this);
    }

    @Override
    public void onNewsDataSuccess(List<NewsModel> newsModelList) {
        if (viewData!=null)
        {
            viewData.onNewsDataSuccess(newsModelList);
        }
    }

    @Override
    public void onNewsDataFailed(String error) {
        if (viewData!=null)
        {
            viewData.onNewsDataFailed(error);
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
