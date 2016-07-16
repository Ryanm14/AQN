package me.ryanmiles.aqn.events;

import android.support.v4.app.Fragment;

/**
 * Created by ryanm on 5/7/2016.
 */
public class ChangeFragmentEvent {
    private Fragment mFragment;
    private String mTag;

    public ChangeFragmentEvent(Fragment fragment, String tag) {
        mFragment = fragment;
        mTag = tag;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public String getTag() {
        return mTag;
    }

    @Override
    public String toString() {
        return "ChangeFragmentEvent{" +
                "mFragment=" + mFragment +
                ", mTag='" + mTag + '\'' +
                '}';
    }
}
