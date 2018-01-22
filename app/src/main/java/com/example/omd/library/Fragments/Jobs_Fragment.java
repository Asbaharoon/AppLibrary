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

import com.example.omd.library.Adapters.JobsAdapter;
import com.example.omd.library.JobsMVP.Presenter;
import com.example.omd.library.JobsMVP.PresenterImp;
import com.example.omd.library.JobsMVP.ViewData;
import com.example.omd.library.Models.JobsModel;
import com.example.omd.library.R;

import java.util.List;

/**
 * Created by Delta on 20/01/2018.
 */

public class Jobs_Fragment extends Fragment implements ViewData {
    private RecyclerView job_recView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private Context context ;
    private Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobs,container,false);
        initView(view);
        presenter = new PresenterImp(this,context);
        presenter.getJobsData();

        return view;
    }



    private void initView(View view) {
        context = view.getContext();
        job_recView = (RecyclerView) view.findViewById(R.id.job_recyclerView);
        manager = new LinearLayoutManager(context);
        job_recView.setLayoutManager(manager);
    }


    @Override
    public void onJobDataSuccess(List<JobsModel> jobsModelList) {
        Toast.makeText(context, jobsModelList.get(0).getJobEmail(), Toast.LENGTH_SHORT).show();
        adapter = new JobsAdapter(jobsModelList,context);
        adapter.notifyDataSetChanged();
        job_recView.setAdapter(adapter);
    }

    @Override
    public void onJobDataFailed(String error) {
        Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
    }


}
