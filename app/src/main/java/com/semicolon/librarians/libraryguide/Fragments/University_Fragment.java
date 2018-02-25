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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.semicolon.librarians.libraryguide.Activities.Activity_Search;
import com.semicolon.librarians.libraryguide.Adapters.UniversityAdapter;
import com.semicolon.librarians.libraryguide.MVP.UniversityMVP.Presenter;
import com.semicolon.librarians.libraryguide.MVP.UniversityMVP.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.UniversityMVP.ViewData;
import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Delta on 15/12/2017.
 */

public class University_Fragment extends Fragment implements ViewData{

    private NormalUserData user_Data = null;
    private PublisherModel publisher_Model = null;
    private LibraryModel library_Model = null;
    private UniversityModel university_Model = null;
    private CompanyModel company_Model = null;

    private RecyclerView uni_recView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private Context context ;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Presenter presenter;
    private LinearLayout error_container,nodata_container;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), Tags.font,true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.university_fragment,container,false);
        initView(view);

        presenter = new PresenterImp(this,getActivity());
        getDataFromBundle();
        return view;
    }

    private void initView(View view) {
        context = view.getContext();
        error_container = (LinearLayout) view.findViewById(R.id.error_container);
        nodata_container= (LinearLayout) view.findViewById(R.id.nodata_container);
        progressBar = (ProgressBar) view.findViewById(R.id.uni_prgBar);
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.uni_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(),R.color.centercolor));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //presenter.getUniversityData();
                if (user_Data !=null)
                {
                    presenter.getUniversityData("");


                }else if (publisher_Model!=null)

                {
                    presenter.getUniversityData("");

                }
                else if (library_Model!=null)
                {
                    presenter.getUniversityData("");

                }
                else if (university_Model!=null)
                {
                    presenter.getUniversityData(university_Model.getUni_username());

                }
                else if (company_Model!=null)
                {
                    presenter.getUniversityData("");

                }


            }
        });
        uni_recView = (RecyclerView) view.findViewById(R.id.uni_recyclerView);
        manager = new LinearLayoutManager(context);
        uni_recView.setLayoutManager(manager);
    }
    private void getDataFromBundle() {
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            if (bundle.getSerializable("user_data")!=null)
            {
                user_Data = (NormalUserData) bundle.getSerializable("user_data");
                presenter.getUniversityData("");

            }
            else if (bundle.getSerializable("publisherData")!=null)
            {
                publisher_Model = (PublisherModel) bundle.getSerializable("publisherData");
                presenter.getUniversityData("");


            }
            else if (bundle.getSerializable("universityData")!=null)
            {
                university_Model = (UniversityModel) bundle.getSerializable("universityData");
                presenter.getUniversityData(university_Model.getUni_username());

            }
            else if (bundle.getSerializable("libraryData")!=null)
            {
                library_Model = (LibraryModel) bundle.getSerializable("libraryData");
                presenter.getUniversityData("");


            }
            else if (bundle.getSerializable("companyData")!=null)
            {
                company_Model = (CompanyModel) bundle.getSerializable("companyData");
                presenter.getUniversityData("");


            }
        }
    }

    @Override
    public void onUniversityDataSuccess(List<UniversityModel> universityModels) {
        if (universityModels.size()>0)
        {
            adapter = new UniversityAdapter(universityModels,getActivity());
            uni_recView.setAdapter(adapter);
            uni_recView.getAdapter().notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            uni_recView.setVisibility(View.VISIBLE);
            error_container.setVisibility(View.GONE);
            nodata_container.setVisibility(View.GONE);

        }else
            {
                nodata_container.setVisibility(View.VISIBLE);
            }

    }

    @Override
    public void onUniversityDataFailed(String error) {
        //CreateAlertDialog(error);
        uni_recView.setVisibility(View.GONE);
        error_container.setVisibility(View.VISIBLE);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.university_search,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.university_searchIcon)
        {
            if (user_Data!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("university_search","university");
                intent.putExtra("userData",user_Data);
                getActivity().startActivity(intent);
            }else if (publisher_Model!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("university_search","university");
                intent.putExtra("publisherData",publisher_Model);
                getActivity().startActivity(intent);
            }
            else if (library_Model!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("university_search","university");
                intent.putExtra("libraryData",library_Model);
                getActivity().startActivity(intent);
            }
            else if (university_Model!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("university_search","university");
                intent.putExtra("universityData",university_Model);
                getActivity().startActivity(intent);
            }else if (company_Model!=null)
            {
                Intent intent = new Intent(getActivity(), Activity_Search.class);
                intent.putExtra("university_search","university");
                intent.putExtra("companyData",company_Model);
                getActivity().startActivity(intent);
            }


        }
        return true;
    }


}
