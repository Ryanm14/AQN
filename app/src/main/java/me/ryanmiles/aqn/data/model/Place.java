package me.ryanmiles.aqn.data.model;

import java.util.ArrayList;
import java.util.List;

import me.ryanmiles.aqn.events.UpdateEvent;

/**
 * Created by Ryan Miles on 5/28/2016.
 */
public class Place {
    private String name;
    private String description;
    private String finished_desc;
    private ArrayList<Creature> mCreatureList;
    private ArrayList<Loot> mLootList;
    private Coin mCoin;
    private UpdateEvent mEvent;

    public Place(String name, String description, String finished_desc, List<Creature> mCreatureList, List<Loot> mLootList, Coin coin, UpdateEvent mEvent) {
        this.name = name;
        this.description = description;
        this.finished_desc = finished_desc;
        this.mCreatureList = new ArrayList<>(mCreatureList);
        this.mLootList = new ArrayList<>(mLootList);
        this.mEvent = mEvent;
        mCoin = coin;
    }

    public String getFinished_desc() {
        return finished_desc;
    }

    public void setFinished_desc(String finished_desc) {
        this.finished_desc = finished_desc;
    }

    public ArrayList<Creature> getCreatureList() {
        return mCreatureList;
    }

    public void setCreatureList(ArrayList<Creature> creatureList) {
        mCreatureList = creatureList;
    }

    public ArrayList<Loot> getLootList() {
        return mLootList;
    }

    public void setLootList(ArrayList<Loot> lootList) {
        mLootList = lootList;
    }

    public Coin getCoin() {
        return mCoin;
    }

    public void setCoin(Coin coin) {
        mCoin = coin;
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

    public UpdateEvent getEvent() {
        return mEvent;
    }

    public void setEvent(UpdateEvent event) {
        mEvent = event;
    }

    public String getMessage() {
        String message = finished_desc + "\n\nYou found: \n";
        for (Loot loot : mLootList) {
            message += (loot.roll());
        }
        message += mCoin.roll();
        postEvent();
        return message;
    }

    private void postEvent() {
        if (mEvent != null) {
            mEvent.post();
        }

    }
}
