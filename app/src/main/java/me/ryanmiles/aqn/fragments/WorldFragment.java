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

import com.afollestad.materialdialogs.AlertDialogWrapper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ryanmiles.aqn.R;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.model.Coordinate;
import me.ryanmiles.aqn.events.ChangeWorldFragmentEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorldFragment extends Fragment {
    ArrayList<Coordinate> mCoords;
    Coordinate mCurrentCord;
    int WORLD_RADIUS = 36;
    Random rng;

    @BindView(R.id.world_fragment_area_text_view)
    TextView mAreaTextView;

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
        if (mCurrentCord.getX() < WORLD_RADIUS) {
            Coordinate newCord;
            mCurrentCord.getX();
            newCord = findCord(mCurrentCord.getX() + 1, mCurrentCord.getY());
            mCurrentCord.revertOldValue();
            newCord.changeValue("O");
            mCurrentCord = newCord;
            update();
        }
    }

    @OnClick(R.id.world_fragment_left_button)
    public void left() {
        if (mCurrentCord.getX() - 1 > 0) {
            Coordinate newCord;
            mCurrentCord.getX();
            newCord = findCord(mCurrentCord.getX() - 1, mCurrentCord.getY());
            mCurrentCord.revertOldValue();
            newCord.changeValue("O");
            mCurrentCord = newCord;
            update();
        }
    }

    @OnClick(R.id.world_fragment_up_button)
    public void up() {
        if (mCurrentCord.getY() - 1 > 0) {
            Coordinate newCord;
            mCurrentCord.getX();
            newCord = findCord(mCurrentCord.getX(), mCurrentCord.getY() - 1);
            mCurrentCord.revertOldValue();
            newCord.changeValue("O");
            mCurrentCord = newCord;
            update();
        }
    }

    @OnClick(R.id.world_fragment_down_button)
    public void down() {
        if (mCurrentCord.getY() < WORLD_RADIUS / 2) {
            Coordinate newCord;
            mCurrentCord.getX();
            newCord = findCord(mCurrentCord.getX(), mCurrentCord.getY() + 1);
            mCurrentCord.revertOldValue();
            newCord.changeValue("O");
            mCurrentCord = newCord;
            update();
            boolean c = false;
            for (Coordinate mCoord : mCoords) {
                if (mCoord.getValue().equals("P")) {
                    c = true;
                }
            }
            if (!c) {
                update();
            }
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
        mCurrentCord = findCord(WORLD_RADIUS / 2, WORLD_RADIUS / 4);
        mCurrentCord.changeValue("O");
        findCord(rng.nextInt(6) + 2, rng.nextInt(4) + 1).changeValue("P");

        findCord(rng.nextInt(WORLD_RADIUS / 2) + 1, rng.nextInt(WORLD_RADIUS / 4) + WORLD_RADIUS / 4 - 1).changeValue("V");
        findCord(rng.nextInt(WORLD_RADIUS / 2) + WORLD_RADIUS / 2, rng.nextInt(WORLD_RADIUS / 4) + WORLD_RADIUS / 4 - 1).changeValue("C");
        findCord(rng.nextInt(WORLD_RADIUS / 2) + WORLD_RADIUS / 2, rng.nextInt(WORLD_RADIUS / 4) + 1).changeValue("A");


        if (testMap("V") && testMap("P") && testMap("C") && testMap("A")) {
            update();
            Data.WORLD_MAP = mCoords;
        } else {
            populate();
        }

    }

    private boolean testMap(String v) {
        for (Coordinate mCoord : mCoords) {
            if (mCoord.getValue() == v) {
                return true;
            }
        }
        return false;
    }

    private void update() {
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
       /*  switch (value) {
            case "#":
                break;
           case "P":
                openDialogue(Data.AV.getName(), Data.AV.getDescription(), new ChangeWorldFragmentEvent(Data.AV));
                break;
            case "V":
                openDialogue(Data.DV.getName(), Data.DV.getDescription(), new ChangeWorldFragmentEvent(Data.DV));
                break;
            case "C":
                openDialogue(Data.CAVE.getName(), Data.CAVE.getDescription(), new ChangeWorldFragmentEvent(Data.CAVE));
                break;
            case "A":
                openDialogue(Data.OLD_AVENUE.getName(), Data.OLD_AVENUE.getDescription(), new ChangeWorldFragmentEvent(Data.OLD_AVENUE));
        }
        */
    }

    public void openDialogue(String title, String desc, final ChangeWorldFragmentEvent event) {
        new AlertDialogWrapper.Builder(getActivity())
                .setTitle(title)
                .setMessage(desc)
                .setCancelable(false)
                .setPositiveButton("Travel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(event);
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

    @Override
    public void onStop() {
        super.onStop();
        mCurrentCord.revertOldValue();
    }


}
