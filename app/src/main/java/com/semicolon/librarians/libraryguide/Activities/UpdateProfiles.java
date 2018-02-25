
package com.semicolon.librarians.libraryguide.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.semicolon.librarians.libraryguide.Fragments.FragmentUpdate_UserProfile;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class UpdateProfiles extends AppCompatActivity {
    private NormalUserData userData;
    private String userType;
    private Toolbar toolbar;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profiles);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, Tags.font,true);

        initView();
        getDataFromIntent();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar = (Toolbar) findViewById(R.id.updateProfile_toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("userType"))
            {
                userType = intent.getStringExtra("userType");
                switch (userType)
                {
                    case "user" :
                        userData = (NormalUserData) intent.getSerializableExtra("userData");
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("userData",userData);
                        FragmentUpdate_UserProfile fragmentUpdate_userProfile = new FragmentUpdate_UserProfile();
                        fragmentUpdate_userProfile.setArguments(bundle1);
                        getSupportFragmentManager().beginTransaction().replace(R.id.updateProfile_fragmentContainer,fragmentUpdate_userProfile).commit();
                        break;
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment :fragmentList)
        {
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}
