package com.semicolon.librarians.libraryguide.MVP.Messages_MVP;


import android.content.Context;

import com.semicolon.librarians.libraryguide.Models.MessageModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Interactor {
    interface onCompleteListener
    {
        void onMessagesSuccess(List<MessageModel> messageModelList);
        void onMessageSendSuccess();
        void onFailed(String error);
    }
    void getMessages(String currUser_userName, String chatUser_userName, Context context, Interactor.onCompleteListener listener);
    void sendMessage(String senderid, String receiverid, String sender_name, String receiver_name, String message, String chat_user_token, String sender_photo, Context context, Interactor.onCompleteListener listener);


}
