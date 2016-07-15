package me.ryanmiles.aqn.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.easyandroidanimations.library.FadeInAnimation;

import org.greenrobot.eventbus.EventBus;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ryanmiles.aqn.MainActivity;
import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.model.Building;
import me.ryanmiles.aqn.events.DataUpdateEvent;

/**
 * Created by ryanm on 5/7/2016.
 */
public class BuildingFragment extends Fragment {

    LinearLayout mLinearLayout;
    ActionBar actionBar;
    CircleProgressView circle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        actionBar = ((AppCompatActivity) context).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.building_fragment_layout, container, false);
        ButterKnife.bind(this, rootView);
        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.buildings_fragment_linear_layout);
        circle = (CircleProgressView) rootView.findViewById(R.id.circleView);
        updateBuildingButtons();
        ((MainActivity) getActivity()).setActionBarTitle("Buildings");
        if (Data.OPENBUILDINGS) {
            Data.OPENBUILDINGS = false;
            new FadeInAnimation(mLinearLayout).setDuration(5000).animate();
        }
        return rootView;
    }

    private void updateBuildingButtons() {
        mLinearLayout.removeAllViews();
        for (final Building building : Data.ALL_BUILDINGS) {
            if (building.isDiscovered() && !building.isBuilt()) {

                final Button bt = new Button(getActivity());
                bt.setText(building.getName());
                bt.setBackground(getResources().getDrawable(R.drawable.button_shape));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        375, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(15, 0, 0, 15);
                bt.setLayoutParams(params);

                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new AlertDialogWrapper.Builder(getActivity())
                                .setTitle("Building: " + building.getName())
                                .setMessage(building.getContentString())
                                .setPositiveButton("Build a " + building.getName(), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (building.build()) {
                                            startBuilding(0, 100);
                                            EventBus.getDefault().post(new DataUpdateEvent(true, building.getLogText()));
                                            mLinearLayout.removeView(bt);
                                          updateBuildingButtons();
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(getActivity(), "You need more supplies!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                });
                mLinearLayout.addView(bt);
            }
        }
    }

    @OnClick(R.id.buildingText)
    public void onClick(){
        startBuilding(0,8);
    }
    @Override
    public void onStop() {
        super.onStop();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    public void startBuilding(int start, int max) {
        BuildingsBackground background = new BuildingsBackground();
        background.execute(new int[]{start,max});
        circle.setValue(start);
    }


    public class BuildingsBackground extends AsyncTask<int[], Integer, Integer> {
        @Override
        protected Integer doInBackground(int[]... params) {
            int[] data = params[0];
            int wait = data[1] * 1000 / 100;
            int start = data[0];
            for (int i = start ; i < 100; i++) {
                SystemClock.sleep(wait);
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            circle.setValue(value[0]);
        }
    }
}

