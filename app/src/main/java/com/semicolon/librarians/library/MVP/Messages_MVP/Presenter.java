package com.semicolon.librarians.library.MVP.Messages_MVP;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Presenter {

    void getMessages(String currUser_userName, String chatUser_userName);
    void sendMessage(String senderid,String receiverid,String sender_name,String receiver_name,String message,String sender_photo);

}
