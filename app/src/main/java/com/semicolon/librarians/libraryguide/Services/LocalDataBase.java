package com.semicolon.librarians.libraryguide.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;

/**
 * Created by Delta on 01/02/2018.
 */

public class LocalDataBase extends SQLiteOpenHelper {

    private static final int dbVersion                       =1;
    private static final String dbName                       ="LIBRARY";
    private static final String TABLE_USERS                  ="USERS";
    private static final String TABLE_PUBLISHERS             ="PUBLISHERS";
    private static final String TABLE_LIBRARIES              ="LIBRARIES";
    private static final String TABLE_UNIVERSITIES           ="UNIVERSITIES";
    private static final String TABLE_COMPANIES              ="COMPANIES";
    private static final String TABLE_USERS_USER_TYPE        ="USER_TYPE";

    private static final String TABLE_USERS_ID               ="ID";
    private static final String TABLE_USERS_PHOTO            ="PHOTO";
    private static final String TABLE_USERS_NAME             ="NAME";
    private static final String TABLE_USERS_EMAIL            ="EMAIL";
    private static final String TABLE_USERS_PHONE            ="PHONE";
    private static final String TABLE_USERS_COUNTRY          ="COUNTRY";
    private static final String TABLE_USERS_USERNAME         ="USER_NAME";

    private static final String TABLE_PUBLISHERS_ID          ="ID";
    private static final String TABLE_PUBLISHERS_PHOTO       ="PHOTO";
    private static final String TABLE_PUBLISHERS_NAME        ="NAME";
    private static final String TABLE_PUBLISHERS_EMAIL       ="EMAIL";
    private static final String TABLE_PUBLISHERS_PHONE       ="PHONE";
    private static final String TABLE_PUBLISHERS_COUNTRY     ="COUNTRY";
    private static final String TABLE_PUBLISHERS_ADDRESS     ="ADDRESS";
    private static final String TABLE_PUBLISHERS_TOWN        ="TOWN";
    private static final String TABLE_PUBLISHERS_WEBSITE     ="WEBSITE";
    private static final String TABLE_PUBLISHERS_USERNAME    ="USER_NAME";


    private static final String TABLE_LIBRARIES_ID           ="ID";
    private static final String TABLE_LIBRARIES_PHOTO        ="PHOTO";
    private static final String TABLE_LIBRARIES_NAME         ="NAME";
    private static final String TABLE_LIBRARIES_TYPE         ="TYPE";
    private static final String TABLE_LIBRARIES_EMAIL        ="EMAIL";
    private static final String TABLE_LIBRARIES_PHONE        ="PHONE";
    private static final String TABLE_LIBRARIES_COUNTRY      ="COUNTRY";
    private static final String TABLE_LIBRARIES_ADDRESS      ="ADDRESS";
    private static final String TABLE_LIBRARIES_USERNAME     ="USER_NAME";

    private static final String TABLE_UNIVERSITIES_ID        ="ID";
    private static final String TABLE_UNIVERSITIES_PHOTO     ="PHOTO";
    private static final String TABLE_UNIVERSITIES_NAME      ="NAME";
    private static final String TABLE_UNIVERSITIES_EMAIL     ="EMAIL";
    private static final String TABLE_UNIVERSITIES_PHONE     ="PHONE";
    private static final String TABLE_UNIVERSITIES_MAJOR     ="MAJOR";
    private static final String TABLE_UNIVERSITIES_COUNTRY   ="COUNTRY";
    private static final String TABLE_UNIVERSITIES_ADDRESS   ="ADDRESS";
    private static final String TABLE_UNIVERSITIES_WEBSITE   ="WEBSITE";
    private static final String TABLE_UNIVERSITIES_USERNAME  ="USER_NAME";

