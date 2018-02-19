package com.semicolon.librarians.libraryguide.MVP.NewsMVP;

import com.semicolon.librarians.libraryguide.Models.NewsModel;

import java.util.List;



public interface ViewData {
    void onNewsDataSuccess(List<NewsModel> newsModelList);
    void onNewsDataFailed(String error);
    void hideProgressBar();
}
