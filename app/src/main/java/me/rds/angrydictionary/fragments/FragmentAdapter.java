package me.rds.angrydictionary.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    private int mSize;

    public FragmentAdapter(FragmentManager fm, int size) {
        super(fm);
        mSize = size;
    }

    @Override
    public Fragment getItem(int arg0) {
        return FragmentFactory.getInstance(arg0);
    }

    @Override
    public int getCount() {
        return mSize;
    }

}
