package com.semicolon.librarians.library.Services;

import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.semicolon.librarians.library.Activities.HomeActivity;

/**
 * Created by Delta on 07/02/2018.
 */

public class MyFirebaseInstanceIdServices extends FirebaseInstanceIdService {

    Context context;
    HomeActivity homeActivity;
    public MyFirebaseInstanceIdServices() {
    }

    public MyFirebaseInstanceIdServices(Context context) {
        this.context = context;
        homeActivity = (HomeActivity) context;
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        homeActivity.GetToken(FirebaseInstanceId.getInstance().getToken());
    }
}
