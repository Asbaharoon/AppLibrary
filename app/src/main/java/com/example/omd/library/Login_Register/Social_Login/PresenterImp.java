package com.example.omd.library.Login_Register.Social_Login;

import android.content.Context;

import com.example.omd.library.Models.NormalUserData;

/**
 * Created by Delta on 16/12/2017.
 */

public class PresenterImp implements Presenter,ModelData.onCompleteListener {
    ViewData data;
    Context mContext;
    ModelDataImp modelDataImp;




    public PresenterImp(ViewData data, Context context) {
        this.data = data;
        this.mContext = context;
        modelDataImp = new ModelDataImp();
    }

    @Override
    public void getUserData() {
        modelDataImp.getUserData(this);
    }

    @Override
    public void OnSuccess(NormalUserData userData) {
        data.OnSuccess(userData);
    }

    @Override
    public void onFailed(String message) {
        data.OnFailed(message);
    }
}
