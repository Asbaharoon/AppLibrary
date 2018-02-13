package com.semicolon.librarians.library.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.semicolon.librarians.library.Activities.HomeActivity;
import com.semicolon.librarians.library.Adapters.AllChatRoomsAdapter;
import com.semicolon.librarians.library.MVP.DisplayRoomsById_MVP.Presenter;
import com.semicolon.librarians.library.MVP.DisplayRoomsById_MVP.PresenterImp;
import com.semicolon.librarians.library.MVP.DisplayRoomsById_MVP.ViewData;
import com.semicolon.librarians.library.Models.CommonUsersData;
import com.semicolon.librarians.library.Models.CompanyModel;
import com.semicolon.librarians.library.Models.LibraryModel;
import com.semicolon.librarians.library.Models.NormalUserData;
import com.semicolon.librarians.library.Models.PublisherModel;
import com.semicolon.librarians.library.Models.UniversityModel;
import com.semicolon.librarians.library.R;
import com.semicolon.librarians.library.Services.Tags;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), Tags.font,true);
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
    }


    @Override
    public void onChatRoomUserSuccess(List<CommonUsersData> commonUsersData) {
        adapter = new AllChatRoomsAdapter(commonUsersData,getActivity());
        adapter.notifyDataSetChanged();
        recRooms.setAdapter(adapter);
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getActivity(), "Error "+error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(homeActivity, ""+currUserId, Toast.LENGTH_SHORT).show();
        presenter.DisplayAllRooms(currUserId);
    }
}
