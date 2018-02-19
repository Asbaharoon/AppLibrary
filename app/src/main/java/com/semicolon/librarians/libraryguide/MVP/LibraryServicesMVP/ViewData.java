package com.semicolon.librarians.libraryguide.MVP.LibraryServicesMVP;

import com.semicolon.librarians.libraryguide.Models.LibraryServices_Model;

import java.util.List;


public interface ViewData {
    void onLibraryServicesDataSuccess(List<LibraryServices_Model> libraryServicesModelList);
    void onLibraryServicesDataFailed(String error);
    void hideProgressBar();
}
