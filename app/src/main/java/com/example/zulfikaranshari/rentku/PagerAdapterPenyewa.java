package com.example.zulfikaranshari.rentku;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterPenyewa extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterPenyewa(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentProfilePenyewa();
            case 1:
                return new FragmentKendaraanPenyewa();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
