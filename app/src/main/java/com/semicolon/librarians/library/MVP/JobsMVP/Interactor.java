package com.semicolon.librarians.library.MVP.JobsMVP;

import com.semicolon.librarians.library.Models.JobsModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Interactor {
    interface onCompleteListener
    {
        void onJobDataSuccess(List<JobsModel> jobsModelList);
        void onJobDataFailed(String error);
        void hideProgressBar();
    }
    void getJobsData(Interactor.onCompleteListener listener);
}
