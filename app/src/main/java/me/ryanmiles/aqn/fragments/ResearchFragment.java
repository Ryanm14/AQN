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
import me.ryanmiles.aqn.data.model.Research;
import me.ryanmiles.aqn.events.DataUpdateEvent;

/**
 * Created by ryanm on 5/7/2016.
 */
public class ResearchFragment extends Fragment {

    private static final String TAG = ResearchFragment.class.getCanonicalName();
    static Button mCompleteButton;
    static CircleProgressView circle;
    @BindView(R.id.current_research_text_view)
    TextView mCurrentCraftingTextView;
    @BindView(R.id.research_fragment_linear_layout)
    LinearLayout mLinearLayout;
    ActionBar actionBar;
    Research mCurrentResearch;

    //Change circles colors for each thing

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach() called");
        actionBar = ((AppCompatActivity) context).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView()");

        //Declarations
        View rootView = inflater.inflate(R.layout.research_fragment, container, false);
        circle = (CircleProgressView) rootView.findViewById(R.id.circleView_research);
        mCompleteButton = (Button) rootView.findViewById(R.id.complete_button_research);
        ((MainActivity) getActivity()).setActionBarTitle("Research");
        ButterKnife.bind(this, rootView);
        setCurrentItem();


        //First Run
        if (Data.OPENRESEARCH) {
            Log.d(TAG, "onCreateView: First Run");
            Data.OPENRESEARCH = false;
            new FadeInAnimation(mLinearLayout).setDuration(5000).animate();
        }

        //Set Current Building Values
        if (mCurrentResearch != null) {

            Log.d(TAG, "onCreateView: Current Crafting != null");
            mCurrentCraftingTextView.setText("Crafting: " + mCurrentResearch.getName());
            circle.setValue(mCurrentResearch.getCurrentProgress());

            //Check for completed Building
            if (!mCurrentResearch.isReadyForCompletion()) {
                mCompleteButton.setEnabled(false);
            }

            //Resume if app was off
            if (Data.RESEARCH_NEW_DATA) {
                Data.RESEARCH_NEW_DATA = false;
                resumeResearch();
            }

        } else {

            Log.d(TAG, "onCreateView: Current Crafting = null");
            mCurrentCraftingTextView.setText("Crafting: ");
            circle.setValue(0);
            mCompleteButton.setEnabled(false);
        }


        //Setup building buttons
        updateItemButtons();

        return rootView;
    }


    private void setCurrentItem() {
        Log.v(TAG, "setCurrentItem() called");
        for (Research item : Data.ALL_RESEARCH) {
            if (item.isBeingResearched()) {
                Log.d(TAG, "setCurrentItem: " + item.getName());
                mCurrentResearch = item;
                break;
            }
        }
    }


    private void updateItemButtons() {
        Log.d(TAG, "updateItemButtons() called");
        mLinearLayout.removeAllViews();
        for (final Research item : Data.ALL_RESEARCH) {
            if (item.isDiscovered() && !item.isResearched()) {

                final Button bt = new Button(getActivity());
                bt.setText(item.getName());
                bt.setBackground(getResources().getDrawable(R.drawable.button_shape));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        375, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(15, 0, 0, 15);
                bt.setLayoutParams(params);
                if (item.isBeingResearched()) {
                    bt.setEnabled(false);
                }
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new AlertDialogWrapper.Builder(getActivity())
                                .setTitle("Item: " + item.getName())
                                .setMessage(item.getContentString())
                                .setPositiveButton("Craft a " + item.getName(), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (mCurrentResearch == null || !mCurrentResearch.isDiscovered()) {
                                            item.setBeingResearched(true);
                                            startResearch(item);
                                            EventBus.getDefault().post(new DataUpdateEvent(true, item.startingLogText()));
                                            bt.setEnabled(false);
                                            updateItemButtons();
                                        } else {
                                            Toast.makeText(getActivity(), "Already Researching!", Toast.LENGTH_LONG).show();
                                        }
                                        dialog.dismiss();
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

    public void startResearch(Research item) {
        Log.d(TAG, "startResearch() called with: " + "item = [" + item + "]");
        //Save to Building Class
        item.setStartTime(System.currentTimeMillis());
        mCurrentResearch = item;
        Data.RESEARCH_NEW_DATA = false;

        //Set Views
        mCurrentCraftingTextView.setText("Crafting: " + item.getName());
        circle.setValue(0);

        //Start Background
        new ResearchBackground().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new int[]{0, item.getTimeToComplete()});
    }

    private void resumeResearch() {
        Log.d(TAG, "resumeResearch() called");
        //Set Views
        mCurrentCraftingTextView.setText("Crafting: " + mCurrentResearch.getName());
        circle.setValue(mCurrentResearch.getCurrentProgress());

        //get time left
        int timeLeft = (int) ((System.currentTimeMillis() - mCurrentResearch.getStartTime()) / 1000);
        Log.d(TAG, "resumeResearch: " + timeLeft);
        if (timeLeft >= mCurrentResearch.getTimeToComplete()) {
            mCurrentResearch.setReadyForCompletion(true);
            mCompleteButton.setEnabled(true);
            circle.setValue(100);
        } else {
            //Start Background
            new ResearchBackground().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new int[]{timeLeft, mCurrentResearch.getTimeToComplete()});
        }
    }

    @OnClick(R.id.complete_button_research)
    public void onClickComplete() {
        Log.d(TAG, "onClickComplete() called");
        //Set UI
        mCompleteButton.setEnabled(false);
        circle.setValue(0);
        mCurrentCraftingTextView.setText("Researching:");

        //Handle Built
        mCurrentResearch.research();
        updateItemButtons();
        EventBus.getDefault().post(new DataUpdateEvent(true, mCurrentResearch.getLogText()));
    }

    public class ResearchBackground extends AsyncTask<int[], Integer, Integer> {
        @Override
        protected Integer doInBackground(int[]... params) {
            Log.d(TAG, "Started work on: " + mCurrentResearch.getName());
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
            mCurrentResearch.setCurrentProgress(value[0]);
            circle.setValue(value[0]);
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mCurrentResearch.setReadyForCompletion(true);
            mCompleteButton.setEnabled(true);
            Log.v(TAG, "onPostExecute - Finished: " + mCurrentResearch.getName());
        }
    }
}

