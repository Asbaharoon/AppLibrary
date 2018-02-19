package com.semicolon.librarians.libraryguide.MVP.LibraryTypeMVP;

import com.semicolon.librarians.libraryguide.Models.LibraryType_Model;

import java.util.List;


public interface ViewData {
    void onLibraryTypeDataSuccess(List<LibraryType_Model> libraryTypeModelList);
    void onLibraryTypeDataFailed(String error);
    void hideProgressBar();
}
