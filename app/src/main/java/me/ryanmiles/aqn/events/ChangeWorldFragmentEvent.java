package me.ryanmiles.aqn.events;

import me.ryanmiles.aqn.data.model.Place;

/**
 * Created by ryanm on 5/23/2016.
 */
public class ChangeWorldFragmentEvent {
    private Place mPlace;

    public ChangeWorldFragmentEvent(Place place) {
        mPlace = place;
    }

    public Place getPlace() {
        return mPlace;
    }

    public void setPlace(Place mPlace) {
        this.mPlace = mPlace;
    }
}
