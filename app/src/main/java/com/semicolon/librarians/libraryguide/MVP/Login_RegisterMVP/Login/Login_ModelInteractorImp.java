package com.semicolon.librarians.libraryguide.MVP.Login_RegisterMVP.Login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.ErrorUtils;
import com.semicolon.librarians.libraryguide.Services.NetworkConnection;
import com.semicolon.librarians.libraryguide.Services.Service;
import com.semicolon.librarians.libraryguide.Services.Tags;

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
    public void Login(String userType,String username, String password, final onCompleteListener listener, Context context) {
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
        }else if (TextUtils.isEmpty(userType)||userType==null)
        {
            listener.showUserTypeDialog();
        }
        else
            {
                isConnected = new NetworkConnection(context).CheckConnection();
                if (isConnected)
                {
                    if (userType.equals("user"))
                    {
                        listener.showProgressDialog();

                        UserLogin(username,password,context,listener);
                        Log.e("u", "Login:user ");
                    }
                    else if (userType.equals("publisher"))
                    {
                        listener.showProgressDialog();

                        PublisherLogin(username,password,context,listener);
                        Log.e("p", "Login:pub ");

                    }
                    else if (userType.equals("library"))
                    {
                        listener.showProgressDialog();

                        LibraryLogin(username,password,context,listener);
                        Log.e("l", "Login:lib ");

                    }
                    else if (userType.equals("university"))
                    {
                        listener.showProgressDialog();

                        UniversityLogin(username,password,context,listener);
                        Log.e("un", "Login:uni ");

                    }
                    else if (userType.toLowerCase().equals("company"))
                    {
                        listener.showProgressDialog();

                        CompanyLogin(username,password,context,listener);
                        Log.e("c", "Login:comp ");

                    }


                }else
                    {
                        listener.hideProgressDialog();
                        listener.onFailed(context.getString(R.string.network_connection));
                    }

            }
    }

    private void UserLogin(String username, String password, final Context context, final onCompleteListener listener)
    {
        Map<String,String> loginMap= new HashMap<>();
        loginMap.put("user_type","user");
        loginMap.put("user_username",username);
        loginMap.put("user_pass",password);

        final Retrofit retrofit = SetUpRetrofit("http://librarians.liboasis.com/");
        Service service = retrofit.create(Service.class);
        Call<NormalUserData> call = service.loginUser(loginMap);
        call.enqueue(new Callback<NormalUserData>() {
            @Override
            public void onResponse(Call<NormalUserData> call, Response<NormalUserData> response) {
                if (response.isSuccessful())
                {
                    NormalUserData userData = response.body();
                    if (userData!=null)
                    {
                        if (userData.getUserType().equals("user"))
                        {
                            listener.onSuccess_NormalUserData(userData);
                            listener.hideProgressDialog();

                        }else
                        {
                            listener.hideProgressDialog();
                            listener.onFailed(context.getString(R.string.no_data_found));
                        }

                    }else
                        {
                            listener.hideProgressDialog();
                            listener.onFailed(context.getString(R.string.nodata_2));

                        }
                     Log.e("u2", "Login:user ");

                   /* Gson gson = new Gson();
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


                    }else
                        {
                            listener.hideProgressDialog();
                        }*/
                }

                else
                    {
                        listener.hideProgressDialog();
                        Converter<ResponseBody,ErrorUtils> converter =retrofit.responseBodyConverter(ErrorUtils.class,new Annotation[0]);
                        try {
                            ErrorUtils errorUtils = converter.convert(response.errorBody());
                            listener.onFailed(errorUtils.getErrorMessage());
                            listener.hideProgressDialog();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }

            @Override
            public void onFailure(Call<NormalUserData> call, Throwable t) {
                listener.hideProgressDialog();
                listener.onFailed(context.getString(R.string.something_haywire));
            }
        });

    }
    private void PublisherLogin(String username, String password, final Context context, final onCompleteListener listener)
    {
        Map<String,String> loginMap= new HashMap<>();
        loginMap.put("user_type","publisher");
        loginMap.put("user_username",username);
        loginMap.put("user_pass",password);

        final Retrofit retrofit = SetUpRetrofit("http://librarians.liboasis.com/");
        Service service = retrofit.create(Service.class);
        Call<PublisherModel> call = service.loginPublisher(loginMap);
        call.enqueue(new Callback<PublisherModel>() {
            @Override
            public void onResponse(Call<PublisherModel> call, Response<PublisherModel> response) {
                if (response.isSuccessful())
                {
                    PublisherModel publisherData = response.body();

                    if (publisherData!=null)
                    {
                        if (publisherData.getUser_type().equals("publisher"))
                        {
                            listener.onSuccess_PublisherData(publisherData);
                            listener.hideProgressDialog();
                        }
                        else
                        {
                            listener.hideProgressDialog();
                            listener.onFailed(context.getString(R.string.no_data_found));
                        }

                    }else
                        {
                            listener.hideProgressDialog();
                            listener.onFailed(context.getString(R.string.nodata_2));

                        }



                }

                else
                {
                    listener.hideProgressDialog();
                    listener.onFailed(context.getString(R.string.nodata_2));

                }
            }

            @Override
            public void onFailure(Call<PublisherModel> call, Throwable t) {
                listener.hideProgressDialog();
                listener.onFailed(context.getString(R.string.something_haywire));
            }
        });

    }
    private void LibraryLogin(String username, String password, final Context context, final onCompleteListener listener)
    {
        Map<String,String> loginMap= new HashMap<>();
        loginMap.put("user_type","library");
        loginMap.put("user_username",username);
        loginMap.put("user_pass",password);


        final Retrofit retrofit = SetUpRetrofit("http://librarians.liboasis.com/");
        Service service = retrofit.create(Service.class);
        Call<LibraryModel> call = service.loginLibrary(loginMap);
        call.enqueue(new Callback<LibraryModel>() {
            @Override
            public void onResponse(Call<LibraryModel> call, Response<LibraryModel> response) {
                if (response.isSuccessful())
                {
                    LibraryModel libraryData = response.body();
                    if (libraryData!=null)
                    {
                        if (libraryData.getUser_type().equals("library"))
                        {
                            Log.e("lt",libraryData.getType_title()+"0001111");
                            listener.onSuccess_LibraryData(libraryData);
                            listener.hideProgressDialog();
                        }
                        else
                        {
                            listener.hideProgressDialog();
                            listener.onFailed(context.getString(R.string.no_data_found));
                        }

                    }else
                    {
                        listener.hideProgressDialog();
                        listener.onFailed(context.getString(R.string.nodata_2));

                    }

                    Log.e("l2", "Login:lib ");


                }

                else
                {
                    listener.hideProgressDialog();
                    Converter<ResponseBody,ErrorUtils> converter =retrofit.responseBodyConverter(ErrorUtils.class,new Annotation[0]);
                    try {
                        ErrorUtils errorUtils = converter.convert(response.errorBody());
                        listener.onFailed(errorUtils.getErrorMessage());
                        listener.hideProgressDialog();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LibraryModel> call, Throwable t) {
                listener.hideProgressDialog();
                listener.onFailed(context.getString(R.string.something_haywire));
            }
        });

    }
    private void UniversityLogin(String username, String password, final Context context, final onCompleteListener listener)
    {
        Map<String,String> loginMap= new HashMap<>();
        loginMap.put("user_type","university");
        loginMap.put("user_username",username);
        loginMap.put("user_pass",password);

        final Retrofit retrofit = SetUpRetrofit("http://librarians.liboasis.com/");
        Service service = retrofit.create(Service.class);
        Call<UniversityModel> call = service.loginUniversity(loginMap);
        call.enqueue(new Callback<UniversityModel>() {
            @Override
            public void onResponse(Call<UniversityModel> call, Response<UniversityModel> response) {
                if (response.isSuccessful())
                {
                    UniversityModel universityData = response.body();
                    if (universityData!=null)
                    {
                         if (universityData.getUser_type().equals("university"))
                         {
                            listener.onSuccess_UniversityData(universityData);
                            listener.hideProgressDialog();
                         }
                        else
                        {
                            listener.hideProgressDialog();
                            listener.onFailed(context.getString(R.string.no_data_found));
                        }

                    }
                    else
                    {
                        listener.hideProgressDialog();
                        listener.onFailed(context.getString(R.string.nodata_2));

                    }

                    //Log.e("un2", "Login:uni ");


                }

                else
                {
                    listener.hideProgressDialog();
                    listener.onFailed(context.getString(R.string.nodata_2));

                }
            }

            @Override
            public void onFailure(Call<UniversityModel> call, Throwable t) {
                listener.hideProgressDialog();
                listener.onFailed(context.getString(R.string.something_haywire));
            }
        });


    }
    private void CompanyLogin(String username, String password, final Context context, final onCompleteListener listener)
    {
        Map<String,String> loginMap= new HashMap<>();
        loginMap.put("user_type","company");
        loginMap.put("user_username",username);
        loginMap.put("user_pass",password);

        final Retrofit retrofit = SetUpRetrofit("http://librarians.liboasis.com/");
        Service service = retrofit.create(Service.class);
        Call<CompanyModel> call = service.loginCompany(loginMap);
        call.enqueue(new Callback<CompanyModel>() {
            @Override
            public void onResponse(Call<CompanyModel> call, Response<CompanyModel> response) {
                if (response.isSuccessful())
                {
                    CompanyModel CompanyData = response.body();
                    if (CompanyData!=null)
                    {
                        if (CompanyData.getUser_type().equals("company"))
                        {
                            listener.onSuccess_CompanyData(CompanyData);
                            listener.hideProgressDialog();
                        }
                        else
                        {
                            listener.hideProgressDialog();
                            listener.onFailed(context.getString(R.string.no_data_found));
                        }
                    }
                    else
                    {
                        listener.hideProgressDialog();
                        listener.onFailed(context.getString(R.string.nodata_2));

                    }




                }

                else
                {
                    listener.hideProgressDialog();
                    listener.onFailed(context.getString(R.string.nodata_2));

                }
            }

            @Override
            public void onFailure(Call<CompanyModel> call, Throwable t) {
                listener.hideProgressDialog();
                listener.onFailed(context.getString(R.string.something_haywire));
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
