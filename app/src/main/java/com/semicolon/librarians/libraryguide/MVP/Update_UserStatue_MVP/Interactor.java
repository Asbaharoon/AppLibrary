package com.semicolon.librarians.libraryguide.MVP.Update_UserStatue_MVP;

import android.content.Context;

/**
 * Created by Delta on 22/01/2018.
 */

public interface Interactor {
    interface onCompleteListener
    {
        void onSuccess();
        void onFailed();
    }
    void UpdateUserData(String user_username,String lat,String lng, Context context, Interactor.onCompleteListener listener);

}
