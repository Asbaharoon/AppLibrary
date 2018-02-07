package com.semicolon.librarians.library.MVP.Search_Publisher_MPV;


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
        void showNoResults();
        void hideNoResults();

    }
    void getPublisherData(String pub_name,String country,onCompleteListener listener);
}
