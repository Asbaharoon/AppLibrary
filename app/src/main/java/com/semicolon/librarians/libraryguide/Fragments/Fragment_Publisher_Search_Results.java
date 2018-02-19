package com.semicolon.librarians.libraryguide.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.semicolon.librarians.libraryguide.Adapters.Publisher_Search_Adapter;
import com.semicolon.librarians.libraryguide.MVP.Search_Publisher_MPV.Presenter;
import com.semicolon.librarians.libraryguide.MVP.Search_Publisher_MPV.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.Search_Publisher_MPV.ViewData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.R;

import java.util.List;

/**
 * Created by Delta on 29/01/2018.
 */

public class Fragment_Publisher_Search_Results extends Fragment implements ViewData {
    private ProgressBar progressBar ;
    private RecyclerView mRecView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private Presenter presenter;
    private String pubName;
    private String country_id;
    private Bundle bundle;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publisher_search_results,container,false);
        initView(view);

        getDataFromBundle();
        presenter = new PresenterImp(this,getActivity());
        return view;
    }

    private void getDataFromBundle() {

        bundle = getArguments();
        if (bundle!=null)
        {
            pubName = bundle.getString("pubName");
            country_id = bundle.getString("country_id");
        }
    }

    private void initView(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.pub_search_results_progressBar);
        mRecView = (RecyclerView) view.findViewById(R.id.recView_pub_search_results);
        mRecView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        mRecView.setLayoutManager(manager);

    }


    @Override
    public void onStart() {
        super.onStart();
        presenter.getPublisherData(pubName,country_id);
    }

    @Override
    public void onPublisherDataSuccess(List<PublisherModel> publishersModelList) {
        adapter = new Publisher_Search_Adapter(publishersModelList,getActivity());
        adapter.notifyDataSetChanged();
        mRecView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPublisherDataFailed(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showNoResults() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(), R.string.no_result, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void hideNoResults() {
        progressBar.setVisibility(View.GONE);

    }
}