    private static final String TABLE_COMPANIES_ID           ="ID";
    private static final String TABLE_COMPANIES_PHOTO        ="PHOTO";
    private static final String TABLE_COMPANIES_NAME         ="NAME";
    private static final String TABLE_COMPANIES_EMAIL        ="EMAIL";
    private static final String TABLE_COMPANIES_PHONE        ="PHONE";
    private static final String TABLE_COMPANIES_COUNTRY      ="COUNTRY";
    private static final String TABLE_COMPANIES_ADDRESS      ="ADDRESS";
    private static final String TABLE_COMPANIES_TOWN         ="TOWN";
    private static final String TABLE_COMPANIES_WEBSITE      ="WEBSITE";
    private static final String TABLE_COMPANIES_USERNAME     ="USER_NAME";
    private String userType;


    public LocalDataBase(Context context,String userType) {
        super(context, dbName, null, dbVersion);
        this.userType = userType;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        switch (userType)
        {
            case "user":
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_USERS+" ("+TABLE_USERS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TABLE_USERS_USER_TYPE+" TEXT,"+TABLE_USERS_PHOTO+" TEXT, "+TABLE_USERS_NAME+" TEXT, "+TABLE_USERS_EMAIL+" TEXT,"+TABLE_USERS_PHONE+" TEXT,"+TABLE_USERS_COUNTRY+" TEXT, "+TABLE_USERS_USERNAME+" TEXT);");
                break;
            case "publisher":
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_PUBLISHERS+" ("+TABLE_PUBLISHERS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TABLE_USERS_USER_TYPE+" TEXT,"+TABLE_PUBLISHERS_PHOTO+" TEXT, "+TABLE_PUBLISHERS_NAME+" TEXT, "+TABLE_PUBLISHERS_EMAIL+" TEXT,"+TABLE_PUBLISHERS_PHONE+" TEXT,"+TABLE_PUBLISHERS_COUNTRY+" TEXT, "+TABLE_PUBLISHERS_ADDRESS+" TEXT, "+TABLE_PUBLISHERS_TOWN+" TEXT, "+TABLE_PUBLISHERS_WEBSITE+" TEXT, "+TABLE_PUBLISHERS_USERNAME+" TEXT);");

