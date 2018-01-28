package com.example.omd.library.Fragments;

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
import android.widget.ProgressBar;

import com.example.omd.library.Adapters.UniversityAdapter;
import com.example.omd.library.MVP.UniversityMVP.Presenter;
import com.example.omd.library.MVP.UniversityMVP.PresenterImp;
import com.example.omd.library.MVP.UniversityMVP.ViewData;
import com.example.omd.library.Models.UniversityModel;
import com.example.omd.library.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

/**
 * Created by Delta on 15/12/2017.
 */

public class University_Fragment extends Fragment implements ViewData{
    private RecyclerView uni_recView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private Context context ;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Presenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.university_fragment,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        context = view.getContext();
        presenter = new PresenterImp(this,getActivity());
        progressBar = (ProgressBar) view.findViewById(R.id.uni_prgBar);
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.uni_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(),R.color.centercolor));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getUniversityData();



            }
        });
        uni_recView = (RecyclerView) view.findViewById(R.id.uni_recyclerView);
        manager = new LinearLayoutManager(context);
        uni_recView.setLayoutManager(manager);
    }


    @Override
    public void onUniversityDataSuccess(List<UniversityModel> universityModels) {
        adapter = new UniversityAdapter(universityModels,getActivity());
        uni_recView.setAdapter(adapter);
        uni_recView.getAdapter().notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onUniversityDataFailed(String error) {
        CreateAlertDialog(error);
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }
    private void CreateAlertDialog(String msg)
    {
        final LovelyStandardDialog dialog = new LovelyStandardDialog(getActivity())
                .setTopColor(ContextCompat.getColor(getActivity(),R.color.centercolor))
                .setTopTitle("Error")
                .setTopTitleColor(ContextCompat.getColor(getActivity(),R.color.base))
                .setPositiveButtonColorRes(R.color.centercolor)
                .setMessage(msg);
        dialog.setPositiveButton("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getUniversityData();
    }
}
