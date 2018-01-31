package com.example.omd.library.Fragments;

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

import com.example.omd.library.Adapters.University_Search_Adapter;
import com.example.omd.library.MVP.Search_University_MVP.Presenter;
import com.example.omd.library.MVP.Search_University_MVP.PresenterImp;
import com.example.omd.library.MVP.Search_University_MVP.ViewData;
import com.example.omd.library.Models.UniversityModel;
import com.example.omd.library.R;

import java.util.List;

/**
 * Created by Delta on 29/01/2018.
 */

public class Fragment_University_Search_Results extends Fragment implements ViewData{
    private ProgressBar progressBar ;
    private RecyclerView mRecView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private String uniName;
    private String country_id;
    private Bundle bundle;
    private Presenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_university_search_results,container,false);
        initView(view);
        getDataFromBundle();
        presenter = new PresenterImp(this,getActivity());
        return view;
    }

    private void getDataFromBundle() {

        bundle = getArguments();
        if (bundle!=null)
        {
            uniName = bundle.getString("uniName");
            country_id = bundle.getString("country_id");
        }
    }

    private void initView(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.uni_search_results_progressBar);
        mRecView = (RecyclerView) view.findViewById(R.id.recView_uni_search_results);
        mRecView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        mRecView.setLayoutManager(manager);

    }


    @Override
    public void onStart() {
        super.onStart();
        presenter.getUniversityData(uniName,country_id);
    }

    @Override
    public void onUniversityDataSuccess(List<UniversityModel> universityModelList) {
        adapter = new University_Search_Adapter(universityModelList,getActivity());
        adapter.notifyDataSetChanged();
        mRecView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onUniversityDataFailed(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showNoResults() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "no results", Toast.LENGTH_SHORT).show();
    }

    /*adapter = new Publisher_Search_Adapter(publishersModelList,getActivity());
        adapter.notifyDataSetChanged();
        mRecView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);

        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

      */
}
