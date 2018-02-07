package com.semicolon.librarians.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.semicolon.librarians.library.Fragments.Fragment_Countries;
import com.semicolon.librarians.library.Fragments.Fragment_LibType;
import com.semicolon.librarians.library.Fragments.Fragment_Library_Search_Results;
import com.semicolon.librarians.library.Fragments.Fragment_Library_Services;
import com.semicolon.librarians.library.Fragments.Fragment_Publisher_Search_Results;
import com.semicolon.librarians.library.Fragments.Fragment_University_Search_Results;
import com.semicolon.librarians.library.R;
import com.semicolon.librarians.library.Services.Tags;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Activity_Search_Results extends AppCompatActivity {

    private Toolbar toolbar ;
    private String searchType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Calligrapher calligrapher = new Calligrapher(getApplicationContext());
        calligrapher.setFont(this, Tags.font,true);

        initView();
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();


        if (intent.hasExtra("searchType"))
        {
            searchType = intent.getStringExtra("searchType");

            switch (searchType)
            {
                case "country":
                    getSupportActionBar().setTitle("Countries");
                    Fragment_Countries fragment_countries =new Fragment_Countries();
                    getSupportFragmentManager().beginTransaction().replace(R.id.searchContainer,fragment_countries).commit();
                    break;
                case "libType":
                    getSupportActionBar().setTitle("Libraries types");
                    Fragment_LibType fragment_libType = new Fragment_LibType();
                    getSupportFragmentManager().beginTransaction().replace(R.id.searchContainer,fragment_libType).commit();
                    break;
                case "libServices":
                    getSupportActionBar().setTitle("Libraries services");
                    Fragment_Library_Services fragment_library_services = new Fragment_Library_Services();
                    getSupportFragmentManager().beginTransaction().replace(R.id.searchContainer,fragment_library_services).commit();
                    break;

                case "publisher":
                    getSupportActionBar().setTitle("Publishers");
                    String pubName =intent.getStringExtra("pubName");
                    String pub_country_id = intent.getStringExtra("country_id");
                    Fragment_Publisher_Search_Results fragment_publisher_search_results = new Fragment_Publisher_Search_Results();
                    Bundle bundle = new Bundle();
                    bundle.putString("pubName",pubName);
                    bundle.putString("country_id",pub_country_id);
                    fragment_publisher_search_results.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.searchContainer,fragment_publisher_search_results).commit();
                    break;
                case "library":
                    getSupportActionBar().setTitle("Libraries");
                    String libName = intent.getStringExtra("libraryName");
                    String lib_country_id = intent.getStringExtra("country_id");
                    String lib_service_id = intent.getStringExtra("service_id");
                    String lib_type       = intent.getStringExtra("lib_type");

                    Fragment_Library_Search_Results fragment_library_search_results = new Fragment_Library_Search_Results();
                    Bundle lib_bundle = new Bundle();
                    lib_bundle.putString("libraryName",libName);
                    lib_bundle.putString("country_id",lib_country_id);
                    lib_bundle.putString("service_id",lib_service_id);
                    lib_bundle.putString("libraryType",lib_type);
                    getSupportFragmentManager().beginTransaction().replace(R.id.searchContainer,fragment_library_search_results).commit();
                    break;
                case "university":
                    getSupportActionBar().setTitle("Universities");
                    String uniName = intent.getStringExtra("uniName");
                    String uni_country_id = intent.getStringExtra("country_id");
                    Bundle uni_bundle = new Bundle();
                    uni_bundle.putString("uniName",uniName);
                    uni_bundle.putString("country_id",uni_country_id);
                    Fragment_University_Search_Results fragment_university_search_results = new Fragment_University_Search_Results();
                    fragment_university_search_results.setArguments(uni_bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.searchContainer,fragment_university_search_results).commit();


                    break;

            }

        }

    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.country_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Fragment_Countries");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*mRecView = (RecyclerView) findViewById(R.id.recView_countries);
        manager = new LinearLayoutManager(this);
        mRecView.setLayoutManager(manager);
        mRecView.setHasFixedSize(true);*/


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return true;
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
