package com.semicolon.librarians.library.MVP.LibraryServicesMVP;

import com.semicolon.librarians.library.Models.LibraryServices_Model;

import java.util.List;


public interface Interactor {
    interface onCompleteListener
    {
        void onLibraryServicesDataSuccess(List<LibraryServices_Model> libraryServicesModelList);
        void onLibraryServicesDataFailed(String error);
        void hideProgressBar();
    }
    void getLibraryServicesData(onCompleteListener listener);
}
