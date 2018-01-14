package com.example.omd.library.Login_Register.Login;

import android.content.Context;
import android.text.TextUtils;

import com.example.omd.library.Models.NormalUserData;
import com.example.omd.library.Services.NetworkConnection;
import com.example.omd.library.Services.Tags;

/**
 * Created by Delta on 10/01/2018.
 */

public class Login_ModelInteractorImp implements Login_ModelInteractor {

    private boolean isConnected =false;
    @Override
    public void Login(String username, String password, final onCompleteListener listener, Context context) {
        if (TextUtils.isEmpty(username))
        {
            listener.setUserNameError();
        }
        else if (!username.matches(Tags.username_Regex))
        {
            listener.setUserName_invalidError();
        }
        else if (TextUtils.isEmpty(password))
        {
            listener.setPasswordError();
        }
        else if (!password.matches(Tags.pass_Regex))
        {
            listener.setPassword_invalidError();
        }
        else
            {
                isConnected = new NetworkConnection(context).CheckConnection();
                if (isConnected)
                {
                    listener.onSuccess_NormalUserData(new NormalUserData());

                }else
                    {
                        listener.onFailed("Error Contacting :Check network connection please contact Wi-Fi or contact Mobile-data ");
                    }

            }
    }
}
