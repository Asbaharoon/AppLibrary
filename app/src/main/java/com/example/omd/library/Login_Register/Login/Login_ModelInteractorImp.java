package com.example.omd.library.Login_Register.Login;

import android.content.Context;
import android.text.TextUtils;

import com.example.omd.library.Models.CompanyModel;
import com.example.omd.library.Models.LibraryModel;
import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Models.PublisherModel;
import com.example.omd.library.Models.UniversityModel;
import com.example.omd.library.Models.User;
import com.example.omd.library.Services.ErrorUtils;
import com.example.omd.library.Services.NetworkConnection;
import com.example.omd.library.Services.Service;
import com.example.omd.library.Services.Tags;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Delta on 10/01/2018.
 */

public class Login_ModelInteractorImp implements Login_ModelInteractor {

    private boolean isConnected =false;
    @Override
    public void Login(String username, String password, final onCompleteListener listener, Context context) {
        if (TextUtils.isEmpty(username))
        {
            listener.setUserNameError();
        }
        else if (!username.matches(Tags.username_Regex))
        {
            listener.setUserName_invalidError();
        }
        else if (TextUtils.isEmpty(password))
        {
            listener.setPasswordError();
        }
        else if (!password.matches(Tags.pass_Regex))
        {
            listener.setPassword_invalidError();
        }
        else
            {
                isConnected = new NetworkConnection(context).CheckConnection();
                if (isConnected)
                {
                    listener.showProgressDialog();
                    UsersLogin(username,password,listener);

                }else
                    {
                        listener.hideProgressDialog();
                        listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
                    }

            }
    }

    private void UsersLogin(String username, String password, final onCompleteListener listener)
    {
        Map<String,String> loginMap= new HashMap<>();
        loginMap.put("",username);
        loginMap.put("",password);

        final Retrofit retrofit = SetUpRetrofit("");
        Service service = retrofit.create(Service.class);
        Call<JsonObject> call = service.login(loginMap);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful())
                {
                    Gson gson = new Gson();
                    User user = gson.fromJson(response.body(), User.class);

                    if (user!=null)
                    {
                        if (user.getUserType().equals("user"))
                        {
                            Gson u_gson= new Gson();
                            NormalUserData normalUserData = u_gson.fromJson(response.body(),NormalUserData.class);
                            if (normalUserData!=null)
                            {
                                listener.hideProgressDialog();
                                listener.onSuccess_NormalUserData(normalUserData);

                            }

                        }
                        else if (user.getUserType().equals("publisher"))
                        {
                            Gson p_gson= new Gson();
                            PublisherModel publisherModel = p_gson.fromJson(response.body(),PublisherModel.class);

                            if (publisherModel!=null)
                            {
                                listener.hideProgressDialog();
                                listener.onSuccess_PublisherData(publisherModel);
                            }
                        }
                        else if (user.getUserType().equals("library")) {
                            Gson l_gson = new Gson();
                            LibraryModel libraryModel = l_gson.fromJson(response.body(),LibraryModel.class);

                            if (libraryModel!=null)
                            {
                                listener.hideProgressDialog();
                                listener.onSuccess_LibraryData(libraryModel);
                            }

                        }
                        else if (user.getUserType().equals("university")) {
                            Gson uni_gson = new Gson();
                            UniversityModel universityModel = uni_gson.fromJson(response.body(),UniversityModel.class);

                            if (universityModel!=null)
                            {
                                listener.hideProgressDialog();
                                listener.onSuccess_UniversityData(universityModel);
                            }

                        }
                        else if (user.getUserType().equals("company")) {
                            Gson comp_gson = new Gson();
                            CompanyModel companyModel = comp_gson.fromJson(response.body(),CompanyModel.class);

                            if (companyModel!=null)
                            {
                                listener.hideProgressDialog();
                                listener.onSuccess_CompanyData(companyModel);
                            }

                        }


                    }
                }else
                    {
                        listener.hideProgressDialog();
                        Converter<ResponseBody,ErrorUtils> converter =retrofit.responseBodyConverter(ErrorUtils.class,new Annotation[0]);
                        try {
                            ErrorUtils errorUtils = converter.convert(response.errorBody());
                            listener.onFailed(errorUtils.getErrorMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
            }
        });

    }
    private Retrofit SetUpRetrofit(String baseUrl)
    {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
