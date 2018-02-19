package com.semicolon.librarians.libraryguide.MVP.JobsMVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.JobsModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public class PresenterImp implements Presenter,Interactor.onCompleteListener {
    ViewData viewData;
    Context context;
    Interactor interactor;

    public PresenterImp(ViewData viewData, Context context) {
        this.viewData = viewData;
        this.context = context;
        interactor = new InteractorImp();


    }

    @Override
    public void getJobsData() {
        interactor.getJobsData(context,this);
    }


    @Override
    public void onJobDataSuccess(List<JobsModel> jobsModelList) {
        if (viewData!=null)
        {
            viewData.onJobDataSuccess(jobsModelList);
        }
    }

    @Override
    public void onJobDataFailed(String error) {
        if (viewData!=null)
        {
            viewData.onJobDataFailed(error);
        }

    }



    @Override
    public void hideProgressBar() {
        if (viewData!=null)
        {
            viewData.hideProgressBar();
        }
    }


}
