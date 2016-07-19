package me.ryanmiles.aqn.data.model;

import org.greenrobot.eventbus.EventBus;

import me.ryanmiles.aqn.events.DataUpdateEvent;

/**
 * Created by ryanm on 5/7/2016.
 */
public class Item extends Object {
    private int amount;
    private int increment = 0;
    private int max;

    //Wood and Stone and stuff
    public Item(int amount, int max, String name, String saved_name, int increment) {
        super(name, saved_name, false);
        this.amount = amount;
        this.increment = increment;
        this.max = max;
    }

    //Normal Items
    public Item(int amount, int max, String name, String saved_name) {
        super(name, saved_name, false);
        this.amount = amount;
        this.max = max;
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
        updateData(amount);
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


    private void updateData() {
        EventBus.getDefault().post(new DataUpdateEvent(true, getLogText()));
    }

    private void updateData(int n) {
        EventBus.getDefault().post(new DataUpdateEvent(true, getLogText(n)));
    }

    private void updateData(String log) {
        EventBus.getDefault().post(new DataUpdateEvent(true, log));
    }

    public String getLogText() {
        return "You collected " + increment + " " + getName();
    }

    public String getLogText(int n) {
        return "You collected " + n + " " + getName();
    }

    public void addIncrement() {
        if ((increment + amount) >= max) {
            amount = max;
            updateData("");
        } else {
            amount += increment;
            updateData("");
        }
    }


    public void setInfo(Item info) {
        amount = info.getAmount();
        setDiscovered(info.isDiscovered());
        increment = info.getIncrement();
        max = info.getMax();
    }
}
