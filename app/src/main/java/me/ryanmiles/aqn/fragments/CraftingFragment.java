package me.ryanmiles.aqn.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;

import org.greenrobot.eventbus.EventBus;

import me.ryanmiles.aqn.MainActivity;
import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.model.CraftedItem;
import me.ryanmiles.aqn.events.LogUpdateEvent;

/**
 * Created by ryanm on 5/7/2016.
 */
public class CraftingFragment extends Fragment {

    LinearLayout mLinearLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.crafting_fragment_layout, container, false);
        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.crafting_fragment_linear_layout);
        updateCraftingButtons();
        ((MainActivity) getActivity()).setActionBarTitle("Crafting");
        return rootView;
    }

    private void updateCraftingButtons() {
        mLinearLayout.removeAllViews();
        for (final CraftedItem craftedItem : Data.ALL_CRAFTED_ITEMS) {
            if (craftedItem.isDiscovered() && !craftedItem.isCrafted()) {
                final Button bt = new Button(getActivity());
                bt.setText(craftedItem.getName());
                bt.setBackground(getResources().getDrawable(R.drawable.button_shape));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        375, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(15, 0, 0, 15);
                bt.setLayoutParams(params);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new AlertDialogWrapper.Builder(getActivity())
                                .setTitle("Item: " + craftedItem.getName())
                                .setMessage(craftedItem.getContentString())
                                .setPositiveButton("Construct a " + craftedItem.getName(), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (craftedItem.craft()) {
                                            EventBus.getDefault().post(new LogUpdateEvent(craftedItem.getLogText()));
                                            mLinearLayout.removeView(bt);
                                            updateCraftingButtons();
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


}
