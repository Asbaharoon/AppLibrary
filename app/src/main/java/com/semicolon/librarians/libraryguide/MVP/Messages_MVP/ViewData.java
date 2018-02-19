package com.semicolon.librarians.libraryguide.MVP.Messages_MVP;

import com.semicolon.librarians.libraryguide.Models.MessageModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onMessagesSuccess(List<MessageModel> messageModelList);
    void onFailed(String error);
    void onMessageSendSuccess();
}
