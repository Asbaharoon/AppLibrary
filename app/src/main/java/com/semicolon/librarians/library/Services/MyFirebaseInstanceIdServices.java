package com.semicolon.librarians.library.Services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.semicolon.librarians.library.Models.RefreshToken;

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
