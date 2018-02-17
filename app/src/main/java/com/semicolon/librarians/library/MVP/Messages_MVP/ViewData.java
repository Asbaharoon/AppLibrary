package com.semicolon.librarians.library.MVP.Messages_MVP;

import com.semicolon.librarians.library.Models.MessageModel;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onMessagesSuccess(List<MessageModel> messageModelList);
    void onFailed(String error);
    void onMessageSendSuccess();
}
