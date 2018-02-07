package com.semicolon.librarians.library.MVP.LibraryTypeMVP;

import com.semicolon.librarians.library.Models.LibraryType_Model;

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
