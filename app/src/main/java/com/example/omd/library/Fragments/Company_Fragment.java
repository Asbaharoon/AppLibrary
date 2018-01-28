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

import com.example.omd.library.Adapters.CompanyAdapter;
import com.example.omd.library.MVP.CompanyMVP.Presenter;
import com.example.omd.library.MVP.CompanyMVP.PresenterImp;
import com.example.omd.library.MVP.CompanyMVP.ViewData;
import com.example.omd.library.Models.CompanyModel;
import com.example.omd.library.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

/**
 * Created by Delta on 20/01/2018.
 */

public class Company_Fragment extends Fragment implements ViewData {
    private RecyclerView comp_recView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private Presenter presenter;
    private Context context ;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        context = view.getContext();
        presenter = new PresenterImp(this,getActivity());
        progressBar = (ProgressBar) view.findViewById(R.id.comp_prgBar);
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.comp_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(),R.color.centercolor));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getCompanyData();

            }
        });
        comp_recView = (RecyclerView) view.findViewById(R.id.comp_recyclerView);
        comp_recView.setHasFixedSize(true);
        manager = new LinearLayoutManager(context);
        comp_recView.setLayoutManager(manager);

    }

    @Override
    public void onCompanyDataSuccess(List<CompanyModel> companyModelList) {
        adapter = new CompanyAdapter(companyModelList,getActivity());
        adapter.notifyDataSetChanged();
        comp_recView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onCompanyDataFailed(String error) {
        CreateAlertDialog(error);
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getCompanyData();


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
}
