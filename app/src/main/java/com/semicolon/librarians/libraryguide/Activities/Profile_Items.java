package com.semicolon.librarians.libraryguide.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Profile_Items extends AppCompatActivity implements View.OnClickListener{
    private TextView ident;
    private EditText ident_et;
    private Button doneBtn,canelBtn;
    private ImageView back;
    private Toolbar toolbar;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__items);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, Tags.font,true);

        initView();
        getDateFromIntent();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.updateProfile_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ident = (TextView) findViewById(R.id.ident);
        ident_et = (EditText) findViewById(R.id.ident_et);
        doneBtn = (Button) findViewById(R.id.doneBtn);
        canelBtn = (Button) findViewById(R.id.cancelBtn);
        back = (ImageView) findViewById(R.id.back_arrow);
        doneBtn.setOnClickListener(this);
        canelBtn.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void getDateFromIntent() {

        Intent intent = getIntent();

        if (intent!=null)
        {
            if (intent.hasExtra("name"))
            {
                type = "name";
                String name = intent.getStringExtra("name");
                ident.setText(getString(R.string.enter_your_name));
                ident_et.setText(name);

            }else if (intent.hasExtra("email"))
            {
                type = "email";

                String email = intent.getStringExtra("email");
                ident.setText(R.string.enter_email_address);
                ident_et.setText(email);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.doneBtn:
                if (type.equals("name"))
                {
                    if (TextUtils.isEmpty(ident_et.getText().toString()))
                    {
                        Toast.makeText(this, R.string.empty_name, Toast.LENGTH_SHORT).show();
                    }else
                        {
                            Intent intent = new Intent();
                            intent.putExtra("username",ident_et.getText().toString());
                            setResult(RESULT_OK,intent);
                            finish();
                        }

                }else if (type.equals("email"))
                {
                    if (TextUtils.isEmpty(ident_et.getText().toString()))
                    {
                        Toast.makeText(this, R.string.email_empty, Toast.LENGTH_SHORT).show();
                    }else if (!ident_et.getText().toString().matches(Tags.email_Regex))
                    {
                        Toast.makeText(this, R.string.invalidEmail, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent intent = new Intent();
                        intent.putExtra("useremail",ident_et.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                }

                break;
            case R.id.cancelBtn:
                finish();
                break;
            case R.id.back_arrow:
                finish();
                break;
        }
    }
}
