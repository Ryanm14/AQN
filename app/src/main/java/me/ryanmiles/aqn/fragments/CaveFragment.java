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
import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.events.ChangeFragmentEvent;

/**
 * Created by ryanm on 5/5/2016.
 */
public class CaveFragment extends Fragment {

    @BindView(R.id.crafting)
    Button mCraftingButton;
    @BindView(R.id.village)
    Button mVillageButton;
    @BindView(R.id.quests)
    Button mQuestsButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cave_fragment_layout, container, false);
        ButterKnife.bind(this, rootView);
        mCraftingButton.setVisibility(View.INVISIBLE);
        mVillageButton.setVisibility(View.INVISIBLE);
        mQuestsButton.setVisibility(View.INVISIBLE);
        return rootView;
    }

    @OnClick(R.id.buildings)
    public void openBuildings() {
        EventBus.getDefault().post(new ChangeFragmentEvent(new BuildingFragment()));

    }




}
