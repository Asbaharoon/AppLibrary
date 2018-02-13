package com.semicolon.librarians.library.MVP.DisplayRoomsById_MVP;


import android.content.Context;
import android.util.Log;

import com.semicolon.librarians.library.Models.CommonUsersData;
import com.semicolon.librarians.library.Services.NetworkConnection;
import com.semicolon.librarians.library.Services.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InteractorImp implements Interactor {

    static Context context;
   private Retrofit setUpRetrofit(final Context context)
   {
        Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("http://librarians.liboasis.com/")
               .addConverterFactory(GsonConverterFactory.create())
               .client(provideOkHttp()).build();
       return retrofit;
   }


    @Override
    public void DisplayAllRooms(String currUserId, Context context, final onCompleteListener listener) {
       InteractorImp.context =context;
        Retrofit retrofit = setUpRetrofit(context);
        Service service = retrofit.create(Service.class);
        Call<List<CommonUsersData>> call = service.DisplayAllRooms(currUserId);
        call.enqueue(new Callback<List<CommonUsersData>>() {
            @Override
            public void onResponse(Call<List<CommonUsersData>> call, Response<List<CommonUsersData>> response) {
                if (response.isSuccessful())
                {
                    List<CommonUsersData> commonUsersDataList = response.body();
                    listener.onChatRoomUserSuccess(commonUsersDataList);
                    Log.e("rooms",""+commonUsersDataList.size());
                }else
                    {
                        listener.onFailed("Error something went haywire");
                    }
            }

            @Override
            public void onFailure(Call<List<CommonUsersData>> call, Throwable t) {
                listener.onFailed("Error something went haywire");
                Log.e("display all rooms",t.getMessage());

            }
        });
    }
    public static Interceptor provideCacheInterceptor ()
    {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                okhttp3.Response response = chain.proceed(chain.request());
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(2,TimeUnit.MINUTES)
                        .build();
                return response.newBuilder().header("Cache-Control",cacheControl.toString()).build();
            }
        };
    }

    public static Interceptor provideOfflineCache()
    {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (new NetworkConnection(context).CheckConnection()==true)
                {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7,TimeUnit.DAYS)
                            .build();
                    request = request.newBuilder().cacheControl(cacheControl).build();

                }
                return chain.proceed(request);
            }
        };
    }
    public static OkHttpClient provideOkHttp()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(provideOfflineCache())
                .addNetworkInterceptor(provideCacheInterceptor())
                .cache(provideCache())
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();

    }
    public static Cache provideCache()
    {
        long cacheSize = 10*1024*1024;
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(),"http_Cache"),cacheSize);

        }catch (Exception e)
        {

        }
        return cache;
    }

}
