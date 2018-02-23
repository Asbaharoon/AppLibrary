package com.semicolon.librarians.libraryguide.MVP.getLastMsg_MVP;


import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.MessageModel;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Interactor {
    interface onCompleteListener
    {
        void onMessagesSuccess(MessageModel messageModel);
        void onFailed(String error);
    }
    void getMessages(String currUser_userName, String chatUser_userName, Context context,Interactor.onCompleteListener listener);


}
