package com.semicolon.librarians.libraryguide.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.semicolon.librarians.libraryguide.Adapters.University_Search_Adapter;
import com.semicolon.librarians.libraryguide.MVP.Search_University_MVP.Presenter;
import com.semicolon.librarians.libraryguide.MVP.Search_University_MVP.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.Search_University_MVP.ViewData;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

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
    private LinearLayout error_container,noresult_container;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), Tags.font,true);

    }

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
        error_container = (LinearLayout) view.findViewById(R.id.error_container);
        noresult_container = (LinearLayout) view.findViewById(R.id.noresult_container);

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
        if (universityModelList.size()>0)
        {
            adapter = new University_Search_Adapter(universityModelList,getActivity());
            adapter.notifyDataSetChanged();
            mRecView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
            error_container.setVisibility(View.GONE);
            noresult_container.setVisibility(View.GONE);

        }else
            {
                noresult_container.setVisibility(View.VISIBLE);
            }

    }

    @Override
    public void onUniversityDataFailed(String error) {
        progressBar.setVisibility(View.GONE);
        mRecView.setVisibility(View.GONE);
        error_container.setVisibility(View.VISIBLE);

    }

    @Override
    public void showNoResults() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(),getString(R.string.no_result), Toast.LENGTH_SHORT).show();
    }

    /*adapter = new Publisher_Search_Adapter(publishersModelList,getActivity());
        adapter.notifyDataSetChanged();
        mRecView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);

        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

      */
}
