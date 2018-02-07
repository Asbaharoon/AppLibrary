package com.semicolon.librarians.library.MVP.PublisherMVP;

import com.semicolon.librarians.library.Models.PublisherModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onPublisherDataSuccess(List<PublisherModel> publishersModelList);
    void onPublisherDataFailed(String error);
    void hideProgressBar();
}
