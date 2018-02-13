package com.semicolon.librarians.library.MVP.Create_ChatRoom_MVP;

import android.content.Context;


public class PresenterImp implements Presenter, Interactor.onCompleteListener {
    ViewData viewData;
    Context context;
    Interactor interactor;

    public PresenterImp(ViewData viewData, Context context) {
        this.viewData = viewData;
        this.context = context;
        interactor = new InteractorImp();


    }


    @Override
    public void Create_ChatRoom(String currUser_userName, String chatUser_userName) {
        interactor.Create_ChatRoom(currUser_userName, chatUser_userName,this);
    }

    @Override
    public void onChatRoom_CreatedSuccessfully(String response) {

        if (viewData!=null)
        {
            viewData.onChatRoom_CreatedSuccessfully(response);
        }
    }

    @Override
    public void onFailed(String error) {
        if (viewData!=null)
        {
            viewData.onFailed(error);
        }
    }
}
