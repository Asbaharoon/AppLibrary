package com.semicolon.librarians.libraryguide.MVP.LibraryServicesMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.LibraryServices_Model;

import java.util.List;


public interface Interactor {
    interface onCompleteListener
    {
        void onLibraryServicesDataSuccess(List<LibraryServices_Model> libraryServicesModelList);
        void onLibraryServicesDataFailed(String error);
        void hideProgressBar();
    }
    void getLibraryServicesData(Context context,onCompleteListener listener);
}
