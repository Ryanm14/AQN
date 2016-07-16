package me.ryanmiles.aqn.events.updates;

import me.ryanmiles.aqn.data.model.Item;

/**
 * Created by ryanm on 5/21/2016.
 */
public class AddIncrementEvent {
    Item mItem;
    int increment;

    public AddIncrementEvent(Item object, int increment) {
        mItem = object;
        this.increment = increment;
    }

    public Object getItem() {
        return mItem;
    }

    public void setItem(Item item) {
        mItem = item;
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    public void post() {
        mItem.setIncrement(increment);
    }

    @Override
    public String toString() {
        return "AddIncrementEvent{" +
                "mItem=" + mItem +
                ", increment=" + increment +
                '}';
    }
}
