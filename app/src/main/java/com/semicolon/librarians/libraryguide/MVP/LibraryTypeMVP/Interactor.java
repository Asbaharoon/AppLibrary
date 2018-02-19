package com.semicolon.librarians.libraryguide.MVP.LibraryTypeMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.LibraryType_Model;

import java.util.List;


public interface Interactor {
    interface onCompleteListener
    {
        void onLibraryTypeDataSuccess(List<LibraryType_Model> libraryTypeModelList);
        void onLibraryTypeDataFailed(String error);
        void hideProgressBar();
    }
    void getLibraryTypeData(Context context,onCompleteListener listener);
}
