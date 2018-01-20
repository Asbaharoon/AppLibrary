package com.example.omd.library.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.example.omd.library.R;

/**
 * Created by Delta on 15/12/2017.
 */

public class Home_Fragment extends Fragment {
    private Context mContext;
    private AHBottomNavigation navBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        initView(view);
        setUpnavBar();

        return view;
    }

    private void initView(View view) {
        mContext = view.getContext();
        navBar = (AHBottomNavigation)view.findViewById(R.id.bottom_navBar);
    }
    private void setUpnavBar()
    {
        AHBottomNavigationAdapter adapter = new AHBottomNavigationAdapter(getActivity(),R.menu.navbar_menu);
        navBar.setDefaultBackgroundColor(ContextCompat.getColor(mContext,R.color.base));
        navBar.setInactiveColor(ContextCompat.getColor(mContext,R.color.dark_gray));
        navBar.setAccentColor(ContextCompat.getColor(mContext, R.color.centercolor));
        adapter.setupWithBottomNavigation(navBar);
        navBar.setCurrentItem(0);
        navBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        if (navBar.getCurrentItem()==0)
        {
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.home_fragment_fragmentsContainers,new Chat_Fragment()).commit();
        }
        navBar.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (position==0)
                {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainers,new Chat_Fragment()).commit();

                    navBar.setCurrentItem(position,false);
                    return true;
                }
                else if (position==1)
                {

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainers,new University_Fragment()).commit();

                    navBar.setCurrentItem(position,false);
                    return true;

                }
                else if (position==2)
                {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainers,new Library_Fragment()).commit();

                    navBar.setCurrentItem(position,false);
                    return true;

                }
                else if (position==3)
                {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_fragmentsContainers,new Publisher_Fragment()).commit();

                    navBar.setCurrentItem(position,false);
                    return true;

                }
                return false;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), "home onResume", Toast.LENGTH_SHORT).show();
    }
}
