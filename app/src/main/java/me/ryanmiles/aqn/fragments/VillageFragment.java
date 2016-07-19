package me.ryanmiles.aqn.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ryanmiles.aqn.MainActivity;
import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.adapters.VillageAdapter;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.model.People;
import me.ryanmiles.aqn.events.updates.UpdateVillageInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class VillageFragment extends Fragment {

    private static final String TAG = VillageFragment.class.getCanonicalName();

    @BindView(R.id.village_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.villageInfoTextView)
    TextView mVillageInfoTextView;
    ActionBar actionBar;

    public VillageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        Log.v(TAG, "onAttach()");
        super.onAttach(context);
        actionBar = ((AppCompatActivity) context).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_village, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Village");
        ButterKnife.bind(this, rootView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new VillageAdapter(getActivity(), Data.PEOPLE_LIST));
        updateVillageInfo();
        EventBus.getDefault().register(this);
        return rootView;
    }

    private void updateVillageInfo() {
        mVillageInfoTextView.setText("Village Info:\nMax Population: " + People.VILLAGE_MAX_POPULATION + "\nCurrent Population: " + People.VILLAGE_CURRENT_POPULATION + "\n\nFood per 5 Secs: " + People.FOOD_NEEDED * People.VILLAGE_CURRENT_POPULATION);
    }

    @Subscribe
    public void onEvent(UpdateVillageInfo event) {
        updateVillageInfo();
    }

    @Override
    public void onStop() {
        Log.v(TAG, "onStop() called");
        super.onStop();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

}
