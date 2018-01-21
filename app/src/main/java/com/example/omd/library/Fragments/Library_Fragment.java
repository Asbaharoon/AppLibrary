package com.example.omd.library.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.omd.library.Activities.Activity_Search;
import com.example.omd.library.R;

/**
 * Created by Delta on 15/12/2017.
 */

public class Library_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.library_fragment,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.library_search,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.library_searchIcon)
        {
            Intent intent = new Intent(getActivity(), Activity_Search.class);
            intent.putExtra("library_search","library");
            getActivity().startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }
}
