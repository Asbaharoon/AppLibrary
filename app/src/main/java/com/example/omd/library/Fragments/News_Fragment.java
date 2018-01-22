package com.example.omd.library.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.omd.library.Adapters.newsAdapter;

import com.example.omd.library.Models.newsModel;
import com.example.omd.library.R;
import com.example.omd.library.newsMVP.Presenter;
import com.example.omd.library.newsMVP.PresenterImp;
import com.example.omd.library.newsMVP.ViewData;

import java.util.List;


public class News_Fragment extends Fragment implements ViewData {
    private RecyclerView news_recView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private Context context ;
    private Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment,container,false);
        initView(view);
        presenter = new PresenterImp(this,context);
        presenter.getNewsData();

        return view;
    }



    private void initView(View view) {
        context = view.getContext();
        news_recView = (RecyclerView) view.findViewById(R.id.news_recyclerView);
        manager = new LinearLayoutManager(context);
        news_recView.setLayoutManager(manager);
    }






    @Override
    public void onNewsDataSuccess(List<newsModel> newsModelList) {
        adapter = new newsAdapter(newsModelList,context);
        adapter.notifyDataSetChanged();
        news_recView.setAdapter(adapter);
    }

    @Override
    public void onNewsDataFailed(String error) {
        Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();

    }
}
