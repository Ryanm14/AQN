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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.easyandroidanimations.library.FadeInAnimation;

import org.greenrobot.eventbus.EventBus;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;
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

    private static final String TAG = "BuildingFragment";
    static Button mCompleteButton;
    static CircleProgressView circle;
    @BindView(R.id.current_building_text_view)
    TextView mCurrentBuildingTextView;
    @BindView(R.id.buildings_fragment_linear_layout)
    LinearLayout mLinearLayout;
    ActionBar actionBar;
    Building mCurrentBuilding;
    BuildingsBackground background;

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

        //Declarations
        View rootView = inflater.inflate(R.layout.building_fragment_layout, container, false);
        circle = (CircleProgressView) rootView.findViewById(R.id.circleView);
        mCompleteButton = (Button) rootView.findViewById(R.id.complete_button);
        background = new BuildingsBackground();
        ((MainActivity) getActivity()).setActionBarTitle("Buildings");
        ButterKnife.bind(this, rootView);
        setCurrentBuilding();


        //First Run
        if (Data.OPENBUILDINGS) {
            Data.OPENBUILDINGS = false;
            new FadeInAnimation(mLinearLayout).setDuration(5000).animate();
        }

        //Set Current Building Values
        if (mCurrentBuilding != null) {
            mCurrentBuildingTextView.setText("Building: " + mCurrentBuilding.getName());
            circle.setValue(mCurrentBuilding.getCurrentProgress());

            //Check for completed Building
            if (!mCurrentBuilding.isReadyForCompletion()) {
                mCompleteButton.setEnabled(false);
            }

            //Resume if app was off
            if (Data.BUILDING_NEW_DATA) {
                Data.BUILDING_NEW_DATA = false;
                resumeBuilding();
            }

        } else {
            mCurrentBuildingTextView.setText("Building: ");
            circle.setValue(0);
            mCompleteButton.setEnabled(false);
        }


        //Setup building buttons
        updateBuildingButtons();

        return rootView;
    }


    private void setCurrentBuilding() {

        for (Building building : Data.ALL_BUILDINGS) {
            if (building.isBeingBuilt()) {
                mCurrentBuilding = building;
                break;
            }
        }
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
                if (building.isBeingBuilt()) {
                    bt.setEnabled(false);
                }
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new AlertDialogWrapper.Builder(getActivity())
                                .setTitle("Building: " + building.getName())
                                .setMessage(building.getContentString())
                                .setPositiveButton("Build a " + building.getName(), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (building.checkRequiredItems()) {
                                            building.setBeingBuilt(true);
                                            building.removeRequiredItems();
                                            startBuilding(building);
                                            EventBus.getDefault().post(new DataUpdateEvent(true, building.startingLogText()));
                                            bt.setEnabled(false);
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


    @Override
    public void onStop() {
        super.onStop();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    public void startBuilding(Building building) {
        //Save to Building Class
        building.setStartTime(System.currentTimeMillis());
        mCurrentBuilding = building;


        //Set Views
        mCurrentBuildingTextView.setText("Building: " + building.getName());
        circle.setValue(0);

        //Start Background

        background.execute(new int[]{0, building.getTimeToComplete()});
    }

    private void resumeBuilding() {
        //Set Views
        mCurrentBuildingTextView.setText("Building: " + mCurrentBuilding.getName());
        circle.setValue(mCurrentBuilding.getCurrentProgress());

        //get time left
        int timeLeft = (int) ((System.currentTimeMillis() - mCurrentBuilding.getStartTime()) / 1000);
        if (timeLeft >= mCurrentBuilding.getTimeToComplete()) {
            mCurrentBuilding.setReadyForCompletion(true);
            mCompleteButton.setEnabled(true);
            circle.setValue(100);
        } else {
            //Start Background
            background.execute(new int[]{timeLeft, mCurrentBuilding.getTimeToComplete()});
        }
    }

    @OnClick(R.id.complete_button)
    public void onClickComplete() {
        //Set UI
        mCompleteButton.setEnabled(false);
        circle.setValue(0);
        mCurrentBuildingTextView.setText("Building:");

        //Handle Built
        mCurrentBuilding.build();
        updateBuildingButtons();
        EventBus.getDefault().post(new DataUpdateEvent(true, mCurrentBuilding.getLogText()));
    }

    public class BuildingsBackground extends AsyncTask<int[], Integer, Integer> {
        @Override
        protected Integer doInBackground(int[]... params) {
            //Get numbers
            int[] data = params[0];
            int wait = data[1] * 1000 / 100;
            int start = data[0];

            //Each %
            for (int i = start; i <= 100; i++) {
                SystemClock.sleep(wait);
                publishProgress(i);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            mCurrentBuilding.setCurrentProgress(value[0]);
            circle.setValue(value[0]);
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mCurrentBuilding.setReadyForCompletion(true);
            mCompleteButton.setEnabled(true);
            Log.i(TAG, "onPostExecute: 100");
        }
    }
}

