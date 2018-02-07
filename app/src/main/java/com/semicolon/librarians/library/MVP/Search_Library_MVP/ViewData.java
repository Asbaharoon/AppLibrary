package com.semicolon.librarians.library.MVP.Search_Library_MVP;

import com.semicolon.librarians.library.Models.LibraryModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onLibraryDataSuccess(List<LibraryModel> libraryModelList);
    void onLibraryDataFailed(String error);
    void hideProgress();

}
