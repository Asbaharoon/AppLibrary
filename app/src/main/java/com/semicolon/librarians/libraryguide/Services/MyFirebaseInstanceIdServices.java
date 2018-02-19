package com.semicolon.librarians.libraryguide.Services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.semicolon.librarians.libraryguide.Models.RefreshToken;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Delta on 07/02/2018.
 */

public class MyFirebaseInstanceIdServices extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        RefreshToken refreshToken = new RefreshToken(FirebaseInstanceId.getInstance().getToken());
        EventBus.getDefault().post(refreshToken);
    }
}
