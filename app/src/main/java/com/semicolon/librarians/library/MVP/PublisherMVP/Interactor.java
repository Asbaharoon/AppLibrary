package com.semicolon.librarians.library.MVP.PublisherMVP;


import com.semicolon.librarians.library.Models.PublisherModel;

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
    void getPublisherData(String pub_userName,onCompleteListener listener);
}
