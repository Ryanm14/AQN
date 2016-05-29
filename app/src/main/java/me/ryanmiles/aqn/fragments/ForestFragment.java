package me.ryanmiles.aqn.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.data.Data;

/**
 * Created by ryanm on 5/5/2016.
 */
public class ForestFragment extends Fragment {
    @BindView(R.id.dirty_water)
    Button mDirtyWaterButton;
    @BindView(R.id.hunt)
    Button mHuntButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.forest_fragment_layout, container, false);
        ButterKnife.bind(this,rootView);
        mDirtyWaterButton.setVisibility(View.GONE);
        mHuntButton.setVisibility(View.GONE);
        updateButtons();
        return rootView;
    }

    private void updateButtons() {
        if (Data.STONESWORD.isCrafted()) {
            mHuntButton.setVisibility(View.VISIBLE);
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
        Data.HIDE.random();
    }
}