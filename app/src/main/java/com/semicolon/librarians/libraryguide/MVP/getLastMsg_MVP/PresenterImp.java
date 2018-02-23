package com.semicolon.librarians.libraryguide.MVP.getLastMsg_MVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.MessageModel;


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
    public void getMessages(String currUser_userName, String chatUser_userName) {
        interactor.getMessages(currUser_userName,chatUser_userName,context,this);
    }

    @Override
    public void onMessagesSuccess(MessageModel messageModel) {
        if (viewData!=null)
        {
            viewData.onMessagesSuccess(messageModel);
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
