package me.ryanmiles.aqn.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.adapters.CustomViewPager;
import me.ryanmiles.aqn.adapters.PagerAdapter;

/**
 * Created by ryanm on 5/7/2016.
 */
public class ViewPagerFragment extends Fragment {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    CustomViewPager mViewPager;

    public ViewPagerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.view_pager_fragment, container, false);
        ButterKnife.bind(this,root);
        mTabLayout.addTab(mTabLayout.newTab().setText("Cave"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Forest"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        PagerAdapter adapter = new PagerAdapter
                (getChildFragmentManager(), 2);
        mViewPager.setAdapter(adapter);
        mViewPager.setPagingEnabled(false);
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
}
