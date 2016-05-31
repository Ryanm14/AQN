package me.ryanmiles.aqn.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cave_fragment_layout, container, false);
        ButterKnife.bind(this, rootView);
        mCraftingButton.setVisibility(View.INVISIBLE);
        mStatsButton.setVisibility(View.INVISIBLE);
        mQuestsButton.setVisibility(View.INVISIBLE);
        ((MainActivity) getActivity()).setActionBarTitle("A Quiet Night");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateButtons();
    }

    private void updateButtons() {
        if (Data.TOOLBENCH.isBuilt()) {
            mCraftingButton.setVisibility(View.VISIBLE);
        }
        if (Data.STONESWORD.isCrafted()) {
            mQuestsButton.setVisibility(View.VISIBLE);
        }
        if (Data.COPPER.isDiscovered()) {
            mStatsButton.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.buildings)
    public void openBuildings() {
        EventBus.getDefault().post(new ChangeFragmentEvent(new BuildingFragment(), "buildingFragment"));

    }

    @OnClick(R.id.crafting)
    public void openCrafting() {
        EventBus.getDefault().post(new ChangeFragmentEvent(new CraftingFragment(), "craftingFragment"));
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
