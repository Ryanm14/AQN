package me.ryanmiles.aqn.data.model;

import java.util.Random;

/**
 * Created by Ryan Miles on 5/28/2016.
 */
public class Loot extends Item {
    private int lowRoll = -1;
    private int highRoll = -1;
    private boolean storageDisplay;
    private Random rng;

    public Loot(boolean discovered, int amount, int max, String name, String saved_name, int low, int high, boolean storageDisplay) { //Loot items like copper
        super(discovered, amount, max, name, saved_name);
        lowRoll = low;
        highRoll = high;
        this.storageDisplay = storageDisplay;
        rng = new Random();
    }

    public Loot(boolean discovered, String name, String saved_name, int randomChance, boolean storageDisplay) {
        super(discovered, name, saved_name, randomChance);
        this.storageDisplay = storageDisplay;
        rng = new Random();
    }

    public int getLowRoll() {
        return lowRoll;
    }

    public void setLowRoll(int lowRoll) {
        this.lowRoll = lowRoll;
    }

    public int getHighRoll() {
        return highRoll;
    }

    public void setHighRoll(int highRoll) {
        this.highRoll = highRoll;
    }


    public boolean isStorageDisplay() {
        return storageDisplay;
    }

    public void setStorageDisplay(boolean storageDisplay) {
        this.storageDisplay = storageDisplay;
    }

    public void setInfo(Loot loot) {
        setAmount(loot.getAmount());
        setMax(loot.getMax());
        setDiscovered(loot.isDiscovered());
        setRandomChance(loot.getRandomChance());
        lowRoll = loot.lowRoll;
        highRoll = loot.highRoll;
    }

    public String getLogText(int add) {
        return "You collected " + add + " " + getName();
    }

    public String roll() {
        if (lowRoll != -1 && highRoll != -1) {
            setAmount((rng.nextInt((highRoll - lowRoll)) + lowRoll));
            return (getName() + ": " + getAmount() + "\n");
        } else {
            if (rng.nextInt(100) + 1 <= getRandomChance()) {
                setDiscovered(true);
                return ("A " + getName() + "\n");
            }
        }
        return "";
    }

}
