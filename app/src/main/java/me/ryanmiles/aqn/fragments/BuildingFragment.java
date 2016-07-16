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

    private static final String TAG = BuildingFragment.class.getCanonicalName();
    static Button mCompleteButton;
    static CircleProgressView circle;
    @BindView(R.id.current_building_text_view)
    TextView mCurrentBuildingTextView;
    @BindView(R.id.buildings_fragment_linear_layout)
    LinearLayout mLinearLayout;
    ActionBar actionBar;
    Building mCurrentBuilding;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView()");

        //Declarations
        View rootView = inflater.inflate(R.layout.building_fragment_layout, container, false);
        circle = (CircleProgressView) rootView.findViewById(R.id.circleView);
        mCompleteButton = (Button) rootView.findViewById(R.id.complete_button);
        ((MainActivity) getActivity()).setActionBarTitle("Buildings");
        ButterKnife.bind(this, rootView);
        setCurrentBuilding();


        //First Run
        if (Data.OPENBUILDINGS) {
            Log.d(TAG, "onCreateView: First Run");
            Data.OPENBUILDINGS = false;
            new FadeInAnimation(mLinearLayout).setDuration(5000).animate();
        }

        //Set Current Building Values
        if (mCurrentBuilding != null) {
            Log.d(TAG, "onCreateView: Current Building != null");
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
            Log.d(TAG, "onCreateView: Current Building = null");
            mCurrentBuildingTextView.setText("Building: ");
            circle.setValue(0);
            mCompleteButton.setEnabled(false);
        }


        //Setup building buttons
        updateBuildingButtons();

        return rootView;
    }


    private void setCurrentBuilding() {
        Log.v(TAG, "setCurrentBuilding() called");
        for (Building building : Data.ALL_BUILDINGS) {
            if (building.isBeingBuilt()) {
                mCurrentBuilding = building;
                Log.d(TAG, "setCurrentBuilding: " + building.getName());
                break;
            }
        }
    }


    private void updateBuildingButtons() {
        Log.d(TAG, "updateBuildingButtons() called");
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
        Log.v(TAG, "onStop() called");
        super.onStop();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    public void startBuilding(Building building) {
        Log.d(TAG, "startBuilding() called with: " + "building = [" + building + "]");
        //Save to Building Class
        building.setStartTime(System.currentTimeMillis());
        mCurrentBuilding = building;


        //Set Views
        mCurrentBuildingTextView.setText("Building: " + building.getName());
        circle.setValue(0);

        //Start Background
        new BuildingsBackground().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new int[]{0, building.getTimeToComplete()});
    }

    private void resumeBuilding() {
        Log.d(TAG, "resumeBuilding() called");
        //Set Views
        mCurrentBuildingTextView.setText("Building: " + mCurrentBuilding.getName());
        circle.setValue(mCurrentBuilding.getCurrentProgress());

        //get time left
        int timeLeft = (int) ((System.currentTimeMillis() - mCurrentBuilding.getStartTime()) / 1000);
        Log.d(TAG, "resumeBuilding: " + timeLeft);
        if (timeLeft >= mCurrentBuilding.getTimeToComplete()) {
            mCurrentBuilding.setReadyForCompletion(true);
            mCompleteButton.setEnabled(true);
            circle.setValue(100);
        } else {
            //Start Background
            new BuildingsBackground().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new int[]{timeLeft, mCurrentBuilding.getTimeToComplete()});
        }
    }

    @OnClick(R.id.complete_button)
    public void onClickComplete() {
        Log.d(TAG, "onClickComplete() called");
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
            Log.d(TAG, "Started work on: " + mCurrentBuilding.getName());
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
            Log.v(TAG, "ProgressUpdate: " + value[0]);
            mCurrentBuilding.setCurrentProgress(value[0]);
            circle.setValue(value[0]);
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mCurrentBuilding.setReadyForCompletion(true);
            mCompleteButton.setEnabled(true);
            Log.v(TAG, "onPostExecute - Finished: " + mCurrentBuilding.getName());
        }
    }
}

