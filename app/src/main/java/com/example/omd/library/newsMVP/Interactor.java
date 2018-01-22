package com.example.omd.library.newsMVP;

import com.example.omd.library.Models.newsModel;

import java.util.List;



public interface Interactor {
    interface onCompleteListener
    {
        void onNewsDataSuccess(List<newsModel> newsModelList);
        void onNewsDataFailed(String error);
    }
    void getNewsData(onCompleteListener listener);
}
