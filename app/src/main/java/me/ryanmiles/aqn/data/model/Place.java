package me.ryanmiles.aqn.data.model;

import java.util.ArrayList;

/**
 * Created by Ryan Miles on 5/28/2016.
 */
public class Place {
    private String name;
    private String description;
    private ArrayList<Creature> mCreatureList;
    private ArrayList<Loot> mLootList;

    public Place(String name, String description, ArrayList<Creature> mCreatureList, ArrayList<Loot> mLootList) {
        this.name = name;
        this.description = description;
        this.mCreatureList = mCreatureList;
        this.mLootList = mLootList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Creature> getmCreatureList() {
        return mCreatureList;
    }

    public void setmCreatureList(ArrayList<Creature> mCreatureList) {
        this.mCreatureList = mCreatureList;
    }

    public ArrayList<Loot> getmLootList() {
        return mLootList;
    }

    public void setmLootList(ArrayList<Loot> mLootList) {
        this.mLootList = mLootList;
    }
}
