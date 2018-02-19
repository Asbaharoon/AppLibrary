package com.semicolon.librarians.libraryguide.MVP.LibraryData_MVP;


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
        void onFailed(String error);
        void hideProgressBar();

    }
    void getLibraryData(String pub_userName, Context context,onCompleteListener listener);
}
