package com.example.omd.library.Services.SocialUserData;

import com.example.omd.library.Models.NormalUserData;

/**
 * Created by Delta on 16/12/2017.
 */

public interface ViewData {
    void OnSuccess(NormalUserData userData);
    void OnFailed(String message);
}
