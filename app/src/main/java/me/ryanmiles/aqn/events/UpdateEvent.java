package me.ryanmiles.aqn.events;

import java.util.ArrayList;

import me.ryanmiles.aqn.data.model.Item;
import me.ryanmiles.aqn.data.model.Object;
import me.ryanmiles.aqn.events.updates.AddIncrementEvent;
import me.ryanmiles.aqn.events.updates.ChangeMaxEvent;
import me.ryanmiles.aqn.events.updates.SetDiscoveredEvent;

/**
 * Created by ryanm on 5/21/2016.
 */
public class UpdateEvent {
    ArrayList<SetDiscoveredEvent> mSetDiscoveredEvent;
    ArrayList<ChangeMaxEvent> mChangeMaxEvent;
    ArrayList<AddIncrementEvent> mAddIncrementEvents;

    public UpdateEvent() {
        mChangeMaxEvent = new ArrayList<>();
        mSetDiscoveredEvent = new ArrayList<>();
        mAddIncrementEvents = new ArrayList<>();
    }

    public UpdateEvent setDiscoveredEvent(Object object) {
        mSetDiscoveredEvent.add(new SetDiscoveredEvent(object));
        return this;
    }

    public UpdateEvent setChangeMaxEvent(Item item, int max) {
        mChangeMaxEvent.add(new ChangeMaxEvent(item, max));
        return this;
    }

    public UpdateEvent setAddIncrementEvent(Item item, int amount) {
        mAddIncrementEvents.add(new AddIncrementEvent(item, amount));
        return this;
    }

    public void post() {
        if (mChangeMaxEvent.size() != 0) {
            for (ChangeMaxEvent event : mChangeMaxEvent) {
                event.post();
            }
        }
        if (mSetDiscoveredEvent.size() != 0) {
            for (SetDiscoveredEvent event : mSetDiscoveredEvent) {
                event.post();
            }
        }
        if (mAddIncrementEvents.size() != 0) {
            for (AddIncrementEvent event : mAddIncrementEvents) {
                event.post();
            }
        }
    }

    @Override
    public String toString() {
        return "UpdateEvent{" +
                "mSetDiscoveredEvent=" + mSetDiscoveredEvent +
                ", mChangeMaxEvent=" + mChangeMaxEvent +
                ", mAddIncrementEvents=" + mAddIncrementEvents +
                '}';
    }
}
