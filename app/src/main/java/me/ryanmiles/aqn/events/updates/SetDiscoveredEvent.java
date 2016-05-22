package me.ryanmiles.aqn.events.updates;

import me.ryanmiles.aqn.data.model.Object;

/**
 * Created by ryanm on 5/21/2016.
 */
public class SetDiscoveredEvent {
    Object mObject;

    public SetDiscoveredEvent(Object object) {
        mObject = object;
    }

    public Object getObject() {
        return mObject;
    }

    public void setObject(Object object) {
        mObject = object;
    }


    public void post() {
        mObject.setDiscovered(true);
    }
}
