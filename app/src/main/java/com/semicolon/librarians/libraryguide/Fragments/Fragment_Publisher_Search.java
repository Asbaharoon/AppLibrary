package com.semicolon.librarians.libraryguide.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semicolon.librarians.libraryguide.Adapters.Publisher_Search_Adapter;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.R;

import java.util.List;

/**
 * Created by Delta on 21/01/2018.
 */

public class Fragment_Publisher_Search extends Fragment {
    private RecyclerView pub_search_recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private List<PublisherModel> publisherModelList;
    private Bundle bundle;
    Context context;
    View view;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_publisher_search,container,false);
        initView(view);
          getDataFromBundle();
        return view;
    }

    private void getDataFromBundle() {
        bundle = getArguments();

        if (bundle!=null)
        {
            publisherModelList  = (List<PublisherModel>) bundle.getSerializable("pub_data");
            adapter = new Publisher_Search_Adapter(publisherModelList,getActivity());
            pub_search_recyclerView.setAdapter(adapter);
        }
    }


    private void initView(final View view) {
        context = view.getContext();
        pub_search_recyclerView = (RecyclerView) view.findViewById(R.id.pub_search_recyclerView);
        manager = new LinearLayoutManager(getActivity());
        pub_search_recyclerView.setLayoutManager(manager);
        pub_search_recyclerView.setHasFixedSize(true);


    }


}
