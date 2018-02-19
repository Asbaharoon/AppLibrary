package com.semicolon.librarians.libraryguide.MVP.JobsMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.JobsModel;

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
    void getJobsData(Context context,Interactor.onCompleteListener listener);
}
