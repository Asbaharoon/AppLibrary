package com.example.omd.library.MVP.LibraryTypeMVP;

import com.example.omd.library.Models.LibraryType_Model;

import java.util.List;


public interface ViewData {
    void onLibraryTypeDataSuccess(List<LibraryType_Model> libraryTypeModelList);
    void onLibraryTypeDataFailed(String error);
    void hideProgressBar();
}
