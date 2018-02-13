package com.semicolon.librarians.library.MVP.Create_ChatRoom_MVP;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onChatRoom_CreatedSuccessfully(String response);
    void onFailed(String error);
}
