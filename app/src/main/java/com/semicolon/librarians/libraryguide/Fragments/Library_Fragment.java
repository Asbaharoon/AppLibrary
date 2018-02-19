package com.semicolon.librarians.libraryguide.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.semicolon.librarians.libraryguide.Activities.Activity_Search;
import com.semicolon.librarians.libraryguide.Adapters.LibraryAdapter;
import com.semicolon.librarians.libraryguide.MVP.LibraryData_MVP.Presenter;
import com.semicolon.librarians.libraryguide.MVP.LibraryData_MVP.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.LibraryData_MVP.ViewData;
import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;

import java.util.List;

/**
 * Created by Delta on 15/12/2017.
 */

public class Library_Fragment extends Fragment implements ViewData {
    private NormalUserData user_Data = null;
    private PublisherModel publisher_Model = null;
    private LibraryModel library_Model = null;
    private UniversityModel university_Model = null;
    private CompanyModel company_Model = null;
    private SwipeRefreshLayout lib_swipeRefresh;
    private ProgressBar lib_progressBar;
    private RecyclerView recView_library;
    private LinearLayoutManager manager;
    private RecyclerView.Adapter adapter;
    private Presenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.library_fragment,container,false);
        initView(view);
        presenter = new PresenterImp(this,getActivity());
        getDataFromBundle();

        return view;
    }

    private void initView(View view) {

        lib_swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.lib_swipeRefresh);
        recView_library  = (RecyclerView) view.findViewById(R.id.recView_library);
        lib_progressBar  = (ProgressBar) view.findViewById(R.id.lib_progressBar);
        recView_library.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        recView_library.setLayoutManager(manager);


        lib_swipeRefresh.setColorSchemeResources(R.color.centercolor);
        lib_swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshData();
            }
        });

    }

    private void RefreshData() {
        if (user_Data!=null)
        {
            lib_swipeRefresh.setRefreshing(true);
            presenter.getLibraryData(user_Data.getUserName());


        }
        else if (publisher_Model!=null)
        {
            lib_swipeRefresh.setRefreshing(true);

            presenter.getLibraryData(publisher_Model.getPub_username());

        }
        else if (university_Model!=null)
        {
            lib_swipeRefresh.setRefreshing(true);

            presenter.getLibraryData(university_Model.getUni_username());


        }
        else if (library_Model!=null)
        {
            lib_swipeRefresh.setRefreshing(true);

            presenter.getLibraryData(library_Model.getLib_username());

        }
        else if (company_Model!=null)
        {
            lib_swipeRefresh.setRefreshing(true);

            presenter.getLibraryData(company_Model.getComp_username());


        }
    }

    private void getDataFromBundle() {
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            if (bundle.getSerializable("user_data")!=null)
            {
                user_Data = (NormalUserData) bundle.getSerializable("user_data");
                presenter.getLibraryData(user_Data.getUserName());


            }
            else if (bundle.getSerializable("publisherData")!=null)
            {
                publisher_Model = (PublisherModel) bundle.getSerializable("publisherData");
                presenter.getLibraryData(publisher_Model.getPub_username());

            }
            else if (bundle.getSerializable("universityData")!=null)
            {
                university_Model = (UniversityModel) bundle.getSerializable("universityData");
                presenter.getLibraryData(university_Model.getUni_username());


            }
            else if (bundle.getSerializable("libraryData")!=null)
            {
                library_Model = (LibraryModel) bundle.getSerializable("libraryData");
                presenter.getLibraryData(library_Model.getLib_username());

            }
            else if (bundle.getSerializable("companyData")!=null)
            {
                company_Model = (CompanyModel) bundle.getSerializable("companyData");
                presenter.getLibraryData(company_Model.getComp_username());


            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.library_search,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.library_searchIcon)
        {

            if (user_Data!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("library_search","library");
                intent.putExtra("userData",user_Data);
                getActivity().startActivity(intent);
            }else if (publisher_Model!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("library_search","library");
                intent.putExtra("publisherData",publisher_Model);
                getActivity().startActivity(intent);
            }
            else if (library_Model!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("library_search","library");
                intent.putExtra("libraryData",library_Model);
                getActivity().startActivity(intent);
            }
            else if (university_Model!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("library_search","library");
                intent.putExtra("universityData",university_Model);
                getActivity().startActivity(intent);
            }else if (company_Model!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("library_search","library");
                intent.putExtra("companyData",company_Model);
                getActivity().startActivity(intent);
            }


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLibraryDataSuccess(List<LibraryModel> libraryModelList) {
        adapter = new LibraryAdapter(libraryModelList,getActivity());
        adapter.notifyDataSetChanged();
        recView_library.setAdapter(adapter);
        lib_swipeRefresh.setRefreshing(false);

    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getActivity(),  getString(R.string.error)+error, Toast.LENGTH_SHORT).show();
        lib_swipeRefresh.setRefreshing(false);

    }

    @Override
    public void hideProgressBar() {
        lib_progressBar.setVisibility(View.GONE);
    }


}
