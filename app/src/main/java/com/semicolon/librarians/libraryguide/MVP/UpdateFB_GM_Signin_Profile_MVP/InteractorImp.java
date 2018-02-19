package com.semicolon.librarians.libraryguide.MVP.UpdateFB_GM_Signin_Profile_MVP;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Service;
import com.semicolon.librarians.libraryguide.Services.Tags;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Delta on 22/01/2018.
 */

public class InteractorImp implements Interactor {




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

    @Override
    public void UpdateUserData(String user_photo, String user_userName, String user_name, String user_email, String user_phone, String user_country, Context context, onCompleteListener listener) {
        if (TextUtils.isEmpty(user_email)){
            listener.setNormalUserEmail_Error();
        }
        else if (!user_email.matches(Tags.email_Regex))
        {
            listener.setNormalUser_invalidEmail_Error();
        }else if (TextUtils.isEmpty(user_phone))
        {
            listener.setNormalUserPhone_Error();
        }
        else if (TextUtils.isEmpty(user_country))
        {
            listener.setNormalUserCountry_Error();
        }
        else
            {
                UpdataData(user_photo,user_userName,user_name,user_email,user_phone,user_country,context,listener);
            }
    }

    private void UpdataData(String user_photo, String user_userName, String user_name, String user_email, String user_phone, String user_country, final Context context, final onCompleteListener listener) {

        listener.showProgressDialog();
        Map<String,String> map = new HashMap<>();
        map.put("user_photo",user_photo);
        map.put("user_username",user_userName);
        map.put("user_name",user_name);
        map.put("user_email",user_email);
        map.put("user_phone",user_phone);
        map.put("user_country",user_country);
        Retrofit retrofit = setUpRetrofit();

        Service service = retrofit.create(Service.class);
        Call<NormalUserData> call = service.Update_FB_GM_UserData(map);
        call.enqueue(new Callback<NormalUserData>() {
            @Override
            public void onResponse(Call<NormalUserData> call, Response<NormalUserData> response) {
                if (response.isSuccessful())
                {
                    NormalUserData userData = response.body();
                    listener.onNormalUserDataSuccess(userData);

                }else
                    {
                        listener.onFailed(context.getString(R.string.something_haywire));
                    }
            }

            @Override
            public void onFailure(Call<NormalUserData> call, Throwable t) {

                listener.onFailed(context.getString(R.string.something_haywire));
                Log.e("error",t.getMessage());


            }
        });
    }
}
