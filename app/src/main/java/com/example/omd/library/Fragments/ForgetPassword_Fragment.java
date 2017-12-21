package com.example.omd.library.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omd.library.R;

/**
 * Created by Delta on 22/12/2017.
 */

public class ForgetPassword_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.forgetpassword_fragment,container,false);
        return view;
    }
}
