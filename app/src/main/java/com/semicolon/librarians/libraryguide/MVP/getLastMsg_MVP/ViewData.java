package com.semicolon.librarians.libraryguide.MVP.getLastMsg_MVP;

import com.semicolon.librarians.libraryguide.Models.MessageModel;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onMessagesSuccess(MessageModel messageModel);
    void onFailed(String error);
}
