package com.example.omd.library.publisherMVP;


import com.example.omd.library.Models.PublishersModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Interactor {
    interface onCompleteListener
    {
        void onpublisherDataSuccess(List<PublishersModel> publishersModelList);
        void onpublisherDataFailed(String error);
    }
    void getpublisherData(onCompleteListener listener);
}
