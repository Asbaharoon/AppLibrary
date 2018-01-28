package com.example.omd.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omd.library.Fragments.Fragment_Library_Search;
import com.example.omd.library.Fragments.Fragment_Publisher_Search;
import com.example.omd.library.Models.CountriesModel;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.R;
import com.example.omd.library.MVP.Search_Publisher_MPV.Presenter;
import com.example.omd.library.MVP.Search_Publisher_MPV.PresenterImp;
import com.example.omd.library.MVP.Search_Publisher_MPV.ViewData;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;

import java.io.Serializable;
import java.util.List;

public class Activity_Search extends AppCompatActivity implements View.OnClickListener,ViewData{

    private Toolbar mToolbar;
    private MaterialSearchView msv;
    private TextView search_type_tv,search_country,tv_noResults;
    CountryPicker countryPicker;
    private final String TAG="COUNTRY_PICKER";
    private Bundle bundle;
    private String searchType;
    private Presenter presenter;
    private String country_id;
    private final int REQ_CODE =111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__search);
        initView();
        getDataFromIntent();
    }

    private void initView()
    {
        mToolbar = (Toolbar) findViewById(R.id.search_toolBar);
        setSupportActionBar(mToolbar);
        search_type_tv = (TextView) findViewById(R.id.search_type_tv);
        search_country = (TextView) findViewById(R.id.country_search);
        msv = (MaterialSearchView) findViewById(R.id.msv);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                msv.showSearch(true);

            }
        },500);
        msv.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (searchType.equals("library_search"))
                {

                }else if (searchType.equals("publisher_search"))
                {
                   presenter.getPublisherData(query,country_id);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        search_country.setOnClickListener(this);
        tv_noResults = (TextView) findViewById(R.id.tv_noResults);
        presenter = new PresenterImp(this,this);


    }
    private void getDataFromIntent()
    {
        Intent intent = getIntent();
        if (intent.hasExtra("library_search"))
        {
            searchType ="library_search";
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Library Search");
            msv.setHint("choose library type");
            search_type_tv.setText("choose library type");
            Fragment_Library_Search fragment_library_search =new Fragment_Library_Search();
            getSupportFragmentManager().beginTransaction().add(R.id.search_fragmentContainer,fragment_library_search,"lib_search").commit();
        }else if (intent.hasExtra("publisher_search"))
        {
            searchType ="publisher_search";
            msv.setHint("publisher name");
            search_type_tv.setText("publisher name");
            search_country.setVisibility(View.VISIBLE);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Publisher Search");

        }

    }
    public void setQuery(String query)
    {
        if (msv.isSearchOpen())
        {
            msv.setQuery(query,false);
        }else
        {
            msv.setQuery(query,false);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search_searchIcon);
        msv.setMenuItem(item);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return true;
    }
    @Override
    public void onBackPressed()
    {
        Fragment_Library_Search fragment_library_search = (Fragment_Library_Search) getSupportFragmentManager().findFragmentByTag("lib_search");
        if (fragment_library_search!=null)
        {
            finish();
        }
        else
            {

                finish();
            }
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.country_search:
               // showCountryPicker();
                Intent intent = new Intent(this,Activity_Countries.class);
                startActivityForResult(intent,REQ_CODE);
                break;
        }
    }

    private void showCountryPicker()
    {
        countryPicker = CountryPicker.newInstance("Select Country");
        countryPicker.show(getSupportFragmentManager(),TAG);
        countryPicker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                search_country.setText(name+"-"+code);
                countryPicker.dismiss();

            }
        });
    }

    @Override
    public void onPublisherDataSuccess(List<PublisherModel> publishersModelList) {

        bundle = new Bundle();
        bundle.putSerializable("pub_data", (Serializable) publishersModelList);
        Fragment_Publisher_Search fragment_publisher_search =new Fragment_Publisher_Search();
        fragment_publisher_search.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.search_fragmentContainer,fragment_publisher_search,"pub_search").commit();

    }

    @Override
    public void onPublisherDataFailed(String error) {
        Toast.makeText(this, "Error "+error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNoResults() {
        tv_noResults.setVisibility(View.VISIBLE);
        Fragment_Publisher_Search fragment_publisher_search = (Fragment_Publisher_Search) getSupportFragmentManager().findFragmentByTag("pub_search");
        if (fragment_publisher_search!=null)
        {
            getSupportFragmentManager().beginTransaction().remove(fragment_publisher_search).commit();
        }

    }

    @Override
    public void hideNoResults() {
        tv_noResults.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_CODE&&resultCode==RESULT_OK&&data!=null)
        {
           CountriesModel countriesModel = (CountriesModel) data.getSerializableExtra("country_data");
           search_country.setText(countriesModel.getCountry_title());
           country_id = countriesModel.getCountry_id();
        }
    }
}
