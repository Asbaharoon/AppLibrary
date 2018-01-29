package com.example.omd.library.MVP.LibraryTypeMVP;

import com.example.omd.library.Models.LibraryType_Model;

import java.util.List;


public interface Interactor {
    interface onCompleteListener
    {
        void onLibraryTypeDataSuccess(List<LibraryType_Model> libraryTypeModelList);
        void onLibraryTypeDataFailed(String error);
        void hideProgressBar();
    }
    void getLibraryTypeData(onCompleteListener listener);
}
