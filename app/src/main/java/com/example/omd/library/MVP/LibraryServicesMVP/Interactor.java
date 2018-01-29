package com.example.omd.library.MVP.LibraryServicesMVP;

import com.example.omd.library.Models.LibraryServices_Model;

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
