package com.semicolon.librarians.libraryguide.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lamudi.phonefield.PhoneInputLayout;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Activity_PhoneNumber extends AppCompatActivity implements View.OnClickListener {


    private PhoneInputLayout phone_input;
    private Toolbar mToolbar;
    private String phoneNumber;
    private Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, Tags.font,true);

        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.phone_toolBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        submitBtn = (Button) findViewById(R.id.btn_submit);
        submitBtn.setOnClickListener(this);

        phone_input = (PhoneInputLayout) findViewById(R.id.phone_input);
        phone_input.setHint(R.string.phone);
        phone_input.setDefaultCountry("QA");



    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_submit)
        {
            submitPhoneNumber();
        }
    }

    private void submitPhoneNumber() {
         boolean valid=true;

        if (phone_input.isValid())
        {
            phone_input.setError(null);
            phoneNumber = phone_input.getPhoneNumber();

        }else
        {
            valid =false;
        }
        if (valid)
        {
            Intent intent = getIntent();
            intent.putExtra("phone_number",phoneNumber);
            setResult(Activity.RESULT_OK,intent);
            finish();

        }else
            {
                Toast.makeText(this, R.string.invalid_phone, Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return true;
    }
}
