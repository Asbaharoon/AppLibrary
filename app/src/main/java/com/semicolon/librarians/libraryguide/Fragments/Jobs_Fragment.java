package com.semicolon.librarians.libraryguide.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.semicolon.librarians.libraryguide.Adapters.JobsAdapter;
import com.semicolon.librarians.libraryguide.MVP.JobsMVP.Presenter;
import com.semicolon.librarians.libraryguide.MVP.JobsMVP.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.JobsMVP.ViewData;
import com.semicolon.librarians.libraryguide.Models.JobsModel;
import com.semicolon.librarians.libraryguide.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

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
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout error_container,nodata_container;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

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
        error_container = (LinearLayout) view.findViewById(R.id.error_container);
        nodata_container = (LinearLayout) view.findViewById(R.id.nodata_container);
        progressBar = (ProgressBar) view.findViewById(R.id.job_prgBar);
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.job_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(),R.color.centercolor));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getJobsData();

            }
        });
        job_recView = (RecyclerView) view.findViewById(R.id.job_recyclerView);
        manager = new LinearLayoutManager(context);
        job_recView.setLayoutManager(manager);
    }


    @Override
    public void onJobDataSuccess(List<JobsModel> jobsModelList) {
        if (jobsModelList.size()>0)
        {
            adapter = new JobsAdapter(jobsModelList,context);
            adapter.notifyDataSetChanged();
            job_recView.setAdapter(adapter);
            swipeRefreshLayout.setRefreshing(false);
            nodata_container.setVisibility(View.GONE);

        }else
            {
                nodata_container.setVisibility(View.VISIBLE);
            }


    }


    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onJobDataFailed(String error) {
        error_container.setVisibility(View.VISIBLE);
        job_recView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        //CreateAlertDialog(error);
        swipeRefreshLayout.setRefreshing(false);

    }

    private void CreateAlertDialog(String msg)
    {
        final LovelyStandardDialog dialog = new LovelyStandardDialog(getActivity())
                .setTopColor(ContextCompat.getColor(getActivity(),R.color.centercolor))
                .setTopTitle(getString(R.string.error))
                .setTopTitleColor(ContextCompat.getColor(getActivity(),R.color.base))
                .setPositiveButtonColorRes(R.color.centercolor)
                .setMessage(msg);
        dialog.setPositiveButton(getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }
}
