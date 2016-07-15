package me.ryanmiles.aqn.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    @BindView(R.id.crafting)
    Button mCraftingButton;
    @BindView(R.id.stats)
    Button mStatsButton;
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
        View rootView = inflater.inflate(R.layout.cave_fragment_layout, container, false);
        ButterKnife.bind(this, rootView);
        ((MainActivity) getActivity()).setActionBarTitle("A Quiet Night");
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        updateButtons();
    }

    private void updateButtons() {
        mExploreForestButton.setVisibility(View.GONE);
        if (!Data.TOOLBENCH.isBuilt()) {
            mCraftingButton.setVisibility(View.INVISIBLE);
        }
        if (!Data.STONESWORD.isCrafted()) {
            mQuestsButton.setVisibility(View.INVISIBLE);
        }
        if (!Data.COPPER.isDiscovered()) {
            mStatsButton.setVisibility(View.INVISIBLE);
        }
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
        TransitionManager.beginDelayedTransition(mTransitionsContainer, new AutoTransition().setDuration(8000));
        mHideInCaveButton.setClickable(false);
        mHideInCaveButton.setVisibility(View.GONE);
        mExploreForestButton.setVisibility(View.VISIBLE);
        EventBus.getDefault().post(new UpdateTabHost("Cave"));
        Data.postLogText("You struggle into the cave as the growling noise and the world fades.");
    }

    @OnClick(R.id.exploreForest)
    public void exploreForestButton(){
        TransitionManager.beginDelayedTransition(mTransitionsContainer, new AutoTransition().setDuration(7000));
        mExploreForestButton.setClickable(false);
        mExploreForestButton.setVisibility(View.GONE);
        mBuildingsButton.setVisibility(View.VISIBLE);
        EventBus.getDefault().post(new UpdateTabHost("Forest"));
        Data.postLogText("The early sun blinds your eyes as you walk out... There are plentiful sticks and stones around.\n");
        Data.toastMessage(getActivity(),"New Area Discovered");
        Data.OPENFOREST = true;
        Data.OPENBUILDINGS = true;
    }

    @OnClick(R.id.buildings)
    public void openBuildings() {
        EventBus.getDefault().post(new ChangeFragmentEvent(new BuildingFragment(), "buildingFragment"));

    }

    @OnClick(R.id.crafting)
    public void openCrafting() {
        EventBus.getDefault().post(new ChangeFragmentEvent(new CraftingFragment(), "craftingFragment"));
    }

    @OnClick(R.id.stats)
    public void openStats() {
        EventBus.getDefault().post(new ChangeFragmentEvent(new PlayerFragment(), "playerFragment"));
    }

    @OnClick(R.id.quests)
    public void openWorld() {
        EventBus.getDefault().post(new ChangeWorldEvent());
    }

    @Override
    public void onPause() {
        super.onPause();
        App.saveData("");
    }
}
