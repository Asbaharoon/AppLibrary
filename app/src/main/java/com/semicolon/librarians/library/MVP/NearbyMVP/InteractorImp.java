package com.semicolon.librarians.library.MVP.NearbyMVP;

import android.util.Log;

import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;
import com.semicolon.librarians.library.Services.Service;
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
    public void getNearbyUsers(String currUserType, String filteredUserType, LatLng currLatLng, onCompleteListener listener) {
        switch (filteredUserType)
        {
            case "user":
                getNearbyUsersDate(currUserType, filteredUserType, currLatLng, listener);
                break;
            case "publisher":
                getNearbyPublishersDate(currUserType, filteredUserType, currLatLng, listener);
                break;
            case "library":
                getNearbyLibrariesDate(currUserType, filteredUserType, currLatLng, listener);
                break;
            case "university":
                getNearbyUniversitiesDate(currUserType, filteredUserType, currLatLng, listener);
                break;
            case "company":
                getNearbyCompaniesDate(currUserType, filteredUserType, currLatLng, listener);
                break;
        }
    }

    private void getNearbyUsersDate(final String currUserType, String filteredUserType, final LatLng currLatLng, final onCompleteListener listener)
    {
        Log.e("user","1");
        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<NormalUserData>> call = service.NearbyUsers(filteredUserType);
        call.enqueue(new Callback<List<NormalUserData>>() {
            @Override
            public void onResponse(Call<List<NormalUserData>> call, Response<List<NormalUserData>> response) {

                if (response.isSuccessful())
                {
                    List<NormalUserData> NearbyUserDataList= new ArrayList<>();
                    List<NormalUserData> normalUserDataList = response.body();
                    switch (currUserType)
                    {
                        case "user":
                            for (NormalUserData user_Data:normalUserDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(user_Data.getUser_google_lat().toString()),Double.parseDouble(user_Data.getUser_google_lng().toString()))<25
                                        &&distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(user_Data.getUser_google_lat().toString()),Double.parseDouble(user_Data.getUser_google_lng().toString()))!=0.0)
                                {
                                    NearbyUserDataList.add(user_Data);
                                    Log.e("user data ",user_Data.getUserName());

                                }
                            }
                            if (NearbyUserDataList.size()>0)
                            {
                                listener.onUsersDataSuccess(NearbyUserDataList);
                            }else
                                {
                                    listener.onUsersDataSuccess(NearbyUserDataList);


                                }
                            break;
                        case "publisher":
                            for (NormalUserData user_Data:normalUserDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(user_Data.getUser_google_lat()),Double.parseDouble(user_Data.getUser_google_lng()))<25)
                                {
                                    NearbyUserDataList.add(user_Data);
                                    Log.e("user data ",user_Data.getUserName());
                                }
                            }
                            if (NearbyUserDataList.size()>0)
                            {
                                listener.onUsersDataSuccess(NearbyUserDataList);
                            }else
                            {
                                listener.onUsersDataSuccess(NearbyUserDataList);

                            }
                            break;
                        case "library":
                            for (NormalUserData user_Data:normalUserDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(user_Data.getUser_google_lat()),Double.parseDouble(user_Data.getUser_google_lng()))<25)
                                {
                                    NearbyUserDataList.add(user_Data);
                                    Log.e("user data ",user_Data.getUserName());

                                }

                            }
                            if (NearbyUserDataList.size()>0)
                            {
                                listener.onUsersDataSuccess(NearbyUserDataList);
                            }else
                            {
                                listener.onUsersDataSuccess(NearbyUserDataList);

                            }
                            break;
                        case "university":
                            for (NormalUserData user_Data:normalUserDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(user_Data.getUser_google_lat()),Double.parseDouble(user_Data.getUser_google_lng()))<25)
                                {
                                    NearbyUserDataList.add(user_Data);
                                    Log.e("user data ",user_Data.getUserName());

                                }

                            }
                            if (NearbyUserDataList.size()>0)
                            {
                                listener.onUsersDataSuccess(NearbyUserDataList);
                            }else
                            {
                                listener.onUsersDataSuccess(NearbyUserDataList);

                            }
                            break;
                        case "company":
                            for (NormalUserData user_Data:normalUserDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(user_Data.getUser_google_lat()),Double.parseDouble(user_Data.getUser_google_lng()))<25)
                                {
                                    NearbyUserDataList.add(user_Data);
                                    Log.e("user data ",user_Data.getUserName());

                                }

                            }
                            if (NearbyUserDataList.size()>0)
                            {
                                listener.onUsersDataSuccess(NearbyUserDataList);
                            }else
                            {
                                listener.onUsersDataSuccess(NearbyUserDataList);

                            }
                            break;


                    }


                }
            }

            @Override
            public void onFailure(Call<List<NormalUserData>> call, Throwable t) {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
            }
        });
    }
    private void getNearbyPublishersDate(final String currUserType, String filteredUserType, final LatLng currLatLng, final onCompleteListener listener)
    {
        Log.e("pub","1");

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<PublisherModel>> call = service.NearbyPublishers(filteredUserType);
        call.enqueue(new Callback<List<PublisherModel>>() {
            @Override
            public void onResponse(Call<List<PublisherModel>> call, Response<List<PublisherModel>> response) {
                if (response.isSuccessful())
                {
                    List<PublisherModel> NearbyPublisherDataList= new ArrayList<>();
                    List<PublisherModel> PublisherDataList = response.body();
                    switch (currUserType)
                    {
                        case "user":
                            for (PublisherModel pub_Data:PublisherDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(pub_Data.getPub_lat()),Double.parseDouble(pub_Data.getPub_lng()))<25)
                                {
                                    NearbyPublisherDataList.add(pub_Data);
                                    Log.e("user data ",pub_Data.getPub_username());

                                }

                            }
                            if (NearbyPublisherDataList.size()>0)
                            {
                                listener.onPublisherDataSuccess(NearbyPublisherDataList);
                            }else
                                {
                                    listener.onPublisherDataSuccess(NearbyPublisherDataList);

                                }
                            break;
                        case "publisher":
                            for (PublisherModel pub_Data:PublisherDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(pub_Data.getPub_lat()),Double.parseDouble(pub_Data.getPub_lng()))<25
                                        &&distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(pub_Data.getPub_lat()),Double.parseDouble(pub_Data.getPub_lng()))!=0.0)
                                {
                                    NearbyPublisherDataList.add(pub_Data);
                                    Log.e("user data ",pub_Data.getPub_username());

                                }

                            }
                            if (NearbyPublisherDataList.size()>0)
                            {
                                listener.onPublisherDataSuccess(NearbyPublisherDataList);
                            }else
                            {
                                listener.onPublisherDataSuccess(NearbyPublisherDataList);

                            }
                            break;
                        case "library":
                            for (PublisherModel pub_Data:PublisherDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(pub_Data.getPub_lat()),Double.parseDouble(pub_Data.getPub_lng()))<25)
                                {
                                    NearbyPublisherDataList.add(pub_Data);
                                    Log.e("user data ",pub_Data.getPub_username());

                                }

                            }
                            if (NearbyPublisherDataList.size()>0)
                            {
                                listener.onPublisherDataSuccess(NearbyPublisherDataList);
                            }else
                            {
                                listener.onPublisherDataSuccess(NearbyPublisherDataList);

                            }
                            break;
                        case "university":
                            for (PublisherModel pub_Data:PublisherDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(pub_Data.getPub_lat()),Double.parseDouble(pub_Data.getPub_lng()))<25)
                                {
                                    NearbyPublisherDataList.add(pub_Data);
                                    Log.e("user data ",pub_Data.getPub_username());

                                }

                            }
                            if (NearbyPublisherDataList.size()>0)
                            {
                                listener.onPublisherDataSuccess(NearbyPublisherDataList);
                            }else
                            {
                                listener.onPublisherDataSuccess(NearbyPublisherDataList);

                            }
                            break;
                        case "company":
                            for (PublisherModel pub_Data:PublisherDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(pub_Data.getPub_lat()),Double.parseDouble(pub_Data.getPub_lng()))<25)
                                {
                                    NearbyPublisherDataList.add(pub_Data);
                                    Log.e("user data ",pub_Data.getPub_username());

                                }

                            }
                            if (NearbyPublisherDataList.size()>0)
                            {
                                listener.onPublisherDataSuccess(NearbyPublisherDataList);
                            }else
                            {
                                listener.onPublisherDataSuccess(NearbyPublisherDataList);

                            }
                            break;


                    }


                }


            }

            @Override
            public void onFailure(Call<List<PublisherModel>> call, Throwable t) {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");

            }
        });

    }
    private void getNearbyLibrariesDate(final String currUserType, String filteredUserType, final LatLng currLatLng, final onCompleteListener listener)
    {
        Log.e("lib","1");

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);

        Call<List<LibraryModel>> call = service.NearbyLibraries(filteredUserType);
        call.enqueue(new Callback<List<LibraryModel>>() {
            @Override
            public void onResponse(Call<List<LibraryModel>> call, Response<List<LibraryModel>> response) {
                if (response.isSuccessful())
                {
                    List<LibraryModel> NearbyLibraryDataList= new ArrayList<>();
                    List<LibraryModel> LibraryDataList = response.body();
                    switch (currUserType)
                    {
                        case "user":
                            for (LibraryModel lib_Data:LibraryDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(lib_Data.getLat()),Double.parseDouble(lib_Data.getLng()))<25)
                                {
                                    NearbyLibraryDataList.add(lib_Data);
                                    Log.e("user data ",lib_Data.getLib_username());

                                }

                            }
                            if (NearbyLibraryDataList.size()>0)
                            {
                                listener.onLibraryDataSuccess(NearbyLibraryDataList);
                            }else
                                {
                                    listener.onLibraryDataSuccess(NearbyLibraryDataList);

                                }
                            break;
                        case "publisher":
                            for (LibraryModel lib_Data:LibraryDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(lib_Data.getLat()),Double.parseDouble(lib_Data.getLng()))<25)
                                {
                                    NearbyLibraryDataList.add(lib_Data);
                                    Log.e("user data ",lib_Data.getLib_username());

                                }

                            }
                            if (NearbyLibraryDataList.size()>0)
                            {
                                listener.onLibraryDataSuccess(NearbyLibraryDataList);
                            }else
                            {
                                listener.onLibraryDataSuccess(NearbyLibraryDataList);

                            }
                            break;
                        case "library":
                            for (LibraryModel lib_Data:LibraryDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(lib_Data.getLat()),Double.parseDouble(lib_Data.getLng()))<25
                                        &&distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(lib_Data.getLat()),Double.parseDouble(lib_Data.getLng()))!=0.0)
                                {
                                    NearbyLibraryDataList.add(lib_Data);
                                    Log.e("user data ",lib_Data.getLib_username());

                                }

                            }
                            if (NearbyLibraryDataList.size()>0)
                            {
                                listener.onLibraryDataSuccess(NearbyLibraryDataList);
                            }else
                            {
                                listener.onLibraryDataSuccess(NearbyLibraryDataList);

                            }
                            break;
                        case "university":
                            for (LibraryModel lib_Data:LibraryDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(lib_Data.getLat()),Double.parseDouble(lib_Data.getLng()))<25)
                                {
                                    NearbyLibraryDataList.add(lib_Data);
                                    Log.e("user data ",lib_Data.getLib_username());

                                }

                            }
                            if (NearbyLibraryDataList.size()>0)
                            {
                                listener.onLibraryDataSuccess(NearbyLibraryDataList);
                            }else
                            {
                                listener.onLibraryDataSuccess(NearbyLibraryDataList);

                            }
                            break;
                        case "company":
                            for (LibraryModel lib_Data:LibraryDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(lib_Data.getLat()),Double.parseDouble(lib_Data.getLng()))<25)
                                {
                                    NearbyLibraryDataList.add(lib_Data);
                                    Log.e("user data ",lib_Data.getLib_username());

                                }

                            }
                            if (NearbyLibraryDataList.size()>0)
                            {
                                listener.onLibraryDataSuccess(NearbyLibraryDataList);
                            }else
                            {
                                listener.onLibraryDataSuccess(NearbyLibraryDataList);

                            }
                            break;


                    }


                }





            }

            @Override
            public void onFailure(Call<List<LibraryModel>> call, Throwable t) {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");

            }
        });
    }
    private void getNearbyUniversitiesDate(final String currUserType, String filteredUserType, final LatLng currLatLng, final onCompleteListener listener)
    {
        Log.e("uni","1");

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<UniversityModel>> call = service.NearbyUniversities(filteredUserType);
        call.enqueue(new Callback<List<UniversityModel>>() {
            @Override
            public void onResponse(Call<List<UniversityModel>> call, Response<List<UniversityModel>> response) {
                if (response.isSuccessful())
                {
                    List<UniversityModel> NearbyUniversityDataList= new ArrayList<>();
                    List<UniversityModel> UniversityDataList = response.body();
                    switch (currUserType)
                    {
                        case "user":
                            for (UniversityModel uni_Data:UniversityDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(uni_Data.getUni_lat()),Double.parseDouble(uni_Data.getUni_lng()))<25)
                                {
                                    NearbyUniversityDataList.add(uni_Data);
                                    Log.e("user data ",uni_Data.getUni_username());

                                }

                            }
                            if (NearbyUniversityDataList.size()>0)
                            {
                                listener.onUniversityDataSuccess(NearbyUniversityDataList);
                            }else
                                {
                                    listener.onUniversityDataSuccess(NearbyUniversityDataList);

                                }
                            break;
                        case "publisher":
                            for (UniversityModel uni_Data:UniversityDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(uni_Data.getUni_lat()),Double.parseDouble(uni_Data.getUni_lng()))<25)
                                {
                                    NearbyUniversityDataList.add(uni_Data);
                                    Log.e("user data ",uni_Data.getUni_username());

                                }

                            }
                            if (NearbyUniversityDataList.size()>0)
                            {
                                listener.onUniversityDataSuccess(NearbyUniversityDataList);
                            }else
                            {
                                listener.onUniversityDataSuccess(NearbyUniversityDataList);

                            }
                            break;
                        case "library":
                            for (UniversityModel uni_Data:UniversityDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(uni_Data.getUni_lat()),Double.parseDouble(uni_Data.getUni_lng()))<25)
                                {
                                    NearbyUniversityDataList.add(uni_Data);
                                    Log.e("user data ",uni_Data.getUni_username());

                                }

                            }
                            if (NearbyUniversityDataList.size()>0)
                            {
                                listener.onUniversityDataSuccess(NearbyUniversityDataList);
                            }else
                            {
                                listener.onUniversityDataSuccess(NearbyUniversityDataList);

                            }
                            break;
                        case "university":
                            for (UniversityModel uni_Data:UniversityDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(uni_Data.getUni_lat()),Double.parseDouble(uni_Data.getUni_lng()))<25
                                        &&distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(uni_Data.getUni_lat()),Double.parseDouble(uni_Data.getUni_lng()))!=0.0)
                                {
                                    NearbyUniversityDataList.add(uni_Data);
                                    Log.e("user data ",uni_Data.getUni_username());

                                }
                            }
                            if (NearbyUniversityDataList.size()>0)
                            {
                                listener.onUniversityDataSuccess(NearbyUniversityDataList);
                            }else
                            {
                                listener.onUniversityDataSuccess(NearbyUniversityDataList);

                            }
                            break;
                        case "company":
                            for (UniversityModel uni_Data:UniversityDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(uni_Data.getUni_lat()),Double.parseDouble(uni_Data.getUni_lng()))<25)
                                {
                                    NearbyUniversityDataList.add(uni_Data);
                                    Log.e("user data ",uni_Data.getUni_username());

                                }
                            }
                            if (NearbyUniversityDataList.size()>0)
                            {
                                listener.onUniversityDataSuccess(NearbyUniversityDataList);
                            }else
                            {
                                listener.onUniversityDataSuccess(NearbyUniversityDataList);

                            }
                            break;


                    }


                }



            }

            @Override
            public void onFailure(Call<List<UniversityModel>> call, Throwable t) {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");

            }
        });
    }
    private void getNearbyCompaniesDate(final String currUserType, String filteredUserType, final LatLng currLatLng, final onCompleteListener listener)
    {
        Log.e("comp","1");

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);

        Call<List<CompanyModel>> call = service.NearbyCompanies(filteredUserType);
        call.enqueue(new Callback<List<CompanyModel>>() {
            @Override
            public void onResponse(Call<List<CompanyModel>> call, Response<List<CompanyModel>> response) {
                if (response.isSuccessful())
                {
                    List<CompanyModel> NearbyCompanyDataList= new ArrayList<>();
                    List<CompanyModel> CompanyDataList = response.body();
                    switch (currUserType)
                    {
                        case "user":
                            for (CompanyModel comp_Data:CompanyDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(comp_Data.getComp_lat()),Double.parseDouble(comp_Data.getComp_lng()))<25)
                                {
                                    NearbyCompanyDataList.add(comp_Data);
                                    Log.e("user data ",comp_Data.getComp_username());

                                }
                            }
                            if (NearbyCompanyDataList.size()>0)
                            {
                                listener.onCompanyDataSuccess(NearbyCompanyDataList);
                            }else
                                {
                                    listener.onCompanyDataSuccess(NearbyCompanyDataList);

                                }
                            break;
                        case "publisher":
                            for (CompanyModel comp_Data:CompanyDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(comp_Data.getComp_lat()),Double.parseDouble(comp_Data.getComp_lng()))<25)
                                {
                                    NearbyCompanyDataList.add(comp_Data);
                                    Log.e("user data ",comp_Data.getComp_username());

                                }
                            }
                            if (NearbyCompanyDataList.size()>0)
                            {
                                listener.onCompanyDataSuccess(NearbyCompanyDataList);
                            }else
                            {
                                listener.onCompanyDataSuccess(NearbyCompanyDataList);

                            }
                            break;
                        case "library":
                            for (CompanyModel comp_Data:CompanyDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(comp_Data.getComp_lat()),Double.parseDouble(comp_Data.getComp_lng()))<25)
                                {
                                    NearbyCompanyDataList.add(comp_Data);
                                    Log.e("user data ",comp_Data.getComp_username());

                                }
                            }
                            if (NearbyCompanyDataList.size()>0)
                            {
                                listener.onCompanyDataSuccess(NearbyCompanyDataList);
                            }else
                            {
                                listener.onCompanyDataSuccess(NearbyCompanyDataList);

                            }
                            break;
                        case "university":
                            for (CompanyModel comp_Data:CompanyDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(comp_Data.getComp_lat()),Double.parseDouble(comp_Data.getComp_lng()))<25)
                                {
                                    NearbyCompanyDataList.add(comp_Data);
                                    Log.e("user data ",comp_Data.getComp_username());

                                }
                            }
                            if (NearbyCompanyDataList.size()>0)
                            {
                                listener.onCompanyDataSuccess(NearbyCompanyDataList);
                            }else
                            {
                                listener.onCompanyDataSuccess(NearbyCompanyDataList);

                            }
                            break;
                        case "company":
                            for (CompanyModel comp_Data:CompanyDataList)
                            {
                                if (distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(comp_Data.getComp_lat()),Double.parseDouble(comp_Data.getComp_lng()))<25
                                        &&distance(currLatLng.latitude,currLatLng.longitude,Double.parseDouble(comp_Data.getComp_lat()),Double.parseDouble(comp_Data.getComp_lng()))!=0.0)
                                {
                                    NearbyCompanyDataList.add(comp_Data);
                                    Log.e("user data ",comp_Data.getComp_username());

                                }
                            }
                            if (NearbyCompanyDataList.size()>0)
                            {
                                listener.onCompanyDataSuccess(NearbyCompanyDataList);
                            }else
                            {
                                listener.onCompanyDataSuccess(NearbyCompanyDataList);

                            }
                            break;


                    }


                }
            }

            @Override
            public void onFailure(Call<List<CompanyModel>> call, Throwable t) {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");

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
