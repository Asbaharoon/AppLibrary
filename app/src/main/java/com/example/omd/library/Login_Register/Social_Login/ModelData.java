package com.example.omd.library.Login_Register.Social_Login;

import com.example.omd.library.Models.NormalUserData;

/**
 * Created by Delta on 16/12/2017.
 */

public interface ModelData {
    interface onCompleteListener
    {
        void OnSuccess(NormalUserData userData);
        void onFailed(String message);
    }
    void getUserData(ModelData.onCompleteListener listener);
}
