package com.example.omd.library.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Delta on 16/12/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    public void AddFragment(List<Fragment> fragments)
    {
        this.fragmentList = fragments;
    }
}
