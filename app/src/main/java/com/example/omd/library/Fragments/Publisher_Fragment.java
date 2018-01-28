package com.example.omd.library.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import com.example.omd.library.Activities.Activity_Search;
import com.example.omd.library.Adapters.PublisherAdapter;
import com.example.omd.library.MVP.PublisherMVP.Presenter;
import com.example.omd.library.MVP.PublisherMVP.PresenterImp;
import com.example.omd.library.MVP.PublisherMVP.ViewData;
import com.example.omd.library.Models.CompanyModel;
import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.Models.UniversityModel;
import com.example.omd.library.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

/**
 * Created by Delta on 15/12/2017.
 */

public class Publisher_Fragment extends Fragment implements ViewData {

    private NormalUserData user_Data;
    private PublisherModel publisher_Model;
    private UniversityModel university_Model;
    private LibraryModel libraryModel;
    private CompanyModel company_Model;
    private PublisherModel publishers_Model;
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
                presenter.getPublisherData();


            }
        });
        publisher_recView = (RecyclerView) view.findViewById(R.id.publisher_recyclerView);
        manager = new LinearLayoutManager(context);
        publisher_recView.setLayoutManager(manager);
    }
   /* private void getDataFromBundle() {

        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            if ( bundle.getSerializable("userData")!=null)
            {
                user_Data = (NormalUserData) bundle.getSerializable("userData");
                Toast.makeText(getActivity(), user_Data.getUserName()+"\n"+user_Data.getUserType(), Toast.LENGTH_SHORT).show();
            }else if (bundle.getSerializable("publisherData")!=null)
            {
                publisher_Model = (PublisherModel) bundle.getSerializable("publisherData");
                Toast.makeText(getActivity(), publisher_Model.getPub_name()+"\n"+publisher_Model.getUser_type(), Toast.LENGTH_SHORT).show();

            }else if (bundle.getSerializable("libraryData")!=null)
            {
                libraryModel = (LibraryModel) bundle.getSerializable("libraryData");
                Toast.makeText(getActivity(), libraryModel.getLib_name()+"\n"+libraryModel.getUser_type(), Toast.LENGTH_SHORT).show();

            }
            else if (bundle.getSerializable("universityData")!=null)
            {
                university_Model = (UniversityModel) bundle.getSerializable("universityData");
                Toast.makeText(getActivity(), university_Model.getUni_name()+"\n"+university_Model.getUser_type(), Toast.LENGTH_SHORT).show();

            }
            else if (bundle.getSerializable("companyData")!=null)
            {
                company_Model = (CompanyModel) bundle.getSerializable("companyData");
                Toast.makeText(getActivity(), company_Model.getComp_name()+"\n"+company_Model.getUser_type(), Toast.LENGTH_SHORT).show();
            }
        }
    }*/

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
            Intent intent = new Intent(getActivity(), Activity_Search.class);
            intent.putExtra("publisher_search","publisher");
            getActivity().startActivity(intent);

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

    @Override
    public void onStart() {
        super.onStart();
        presenter.getPublisherData();

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
