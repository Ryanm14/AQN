package me.ryanmiles.aqn.events;

import android.support.v4.app.Fragment;

/**
 * Created by ryanm on 5/7/2016.
 */
public class ChangeFragmentEvent {
    private Fragment mFragment;

    public ChangeFragmentEvent(Fragment fragment) {
        mFragment = fragment;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }
}
