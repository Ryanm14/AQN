package me.ryanmiles.aqn.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ryanmiles.aqn.MainActivity;
import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.model.Loot;

/**
 * Created by Ryan Miles on 6/12/2016.
 */
public class PlayerFragment extends Fragment {

    ActionBar actionBar;
    @BindView(R.id.player_fragment_text_view)
    TextView textView;

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
        View rootView = inflater.inflate(R.layout.player_fragment_layout, container, false);
        ButterKnife.bind(this, rootView);
        ((MainActivity) getActivity()).setActionBarTitle("Player");
        setupTextView();
        return rootView;
    }

    private void setupTextView() {
        textView.setText("Loot:");
        for (Loot loot : Data.ALL_LOOT) {
            if (loot.isDiscovered() && !loot.isStorageDisplay()) {
                textView.append("\n" + loot.getName());
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }
}
