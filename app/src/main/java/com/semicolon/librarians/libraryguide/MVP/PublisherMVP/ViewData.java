package com.semicolon.librarians.libraryguide.MVP.PublisherMVP;

import com.semicolon.librarians.libraryguide.Models.PublisherModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onPublisherDataSuccess(List<PublisherModel> publishersModelList);
    void onPublisherDataFailed(String error);
    void hideProgressBar();
}
