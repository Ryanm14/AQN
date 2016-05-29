package me.ryanmiles.aqn.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.tin_button)
    Button mTinButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.crafting_fragment_layout, container, false);
        ButterKnife.bind(this, rootView);
        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.crafting_fragment_linear_layout);
        updateCraftingButtons();
        updateMaterialButtons();
        ((MainActivity) getActivity()).setActionBarTitle("Crafting");
        return rootView;
    }

    private void updateMaterialButtons() {
        if (!Data.BASIC_SMELTERY.isBuilt()) {
            mTinButton.setVisibility(View.GONE);
        }
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

    @OnClick(R.id.tin_button)
    public void tinButtonOnClick() {
        Dialog dialog = new AlertDialogWrapper.Builder(getActivity())
                .setTitle("Item: " + Data.TIN_BULLION.getName())
                .setMessage("Needed Resources: \nTin: 1 \nWood:10")
                .setPositiveButton("Construct a " + Data.TIN_BULLION.getName(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Data.TIN.getAmount() >= 1 && Data.WOOD.getAmount() >= 10) {
                            EventBus.getDefault().post(new LogUpdateEvent("You smelted a Tin Bullion"));
                            Data.TIN.setDiscovered(true);
                            Data.TIN_BULLION.addIncrement();
                            Data.WOOD.remove(10);
                            Data.TIN.remove(1);
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
}
