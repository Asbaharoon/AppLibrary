package com.semicolon.librarians.libraryguide.MVP.UpdateToken;

import android.content.Context;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Interactor {
    interface onCompleteListener
    {
        void onSuccess();
    }
    void UpdateUserData(String user_username, String token, Context context, Interactor.onCompleteListener listener);

}
