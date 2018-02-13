package com.semicolon.librarians.library.MVP.CompanyMVP;


import android.text.TextUtils;
import android.util.Log;

import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Services.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InteractorImp implements Interactor {


    @Override
    public void getCompanyData(final String userName, final onCompleteListener listener) {
        Log.e("zzzzzz","response company");

        Retrofit retrofit = setUpRetrofit();
        Service service = retrofit.create(Service.class);
        Call<List<CompanyModel>> call = service.getCompanyData();
        call.enqueue(new Callback<List<CompanyModel>>() {
            @Override
            public void onResponse(Call<List<CompanyModel>> call, Response<List<CompanyModel>> response) {
                if (response.isSuccessful())
                {
                    Log.e("fffff","response company");

                     if (TextUtils.isEmpty(userName))
                    {
                        List<CompanyModel> companyModelList = response.body();
                        if (companyModelList.size()>0)
                        {
                            listener.onCompanyDataSuccess(companyModelList);
                            listener.hideProgressBar();
                        }else
                        {
                            listener.hideProgressBar();
                        }
                    }else
                        {
                            List<CompanyModel> companyModelList = response.body();
                            List<CompanyModel> companyModelList2=new ArrayList<>();
                            if (companyModelList.size()>0)
                            {
                                for (CompanyModel companyModel :companyModelList)
                                {
                                    if (!companyModel.getComp_username().equals(userName))
                                    {
                                        companyModelList2.add(companyModel);
                                    }
                                }
                                if (companyModelList2.size()>0)
                                {
                                    listener.onCompanyDataSuccess(companyModelList2);
                                    listener.hideProgressBar();
                                }else
                                    {
                                        listener.onCompanyDataSuccess(companyModelList2);
                                        listener.hideProgressBar();
                                    }

                            }else
                            {
                                listener.hideProgressBar();
                            }
                        }

                }
            }

            @Override
            public void onFailure(Call<List<CompanyModel>> call, Throwable t) {
                listener.onCompanyDataFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
                listener.hideProgressBar();
            }
        });
    }

   private Retrofit setUpRetrofit()
   {
       OkHttpClient client = new OkHttpClient.Builder()
               .connectTimeout(1, TimeUnit.MINUTES)
               .writeTimeout(20,TimeUnit.SECONDS)
               .readTimeout(20,TimeUnit.SECONDS).build();
       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("http://librarians.liboasis.com/")
               .addConverterFactory(GsonConverterFactory.create())
               .client(client).build();
       return retrofit;
   }
}
