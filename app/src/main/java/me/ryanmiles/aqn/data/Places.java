package me.ryanmiles.aqn.data;

import java.util.ArrayList;
import java.util.Arrays;

import me.ryanmiles.aqn.data.model.Creature;
import me.ryanmiles.aqn.data.model.Place;
import me.ryanmiles.aqn.events.UpdateEvent;

import static me.ryanmiles.aqn.data.Data.MINING;
import static me.ryanmiles.aqn.data.Data.SMELTING;
import static me.ryanmiles.aqn.fragments.WorldFragment.WORLD_RADIUS;

/**
 * Created by Ryan Miles on 7/31/2016.
 */
public class Places {
    public static Creature RAT = new Creature("Coal Rat", 5, 1, 5000);
    public static Creature WILD_BAT = new Creature("Wild Bat", 10, 2, 5000);
    public static Place COAL_MINE = new Place("Coal Mine", "Dustied paint yolo", "You acquired a Coal Mine!", Arrays.asList(RAT, WILD_BAT, RAT), new UpdateEvent().setDiscoveredEvent(MINING), WORLD_RADIUS / 2, WORLD_RADIUS - 1, 1, WORLD_RADIUS / 9);
    public static Place PASTURE = new Place("Pastures", "Green grass", "Some villagers have sparked intresets in leather", null, null, WORLD_RADIUS / 4, WORLD_RADIUS / 2 - 2, WORLD_RADIUS / 9, WORLD_RADIUS / 4);
    public static Place OREMINE = new Place("Ore Mine", "Some things shine in the distance", "This requries more research", null, new UpdateEvent().setDiscoveredEvent(SMELTING), 1, WORLD_RADIUS / 4, WORLD_RADIUS / 4, WORLD_RADIUS / 2 - 1);
    public static Place RESEARCH_RUIN = new Place("Ruins", "Kowlaegfe", "Kownladge!", null, null, WORLD_RADIUS / 2, (int) (WORLD_RADIUS / 1.3), WORLD_RADIUS / 4, (int) (WORLD_RADIUS / 2.7));
    public static Place OLDARMORY = new Place("Armery", "Fight1", "Giht!", null, null, 1, WORLD_RADIUS / 4, 1, WORLD_RADIUS / 9);
    public static Place FOODRUIN = new Place("Ruins", "Ayy dioritis", "Yumt!", null, null, WORLD_RADIUS / 4, WORLD_RADIUS / 2 - 2, WORLD_RADIUS / 4, (int) (WORLD_RADIUS / 1.3));
    public static Place OLDVILLAGE1 = new Place("Village", "Ruined Village", "Ayy more fam", null, new UpdateEvent().increaseVillageMax(5), (int) (WORLD_RADIUS / 1.3), WORLD_RADIUS - 1, WORLD_RADIUS / 9, WORLD_RADIUS / 4);
    public static Place OLDVILLAGE2 = new Place("Village", "Ruined Village", "Ayy more fam", null, new UpdateEvent().increaseVillageMax(3), (int) (WORLD_RADIUS / 1.3), WORLD_RADIUS - 1, WORLD_RADIUS / 4, WORLD_RADIUS / 2);
    public static ArrayList<Place> ALL_PLACES = new ArrayList<>(Arrays.asList(COAL_MINE, PASTURE, OREMINE, RESEARCH_RUIN, OLDARMORY, FOODRUIN, OLDVILLAGE1, OLDVILLAGE2));

}
