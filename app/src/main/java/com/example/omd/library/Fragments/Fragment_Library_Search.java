package com.example.omd.library.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.omd.library.Activities.Activity_Search;
import com.example.omd.library.R;

/**
 * Created by Delta on 21/01/2018.
 */

public class Fragment_Library_Search extends Fragment implements View.OnClickListener{
    Context context ;
    TextView Public_library,National_library,Specialized_library,Academy_library,School_library,University_library,Children_library,Electronic_library,Migratory_library;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library_search,container,false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        context = view.getContext();
        Public_library = (TextView) view.findViewById(R.id.pub_lib_tv);
        National_library = (TextView) view.findViewById(R.id.nat_lib_tv);
        Specialized_library = (TextView) view.findViewById(R.id.spec_lib_tv);
        Academy_library = (TextView) view.findViewById(R.id.acdm_lib_tv);
        School_library = (TextView) view.findViewById(R.id.sch_lib_tv);
        University_library = (TextView) view.findViewById(R.id.uni_lib_tv);
        Children_library = (TextView) view.findViewById(R.id.chil_lib_tv);
        Electronic_library = (TextView) view.findViewById(R.id.elec_lib_tv);
        Migratory_library = (TextView) view.findViewById(R.id.mig_lib_tv);

        Public_library.setOnClickListener(this);
        National_library.setOnClickListener(this);
        Specialized_library.setOnClickListener(this);
        Academy_library.setOnClickListener(this);
        School_library.setOnClickListener(this);
        University_library.setOnClickListener(this);
        Children_library.setOnClickListener(this);
        Electronic_library.setOnClickListener(this);
        Migratory_library.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.pub_lib_tv:
                setQuery(Public_library.getText().toString().trim());
                break;
            case R.id.nat_lib_tv:
                setQuery(National_library.getText().toString().trim());
                break;
            case R.id.spec_lib_tv:
                setQuery(Specialized_library.getText().toString().trim());
                break;
            case R.id.acdm_lib_tv:
                setQuery(Academy_library.getText().toString().trim());
                break;
            case R.id.sch_lib_tv:
                setQuery(School_library.getText().toString().trim());
                break;
            case R.id.uni_lib_tv:
                setQuery(University_library.getText().toString().trim());
                break;
            case R.id.chil_lib_tv:
                setQuery(Children_library.getText().toString().trim());
                break;
            case R.id.elec_lib_tv:
                setQuery(Electronic_library.getText().toString().trim());
                break;
            case R.id.mig_lib_tv:
                setQuery(Migratory_library.getText().toString().trim());
                break;


        }
    }
    private void setQuery(String query)
    {
        ((Activity_Search)getActivity()).setQuery(query);
    }
}
