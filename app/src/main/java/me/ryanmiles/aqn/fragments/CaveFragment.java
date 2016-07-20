package me.ryanmiles.aqn.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easyandroidanimations.library.FadeInAnimation;
import com.transitionseverywhere.AutoTransition;
import com.transitionseverywhere.TransitionManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ryanmiles.aqn.App;
import me.ryanmiles.aqn.MainActivity;
import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.events.ChangeFragmentEvent;
import me.ryanmiles.aqn.events.ChangeWorldEvent;
import me.ryanmiles.aqn.events.updates.UpdateTabHost;

/**
 * Created by ryanm on 5/5/2016.
 */
public class CaveFragment extends Fragment {

    private static final String TAG = CaveFragment.class.getCanonicalName();
    @BindView(R.id.crafting)
    Button mCraftingButton;
    @BindView(R.id.village)
    Button mVillageButton;
    @BindView(R.id.quests)
    Button mQuestsButton;
    @BindView(R.id.buildings)
    Button mBuildingsButton;
    @BindView(R.id.hideInCaveButton)
    Button mHideInCaveButton;
    @BindView(R.id.exploreForest)
    Button mExploreForestButton;
    @BindView(R.id.transitions_container_fragment_cave)
    ViewGroup mTransitionsContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView()");
        View rootView = inflater.inflate(R.layout.cave_fragment_layout, container, false);
        ButterKnife.bind(this, rootView);
        ((MainActivity) getActivity()).setActionBarTitle("A Quiet Night");
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
        mExploreForestButton.setVisibility(View.GONE);
        if (!Data.TOOLBENCH.isBuilt()) {
            mCraftingButton.setVisibility(View.INVISIBLE);
        } else if (Data.OPENCRAFTING) {
            Data.OPENCRAFTING = false;
            new FadeInAnimation(mCraftingButton).setDuration(5000).animate();
        }

        if (!Data.FOUNDATION.isBuilt()) {
            mVillageButton.setVisibility(View.INVISIBLE);
        } else if (Data.OPENVILLAGE) {
            Data.OPENVILLAGE = false;
            new FadeInAnimation(mVillageButton).setDuration(5000).animate();
        }
            mQuestsButton.setVisibility(View.INVISIBLE);
        if(Data.FIRSTRUN){
            new FadeInAnimation(mHideInCaveButton).setDuration(5000).animate();
            mBuildingsButton.setVisibility(View.INVISIBLE);
            Data.FIRSTRUN = false;
        }else{
            mHideInCaveButton.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.hideInCaveButton)
    public void caveButton(){
        Log.d(TAG, "caveButton() called");
        TransitionManager.beginDelayedTransition(mTransitionsContainer, new AutoTransition().setDuration(6000));
        mHideInCaveButton.setClickable(false);
        mHideInCaveButton.setVisibility(View.GONE);
        mExploreForestButton.setVisibility(View.VISIBLE);
        EventBus.getDefault().post(new UpdateTabHost("Cave"));
        Data.postLogText("You struggle into the cave as the growling noise and the world fades.");
    }

    @OnClick(R.id.exploreForest)
    public void exploreForestButton(){
        Log.d(TAG, "exploreForestButton() called");
        TransitionManager.beginDelayedTransition(mTransitionsContainer, new AutoTransition().setDuration(6000));
        mExploreForestButton.setClickable(false);
        mExploreForestButton.setVisibility(View.GONE);
        mBuildingsButton.setVisibility(View.VISIBLE);
        EventBus.getDefault().post(new UpdateTabHost("Forest"));
        Data.postLogText("The early sun blinds your eyes as you walk out... There are plentiful sticks and stones around.\n");
        Data.toastMessage(getActivity(),"New Area Discovered");
        Data.OPENFOREST = true;
        Data.OPENBUILDINGS = true;
        Data.OPENCRAFTING = true;
        Data.OPENVILLAGE = true;
    }

    @OnClick(R.id.buildings)
    public void openBuildings() {
        Log.d(TAG, "openBuildings() called");
        EventBus.getDefault().post(new ChangeFragmentEvent(new BuildingFragment(), "buildingFragment"));

    }

    @OnClick(R.id.crafting)
    public void openCrafting() {
        Log.d(TAG, "openCrafting() called");
        EventBus.getDefault().post(new ChangeFragmentEvent(new CraftingFragment(), "craftingFragment"));
    }

    @OnClick(R.id.village)
    public void openVillage() {
        EventBus.getDefault().post(new ChangeFragmentEvent(new VillageFragment(), "villageFragment"));
    }

    @OnClick(R.id.quests)
    public void openWorld() {
        EventBus.getDefault().post(new ChangeWorldEvent());
    }

    @Override
    public void onPause() {
        Log.v(TAG, "onPause() called");
        super.onPause();
        App.saveData("");
    }
}
