package me.ryanmiles.aqn.data.model;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

import me.ryanmiles.aqn.events.DataUpdateEvent;

/**
 * Created by ryanm on 5/7/2016.
 */
public class Item extends Object {
    Random rng;
    private int amount;
    private int increment;
    private int max;
    private int randomChance;


    public Item(boolean discovered, int amount, int max, String name, String saved_name, int increment, int randomChance) {
        super(name, saved_name, discovered);
        this.amount = amount;
        this.increment = increment;
        this.max = max;
        this.randomChance = randomChance;
        rng = new Random();
    }

    public Item(boolean discovered, int amount, int max, String name, String saved_name) { //Loot items like copper
        super(name, saved_name, discovered);
        this.amount = amount;
        this.max = max;
    }

    public Item(boolean discovered, String name, String saved_name, int randomChance) { //Loot items like jacket
        super(name, saved_name, discovered);
        this.randomChance = randomChance;
    }

    public int getRandomChance() {
        return randomChance;
    }

    public void setRandomChance(int randomChance) {
        this.randomChance = randomChance;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }


    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        updateData();
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
            updateData();
        }
    }


    private void updateData() {
        EventBus.getDefault().post(new DataUpdateEvent(true, getLogText()));
    }

    private void updateData(String log) {
        EventBus.getDefault().post(new DataUpdateEvent(true, log));
    }

    public String getLogText() {
        return "You collected " + increment + " " + getName();
    }

    public void addIncrement() {
        if ((increment + amount) >= max) {
            amount = max;
            updateData("");
        } else {
            amount += increment;
            updateData();
        }
    }

    public void random() {
        if (rng.nextInt(100) + 1 <= randomChance) {
            addIncrement();
        }
    }


    public void setInfo(Item info) {
        amount = info.getAmount();
        setDiscovered(info.isDiscovered());
        increment = info.getIncrement();
        max = info.getMax();
        randomChance = info.getRandomChance();
    }
}
