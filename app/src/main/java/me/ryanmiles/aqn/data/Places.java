package me.ryanmiles.aqn.data;

import java.util.ArrayList;
import java.util.Arrays;

import me.ryanmiles.aqn.data.model.Creature;
import me.ryanmiles.aqn.data.model.Place;

/**
 * Created by Ryan Miles on 7/31/2016.
 */
public class Places {
    public static Creature RAT = new Creature("Coal Rat", 5, 1, 5000);
    public static Creature WILD_BAT = new Creature("Wild Bat", 10, 2, 5000);
    public static Place COAL_MINE = new Place("Coal Mine", "Dustied paint yolo", "You acquired a Coal Mine!", Arrays.asList(RAT, WILD_BAT, RAT), null);
    public static ArrayList<Place> ALL_PLACES = new ArrayList<>(Arrays.asList(COAL_MINE));

}
