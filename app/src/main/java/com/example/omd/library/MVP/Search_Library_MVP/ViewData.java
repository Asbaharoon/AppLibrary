package com.example.omd.library.MVP.Search_Library_MVP;

import com.example.omd.library.Models.LibraryModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onLibraryDataSuccess(List<LibraryModel> libraryModelList);
    void onLibraryDataFailed(String error);
    void hideProgress();

}
