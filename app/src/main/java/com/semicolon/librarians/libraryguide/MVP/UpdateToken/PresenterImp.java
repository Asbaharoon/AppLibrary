package com.semicolon.librarians.libraryguide.MVP.UpdateToken;

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
    public void UpdateToken(String user_username, String token) {
        interactor.UpdateUserData(user_username,token,context,this);
    }

    @Override
    public void onSuccess() {
        if (viewData!=null)
        {
            viewData.onSuccess();
        }
    }
}
