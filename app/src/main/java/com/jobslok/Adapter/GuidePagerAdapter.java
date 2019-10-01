package com.jobslok.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.jobslok.ViewPagerManager.GuideModelManager;


public class GuidePagerAdapter extends FragmentStatePagerAdapter {
    private GuideModelManager pagerModelManager;

    public GuidePagerAdapter(FragmentManager fm, GuideModelManager pagerModelManager) {
        super(fm);
        this.pagerModelManager = pagerModelManager;
    }

    public Fragment getItem(int position) {
        return this.pagerModelManager.getItem(position);
    }

    public int getCount() {
        return this.pagerModelManager.getFragmentCount();
    }

    public CharSequence getPageTitle(int position) {
        return this.pagerModelManager.hasTitles() ? this.pagerModelManager.getTitle(position) : super.getPageTitle(position);
    }
}

