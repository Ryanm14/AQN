package me.ryanmiles.aqn.data.model;

/**
 * Created by ryanm on 5/23/2016.
 */
public class Creature {
    private String name;
    private int health, damage, attackspeed;

    public Creature(String name, int health, int damage, int attackspeed) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.attackspeed = attackspeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getAttackspeed() {
        return attackspeed;
    }

    public void setAttackspeed(int attackspeed) {
        this.attackspeed = attackspeed;
    }
}
