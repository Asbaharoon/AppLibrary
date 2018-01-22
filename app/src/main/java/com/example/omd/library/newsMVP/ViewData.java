package com.example.omd.library.newsMVP;

import com.example.omd.library.Models.newsModel;

import java.util.List;



public interface ViewData {
    void onNewsDataSuccess(List<newsModel> newsModelList);
    void onNewsDataFailed(String error);
}
