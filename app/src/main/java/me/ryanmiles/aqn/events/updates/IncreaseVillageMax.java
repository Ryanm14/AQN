package me.ryanmiles.aqn.events.updates;

import me.ryanmiles.aqn.data.model.People;

/**
 * Created by Ryan Miles on 8/1/2016.
 */
public class IncreaseVillageMax {
    private int amount;

    public IncreaseVillageMax(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void post() {
        People.VILLAGE_MAX_POPULATION += amount;
    }
}
