package me.ryanmiles.aqn.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.adapters.PagerAdapter;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.events.updates.UpdateTabHost;

/**
 * Created by ryanm on 5/7/2016.
 */
public class ViewPagerFragment extends Fragment {
    private static final String TAG = ViewPagerFragment.class.getCanonicalName();
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    public ViewPagerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.view_pager_fragment, container, false);
        ButterKnife.bind(this,root);
        if(!Data.FIRSTRUN) {
            mTabLayout.addTab(mTabLayout.newTab().setText("Cave"));
            mTabLayout.addTab(mTabLayout.newTab().setText("Forest"));
        }
        if (Data.FOUNDATION.isBuilt()) {
            mTabLayout.addTab(mTabLayout.newTab().setText("Village"));
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        PagerAdapter adapter = new PagerAdapter
                (getChildFragmentManager(), 3);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

    public void switchTab() {
        if (mViewPager.getCurrentItem() == 1) {
            mViewPager.setCurrentItem(0);
        }
    }

    @Subscribe
    public void onEvent(UpdateTabHost event) {
        mTabLayout.addTab(mTabLayout.newTab().setText(event.getName()));
    }
}
