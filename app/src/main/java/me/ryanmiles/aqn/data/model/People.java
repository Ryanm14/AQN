package me.ryanmiles.aqn.data.model;

import org.greenrobot.eventbus.EventBus;

import me.ryanmiles.aqn.events.updates.UpdateVillageInfo;

/**
 * Created by Ryan Miles on 7/17/2016.
 */
public class People extends Object {
    public static int VILLAGE_CURRENT_POPULATION = 0;
    public static int VILLAGE_MAX_POPULATION = 5;
    public static int FOOD_NEEDED = 1;
    private int amount = 0;
    private Item increaseType;
    private double increaseAmount = 1.5;

    public People(String name, String saved_name, boolean discovered, Item increaseType) { //First few
        super(name, saved_name, discovered);
        this.increaseType = increaseType;
    }

    public People(String name, String saved_name, Item increaseType) {
        super(name, saved_name, false);
        this.increaseType = increaseType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Item getIncreaseType() {
        return increaseType;
    }

    public void setIncreaseType(Item increaseType) {
        this.increaseType = increaseType;
    }

    public double getIncreaseAmount() {
        return increaseAmount;
    }

    public void setIncreaseAmount(double increaseAmount) {
        this.increaseAmount = increaseAmount;
    }

    public double getIncrease() {
        return increaseAmount * amount;
    }

    public boolean checkIfSpace() {
        return (People.VILLAGE_MAX_POPULATION > People.VILLAGE_CURRENT_POPULATION);
    }

    public void addOne() {
        People.VILLAGE_CURRENT_POPULATION++;
        amount++;
        updateVillageInfo();
    }

    private void updateVillageInfo() {
        EventBus.getDefault().post(new UpdateVillageInfo());
    }

    public void removeOne() {
        People.VILLAGE_CURRENT_POPULATION--;
        amount--;
        updateVillageInfo();
    }
}
