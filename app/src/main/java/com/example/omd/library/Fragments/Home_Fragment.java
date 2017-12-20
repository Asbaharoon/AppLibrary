package com.example.omd.library.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.example.omd.library.Adapters.ViewPagerAdapter;
import com.example.omd.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Delta on 15/12/2017.
 */

public class Home_Fragment extends Fragment {
    private Context mContext;
    private AHBottomNavigation navBar;
    private ViewPager pager;
    private List<Fragment> fragmentList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        initView(view);
        setUpnavBar();
        setUpViewPager();

        return view;
    }

    private void initView(View view) {
        mContext = view.getContext();
        navBar = (AHBottomNavigation)view.findViewById(R.id.bottom_navBar);
        pager = (ViewPager) view.findViewById(R.id.pager);
    }
    private void setUpnavBar()
    {
        AHBottomNavigationAdapter adapter = new AHBottomNavigationAdapter(getActivity(),R.menu.navbar_menu);
        navBar.setDefaultBackgroundColor(ContextCompat.getColor(mContext,R.color.base));
        navBar.setInactiveColor(ContextCompat.getColor(mContext,R.color.dark_gray));
        navBar.setAccentColor(ContextCompat.getColor(mContext, R.color.ahnav));
        adapter.setupWithBottomNavigation(navBar);
        navBar.setCurrentItem(0);
        navBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        if (navBar.getCurrentItem()==0)
        {
            pager.setCurrentItem(0);
        }
        navBar.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (position==0)
                {
                    pager.setCurrentItem(0);
                    navBar.setCurrentItem(position,false);
                    return true;
                }
                else if (position==1)
                {

                    pager.setCurrentItem(1);

                    navBar.setCurrentItem(position,false);
                    return true;

                }
                else if (position==2)
                {
                    pager.setCurrentItem(2);

                    navBar.setCurrentItem(position,false);
                    return true;

                }
                else if (position==3)
                {

                    pager.setCurrentItem(3);
                    navBar.setCurrentItem(position,false);
                    return true;

                }
                return false;
            }
        });

    }
    private void setUpViewPager()
    {
        fragmentList = new ArrayList<>();
        fragmentList.add(new Chat_Fragment());
        fragmentList.add(new University_Fragment());
        fragmentList.add(new Library_Fragment());
        fragmentList.add(new Publisher_Fragment());

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.AddFragment(fragmentList);
        pager.setAdapter(adapter);
        pager.beginFakeDrag();



    }
}
