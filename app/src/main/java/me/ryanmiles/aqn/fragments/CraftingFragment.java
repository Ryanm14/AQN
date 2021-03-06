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
import me.ryanmiles.aqn.data.model.CraftedItem;
import me.ryanmiles.aqn.events.DataUpdateEvent;

/**
 * Created by ryanm on 5/7/2016.
 */
public class CraftingFragment extends Fragment {

    private static final String TAG = "CraftingFragment";
    static Button mCompleteButton;
    static CircleProgressView circle;
    @BindView(R.id.current_crafting_text_view)
    TextView mCurrentCraftingTextView;
    @BindView(R.id.crafting_fragment_linear_layout)
    LinearLayout mLinearLayout;
    ActionBar actionBar;
    CraftedItem mCurrentCrafting;

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
        View rootView = inflater.inflate(R.layout.crafting_fragment_layout, container, false);
        circle = (CircleProgressView) rootView.findViewById(R.id.circleView_crafting);
        mCompleteButton = (Button) rootView.findViewById(R.id.complete_button_crafting);
        ((MainActivity) getActivity()).setActionBarTitle("Crafting");
        ButterKnife.bind(this, rootView);
        setCurrentItem();


        //First Run
        if (Data.OPENCRAFTING) {
            Log.d(TAG, "onCreateView: First Run");
            Data.OPENCRAFTING = false;
            new FadeInAnimation(mLinearLayout).setDuration(5000).animate();
        }

        //Set Current Building Values
        if (mCurrentCrafting != null) {

            Log.d(TAG, "onCreateView: Current Crafting != null");
            mCurrentCraftingTextView.setText("Crafting: " + mCurrentCrafting.getName());
            circle.setValue(mCurrentCrafting.getCurrentProgress());

            //Check for completed Building
            if (!mCurrentCrafting.isReadyForCompletion()) {
                mCompleteButton.setEnabled(false);
            }

            //Resume if app was off
            if (Data.CRAFTING_NEW_DATA) {
                Data.CRAFTING_NEW_DATA = false;
                resumeCrafting();
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
        for (CraftedItem item : Data.ALL_CRAFTED_ITEMS) {
            if (item.isBeingCrafted()) {
                Log.d(TAG, "setCurrentItem: " + item.getName());
                mCurrentCrafting = item;
                break;
            }
        }
    }


    private void updateItemButtons() {
        Log.d(TAG, "updateItemButtons() called");
        mLinearLayout.removeAllViews();
        for (final CraftedItem item : Data.ALL_CRAFTED_ITEMS) {
            if (item.isDiscovered() && !item.isCrafted()) {

                final Button bt = new Button(getActivity());
                bt.setText(item.getName());
                bt.setBackground(getResources().getDrawable(R.drawable.button_shape));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        375, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(15, 0, 0, 15);
                bt.setLayoutParams(params);
                if (item.isBeingCrafted()) {
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
                                        if (item.checkRequiredItems()) {
                                            if (mCurrentCrafting == null || (!mCurrentCrafting.isBeingCrafted() && !mCurrentCrafting.isReadyForCompletion())) {
                                                item.setBeingCrafted(true);
                                                item.removeRequiredItems();
                                                startCrafting(item);
                                                EventBus.getDefault().post(new DataUpdateEvent(true, item.startingLogText()));
                                                bt.setEnabled(false);
                                            } else {
                                                Toast.makeText(getActivity(), "Already Crafting!", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(getActivity(), "You need more supplies!", Toast.LENGTH_LONG).show();
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

    public void startCrafting(CraftedItem item) {
        Log.d(TAG, "startCrafting() called with: " + "item = [" + item + "]");
        //Save to Building Class
        item.setStartTime(System.currentTimeMillis());
        mCurrentCrafting = item;
        Data.CRAFTING_NEW_DATA = false;

        //Set Views
        mCurrentCraftingTextView.setText("Crafting: " + item.getName());
        circle.setValue(0);

        //Start Background
        new CraftingBackground().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new int[]{0, item.getTimeToComplete()});
    }

    private void resumeCrafting() {
        Log.d(TAG, "resumeCrafting() called");
        //Set Views
        mCurrentCraftingTextView.setText("Crafting: " + mCurrentCrafting.getName());
        circle.setValue(mCurrentCrafting.getCurrentProgress());

        //get time left
        int timeLeft = (int) ((System.currentTimeMillis() - mCurrentCrafting.getStartTime()) / 1000);
        Log.d(TAG, "resumeCrafting: " + timeLeft);
        if (timeLeft >= mCurrentCrafting.getTimeToComplete()) {
            mCurrentCrafting.setReadyForCompletion(true);
            mCompleteButton.setEnabled(true);
            circle.setValue(100);
        } else {
            //Start Background
            new CraftingBackground().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new int[]{timeLeft, mCurrentCrafting.getTimeToComplete()});
        }
    }

    @OnClick(R.id.complete_button_crafting)
    public void onClickComplete() {
        Log.d(TAG, "onClickComplete() called");
        //Set UI
        mCompleteButton.setEnabled(false);
        circle.setValue(0);
        mCurrentCraftingTextView.setText("Crafting:");

        //Handle Built
        mCurrentCrafting.build();
        updateItemButtons();
        EventBus.getDefault().post(new DataUpdateEvent(true, mCurrentCrafting.getLogText()));
    }

    public class CraftingBackground extends AsyncTask<int[], Integer, Integer> {
        @Override
        protected Integer doInBackground(int[]... params) {
            Log.d(TAG, "Started work on: " + mCurrentCrafting.getName());
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
            mCurrentCrafting.setCurrentProgress(value[0]);
            circle.setValue(value[0]);
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mCurrentCrafting.setReadyForCompletion(true);
            mCompleteButton.setEnabled(true);
            Log.v(TAG, "onPostExecute - Finished: " + mCurrentCrafting.getName());
        }
    }
}

