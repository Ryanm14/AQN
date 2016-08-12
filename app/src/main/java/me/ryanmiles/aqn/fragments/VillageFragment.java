package me.ryanmiles.aqn.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easyandroidanimations.library.FadeInAnimation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.trans_fragment_village)
    ViewGroup mLayout;
    ActionBar actionBar;

    public VillageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_village, container, false);
        ButterKnife.bind(this, rootView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<People> discoveredPeople = new ArrayList<>();
        for (People people : Data.PEOPLE_LIST) {
            if (people.isDiscovered()) {
                discoveredPeople.add(people);
            }
        }
        mRecyclerView.setAdapter(new VillageAdapter(getActivity(), discoveredPeople));
        updateVillageInfo();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        VillageBackground background = new VillageBackground();
        if (background.getStatus().equals(AsyncTask.Status.PENDING)) {
            background.execute();
        }

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v(TAG, "setUserVisibleHint() called with: " + "isVisibleToUser = [" + isVisibleToUser + "]");
        if (isVisibleToUser && Data.OPENVILLAGE) {
            Data.OPENVILLAGE = false;
            new FadeInAnimation(mLayout).setDuration(5000).animate();
        }
    }
    private void updateVillageInfo() {
        mVillageInfoTextView.setText("Village Info:\nMax Population: " + People.VILLAGE_MAX_POPULATION + "\nCurrent Population: " + People.VILLAGE_CURRENT_POPULATION + "\n\nFood Needed per 5 Secs: " + People.FOOD_NEEDED * People.VILLAGE_CURRENT_POPULATION);
    }

    @Subscribe
    public void onEvent(UpdateVillageInfo event) {
        updateVillageInfo();
    }

    @Override
    public void onStop() {
        Log.v(TAG, "onStop() called");
        super.onStop();
    }

    public class VillageBackground extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            while (true) {
                SystemClock.sleep(5000);
                publishProgress();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            if (People.checkFood(Data.FARMER.getIncrease())) {
                for (People people : Data.PEOPLE_LIST) {
                    people.post();
                }
            }
            People.updateAll();
        }
    }
}
