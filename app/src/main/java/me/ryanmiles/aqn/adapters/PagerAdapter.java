package me.ryanmiles.aqn.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import me.ryanmiles.aqn.fragments.CaveFragment;
import me.ryanmiles.aqn.fragments.ForestFragment;
import me.ryanmiles.aqn.fragments.VillageFragment;

/**
 * Created by ryanm on 5/5/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    public static CaveFragment sCaveFragment;
    public static ForestFragment sForestFragment;
    public static VillageFragment sVillageFragment;
    int mNumOfTabs;
    FragmentManager mFragmentManager;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        mFragmentManager = fm;
        this.mNumOfTabs = NumOfTabs;
        sForestFragment = new ForestFragment();
        sCaveFragment = new CaveFragment();
        sVillageFragment = new VillageFragment();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return sCaveFragment;
            case 1:
                return sForestFragment;
            case 2:
                return sVillageFragment;
            default:
                return null;
        }
    }



    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}