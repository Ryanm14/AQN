package me.ryanmiles.aqn.data;

import org.greenrobot.eventbus.EventBus;

import me.ryanmiles.aqn.events.DataUpdateEvent;

/**
 * Created by ryanm on 5/7/2016.
 */
public class Item{
    private int amount;
    private int increment;
    private int max;
    private boolean discovered;
    private String name;
    private String saved_name;

    public Item(boolean discovered, int amount, int max, String name, String saved_name, int increment) {
        this.amount = amount;
        this.name = name;
        this.saved_name = saved_name;
        this.increment = increment;
        this.discovered = discovered;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
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
    public void remove(int remove){
        amount -= remove;
        updateData();
    }



    public void addAmount(int add){
        if((add + amount) >= max){
            amount = max;
            updateData("You can't carry anymore " + name);
        }else{
            amount += add;
            updateData();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaved_name() {
        return saved_name;
    }

    public void setSaved_name(String saved_name) {
        this.saved_name = saved_name;
    }

    private void updateData() {
        EventBus.getDefault().post(new DataUpdateEvent(true, getLogText()));
    }
    private void updateData(String log) {
        EventBus.getDefault().post(new DataUpdateEvent(true, log));
    }

    public String getLogText() {
        return "You collected " + increment + " " + name;
    }

    public void addIncrement() {
        if((increment + amount) >= max){
            amount = max;
            updateData("You can't carry anymore " + name);
        }else{
            amount += increment;
            updateData();
        }
    }
}
