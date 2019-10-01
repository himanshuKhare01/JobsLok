package com.jobslok.ViewPagerManager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GuideModelManager  {
    private List<CharSequence> titleList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();


    public GuideModelManager() {

    }

    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }

    public int getFragmentCount() {
        return this.fragmentList.size();
    }

    public boolean hasTitles() {
        return this.titleList.size() != 0;
    }

    public CharSequence getTitle(int position) {
        return this.titleList.get(position);
    }


    public void addCommonFragment(List<? extends Serializable> list, List<String> fragmentNumber, List<? extends Serializable> listDes, List<? extends Serializable> listtitle) {
        this.titleList.addAll(fragmentNumber);
        this.addCommonFragment(list, listDes, listtitle);
    }

    public void addCommonFragment(List<? extends Serializable> list, List<? extends Serializable> listdesc, List<? extends Serializable> listtitle) {
        try {
            for (int i = 0; i < list.size(); ++i) {
                Fragment fragment = (Fragment) ((Class<?>) com.jobslok.Fragment.GuideFragment.class).newInstance();
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", list.get(i));
                bundle.putSerializable("data2", listdesc.get(i));
                bundle.putSerializable("data3", listtitle.get(i));
                fragment.setArguments(bundle);
                this.fragmentList.add(fragment);
            }
        } catch (InstantiationException var6) {
            var6.printStackTrace();
        } catch (IllegalAccessException var7) {
            var7.printStackTrace();
        }

    }
}
