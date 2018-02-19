package com.semicolon.librarians.libraryguide.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.semicolon.librarians.libraryguide.Activities.Activity_Search;
import com.semicolon.librarians.libraryguide.Adapters.PublisherAdapter;
import com.semicolon.librarians.libraryguide.MVP.PublisherMVP.Presenter;
import com.semicolon.librarians.libraryguide.MVP.PublisherMVP.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.PublisherMVP.ViewData;
import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

/**
 * Created by Delta on 15/12/2017.
 */

public class Publisher_Fragment extends Fragment implements ViewData {
    private NormalUserData user_Data = null;
    private PublisherModel publisher_Model = null;
    private LibraryModel library_Model = null;
    private UniversityModel university_Model = null;
    private CompanyModel company_Model = null;

    private RecyclerView publisher_recView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private Context context ;
    private Presenter presenter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publisher_fragment,container,false);
        initView(view);
        presenter = new PresenterImp(this,context);
        getDataFromBundle();

        return view;
    }

    private void initView(View view) {

        context = view.getContext();
        progressBar = (ProgressBar) view.findViewById(R.id.pub_prgBar);
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.pub_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(),R.color.centercolor));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (user_Data !=null)
                {
                    presenter.getPublisherData("");


                }else if (publisher_Model!=null)

                {
                    Log.e("pubusername",publisher_Model.getPub_username());
                    presenter.getPublisherData(publisher_Model.getPub_username());

                }
                else if (library_Model!=null)
                {
                    presenter.getPublisherData("");

                }
                else if (university_Model!=null)
                {
                    presenter.getPublisherData("");

                }
                else if (company_Model!=null)
                {
                    presenter.getPublisherData("");

                }

            }
        });
        publisher_recView = (RecyclerView) view.findViewById(R.id.publisher_recyclerView);
        manager = new LinearLayoutManager(context);
        publisher_recView.setLayoutManager(manager);
    }
    private void getDataFromBundle() {
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            if (bundle.getSerializable("user_data")!=null)
            {
                user_Data = (NormalUserData) bundle.getSerializable("user_data");
                presenter.getPublisherData("");


            }
            else if (bundle.getSerializable("publisherData")!=null)
            {
                publisher_Model = (PublisherModel) bundle.getSerializable("publisherData");
                presenter.getPublisherData(publisher_Model.getPub_username());
                Log.e("pubusername",publisher_Model.getPub_username());


            }
            else if (bundle.getSerializable("universityData")!=null)
            {
                university_Model = (UniversityModel) bundle.getSerializable("universityData");
                presenter.getPublisherData("");


            }
            else if (bundle.getSerializable("libraryData")!=null)
            {
                library_Model = (LibraryModel) bundle.getSerializable("libraryData");
                presenter.getPublisherData("");


            }
            else if (bundle.getSerializable("companyData")!=null)
            {
                company_Model = (CompanyModel) bundle.getSerializable("companyData");
                presenter.getPublisherData("");


            }
        }
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.publisher_search,menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.publisher_searchIcon)
        {

            if (user_Data!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("publisher_search","publisher");
                intent.putExtra("userData",user_Data);
                getActivity().startActivity(intent);
            }else if (publisher_Model!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("publisher_search","publisher");
                intent.putExtra("publisherData",publisher_Model);
                getActivity().startActivity(intent);
            }
            else if (library_Model!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("publisher_search","publisher");
                intent.putExtra("libraryData",library_Model);
                getActivity().startActivity(intent);
            }
            else if (university_Model!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("publisher_search","publisher");
                intent.putExtra("universityData",university_Model);
                getActivity().startActivity(intent);
            }else if (company_Model!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("publisher_search","publisher");
                intent.putExtra("companyData",company_Model);
                getActivity().startActivity(intent);
            }



        }
        return true;
    }

    @Override
    public void onPublisherDataSuccess(List<PublisherModel> publishersModelList) {
        adapter = new PublisherAdapter(publishersModelList,context);
        adapter.notifyDataSetChanged();
        publisher_recView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);


    }

    @Override
    public void onPublisherDataFailed(String error) {
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
