package com.semicolon.librarians.library.Services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Delta on 17/12/2017.
 */

public class NetworkConnection {
    Context mContext;

    public NetworkConnection(Context mContext) {
        this.mContext = mContext;
    }
    public boolean CheckConnection()
    {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info!=null&&info.isAvailable()&&info.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
