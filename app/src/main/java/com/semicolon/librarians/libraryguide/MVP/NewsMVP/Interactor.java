package com.semicolon.librarians.libraryguide.MVP.NewsMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.NewsModel;

import java.util.List;



public interface Interactor {
    interface onCompleteListener
    {
        void onNewsDataSuccess(List<NewsModel> newsModelList);
        void onNewsDataFailed(String error);
        void hideProgressBar();
    }
    void getNewsData(Context context,onCompleteListener listener);
}
