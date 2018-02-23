package com.semicolon.librarians.libraryguide.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.semicolon.librarians.libraryguide.Activities.HomeActivity;
import com.semicolon.librarians.libraryguide.Adapters.AllChatRoomsAdapter;
import com.semicolon.librarians.libraryguide.MVP.DisplayRoomsById_MVP.Presenter;
import com.semicolon.librarians.libraryguide.MVP.DisplayRoomsById_MVP.PresenterImp;
import com.semicolon.librarians.libraryguide.MVP.DisplayRoomsById_MVP.ViewData;
import com.semicolon.librarians.libraryguide.Models.CommonUsersData;
import com.semicolon.librarians.libraryguide.Models.CompanyModel;
import com.semicolon.librarians.libraryguide.Models.LibraryModel;
import com.semicolon.librarians.libraryguide.Models.NormalUserData;
import com.semicolon.librarians.libraryguide.Models.PublisherModel;
import com.semicolon.librarians.libraryguide.Models.UniversityModel;
import com.semicolon.librarians.libraryguide.R;

import java.util.List;

/**
 * Created by Delta on 15/12/2017.
 */

public class Chat_Fragment extends Fragment implements ViewData{

    private RecyclerView recRooms;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private Presenter presenter;
    private NormalUserData userData=null;
    private PublisherModel publisherModel = null;
    private LibraryModel  libraryModel = null;
    private UniversityModel universityModel = null;
    private CompanyModel companyModel = null;
    private HomeActivity homeActivity;
    private String currUserId = "";
    private LinearLayout no_friends_container,error_container;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        homeActivity  = (HomeActivity) getActivity();
        if (homeActivity.user_Data!=null)
        {
            userData = homeActivity.user_Data;
            currUserId = userData.getUserId();
        }else if (homeActivity.publisher_Model!=null)
        {
            publisherModel = homeActivity.publisher_Model;
            currUserId = publisherModel.getPub_username();
        }
        else if (homeActivity.library_Model!=null)
        {
            libraryModel = homeActivity.library_Model;
            currUserId = libraryModel.getLib_username();
        }else if (homeActivity.university_Model!=null)
        {
            universityModel =homeActivity.university_Model;
            currUserId = universityModel.getUni_username();
        }else if (homeActivity.company_Model!=null)
        {
            companyModel = homeActivity.company_Model;
            currUserId = companyModel.getComp_username();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment,container,false);
        presenter = new PresenterImp(this,getActivity());
        initView(view);
        return view;
    }

    private void initView(View view) {
        recRooms = (RecyclerView) view.findViewById(R.id.recRooms);
        manager = new LinearLayoutManager(getActivity());
        recRooms.setLayoutManager(manager);
        recRooms.setHasFixedSize(true);
        no_friends_container = (LinearLayout) view.findViewById(R.id.no_friends_container);
        error_container = (LinearLayout) view.findViewById(R.id.error_container);

    }


    @Override
    public void onChatRoomUserSuccess(List<CommonUsersData> commonUsersData) {
        if (commonUsersData.size()>0)
        {
            adapter = new AllChatRoomsAdapter(commonUsersData,getActivity());
            adapter.notifyDataSetChanged();
            recRooms.setAdapter(adapter);
            recRooms.setVisibility(View.VISIBLE);
            no_friends_container.setVisibility(View.GONE);
            error_container.setVisibility(View.GONE);


        }else
            {
                recRooms.setVisibility(View.GONE);
                no_friends_container.setVisibility(View.VISIBLE);
                error_container.setVisibility(View.GONE);

            }

    }

    @Override
    public void onFailed(String error) {
        error_container.setVisibility(View.VISIBLE);
        recRooms.setVisibility(View.GONE);
        no_friends_container.setVisibility(View.GONE);

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.DisplayAllRooms(currUserId);
    }
}
