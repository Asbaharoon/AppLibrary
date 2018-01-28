package com.example.omd.library.Activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.omd.library.Adapters.CountryAdapter;
import com.example.omd.library.MVP.CountriesMVP.Presenter;
import com.example.omd.library.MVP.CountriesMVP.PresenterImp;
import com.example.omd.library.MVP.CountriesMVP.ViewData;
import com.example.omd.library.Models.CountriesModel;
import com.example.omd.library.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

public class Activity_Countries extends AppCompatActivity implements ViewData {

    private Toolbar toolbar ;
    private RecyclerView mRecView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private List<CountriesModel> countriesModels;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        initView();
    }

    private void initView() {
        presenter = new PresenterImp(this,this);

        toolbar = (Toolbar) findViewById(R.id.country_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Countries");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecView = (RecyclerView) findViewById(R.id.recView_countries);
        manager = new LinearLayoutManager(this);
        mRecView.setLayoutManager(manager);
        mRecView.setHasFixedSize(true);


    }

    @Override
    public void onCountryDataSuccess(List<CountriesModel> countriesModelList) {
        countriesModels = countriesModelList;
        adapter = new CountryAdapter(countriesModels,this);
        mRecView.setAdapter(adapter);
    }

    @Override
    public void onCountryDataFailed(String error) {
        CreateAlertDialog(error);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getCountryData();
    }
    private void CreateAlertDialog(String msg)
    {
        final LovelyStandardDialog dialog = new LovelyStandardDialog(this)
                .setTopColor(ContextCompat.getColor(this,R.color.centercolor))
                .setTopTitle("Error")
                .setTopTitleColor(ContextCompat.getColor(this,R.color.base))
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
