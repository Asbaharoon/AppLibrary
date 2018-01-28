package com.example.omd.library.MVP.NewsMVP;

import com.example.omd.library.Models.NewsModel;

import java.util.List;



public interface ViewData {
    void onNewsDataSuccess(List<NewsModel> newsModelList);
    void onNewsDataFailed(String error);
    void hideProgressBar();
}
