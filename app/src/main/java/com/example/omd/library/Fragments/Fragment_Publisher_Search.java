package com.example.omd.library.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omd.library.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

/**
 * Created by Delta on 21/01/2018.
 */

public class Fragment_Publisher_Search extends Fragment {
    MaterialSearchView msv;
    Context context ;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_publisher_search,container,false);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        context = view.getContext();
        msv = (MaterialSearchView) view.findViewById(R.id.pub_msv);
        msv.showSearch();
        msv.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                getActivity().finish();

            }
        });

    }


}
