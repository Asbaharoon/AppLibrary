package com.example.omd.library.publisherMVP;

import com.example.omd.library.Models.PublishersModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onpublisherDataSuccess(List<PublishersModel> publishersModelList);
    void onpublisherDataFailed(String error);
}
