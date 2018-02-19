package com.semicolon.librarians.libraryguide.MVP.Search_Library_MVP;


import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.LibraryModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Interactor {
    interface onCompleteListener
    {
        void onLibraryDataSuccess(List<LibraryModel> libraryModelList);
        void onLibraryDataFailed(String error);
        void hideProgress();



    }
    void getLibraryData(String lib_name, String lib_type, String country_id, Context context, onCompleteListener listener);
}
