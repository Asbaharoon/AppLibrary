package com.example.omd.library.NearbyMVP;

import android.util.Log;

import com.example.omd.library.Models.CompanyModel;
import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.Models.UniversityModel;
import com.example.omd.library.Services.Service;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Delta on 17/01/2018.
 */

public class InteractorImp implements Interactor {
    @Override
    public void getNearbyUsers(final String userType, final LatLng latLng, final onCompleteListener listener) {
        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<NormalUserData>> call = service.NearbyUsers(userType);
        call.enqueue(new Callback<List<NormalUserData>>() {
            @Override
            public void onResponse(Call<List<NormalUserData>> call, Response<List<NormalUserData>> response) {

                if (response.isSuccessful())
                {
                    List<NormalUserData> nearbyUsersList= new ArrayList<>();
                    List<NormalUserData> normalUserDataList =response.body();

                    for (NormalUserData userData:normalUserDataList)
                    {
                        if (distance(latLng.latitude,latLng.longitude,latLng.latitude,latLng.longitude)<30)
                        {
                            nearbyUsersList.add(userData);
                        }
                        Log.e("nearbyUsers", "onResponse: "+userData.getUserType()+"\n"+userData.getUserId()+"\n"+userData.getUserEmail());

                    }
                    if (nearbyUsersList.size()>0)
                    {
                       // listener.onUsersDataSuccess(nearbyUsersList);
                    }

                    Log.e("nearbyUsers", "onResponse: ");

                }
            }

            @Override
            public void onFailure(Call<List<NormalUserData>> call, Throwable t) {
                Log.e("usererror", "onFailure: "+t.getMessage() );
            }
        });
    }

    @Override
    public void getNearbyPublishers(String userType, final LatLng latLng, final onCompleteListener listener) {
        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<PublisherModel>> call = service.NearbyPublishers(userType);
        call.enqueue(new Callback<List<PublisherModel>>() {
            @Override
            public void onResponse(Call<List<PublisherModel>> call, Response<List<PublisherModel>> response) {
                if (response.isSuccessful())
                {
                    List<PublisherModel> nearbyPublishersList = new ArrayList<>();
                    List<PublisherModel> publisherModelList = response.body();

                    for (PublisherModel publisherModel:publisherModelList)
                    {
                        if (distance(latLng.latitude,latLng.longitude,Double.parseDouble(publisherModel.getPub_lat()),Double.parseDouble(publisherModel.getPub_lng()))<30)
                        {
                            nearbyPublishersList.add(publisherModel);
                        }
                        Log.e("nearpub", "onResponse: "+publisherModel.getUser_type()+"\n"+publisherModel.getPub_username()+"\n"+publisherModel.getPub_lng());
                    }
                    if (nearbyPublishersList.size()>0)
                    {
                        listener.onPublisherDataSuccess(nearbyPublishersList);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PublisherModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getNearbyLibraries(String userType, final LatLng latLng, final onCompleteListener listener) {
        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<LibraryModel>> call = service.NearbyLibraries(userType);
        call.enqueue(new Callback<List<LibraryModel>>() {
            @Override
            public void onResponse(Call<List<LibraryModel>> call, Response<List<LibraryModel>> response) {
                if (response.isSuccessful())
                {
                    List<LibraryModel> nearbyLibraryList = new ArrayList<>();
                    List<LibraryModel> libraryModelList = response.body();

                    for (LibraryModel libraryModel:libraryModelList)
                    {
                        if (distance(latLng.latitude,latLng.longitude,Double.parseDouble(libraryModel.getLat()),Double.parseDouble(libraryModel.getLng()))<25)
                        {
                            nearbyLibraryList.add(libraryModel);
                        }
                        Log.e("nearlib", "onResponse: "+libraryModel.getLib_username()+"\n"+libraryModel.getUser_type()+"\n"+libraryModel.getLng());

                    }
                    if (nearbyLibraryList.size()>0)
                    {
                        Log.e("dis",nearbyLibraryList.get(0).getLib_name()+"\n"+nearbyLibraryList.get(1).getLib_name()+"\n"+nearbyLibraryList.get(2).getLib_name()+"\n");

                        listener.onLibraryDataSuccess(nearbyLibraryList);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LibraryModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getNearbyUniversities(String userType, final LatLng latLng, final onCompleteListener listener) {

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<UniversityModel>> call = service.NearbyUniversities(userType);
        call.enqueue(new Callback<List<UniversityModel>>() {
            @Override
            public void onResponse(Call<List<UniversityModel>> call, Response<List<UniversityModel>> response) {
                if (response.isSuccessful())
                {
                    List<UniversityModel> nearbyUniversityList = new ArrayList<>();
                    List<UniversityModel> universityModelList = response.body();

                    for (UniversityModel universityModel:universityModelList)
                    {
                        if (distance(latLng.latitude,latLng.longitude,Double.parseDouble(universityModel.getUni_lat()),Double.parseDouble(universityModel.getUni_lng()))<30)
                        {
                            nearbyUniversityList.add(universityModel);
                        }
                        Log.e("nearuni", "onResponse: "+universityModel.getUser_type()+"\n"+universityModel.getUni_username()+"\n"+universityModel.getUni_lat());

                    }
                    if (nearbyUniversityList.size()>0)
                    {
                        listener.onUniversityDataSuccess(nearbyUniversityList);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UniversityModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getNearbyCompanies(String userType, final LatLng latLng, final onCompleteListener listener) {
        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<CompanyModel>> call = service.NearbyCompanies(userType);
        call.enqueue(new Callback<List<CompanyModel>>() {
            @Override
            public void onResponse(Call<List<CompanyModel>> call, Response<List<CompanyModel>> response) {
                if (response.isSuccessful())
                {
                    List<CompanyModel> nearbyCompanyList = new ArrayList<>();
                    List<CompanyModel> companyModelList = response.body();

                    for (CompanyModel companyModel:companyModelList)
                    {
                        if (distance(latLng.latitude,latLng.longitude,latLng.latitude,latLng.longitude)<30)
                        {
                            nearbyCompanyList.add(companyModel);
                        }
                        Log.e("nearcomp", "onResponse: "+companyModel.getUser_type()+"\n"+companyModel.getComp_username()+"\n"+companyModel.getComp_email());

                    }
                    if (nearbyCompanyList.size()>0)
                    {
                        listener.onCompanyDataSuccess(nearbyCompanyList);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CompanyModel>> call, Throwable t) {

            }
        });
    }
    private Retrofit setUpRetrofit()
    {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://librarians.liboasis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
