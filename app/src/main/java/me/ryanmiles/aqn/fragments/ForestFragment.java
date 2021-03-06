package me.ryanmiles.aqn.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    private static final String TAG = ForestFragment.class.getCanonicalName();
    @BindView(R.id.wood)
    Button mWoodButton;
    @BindView(R.id.stone)
    Button mStoneButton;
    @BindView(R.id.water)
    Button mWaterButton;
    @BindView(R.id.gather_food)
    Button mFoodButton;
    @BindView(R.id.trans_container_forest_fragment)
    ViewGroup mLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView()");
        View rootView =  inflater.inflate(R.layout.forest_fragment_layout, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        Log.v(TAG, "onResume() called");
        super.onResume();
        updateButtons();
    }

    private void updateButtons() {
        Log.d(TAG, "updateButtons() called");
        if (!Data.FARMING.isResearched()) {
            mFoodButton.setVisibility(View.INVISIBLE);
        }
        if (!Data.FLASK.isCrafted()) {
            mWaterButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v(TAG, "setUserVisibleHint() called with: " + "isVisibleToUser = [" + isVisibleToUser + "]");
        if (isVisibleToUser && Data.OPENFOREST) {
            Data.OPENFOREST = false;
            new FadeInAnimation(mLayout).setDuration(5000).animate();
        }
    }

    @OnClick(R.id.wood)
    public void woodOnClick(){
        Log.v(TAG, "woodOnClick() called");
        Data.WOOD.addIncrement();
    }

    @OnClick(R.id.stone)
    public void stoneOnClick(){
        Log.v(TAG, "stoneOnClick() called");
        Data.STONE.addIncrement();

    }

    @OnClick(R.id.gather_food)
    public void huntOnClick() {
        Data.FOOD.addIncrement();
    }

    @OnClick(R.id.water)
    public void waterOnClick() {
        Data.WATER.addIncrement();
    }
}