package com.semicolon.librarians.libraryguide.Services;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Delta on 19/02/2018.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
