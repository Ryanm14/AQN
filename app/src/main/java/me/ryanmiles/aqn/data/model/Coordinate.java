package me.ryanmiles.aqn.data.model;

/**
 * Created by ryanm on 5/23/2016.
 */
public class Coordinate {
    private int x;
    private int y;
    private String value;
    private String DEAFAULT_VALUE = "#";
    private String old_value = "";
    private boolean random;

    public Coordinate() {

    }

    public Coordinate(int x, int y, String value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        value = DEAFAULT_VALUE;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void changeValue(String s) {
        old_value = value;
        value = s;
    }

    public void revertOldValue() {
        value = old_value;
    }

    public String getOld_value() {
        return old_value;
    }
}
