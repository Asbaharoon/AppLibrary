package com.semicolon.librarians.libraryguide.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.CountriesModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.LibraryType_Model;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Activity_Search extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    private MaterialSearchView msv;
    private TextView search_type_tv,search_country,libType_search;
    CountryPicker countryPicker;
    private final String TAG="COUNTRY_PICKER";
    private TextView toolBar_tv;

    private String searchType;
    private String country_id;
    private String libType_id="";
    public NormalUserData userData=null;
    public PublisherModel publisherModel=null;
    public LibraryModel libraryModel=null;
    public UniversityModel universityModel =null;
    public CompanyModel companyModel;



    private final int REQ_CODE_COUNTRY =111;
    private final int REQ_CODE_LIB_TYPE =222;
    private final int REQ_CODE_LIB_SERVICES =333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__search);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, Tags.font,true);

        EventBus.getDefault().register(this);

        initView();
        getDataFromIntent();
    }

    private void initView()
    {

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
                    if (!TextUtils.isEmpty(query)&&!TextUtils.isEmpty(country_id)&&!TextUtils.isEmpty(libType_id))
                    {

                        if (userData!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","library");
                            intent.putExtra("libraryName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("lib_type",libType_id);
                            intent.putExtra("userData",userData);
                            startActivity(intent);

                        }else if (publisherModel!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","library");
                            intent.putExtra("libraryName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("lib_type",libType_id);
                            intent.putExtra("pubData",publisherModel);
                            startActivity(intent);

                        }
                        else if (libraryModel!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","library");
                            intent.putExtra("libraryName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("lib_type",libType_id);
                            intent.putExtra("libData",libraryModel);
                            startActivity(intent);

                        }
                        else if (universityModel!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","library");
                            intent.putExtra("libraryName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("lib_type",libType_id);
                            intent.putExtra("uniData",universityModel);
                            startActivity(intent);

                        }else if (companyModel!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","library");
                            intent.putExtra("libraryName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("lib_type",libType_id);
                            intent.putExtra("compData",userData);
                            startActivity(intent);

                        }

                           Log.e("Data",query+"\n"+country_id+"\n"+"\n"+libType_id);
                    }/*else if (!TextUtils.isEmpty(query)&&!TextUtils.isEmpty(country_id)&&!TextUtils.isEmpty(libType_id))
                    {
                        Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                        intent.putExtra("searchType","library");
                        intent.putExtra("libraryName",query);
                        intent.putExtra("country_id",country_id);
                        intent.putExtra("service_id","");
                        intent.putExtra("lib_type",libType_id);
                        startActivity(intent);
                        Log.e("lib",query+"\n"+country_id+"\n"+libService_id+"\n"+libType_id);


                    }*/
                }else if (searchType.equals("publisher_search"))
                {
                    if (!TextUtils.isEmpty(query)&&!TextUtils.isEmpty(country_id))
                    {
                        if (userData!=null)
                        {

                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","publisher");
                            intent.putExtra("pubName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("userData",userData);
                            startActivity(intent);
                        }else if (publisherModel!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","publisher");
                            intent.putExtra("pubName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("pubData",publisherModel);

                            startActivity(intent);
                        }else if (libraryModel!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","publisher");
                            intent.putExtra("pubName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("libData",libraryModel);
                            startActivity(intent);
                        }
                        else if (universityModel!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","publisher");
                            intent.putExtra("pubName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("uniData",universityModel);
                            startActivity(intent);
                        }
                        else if (companyModel!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","publisher");
                            intent.putExtra("pubName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("compData",companyModel);
                            startActivity(intent);
                        }

                    }


                }
                else if (searchType.equals("university_search"))
                {
                    if (!TextUtils.isEmpty(query)&&!TextUtils.isEmpty(country_id))
                    {
                        if (userData!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","university");
                            intent.putExtra("uniName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("userData",userData);

                            startActivity(intent);
                        }else if (publisherModel!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","university");
                            intent.putExtra("uniName",query);
                            intent.putExtra("country_id",country_id);
                            startActivity(intent);
                        }else if (libraryModel!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","university");
                            intent.putExtra("uniName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("libData",libraryModel);

                            startActivity(intent);
                        }else if (universityModel!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","university");
                            intent.putExtra("uniName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("uniData",universityModel);

                            startActivity(intent);
                        }else if (companyModel!=null)
                        {
                            Intent intent = new Intent(Activity_Search.this,Activity_Search_Results.class);
                            intent.putExtra("searchType","university");
                            intent.putExtra("uniName",query);
                            intent.putExtra("country_id",country_id);
                            intent.putExtra("compData",companyModel);

                            startActivity(intent);
                        }

                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        msv.setCloseIcon(null);
        msv.setBackIcon(null);
        mToolbar = (Toolbar) findViewById(R.id.search_toolBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolBar_tv = (TextView) findViewById(R.id.toolBar_tv);

        search_country.setOnClickListener(this);
        libType_search = (TextView) findViewById(R.id.libType_search);
        libType_search.setOnClickListener(this);

    }
    private void getDataFromIntent()
    {
        Intent intent = getIntent();
        if (intent.hasExtra("library_search"))
        {
            searchType ="library_search";
            toolBar_tv.setText(R.string.lib_search);
            msv.setHint(getString(R.string.librart_name));
            search_type_tv.setText(R.string.librart_name);
            libType_search.setVisibility(View.VISIBLE);

            if (intent.hasExtra("userData"))
            {
                userData = (NormalUserData) intent.getSerializableExtra("userData");
            }else if (intent.hasExtra("publisherData"))
            {
                publisherModel = (PublisherModel) intent.getSerializableExtra("publisherData");
            }else if (intent.hasExtra("libraryData"))
            {

                libraryModel = (LibraryModel) intent.getSerializableExtra("libraryData");
            }else if (intent.hasExtra("universalData"))
            {
                universityModel = (UniversityModel) intent.getSerializableExtra("universityData");
            }else if (intent.hasExtra("companyData"))
            {
                companyModel = (CompanyModel) intent.getSerializableExtra("companyData");
            }

        }
        else if (intent.hasExtra("publisher_search"))
        {
            searchType ="publisher_search";
            msv.setHint(getString(R.string.pub_name));
            search_type_tv.setText(getString(R.string.pub_name));
            toolBar_tv.setText(R.string.pub_search);
            if (intent.hasExtra("userData"))
            {
                userData = (NormalUserData) intent.getSerializableExtra("userData");
            }else if (intent.hasExtra("publisherData"))
            {
                publisherModel = (PublisherModel) intent.getSerializableExtra("publisherData");
            }else if (intent.hasExtra("libraryData"))
            {

                libraryModel = (LibraryModel) intent.getSerializableExtra("libraryData");
            }else if (intent.hasExtra("universalData"))
            {
                universityModel = (UniversityModel) intent.getSerializableExtra("universityData");
            }else if (intent.hasExtra("companyData"))
            {
                companyModel = (CompanyModel) intent.getSerializableExtra("companyData");
            }

        }else if (intent.hasExtra("university_search"))
        {
            searchType ="university_search";
            msv.setHint(getString(R.string.university_name));
            search_type_tv.setText(getString(R.string.university_name));
            toolBar_tv.setText(R.string.uni_search);
            if (intent.hasExtra("userData"))
            {
                userData = (NormalUserData) intent.getSerializableExtra("userData");
            }else if (intent.hasExtra("publisherData"))
            {
                publisherModel = (PublisherModel) intent.getSerializableExtra("publisherData");
            }else if (intent.hasExtra("libraryData"))
            {

                libraryModel = (LibraryModel) intent.getSerializableExtra("libraryData");
            }else if (intent.hasExtra("universalData"))
            {
                universityModel = (UniversityModel) intent.getSerializableExtra("universityData");
            }else if (intent.hasExtra("companyData"))
            {
                companyModel = (CompanyModel) intent.getSerializableExtra("companyData");
            }

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

        return true;
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.country_search:
               // showCountryPicker();
                Intent intent_country = new Intent(this,Activity_Search_Results.class);
                intent_country.putExtra("searchType","country");
                startActivityForResult(intent_country,REQ_CODE_COUNTRY);
                break;
            case R.id.libType_search:
                Intent intent_libType = new Intent(this,Activity_Search_Results.class);
                intent_libType.putExtra("searchType","libType");
                startActivityForResult(intent_libType,REQ_CODE_LIB_TYPE);
                break;
           /* case R.id.libService_search:
                Intent intent_libServices = new Intent(this,Activity_Search_Results.class);
                intent_libServices.putExtra("searchType","libServices");
                startActivityForResult(intent_libServices,REQ_CODE_LIB_SERVICES);
                break;*/
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_CODE_COUNTRY&&resultCode==RESULT_OK&&data!=null)
        {
           CountriesModel countriesModel = (CountriesModel) data.getSerializableExtra("country_data");
           search_country.setText(countriesModel.getCountry_title()+"-"+countriesModel.getCountry_flag());
           search_country.setTextColor(ActivityCompat.getColor(Activity_Search.this,R.color.black));
           country_id = countriesModel.getCountry_id();

        }
        else if (requestCode==REQ_CODE_LIB_TYPE&&resultCode==RESULT_OK&&data!=null)
        {
            LibraryType_Model libraryType_model = (LibraryType_Model) data.getSerializableExtra("libType_data");
            libType_search.setText(libraryType_model.getLib_type_title());
            libType_search.setTextColor(ActivityCompat.getColor(Activity_Search.this,R.color.black));
            libType_id = libraryType_model.getLib_type_id();

        }
        else if (requestCode==REQ_CODE_LIB_SERVICES&&resultCode==RESULT_OK&&data!=null)
        {
           /* LibraryServices_Model libraryServicesModel = (LibraryServices_Model) data.getSerializableExtra("libServices_data");
            libService_id = libraryServicesModel.getLib_service_id();
      */  }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUserData(NormalUserData normalUserData)
    {
        this.userData  = normalUserData;
        Log.e("a s _user",""+userData.getUserId());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPublisherData(PublisherModel publisherModel)
    {
        this.publisherModel  = publisherModel;
        Log.e("a s_pub",""+publisherModel.getPub_username());

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLibraryData(LibraryModel libraryModel)
    {
        this.libraryModel  = libraryModel;
        Log.e("a s _lib",""+libraryModel.getLib_username());

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUniversityData(UniversityModel universityModel)
    {
        this.universityModel  = universityModel;
        Log.e("a s_uni"," "+universityModel.getUni_username());

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCompanyData(CompanyModel companyModel)
    {
        this.companyModel  = companyModel;
        Log.e("a s_comp",""+companyModel.getComp_username());

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (userData!=null)
        {
            EventBus.getDefault().post(userData);

        }else if (publisherModel!=null)
        {
            EventBus.getDefault().post(publisherModel);

        }
        else if (libraryModel!=null)
        {
            EventBus.getDefault().post(libraryModel);

        }
        else if (universityModel!=null)
        {
            EventBus.getDefault().post(universityModel);

        }
        else if (companyModel!=null)
        {
            EventBus.getDefault().post(companyModel);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
