package com.semicolon.librarians.library.MVP.Messages_MVP;


import com.semicolon.librarians.library.Models.MessageModel;

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
    void getMessages(String currUser_userName, String chatUser_userName,Interactor.onCompleteListener listener);
    void sendMessage(String senderid,String receiverid,String sender_name,String receiver_name,String message,String sender_photo,Interactor.onCompleteListener listener);


}