                break;
            case "library":
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_LIBRARIES+" ("+TABLE_LIBRARIES_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TABLE_USERS_USER_TYPE+" TEXT,"+TABLE_LIBRARIES_PHOTO+" TEXT, "+TABLE_LIBRARIES_NAME+" TEXT, "+TABLE_LIBRARIES_TYPE+" TEXT, "+TABLE_LIBRARIES_EMAIL+" TEXT,"+TABLE_LIBRARIES_PHONE+" TEXT,"+TABLE_LIBRARIES_COUNTRY+" TEXT, "+TABLE_LIBRARIES_ADDRESS+" TEXT, "+TABLE_LIBRARIES_USERNAME+" TEXT);");

                break;
            case "university":
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_UNIVERSITIES+" ("+TABLE_UNIVERSITIES_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TABLE_USERS_USER_TYPE+" TEXT,"+TABLE_UNIVERSITIES_PHOTO+" TEXT, "+TABLE_UNIVERSITIES_NAME+" TEXT, "+TABLE_UNIVERSITIES_EMAIL+" TEXT,"+TABLE_UNIVERSITIES_PHONE+" TEXT, "+TABLE_UNIVERSITIES_MAJOR+" TEXT,"+TABLE_UNIVERSITIES_COUNTRY+" TEXT, "+TABLE_UNIVERSITIES_ADDRESS+" TEXT, "+TABLE_UNIVERSITIES_WEBSITE+" TEXT, "+TABLE_UNIVERSITIES_USERNAME+" TEXT);");

                break;
            case "company":
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_COMPANIES+" ("+TABLE_COMPANIES_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TABLE_USERS_USER_TYPE+" TEXT,"+TABLE_COMPANIES_PHOTO+" TEXT, "+TABLE_COMPANIES_NAME+" TEXT, "+TABLE_COMPANIES_EMAIL+" TEXT,"+TABLE_COMPANIES_PHONE+" TEXT,"+TABLE_COMPANIES_COUNTRY+" TEXT, "+TABLE_COMPANIES_ADDRESS+" TEXT, "+TABLE_COMPANIES_TOWN+" TEXT, "+TABLE_COMPANIES_WEBSITE+" TEXT, "+TABLE_COMPANIES_USERNAME+" TEXT);");

                break;
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

        switch (userType)
        {
            case "user":
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
                onCreate(sqLiteDatabase);
                break;
            case "publisher":
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_PUBLISHERS);
                onCreate(sqLiteDatabase);

                break;
            case "library":
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_LIBRARIES);
                onCreate(sqLiteDatabase);

                break;
            case "university":
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_UNIVERSITIES);
                onCreate(sqLiteDatabase);

                break;
            case "company":
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_COMPANIES);
                onCreate(sqLiteDatabase);

                break;
        }
    }
    public boolean InsertUserData(NormalUserData normalUserData)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_USERS_USER_TYPE,normalUserData.getUserType());
        values.put(TABLE_USERS_PHOTO,normalUserData.getUserPhoto());
        values.put(TABLE_USERS_NAME,normalUserData.getUserName());
        values.put(TABLE_USERS_EMAIL,normalUserData.getUserEmail());
        values.put(TABLE_USERS_PHONE,normalUserData.getUserPhone());
        values.put(TABLE_USERS_COUNTRY,normalUserData.getUserCountry());
        values.put(TABLE_USERS_USERNAME,normalUserData.getUserId());
        long result = sqLiteDatabase.insert(TABLE_USERS,null,values);
        if (result==-1)
        {
            return false;
        }else
            {
                return true;
            }



    }
    public boolean InsertPublisherData(PublisherModel publisherModel)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_USERS_USER_TYPE,publisherModel.getUser_type());
        values.put(TABLE_PUBLISHERS_PHOTO,"");
        values.put(TABLE_PUBLISHERS_NAME,publisherModel.getPub_name());
        values.put(TABLE_PUBLISHERS_EMAIL,publisherModel.getPub_email());
        values.put(TABLE_PUBLISHERS_PHONE,publisherModel.getPub_phone());
        values.put(TABLE_PUBLISHERS_COUNTRY,publisherModel.getPub_country());
        values.put(TABLE_PUBLISHERS_ADDRESS,publisherModel.getPub_address());
        values.put(TABLE_PUBLISHERS_TOWN,publisherModel.getPub_town());
        values.put(TABLE_PUBLISHERS_WEBSITE,publisherModel.getPub_website());
        values.put(TABLE_PUBLISHERS_USERNAME,publisherModel.getPub_username());
        long result = sqLiteDatabase.insert(TABLE_USERS,null,values);
        if (result==-1)
        {
            return false;
        }else
        {
            return true;
        }

    }
    public boolean InsertLibraryData(LibraryModel libraryModel)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_USERS_USER_TYPE,libraryModel.getUser_type());
        values.put(TABLE_LIBRARIES_PHOTO,"");
        values.put(TABLE_LIBRARIES_NAME,libraryModel.getLib_name());
        values.put(TABLE_LIBRARIES_TYPE,libraryModel.getLib_type());
        values.put(TABLE_LIBRARIES_EMAIL,libraryModel.getLib_email());
        values.put(TABLE_LIBRARIES_PHONE,libraryModel.getLib_phone());
        values.put(TABLE_LIBRARIES_COUNTRY,libraryModel.getLib_country());
        values.put(TABLE_LIBRARIES_ADDRESS,libraryModel.getLib_address());
        values.put(TABLE_PUBLISHERS_USERNAME,libraryModel.getLib_username());
        long result = sqLiteDatabase.insert(TABLE_USERS,null,values);
        if (result==-1)
        {
            return false;
        }else
        {
            return true;
        }


    }
    public boolean InsertUniversityData(UniversityModel universityModel)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_USERS_USER_TYPE,universityModel.getUser_type());
        values.put(TABLE_UNIVERSITIES_PHOTO,"");
        values.put(TABLE_UNIVERSITIES_NAME,universityModel.getUni_name());
        values.put(TABLE_UNIVERSITIES_EMAIL,universityModel.getUni_email());
        values.put(TABLE_UNIVERSITIES_PHONE,universityModel.getUni_phone());
        values.put(TABLE_UNIVERSITIES_MAJOR,universityModel.getUni_major());
        values.put(TABLE_UNIVERSITIES_COUNTRY,universityModel.getUni_country());
        values.put(TABLE_UNIVERSITIES_ADDRESS,universityModel.getUni_address());
        values.put(TABLE_UNIVERSITIES_WEBSITE,universityModel.getUni_site());
        values.put(TABLE_PUBLISHERS_USERNAME,universityModel.getUni_username());
        long result = sqLiteDatabase.insert(TABLE_USERS,null,values);
        if (result==-1)
        {
            return false;
        }else
        {
            return true;
        }

    }
    public boolean InsertCompanyData(CompanyModel companyModel)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_USERS_USER_TYPE,userType);
        values.put(TABLE_COMPANIES_PHOTO,"");
        values.put(TABLE_COMPANIES_NAME,companyModel.getComp_name());
        values.put(TABLE_COMPANIES_EMAIL,companyModel.getComp_email());
        values.put(TABLE_COMPANIES_PHONE,companyModel.getComp_phone());
        values.put(TABLE_COMPANIES_COUNTRY,companyModel.getComp_country());
        values.put(TABLE_COMPANIES_ADDRESS,companyModel.getComp_address());
        values.put(TABLE_COMPANIES_TOWN,companyModel.getComp_town());
        values.put(TABLE_COMPANIES_WEBSITE,companyModel.getComp_site());
        values.put(TABLE_COMPANIES_USERNAME,companyModel.getComp_username());
        long result = sqLiteDatabase.insert(TABLE_USERS,null,values);
        if (result==-1)
        {
            return false;
        }else
        {
            return true;
        }


    }


   ////////////////////////////////////////////////////////////////////////

    public boolean updateUserData(NormalUserData normalUserData)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_USERS_USER_TYPE,normalUserData.getUserType());
        values.put(TABLE_USERS_PHOTO,normalUserData.getUserPhoto());
        values.put(TABLE_USERS_NAME,normalUserData.getUserName());
        values.put(TABLE_USERS_EMAIL,normalUserData.getUserEmail());
        values.put(TABLE_USERS_PHONE,normalUserData.getUserPhone());
        values.put(TABLE_USERS_COUNTRY,normalUserData.getUserCountry());
        values.put(TABLE_USERS_USERNAME,normalUserData.getUserId());
        int update = sqLiteDatabase.update(TABLE_USERS, values, TABLE_USERS_USERNAME + " = ?", new String[]{normalUserData.getUserId()});
        if (update>0)
        {
         return true;
        }else
            {
                return false;

            }
    }
    public boolean updatePublisherData(PublisherModel publisherModel)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_USERS_USER_TYPE,publisherModel.getUser_type());
        values.put(TABLE_PUBLISHERS_PHOTO,"");
        values.put(TABLE_PUBLISHERS_NAME,publisherModel.getPub_name());
        values.put(TABLE_PUBLISHERS_EMAIL,publisherModel.getPub_email());
        values.put(TABLE_PUBLISHERS_PHONE,publisherModel.getPub_phone());
        values.put(TABLE_PUBLISHERS_COUNTRY,publisherModel.getPub_country());
        values.put(TABLE_PUBLISHERS_ADDRESS,publisherModel.getPub_address());
        values.put(TABLE_PUBLISHERS_TOWN,publisherModel.getPub_town());
        values.put(TABLE_PUBLISHERS_WEBSITE,publisherModel.getPub_website());
        values.put(TABLE_PUBLISHERS_USERNAME,publisherModel.getPub_username());

        int update = sqLiteDatabase.update(TABLE_PUBLISHERS, values, TABLE_PUBLISHERS_USERNAME + " = ?", new String[]{publisherModel.getPub_username()});
        if (update>0)
        {
            return true;
        }else

            {
                return false;
            }


    }
    public boolean updateLibraryData(LibraryModel libraryModel)
    {
        ContentValues values = new ContentValues();
        values.put(TABLE_USERS_USER_TYPE,libraryModel.getUser_type());
        values.put(TABLE_LIBRARIES_PHOTO,"");
        values.put(TABLE_LIBRARIES_NAME,libraryModel.getLib_name());
        values.put(TABLE_LIBRARIES_TYPE,libraryModel.getLib_type());
        values.put(TABLE_LIBRARIES_EMAIL,libraryModel.getLib_email());
        values.put(TABLE_LIBRARIES_PHONE,libraryModel.getLib_phone());
        values.put(TABLE_LIBRARIES_COUNTRY,libraryModel.getLib_country());
        values.put(TABLE_LIBRARIES_ADDRESS,libraryModel.getLib_address());
        values.put(TABLE_PUBLISHERS_USERNAME,libraryModel.getLib_username());

        return true;
    }
    public boolean updateUniversityData(UniversityModel universityModel)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_USERS_USER_TYPE,universityModel.getUser_type());
        values.put(TABLE_UNIVERSITIES_PHOTO,"");
        values.put(TABLE_UNIVERSITIES_NAME,universityModel.getUni_name());
        values.put(TABLE_UNIVERSITIES_EMAIL,universityModel.getUni_email());
        values.put(TABLE_UNIVERSITIES_PHONE,universityModel.getUni_phone());
        values.put(TABLE_UNIVERSITIES_MAJOR,universityModel.getUni_major());
        values.put(TABLE_UNIVERSITIES_COUNTRY,universityModel.getUni_country());
        values.put(TABLE_UNIVERSITIES_ADDRESS,universityModel.getUni_address());
        values.put(TABLE_UNIVERSITIES_WEBSITE,universityModel.getUni_site());
        values.put(TABLE_PUBLISHERS_USERNAME,universityModel.getUni_username());

        int update = sqLiteDatabase.update(TABLE_UNIVERSITIES, values, TABLE_UNIVERSITIES_USERNAME + " = ?", new String[]{universityModel.getUni_username()});
        if (update>0)
        {
            return true;
        }else
            {
                return false;
            }
    }
    public boolean updateCompanyData(CompanyModel companyModel)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_USERS_USER_TYPE,userType);
        values.put(TABLE_COMPANIES_PHOTO,"");
        values.put(TABLE_COMPANIES_NAME,companyModel.getComp_name());
        values.put(TABLE_COMPANIES_EMAIL,companyModel.getComp_email());
        values.put(TABLE_COMPANIES_PHONE,companyModel.getComp_phone());
        values.put(TABLE_COMPANIES_COUNTRY,companyModel.getComp_country());
        values.put(TABLE_COMPANIES_ADDRESS,companyModel.getComp_address());
        values.put(TABLE_COMPANIES_TOWN,companyModel.getComp_town());
        values.put(TABLE_COMPANIES_WEBSITE,companyModel.getComp_site());
        values.put(TABLE_COMPANIES_USERNAME,companyModel.getComp_username());

        int update = sqLiteDatabase.update(TABLE_COMPANIES, values, TABLE_COMPANIES_USERNAME + " = ?", new String[]{companyModel.getComp_username()});
        if (update>0)
        {
            return true;
        }else
            {
                return false;
            }

    }


    ////////////////////////////////////////////////////////////////////////

    public boolean deleteUserData(String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int delete = sqLiteDatabase.delete(TABLE_USERS, TABLE_USERS_USERNAME + " = ?", new String[]{id});
        if (delete>0)
        {
            return true;
        }
        else
            {
                return false;
            }
    }
    public boolean deletePublisherData(String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int delete = sqLiteDatabase.delete(TABLE_PUBLISHERS, TABLE_PUBLISHERS_USERNAME + " = ?", new String[]{id});
        if (delete>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean deleteLibraryData(String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int delete = sqLiteDatabase.delete(TABLE_LIBRARIES, TABLE_LIBRARIES_USERNAME + " = ?", new String[]{id});
        if (delete>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean deleteUniversityData(String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int delete = sqLiteDatabase.delete(TABLE_UNIVERSITIES, TABLE_UNIVERSITIES_USERNAME + " = ?", new String[]{id});
        if (delete>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean deleteCompanyData(String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int delete = sqLiteDatabase.delete(TABLE_COMPANIES, TABLE_COMPANIES_USERNAME + " = ?", new String[]{id});
        if (delete>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    ////////////////////////////////////////////////////////////////////////

    public NormalUserData DisplayUserData()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_USERS;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        NormalUserData userData = new NormalUserData();
        if (cursor.moveToFirst())
        {

            while (cursor.moveToNext())
            {
                userData.setUserType(cursor.getString(cursor.getColumnIndex(TABLE_USERS_USER_TYPE)));
                userData.setUserName(cursor.getString(cursor.getColumnIndex(TABLE_USERS_NAME)));
                userData.setUserEmail(cursor.getString(cursor.getColumnIndex(TABLE_USERS_EMAIL)));
                userData.setUserPhone(cursor.getString(cursor.getColumnIndex(TABLE_USERS_PHONE)));
                userData.setUserPhoto(cursor.getString(cursor.getColumnIndex(TABLE_USERS_PHOTO)));
                userData.setUserId(cursor.getString(cursor.getColumnIndex(TABLE_USERS_USERNAME)));
            }
        }
        return userData;
    }
    public PublisherModel DisplayPublisherData()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        PublisherModel publisherModel = new PublisherModel();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_PUBLISHERS,null);
        if (cursor.moveToFirst())
        {
            while (cursor.moveToNext())
            {
                publisherModel.setUser_type(cursor.getString(cursor.getColumnIndex(TABLE_USERS_USER_TYPE)));
                //publisherModel.sp
                publisherModel.setPub_name(cursor.getString(cursor.getColumnIndex(TABLE_PUBLISHERS_NAME)));
                publisherModel.setPub_email(cursor.getString(cursor.getColumnIndex(TABLE_PUBLISHERS_EMAIL)));
                publisherModel.setPub_phone(cursor.getString(cursor.getColumnIndex(TABLE_PUBLISHERS_PHONE)));
                publisherModel.setPub_country(cursor.getString(cursor.getColumnIndex(TABLE_PUBLISHERS_COUNTRY)));
                publisherModel.setPub_address(cursor.getString(cursor.getColumnIndex(TABLE_PUBLISHERS_ADDRESS)));
                publisherModel.setPub_town(cursor.getString(cursor.getColumnIndex(TABLE_PUBLISHERS_TOWN)));
                publisherModel.setPub_website(cursor.getString(cursor.getColumnIndex(TABLE_PUBLISHERS_WEBSITE)));
                publisherModel.setPub_username(cursor.getString(cursor.getColumnIndex(TABLE_PUBLISHERS_USERNAME)));

            }
        }

        return publisherModel;
    }
    public LibraryModel DisplayLibraryData()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        LibraryModel libraryModel = new LibraryModel();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_LIBRARIES,null);
        if (cursor.moveToFirst())
        {
            while (cursor.moveToNext())
            {
                libraryModel.setUser_type(cursor.getString(cursor.getColumnIndex(TABLE_USERS_USER_TYPE)));
                //libraryModel.setLib_phone(cursor.getString(cursor.getColumnIndex(TABLE_LIBRARIES_PHOTO)));
                libraryModel.setLib_name(cursor.getString(cursor.getColumnIndex(TABLE_LIBRARIES_NAME)));
                libraryModel.setLib_type(cursor.getString(cursor.getColumnIndex(TABLE_LIBRARIES_TYPE)));
                libraryModel.setLib_email(cursor.getString(cursor.getColumnIndex(TABLE_LIBRARIES_EMAIL)));
                libraryModel.setLib_phone(cursor.getString(cursor.getColumnIndex(TABLE_LIBRARIES_PHONE)));
                libraryModel.setLib_country(cursor.getString(cursor.getColumnIndex(TABLE_LIBRARIES_COUNTRY)));
                libraryModel.setLib_address(cursor.getString(cursor.getColumnIndex(TABLE_LIBRARIES_ADDRESS)));
                libraryModel.setLib_username(cursor.getString(cursor.getColumnIndex(TABLE_LIBRARIES_USERNAME)));


            }
        }
        return libraryModel;
    }
    public UniversityModel DisplayUniversityData()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        UniversityModel universityModel = new UniversityModel();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_UNIVERSITIES,null);
        if (cursor.moveToFirst())
        {
            while (cursor.moveToNext())
            {
                universityModel.setUser_type(cursor.getString(cursor.getColumnIndex(TABLE_USERS_USER_TYPE)));
                //universityModel.setUni_phone(cursor.getString(cursor.getColumnIndex(TABLE_UNIVERSITIES_PHOTO)));
                universityModel.setUni_name(cursor.getString(cursor.getColumnIndex(TABLE_UNIVERSITIES_NAME)));
                universityModel.setUni_email(cursor.getString(cursor.getColumnIndex(TABLE_UNIVERSITIES_EMAIL)));
                universityModel.setUni_country(cursor.getString(cursor.getColumnIndex(TABLE_UNIVERSITIES_COUNTRY)));
                universityModel.setUni_address(cursor.getString(cursor.getColumnIndex(TABLE_UNIVERSITIES_ADDRESS)));
                universityModel.setUni_phone(cursor.getString(cursor.getColumnIndex(TABLE_UNIVERSITIES_PHONE)));
                universityModel.setUni_major(cursor.getString(cursor.getColumnIndex(TABLE_UNIVERSITIES_MAJOR)));
                universityModel.setUni_site(cursor.getString(cursor.getColumnIndex(TABLE_UNIVERSITIES_WEBSITE)));
                universityModel.setUni_username(cursor.getString(cursor.getColumnIndex(TABLE_UNIVERSITIES_USERNAME)));

            }
        }
        return universityModel;

    }
    public CompanyModel DisplayCompanyData()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        CompanyModel companyModel = new CompanyModel();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_COMPANIES,null);

        if (cursor.moveToFirst())
        {
            while (cursor.moveToNext())
            {
                companyModel.setUser_type(cursor.getString(cursor.getColumnIndex(TABLE_USERS_USER_TYPE)));
                companyModel.setComp_name(cursor.getString(cursor.getColumnIndex(TABLE_COMPANIES_NAME)));
                companyModel.setComp_email(cursor.getString(cursor.getColumnIndex(TABLE_COMPANIES_EMAIL)));
                companyModel.setComp_phone(cursor.getString(cursor.getColumnIndex(TABLE_COMPANIES_PHONE)));
                companyModel.setComp_country(cursor.getString(cursor.getColumnIndex(TABLE_COMPANIES_COUNTRY)));
                companyModel.setComp_address(cursor.getString(cursor.getColumnIndex(TABLE_COMPANIES_ADDRESS)));
                companyModel.setComp_town(cursor.getString(cursor.getColumnIndex(TABLE_COMPANIES_TOWN)));
                companyModel.setComp_site(cursor.getString(cursor.getColumnIndex(TABLE_COMPANIES_WEBSITE)));
                companyModel.setComp_username(cursor.getString(cursor.getColumnIndex(TABLE_COMPANIES_USERNAME)));
            }
        }
        return companyModel;
    }
}
