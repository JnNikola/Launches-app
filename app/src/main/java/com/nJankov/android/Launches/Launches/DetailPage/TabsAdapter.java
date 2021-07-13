package com.nJankov.android.Launches.Launches.DetailPage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nJankov.android.Launches.Launches.MainLaunches.LaunchItem;

/**
 * Created by nJankov on 2/26/2020.
 */


//menuva fragmenti vo detailView activity


class TabsAdapter extends FragmentPagerAdapter {

    private LaunchItem mCurrentLaunchItem;

    public TabsAdapter(FragmentManager fm, LaunchItem currentLaunch) {
        super(fm);
        mCurrentLaunchItem = currentLaunch;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i) {
            case 0: return DetailpageDetailFragment.newInstance(mCurrentLaunchItem);
            case 1: return DetailpageMissionFragment.newInstance(mCurrentLaunchItem);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0: return "DETAILS";
            case 1: return "MISSION";
        }
        return "";
    }
}