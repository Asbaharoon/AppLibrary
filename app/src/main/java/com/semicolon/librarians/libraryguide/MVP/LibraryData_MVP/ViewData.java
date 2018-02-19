package com.semicolon.librarians.libraryguide.MVP.LibraryData_MVP;

import com.semicolon.librarians.libraryguide.Models.LibraryModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onLibraryDataSuccess(List<LibraryModel> libraryModelList);
    void onFailed(String error);
    void hideProgressBar();
}
