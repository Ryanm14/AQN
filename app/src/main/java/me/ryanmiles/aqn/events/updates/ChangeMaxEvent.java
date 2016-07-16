package me.ryanmiles.aqn.events.updates;

import me.ryanmiles.aqn.data.model.Item;

/**
 * Created by ryanm on 5/21/2016.
 */
public class ChangeMaxEvent {
    Item mItem;
    int max;

    public ChangeMaxEvent(Item item, int max) {
        mItem = item;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Item getItem() {
        return mItem;
    }

    public void setItem(Item item) {
        mItem = item;
    }

    public void post() {
        mItem.setMax(max);
    }

    @Override
    public String toString() {
        return "ChangeMaxEvent{" +
                "mItem=" + mItem +
                ", max=" + max +
                '}';
    }
}
