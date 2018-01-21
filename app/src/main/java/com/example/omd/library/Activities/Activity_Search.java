package com.example.omd.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.omd.library.Fragments.Fragment_Library_Search;
import com.example.omd.library.Fragments.Fragment_Publisher_Search;
import com.example.omd.library.R;

public class Activity_Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__search);

        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("library_search"))
        {
            getSupportFragmentManager().beginTransaction().add(R.id.search_fragmentContainer,new Fragment_Library_Search()).commit();
        }else if (intent.hasExtra("publisher_search"))
        {
            getSupportFragmentManager().beginTransaction().add(R.id.search_fragmentContainer,new Fragment_Publisher_Search()).commit();

        }
    }
}
