package com.semicolon.librarians.library.MVP.DisplayRoomsById_MVP;

import com.semicolon.librarians.library.Models.CommonUsersData;

import java.util.List;

/**
 * Created by Delta on 22/01/2018.
 */

public interface ViewData {
    void onChatRoomUserSuccess(List<CommonUsersData> commonUsersData);
    void onFailed(String error);
}
