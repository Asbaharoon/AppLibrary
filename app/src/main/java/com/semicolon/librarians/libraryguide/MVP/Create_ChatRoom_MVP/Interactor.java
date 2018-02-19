package com.semicolon.librarians.libraryguide.MVP.Create_ChatRoom_MVP;


import android.content.Context;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Interactor {
    interface onCompleteListener
    {
        void onChatRoom_CreatedSuccessfully(String response);
        void onFailed(String error);
    }
    void Create_ChatRoom(String currUser_userName, String chatUser_userName, Context context, Interactor.onCompleteListener listener);

}
