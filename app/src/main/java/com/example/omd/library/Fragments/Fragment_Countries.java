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

import com.example.omd.library.Adapters.CountryAdapter;
import com.example.omd.library.MVP.CountriesMVP.Presenter;
import com.example.omd.library.MVP.CountriesMVP.PresenterImp;
import com.example.omd.library.MVP.CountriesMVP.ViewData;
import com.example.omd.library.Models.CountriesModel;
import com.example.omd.library.R;

import java.util.List;

/**
 * Created by Delta on 28/01/2018.
 */

public class Fragment_Countries extends Fragment implements ViewData{

    private ProgressBar progressBar ;
    private RecyclerView mRecView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private Presenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countries,container,false);
        initView(view);
        presenter = new PresenterImp(this,getActivity());
        return view;
    }


    private void initView(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.country_progressBar);
        mRecView = (RecyclerView) view.findViewById(R.id.recView_countries);
        mRecView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        mRecView.setLayoutManager(manager);


    }

    @Override
    public void onCountryDataSuccess(List<CountriesModel> countriesModelList) {
        adapter = new CountryAdapter(countriesModelList,getActivity());
        adapter.notifyDataSetChanged();
        mRecView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCountryDataFailed(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getCountryData();
    }
}
