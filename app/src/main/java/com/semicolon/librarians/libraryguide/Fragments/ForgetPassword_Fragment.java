package com.semicolon.librarians.libraryguide.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by Delta on 22/12/2017.
 */

public class ForgetPassword_Fragment extends Fragment implements View.OnClickListener {

    private EditText forgetPassword_email;
    private Button forgetBtn;
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
        initView(view);
        return view;
    }

    private void initView(View view) {
        forgetPassword_email = (EditText) view.findViewById(R.id.forgetPassword_email);
        forgetBtn = (Button) view.findViewById(R.id.forgetBtn);
        forgetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.forgetBtn)
        {
            sendEmail();
        }
    }

    private void sendEmail() {
        if (TextUtils.isEmpty(forgetPassword_email.getText().toString()))
        {
            forgetPassword_email.setError(getString(R.string.empty_field));
        }else if (!forgetPassword_email.getText().toString().matches(Tags.email_Regex))
        {
            forgetPassword_email.setError(getString(R.string.invalidEmail));

        }else
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"heshamtatawy@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT,"Library Guide");
                intent.putExtra(Intent.EXTRA_TEXT,"forget password");

                getActivity().startActivity(Intent.createChooser(intent,"Send email"));
            }
    }
}
