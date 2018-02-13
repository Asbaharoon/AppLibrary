package com.semicolon.librarians.library.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;

/**
 * Created by Delta on 08/02/2018.
 */

public class Preferences {
    Context context;

    public Preferences(Context context) {
        this.context = context;


    }
    public void Create_SharedPreference_User(NormalUserData userData)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_pref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        editor.putString("type",userData.getUserType());
        editor.putString("id",userData.getUserId());
        editor.putString("name",userData.getUserName());
        editor.putString("email",userData.getUserEmail());
        editor.putString("phone",userData.getUserPhone());
        editor.putString("country",userData.getUserCountry());

        if (userData.getUserPhoto()==null)
        {
            editor.putString("photo",userData.getUser_photo());
            editor.putString("photo_link","null");

        }else
            {
                editor.putString("photo",userData.getUser_photo());
                editor.putString("photo_link",userData.getUserPhoto());

            }
        editor.apply();

        Log.e("ID",userData.getUserId()+"\n"+"Photo"+userData.getUser_photo()+"\n"+"link"+userData.getUserPhoto());

    }
    public void Create_SharedPreference_Publisher(PublisherModel publisherModel)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pub_pref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("type",publisherModel.getUser_type());
        editor.putString("id",publisherModel.getPub_username());
        editor.putString("name",publisherModel.getPub_name());
        editor.putString("email",publisherModel.getPub_email());
        editor.putString("phone",publisherModel.getPub_phone());
        editor.putString("country",publisherModel.getPub_country());
        editor.putString("address",publisherModel.getPub_address());
        editor.putString("town",publisherModel.getPub_town());
        editor.putString("site",publisherModel.getPub_website());
        editor.putString("photo",publisherModel.getUser_photo());
        editor.apply();
        Log.e("ID",publisherModel.getPub_username()+"\n"+"Photo"+publisherModel.getUser_photo());

    }
    public void Create_SharedPreference_Library(LibraryModel libraryModel)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("lib_pref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("type",libraryModel.getUser_type());
        editor.putString("id",libraryModel.getLib_username());
        editor.putString("name",libraryModel.getLib_name());
        editor.putString("libType",libraryModel.getLib_type());
        editor.putString("email",libraryModel.getLib_email());
        editor.putString("phone",libraryModel.getLib_phone());
        editor.putString("country",libraryModel.getLib_country());
        editor.putString("address",libraryModel.getLib_address());
        editor.putString("photo",libraryModel.getUser_photo());
        editor.apply();
        Log.e("ID",libraryModel.getLib_username()+"\n"+"Photo"+libraryModel.getUser_photo());

    }
    public void Create_SharedPreference_University(UniversityModel universityModel)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("uni_pref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("type",universityModel.getUser_type());
        editor.putString("id",universityModel.getUni_username());
        editor.putString("name",universityModel.getUni_name());
        editor.putString("email",universityModel.getUni_email());
        editor.putString("phone",universityModel.getUni_phone());
        editor.putString("country",universityModel.getUni_country());
        editor.putString("address",universityModel.getUni_address());
        editor.putString("major",universityModel.getUni_major());
        editor.putString("site",universityModel.getUni_site());
        editor.putString("photo",universityModel.getUser_photo());
        editor.apply();
        Log.e("ID",universityModel.getUni_username()+"\n"+"Photo"+universityModel.getUser_photo());

    }
    public void Create_SharedPreference_Company(CompanyModel companyModel)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("comp_pref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("type",companyModel.getUser_type());
        editor.putString("id",companyModel.getComp_username());
        editor.putString("name",companyModel.getComp_name());
        editor.putString("email",companyModel.getComp_email());
        editor.putString("phone",companyModel.getComp_phone());
        editor.putString("country",companyModel.getComp_country());
        editor.putString("address",companyModel.getComp_address());
        editor.putString("town",companyModel.getComp_town());
        editor.putString("site",companyModel.getComp_site());
        editor.putString("photo",companyModel.getUser_photo());
        editor.apply();
        Log.e("ID",companyModel.getComp_username()+"\n"+"Photo"+companyModel.getUser_photo());

    }


    public void Clear_SharedPrefrence(String userType)
    {
        switch (userType)
        {
            case "user":
                SharedPreferences sharedPreferences_user = context.getSharedPreferences("user_pref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_user = sharedPreferences_user.edit();
                editor_user.clear();
                editor_user.commit();
                break;
            case "publisher":
                SharedPreferences sharedPreferences_pub = context.getSharedPreferences("pub_pref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_pub = sharedPreferences_pub.edit();
                editor_pub.clear();
                editor_pub.commit();
                break;
            case "library":
                SharedPreferences sharedPreferences_lib = context.getSharedPreferences("lib_pref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_lib = sharedPreferences_lib.edit();
                editor_lib.clear();
                editor_lib.commit();
                break;
            case "university":
                SharedPreferences sharedPreferences_uni = context.getSharedPreferences("uni_pref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_uni = sharedPreferences_uni.edit();
                editor_uni.clear();
                editor_uni.commit();
                break;
            case "company":
                SharedPreferences sharedPreferences_comp = context.getSharedPreferences("comp_pref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_comp = sharedPreferences_comp.edit();
                editor_comp.clear();
                editor_comp.commit();
                break;
        }


    }

    public void LoggedUserPref(String userType,String id)
    {
        SharedPreferences pref = context.getSharedPreferences("userType",context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userType",userType);
        editor.putString("id",id);
        editor.apply();
        Log.e("usertype",userType);
    }
    public void Session(String session)
    {
        SharedPreferences pref = context.getSharedPreferences("session",context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("session",session);
        editor.apply();
        editor.commit();


        Log.e("session",pref.getString("session",""));
    }
    public String getSession()
    {
        SharedPreferences pref = context.getSharedPreferences("session",context.MODE_PRIVATE);

        String session = pref.getString("session","");

        Log.e("ss",session);
        return session;
    }

}
