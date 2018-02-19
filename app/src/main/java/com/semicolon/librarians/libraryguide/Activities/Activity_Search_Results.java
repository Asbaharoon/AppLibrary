package com.semicolon.librarians.libraryguide.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.semicolon.librarians.libraryguide.Fragments.Fragment_Countries;
import com.semicolon.librarians.libraryguide.Fragments.Fragment_LibType;
import com.semicolon.librarians.libraryguide.Fragments.Fragment_Library_Search_Results;
import com.semicolon.librarians.libraryguide.Fragments.Fragment_Library_Services;
import com.semicolon.librarians.libraryguide.Fragments.Fragment_Publisher_Search_Results;
import com.semicolon.librarians.libraryguide.Fragments.Fragment_University_Search_Results;
import com.semicolon.librarians.libraryguide.MVP.Create_ChatRoom_MVP.Presenter;
import com.semicolon.librarians.libraryguide.MVP.Create_ChatRoom_MVP.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.Create_ChatRoom_MVP.ViewData;
import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Activity_Search_Results extends AppCompatActivity implements ViewData {

    private Toolbar toolbar ;
    private TextView toolBar_tv;
    private String searchType;
    public NormalUserData userData=null;
    public PublisherModel publisherModel=null;
    public LibraryModel libraryModel=null;
    public UniversityModel universityModel =null;
    public CompanyModel companyModel;
    public Presenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, Tags.font,true);

        presenter = new PresenterImp(this,this);
        EventBus.getDefault().register(this);
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
                    toolBar_tv.setText(getString(R.string.country));
                    Fragment_Countries fragment_countries =new Fragment_Countries();
                    getSupportFragmentManager().beginTransaction().replace(R.id.searchContainer,fragment_countries).commit();
                    break;
                case "libType":
                    toolBar_tv.setText(getString(R.string.lib_type));
                    Fragment_LibType fragment_libType = new Fragment_LibType();
                    getSupportFragmentManager().beginTransaction().replace(R.id.searchContainer,fragment_libType).commit();
                    break;
                case "libServices":
                    toolBar_tv.setText(R.string.lib_services);

                    Fragment_Library_Services fragment_library_services = new Fragment_Library_Services();
                    getSupportFragmentManager().beginTransaction().replace(R.id.searchContainer,fragment_library_services).commit();
                    break;

                case "publisher":
                    if (intent.hasExtra("userData"))
                    {
                        userData = (NormalUserData) intent.getSerializableExtra("userData");
                    }else if (intent.hasExtra("pubData"))
                    {
                        publisherModel = (PublisherModel) intent.getSerializableExtra("pubData");
                    }else if (intent.hasExtra("libData"))
                    {
                        libraryModel = (LibraryModel) intent.getSerializableExtra("libData");
                    }else if (intent.hasExtra("uniData"))
                    {
                        universityModel = (UniversityModel) intent.getSerializableExtra("uniData");
                    }else if (intent.hasExtra("compData"))
                    {
                        companyModel = (CompanyModel) intent.getSerializableExtra("compData");
                    }

                    toolBar_tv.setText(R.string.publishers);

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
                    if (intent.hasExtra("userData"))
                    {
                        userData = (NormalUserData) intent.getSerializableExtra("userData");
                    }else if (intent.hasExtra("pubData"))
                    {
                        publisherModel = (PublisherModel) intent.getSerializableExtra("pubData");
                    }else if (intent.hasExtra("libData"))
                    {
                        libraryModel = (LibraryModel) intent.getSerializableExtra("libData");
                    }else if (intent.hasExtra("uniData"))
                    {
                        universityModel = (UniversityModel) intent.getSerializableExtra("uniData");
                    }else if (intent.hasExtra("compData"))
                    {
                        companyModel = (CompanyModel) intent.getSerializableExtra("compData");
                    }
                    toolBar_tv.setText(R.string.libraries);

                    String libName = intent.getStringExtra("libraryName");
                    String lib_country_id = intent.getStringExtra("country_id");
                    //String lib_service_id = intent.getStringExtra("service_id");
                    String lib_type       = intent.getStringExtra("lib_type");

                    Fragment_Library_Search_Results fragment_library_search_results = new Fragment_Library_Search_Results();
                    Bundle lib_bundle = new Bundle();
                    lib_bundle.putString("libraryName",libName);
                    lib_bundle.putString("country_id",lib_country_id);
                   // lib_bundle.putString("service_id",lib_service_id);
                    lib_bundle.putString("libraryType",lib_type);
                    fragment_library_search_results.setArguments(lib_bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.searchContainer,fragment_library_search_results).commit();
                    break;
                case "university":
                    if (intent.hasExtra("userData"))
                    {
                        userData = (NormalUserData) intent.getSerializableExtra("userData");
                    }else if (intent.hasExtra("pubData"))
                    {
                        publisherModel = (PublisherModel) intent.getSerializableExtra("pubData");
                    }else if (intent.hasExtra("libData"))
                    {
                        libraryModel = (LibraryModel) intent.getSerializableExtra("libData");
                    }else if (intent.hasExtra("uniData"))
                    {
                        universityModel = (UniversityModel) intent.getSerializableExtra("uniData");
                    }else if (intent.hasExtra("compData"))
                    {
                        companyModel = (CompanyModel) intent.getSerializableExtra("compData");
                    }
                    toolBar_tv.setText(R.string.universities);

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
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolBar_tv = (TextView) findViewById(R.id.toolBar_tv);
        /*mRecView = (RecyclerView) findViewById(R.id.recView_countries);
        manager = new LinearLayoutManager(this);
        mRecView.setLayoutManager(manager);
        mRecView.setHasFixedSize(true);*/


    }

    @Override
    public void onChatRoom_CreatedSuccessfully(String response) {
        Log.e("chatroom created",response);
    }

    @Override
    public void onFailed(String error) {
        Log.e("chatroom created",error);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUserData(NormalUserData normalUserData)
    {
        this.userData  = normalUserData;
        Log.e("a s_get user",""+userData.getUserId());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPublisherData(PublisherModel publisherModel)
    {
        this.publisherModel  = publisherModel;
        Log.e("a s_get pub",""+publisherModel.getPub_username());

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLibraryData(LibraryModel libraryModel)
    {
        this.libraryModel  = libraryModel;
        Log.e("a s_getlib",""+libraryModel.getLib_username());

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUniversityData(UniversityModel universityModel)
    {
        this.universityModel  = universityModel;
        Log.e("a s_get uni",""+universityModel.getUni_username());

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCompanyData(CompanyModel companyModel)
    {
        this.companyModel  = companyModel;
        Log.e("a s_get comp",""+companyModel.getComp_username());

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (userData!=null)
        {
            Log.e("act search_post_user"," "+userData.getUserId());

            EventBus.getDefault().post(userData);

        }else if (publisherModel!=null)
        {
            Log.e("act search_post_pub"," "+publisherModel.getPub_username());

            EventBus.getDefault().post(publisherModel);

        }
        else if (libraryModel!=null)
        {
            Log.e("act search_post_lib"," "+libraryModel.getLib_username());

            EventBus.getDefault().post(libraryModel);

        }
        else if (universityModel!=null)
        {
            Log.e("act search_post_uni"," "+universityModel.getUni_username());

            EventBus.getDefault().post(universityModel);

        }
        else if (companyModel!=null)
        {
            Log.e("act search_post_comp"," "+companyModel.getComp_username());

            EventBus.getDefault().post(companyModel);

        }
    }
}
