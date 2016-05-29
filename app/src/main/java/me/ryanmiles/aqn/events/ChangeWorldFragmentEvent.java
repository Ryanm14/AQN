package me.ryanmiles.aqn.events;

import java.util.ArrayList;

import me.ryanmiles.aqn.data.model.Creature;

/**
 * Created by ryanm on 5/23/2016.
 */
public class ChangeWorldFragmentEvent {
    ArrayList<Creature> mCreatureArrayList;

    public ChangeWorldFragmentEvent(ArrayList<Creature> av) {
        mCreatureArrayList = av;
    }

    public ArrayList<Creature> getCreatureArrayList() {
        return mCreatureArrayList;
    }

    public void setCreatureArrayList(ArrayList<Creature> creatureArrayList) {
        mCreatureArrayList = creatureArrayList;
    }
}
