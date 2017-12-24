package com.example.omd.library.Login_Register.Social_Login;

import android.content.Context;

import com.example.omd.library.Activities.ChooserSingin;
import com.example.omd.library.Models.NormalUserData;

/**
 * Created by Delta on 16/12/2017.
 */

public class ModelDataImp implements ModelData {
    private static NormalUserData userData;
    ChooserSingin context;

    public ModelDataImp() {
    }

    public ModelDataImp(Context context) {
        this.context = (ChooserSingin) context;
    }

    @Override
    public void getUserData(onCompleteListener listener) {
        if (userData!=null)
        {
            listener.OnSuccess(userData);
        }else
            {
                listener.onFailed("no data founded");
            }
    }
    public void setUserData(NormalUserData userData)
    {
        this.userData = userData;
    }
}
