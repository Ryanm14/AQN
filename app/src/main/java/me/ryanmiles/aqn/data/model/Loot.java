package me.ryanmiles.aqn.data.model;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

import me.ryanmiles.aqn.events.DataUpdateEvent;

/**
 * Created by Ryan Miles on 5/28/2016.
 */
public class Loot extends Object {
    private int chance;
    private boolean storageDisplay;
    private int amount;
    private int max;
    private Random rng;

    public Loot(String name, String saved_name, boolean discovered, int chance, boolean storageDisplay, int amount, int max) {
        super(name, saved_name, discovered);
        this.chance = chance;
        this.storageDisplay = storageDisplay;
        this.amount = amount;
        this.max = max;
        rng = new Random();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public boolean isStorageDisplay() {
        return storageDisplay;
    }

    public void setStorageDisplay(boolean storageDisplay) {
        this.storageDisplay = storageDisplay;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public void setInfo(Loot loot) {
        amount = loot.getAmount();
        max = loot.getMax();
        setDiscovered(loot.isDiscovered());
        chance = loot.getChance();
    }

    public void remove(int remove) {
        if (amount - remove > 0) {
            amount -= remove;
        } else {
            amount = 0;
        }
        updateData("");
    }


    public void addAmount(int add) {
        if ((add + amount) >= max) {
            amount = max;
            updateData("You can't carry anymore " + getName());
        } else {
            amount += add;
            updateData(add);
        }
    }


    private void updateData(int add) {
        EventBus.getDefault().post(new DataUpdateEvent(true, getLogText(add)));
    }

    private void updateData(String log) {
        EventBus.getDefault().post(new DataUpdateEvent(true, log));
    }

    public String getLogText(int add) {
        return "You collected " + add + " " + getName();
    }

    public void random() {
        if (rng.nextInt(100) + 1 <= chance) {
            setAmount(1);
        }
    }
}
