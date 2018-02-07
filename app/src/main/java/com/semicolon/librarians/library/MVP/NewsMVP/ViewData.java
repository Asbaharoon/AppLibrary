package com.semicolon.librarians.library.MVP.NewsMVP;

import com.semicolon.librarians.library.Models.NewsModel;

import java.util.List;



public interface ViewData {
    void onNewsDataSuccess(List<NewsModel> newsModelList);
    void onNewsDataFailed(String error);
    void hideProgressBar();
}
