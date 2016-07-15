package me.ryanmiles.aqn.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easyandroidanimations.library.FadeInAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.data.Data;

/**
 * Created by ryanm on 5/5/2016.
 */
public class ForestFragment extends Fragment {
    @BindView(R.id.wood)
    Button mWoodButton;
    @BindView(R.id.stone)
    Button mStoneButton;
    @BindView(R.id.dirty_water)
    Button mDirtyWaterButton;
    @BindView(R.id.hunt)
    Button mHuntButton;
    @BindView(R.id.trans_container_forest_fragment)
    ViewGroup mLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.forest_fragment_layout, container, false);
        ButterKnife.bind(this,rootView);
        mDirtyWaterButton.setVisibility(View.GONE);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateButtons();
    }

    private void updateButtons() {
        if (!Data.STONESWORD.isCrafted()) {
            mHuntButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && Data.OPENFOREST) {
            Data.OPENFOREST = false;
            new FadeInAnimation(mLayout).setDuration(5000).animate();
        }
    }

    @OnClick(R.id.wood)
    public void woodOnClick(){
        Data.WOOD.addIncrement();
        Data.LEAVES.random();
    }

    @OnClick(R.id.stone)
    public void stoneOnClick(){
        Data.STONE.addIncrement();
        if (Data.STONEPICK.isCrafted()) {
            // Data.TIN.random();
        }
    }

    @OnClick(R.id.hunt)
    public void huntOnClick() {
        Data.MEAT.addIncrement();
    }
}