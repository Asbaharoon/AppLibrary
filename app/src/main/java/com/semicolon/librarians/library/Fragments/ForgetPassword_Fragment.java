package com.semicolon.librarians.library.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semicolon.librarians.library.R;
import com.semicolon.librarians.library.Services.Tags;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Delta on 22/12/2017.
 */

public class ForgetPassword_Fragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), Tags.font,true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.forgetpassword_fragment,container,false);

        return view;
    }
}
