package com.semicolon.librarians.libraryguide.MVP.NewsMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.NewsModel;

import java.util.List;



public class PresenterImp implements com.semicolon.librarians.libraryguide.MVP.NewsMVP.Presenter, com.semicolon.librarians.libraryguide.MVP.NewsMVP.Interactor.onCompleteListener {
    com.semicolon.librarians.libraryguide.MVP.NewsMVP.ViewData viewData;
    Context context;
    Interactor interactor;

    public PresenterImp(ViewData viewData, Context context) {
        this.viewData = viewData;
        this.context = context;
        interactor = new InteractorImp();


    }





    @Override
    public void getNewsData() {
        interactor.getNewsData(context,this);
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
