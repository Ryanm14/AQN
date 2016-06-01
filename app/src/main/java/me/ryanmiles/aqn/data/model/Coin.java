package me.ryanmiles.aqn.data.model;

import java.util.Random;

/**
 * Created by Ryan Miles on 5/31/2016.
 */
public class Coin extends Object {
    private static int amount;
    private Random rng;
    private int highRoll;
    private int lowRoll;

    public Coin(int lowRoll, int highRoll) {
        super("Coins", "coins", true);
        this.highRoll = highRoll;
        this.lowRoll = lowRoll;
        rng = new Random();
    }

    public static int getAmount() {
        return amount;
    }

    public static void setAmount(int amount) {
        Coin.amount = amount;
    }

    public int getHighRoll() {
        return highRoll;
    }

    public void setHighRoll(int highRoll) {
        this.highRoll = highRoll;
    }

    public int getLowRoll() {
        return lowRoll;
    }

    public void setLowRoll(int lowRoll) {
        this.lowRoll = lowRoll;
    }

    public String roll() {
        int found = (rng.nextInt(highRoll - lowRoll) + lowRoll);
        amount += found;
        return (getName() + ": " + amount);
    }
}
