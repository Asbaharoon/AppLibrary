package com.example.omd.library.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.omd.library.Adapters.Library_Search_Adapter;
import com.example.omd.library.MVP.Search_Library_MVP.Presenter;
import com.example.omd.library.MVP.Search_Library_MVP.PresenterImp;
import com.example.omd.library.MVP.Search_Library_MVP.ViewData;
import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.R;

import java.util.List;

/**
 * Created by Delta on 29/01/2018.
 */

public class Fragment_Library_Search_Results extends Fragment implements ViewData{
    private ProgressBar progressBar ;
    private RecyclerView mRecView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private Bundle bundle;
    private String libName,lib_type,country_id,service_id;
    private Presenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library_search_results,container,false);
        initView(view);
        presenter = new PresenterImp(this,getActivity());
        getDataFromBundle();
        return view;
    }

    private void getDataFromBundle() {

        bundle = getArguments();
        if (bundle!=null)
        {
            libName    = bundle.getString("libraryName");
            lib_type   = bundle.getString("libraryType");
            country_id = bundle.getString("country_id");
            service_id = bundle.getString("service_id");
            Log.e("Data2",libName+"\n"+country_id+"\n"+service_id+"\n"+lib_type);
            presenter.getLibraryData(libName,lib_type,country_id,service_id);


        }
    }

    private void initView(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.lib_search_results_progressBar);
        mRecView = (RecyclerView) view.findViewById(R.id.recView_lib_search_results);
        mRecView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        mRecView.setLayoutManager(manager);

    }


    @Override
    public void onLibraryDataSuccess(List<LibraryModel> libraryModelList) {
        Toast.makeText(getActivity(), "data"+libraryModelList.size()+"\n"+libraryModelList.get(0).getLib_name(), Toast.LENGTH_SHORT).show();
        adapter = new Library_Search_Adapter(libraryModelList,getActivity());
        adapter.notifyDataSetChanged();
        mRecView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onLibraryDataFailed(String error) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);

    }


}
