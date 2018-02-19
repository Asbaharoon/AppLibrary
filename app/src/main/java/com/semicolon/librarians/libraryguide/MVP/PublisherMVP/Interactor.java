package com.semicolon.librarians.libraryguide.MVP.PublisherMVP;


import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.PublisherModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Interactor {
    interface onCompleteListener
    {
        void onPublisherDataSuccess(List<PublisherModel> publishersModelList);
        void onPublisherDataFailed(String error);
        void hideProgressBar();

    }
    void getPublisherData(String pub_userName, Context context,onCompleteListener listener);
}
