package me.ryanmiles.aqn.data.model;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.events.DataUpdateEvent;
import me.ryanmiles.aqn.events.updates.UpdateVillageInfo;

/**
 * Created by Ryan Miles on 7/17/2016.
 */
public class People extends Object {
    private static final String TAG = People.class.getCanonicalName();
    public static int VILLAGE_CURRENT_POPULATION;
    public static int VILLAGE_MAX_POPULATION = 0;
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

    public static boolean checkFood(double v) {
        int foodNeeded = FOOD_NEEDED * VILLAGE_CURRENT_POPULATION;
        Log.v(TAG, "checkFood() called with: " + "v = [" + v + "]" + " foodNeeded = " + foodNeeded + " Current Food = " + Data.FOOD.getAmount() + " RESULT = " + (foodNeeded - Data.FOOD.getAmount() + v));
        if (Data.FOOD.getAmount() - foodNeeded + v > 0) {
            Data.FOOD.removeVillage(foodNeeded);
            return true;
        } else {
            Data.FOOD.setAmountVillage(0);
            return false;
        }
    }

    public static void updateAll() {
        Log.v(TAG, "updateAll(): ");
        EventBus.getDefault().post(new DataUpdateEvent(true, ""));
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

    public void post() {
        Log.v(TAG, "post() called with: " + increaseType.getName() + " +" + (int) (increaseAmount * amount));
        increaseType.addAmountVillage((int) (increaseAmount * amount));
    }

    public void setInfo(People info) {
        this.amount = info.getAmount();
        setDiscovered(info.isDiscovered());
        VILLAGE_CURRENT_POPULATION += amount;
    }

    @Override
    public String toString() {
        return "People{" +
                "name = " + getName() +
                ", amount=" + amount +
                ", increaseType=" + increaseType +
                ", increaseAmount=" + increaseAmount +
                '}';
    }
}
