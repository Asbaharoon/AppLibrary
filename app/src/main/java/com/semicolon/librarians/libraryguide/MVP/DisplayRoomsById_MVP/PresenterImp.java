package com.semicolon.librarians.libraryguide.MVP.DisplayRoomsById_MVP;

import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.CommonUsersData;

import java.util.List;


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
    public void DisplayAllRooms(String currUserId)
    {
        interactor.DisplayAllRooms(currUserId,context,this);
    }

    @Override
    public void onChatRoomUserSuccess(List<CommonUsersData> commonUsersData) {

        if (viewData!=null)
        {
            viewData.onChatRoomUserSuccess(commonUsersData);
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
