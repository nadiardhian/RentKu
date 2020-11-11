package com.example.zulfikaranshari.rentku;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ASUS on 01/03/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
	int mNumOfTabs;

	public PagerAdapter(FragmentManager fm, int NumOfTabs) {
		super(fm);
		this.mNumOfTabs = NumOfTabs;
	}

	@Override
	public Fragment getItem(int position) {

		switch (position) {
			case 0:
				return new fragmentAkun();
			case 1:
				return new fragmentProfile();

			default:
				return null;
		}
	}

	@Override
	public int getCount() {
		return mNumOfTabs;
	}
}
