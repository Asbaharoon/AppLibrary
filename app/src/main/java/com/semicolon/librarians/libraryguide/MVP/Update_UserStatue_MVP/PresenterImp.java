package com.semicolon.librarians.libraryguide.MVP.Update_UserStatue_MVP;

import android.content.Context;

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
    public void UpdateUserData(String user_username, String lat, String lng) {
        interactor.UpdateUserData(user_username,lat,lng,context,this);
    }

    @Override
    public void onSuccess() {

        if (viewData!=null)
        {
            viewData.onSucess();
        }
    }

    @Override
    public void onFailed() {
        if (viewData!=null)
        {
            viewData.onFailed();
        }
    }


}
