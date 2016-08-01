package me.ryanmiles.aqn.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.WorldActivity;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.Places;
import me.ryanmiles.aqn.data.model.Coordinate;
import me.ryanmiles.aqn.data.model.Place;
import me.ryanmiles.aqn.events.ChangeWorldFragmentEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorldFragment extends Fragment {
    public static final int WORLD_RADIUS = 36;
    private static final String TAG = WorldFragment.class.getCanonicalName();
    ArrayList<Coordinate> mCoords;
    Coordinate mCurrentCord;
    Random rng;

    @BindView(R.id.world_fragment_area_text_view)
    TextView mAreaTextView;
    @BindView(R.id.world_fragment_storage_text_view)
    TextView mStorageTextView;

    public WorldFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_world, container, false);
        ButterKnife.bind(this, rootview);
        if (Data.WORLD_MAP == null) {
            populate();
        } else {
            mCoords = Data.WORLD_MAP;
            fixBug();
            mCurrentCord = findCord(WORLD_RADIUS / 2, WORLD_RADIUS / 4);
            update();
            mCurrentCord.changeValue("O");
        }
        return rootview;
    }

    private void updateItems() {
        mStorageTextView.setText("Water: " + WorldActivity.water_count + "/" + Data.WATER.getMax() + "  Food: " + WorldActivity.food_count + "/" + Data.FOOD.getMax());
        if (WorldActivity.water_count <= 0) {
            Toast.makeText(getActivity(), "Out of Water!", Toast.LENGTH_SHORT).show();
        }
    }

    private void fixBug() {
        for (Coordinate mCoord : mCoords) {
            if (mCoord.getValue().equals("O")) {
                mCoord.revertOldValue();
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoords = new ArrayList<>();
        rng = new Random();
    }

    @OnClick(R.id.world_fragment_right_button)
    public void right() {
        if (mCurrentCord.getX() < WORLD_RADIUS && WorldActivity.water_count > 0) {
            Coordinate newCord;
            mCurrentCord.getX();
            newCord = findCord(mCurrentCord.getX() + 1, mCurrentCord.getY());
            mCurrentCord.revertOldValue();
            newCord.changeValue("O");
            mCurrentCord = newCord;
            WorldActivity.water_count--;
            update();
        } else {
            updateItems();
        }
    }

    @OnClick(R.id.world_fragment_left_button)
    public void left() {
        if (mCurrentCord.getX() - 1 > 0 && WorldActivity.water_count > 0) {
            Coordinate newCord;
            mCurrentCord.getX();
            newCord = findCord(mCurrentCord.getX() - 1, mCurrentCord.getY());
            mCurrentCord.revertOldValue();
            newCord.changeValue("O");
            mCurrentCord = newCord;
            WorldActivity.water_count--;
            update();
        } else {
            updateItems();
        }
    }

    @OnClick(R.id.world_fragment_up_button)
    public void up() {
        if (mCurrentCord.getY() - 1 > 0 && WorldActivity.water_count > 0) {
            Coordinate newCord;
            mCurrentCord.getX();
            newCord = findCord(mCurrentCord.getX(), mCurrentCord.getY() - 1);
            mCurrentCord.revertOldValue();
            newCord.changeValue("O");
            mCurrentCord = newCord;
            WorldActivity.water_count--;
            update();
        } else {
            updateItems();
        }
    }

    @OnClick(R.id.world_fragment_down_button)
    public void down() {
        if (mCurrentCord.getY() < WORLD_RADIUS / 2 && WorldActivity.water_count > 0) {
            Coordinate newCord;
            mCurrentCord.getX();
            newCord = findCord(mCurrentCord.getX(), mCurrentCord.getY() + 1);
            mCurrentCord.revertOldValue();
            newCord.changeValue("O");
            mCurrentCord = newCord;
            WorldActivity.water_count--;
            update();

        } else {
            updateItems();
        }
    }

    private Coordinate findCord(int x, int y) {
        for (Coordinate coord : mCoords) {
            if (coord.getX() == x && coord.getY() == y) {
                return coord;
            }
        }
        return null;
    }


    private void populate() {
        for (int y = 1; y <= WORLD_RADIUS / 2; y++) {
            for (int x = 1; x <= WORLD_RADIUS; x++) {
                mCoords.add(new Coordinate(x, y));
            }
        }
        findCord(1, 1);
        mCurrentCord = findCord(WORLD_RADIUS / 2, WORLD_RADIUS / 4);
        mCurrentCord.changeValue("O");
        for (Place places : Places.ALL_PLACES) {
            if (findCord(places.getX(), places.getY()) != null) {
                findCord(places.getX(), places.getY()).changeValue(places.getLetter());
            }
        }
        boolean allgood = true;
        for (Place places : Places.ALL_PLACES) {
            if (!testMap(places.getLetter())) {
                allgood = false;
            }
        }
        if (allgood) {
            update();
            Data.WORLD_MAP = mCoords;
        } else {
            populate();
        }
        //TODO polish world gen
    }

    private boolean testMap(String v) {
        for (Coordinate mCoord : mCoords) {
            if (mCoord.getValue().equals(v)) {
                return true;
            }
        }
        return false;
    }

    private void update() {
        updateItems();
        mAreaTextView.setText("");
        String add = "";
        add += mCoords.get(0).getValue();
        for (int i = 1; i < mCoords.size(); i++) {
            if (mCoords.get(i).getY() == mCoords.get(i - 1).getY()) {
                add += mCoords.get(i).getValue();
            } else {
                mAreaTextView.append("\n" + add);
                add = "";
                add += mCoords.get(i).getValue();
            }
        }
        mAreaTextView.append("\n" + add);
        checkBelow(mCurrentCord.getOld_value());
    }

    private void checkBelow(String value) {
        switch (value) {
            case "#":
                break;
            case "C":
                openDialogue(Places.COAL_MINE);
                break;
        }

    }

    public void openDialogue(final Place place) {
        if (!place.isCompleted()) {
            new AlertDialogWrapper.Builder(getActivity())
                    .setTitle("Go to: " + place.getName())
                    .setMessage(place.getDesc())
                    .setCancelable(false)
                    .setPositiveButton("Travel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (place.getCreatureList() != null) {
                                EventBus.getDefault().post(new ChangeWorldFragmentEvent(place));
                            } else {
                                openFinishedDialog(place);
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
        } else {
            new AlertDialogWrapper.Builder(getActivity())
                    .setTitle("Go to: " + place.getName())
                    .setMessage("Already completed!")
                    .setCancelable(false)
                    .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    private void openFinishedDialog(final Place place) {

        new AlertDialogWrapper.Builder(getActivity())
                .setTitle("Completed: " + place.getName())
                .setMessage(place.getFinishedDesc())
                .setCancelable(false)
                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        place.postEvent();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onStop() {
        super.onStop();
        mCurrentCord.revertOldValue();
    }


}
