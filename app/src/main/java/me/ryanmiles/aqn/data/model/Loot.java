package me.ryanmiles.aqn.data.model;

/**
 * Created by Ryan Miles on 5/28/2016.
 */
public class Loot extends Object {
    private int chance;

    public Loot(String name, String saved_name, boolean discovered, int chance) {
        super(name, saved_name, discovered);
        this.chance = chance;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}
