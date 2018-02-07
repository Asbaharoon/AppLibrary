package com.semicolon.librarians.library.MVP.NewsMVP;

import com.semicolon.librarians.library.Models.NewsModel;

import java.util.List;



public interface Interactor {
    interface onCompleteListener
    {
        void onNewsDataSuccess(List<NewsModel> newsModelList);
        void onNewsDataFailed(String error);
        void hideProgressBar();
    }
    void getNewsData(onCompleteListener listener);
}
