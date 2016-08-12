package me.ryanmiles.aqn.data;

import java.util.ArrayList;
import java.util.Arrays;

import me.ryanmiles.aqn.data.model.Creature;
import me.ryanmiles.aqn.data.model.Place;
import me.ryanmiles.aqn.events.UpdateEvent;

import static me.ryanmiles.aqn.data.Data.MINING;
import static me.ryanmiles.aqn.data.Data.PRESERVE;
import static me.ryanmiles.aqn.data.Data.PURIFY;
import static me.ryanmiles.aqn.data.Data.SMELTING;
import static me.ryanmiles.aqn.data.Data.STAB;
import static me.ryanmiles.aqn.data.Data.TANNING;
import static me.ryanmiles.aqn.fragments.WorldFragment.WORLD_RADIUS;

/**
 * Created by Ryan Miles on 7/31/2016.
 */
public class Places {
    public static Creature RAT = new Creature("Coal Rat", 5, 1, 5000);
    public static Creature WILD_BAT = new Creature("Wild Bat", 10, 2, 5000);
    public static Place COAL_MINE = new Place("Coal Mine", "Dustied paint yolo", "You acquired a Coal Mine!", Arrays.asList(RAT, WILD_BAT, RAT), new UpdateEvent().setDiscoveredEvent(MINING), WORLD_RADIUS / 2, WORLD_RADIUS - 1, 1, WORLD_RADIUS / 9);


    public static Creature COW = new Creature("Cow", 10, 1, 5000);
    public static Creature HERDER = new Creature("Herder", 20, 2, 4000);
    public static Place PASTURE = new Place("Pastures", "Green grass", "Some villagers have sparked intresets in leather", Arrays.asList(COW, COW, HERDER), new UpdateEvent().setDiscoveredEvent(TANNING), WORLD_RADIUS / 4, WORLD_RADIUS / 2 - 2, WORLD_RADIUS / 9, WORLD_RADIUS / 4);

    public static Creature WORM = new Creature("Worm", 10, 1, 5000);
    public static Creature SNAKE = new Creature("Snake", 20, 2, 4000);
    public static Creature HUGE_BEATLE = new Creature("Huge Beatle", 20, 2, 4000);
    public static Place OREMINE = new Place("Ore Mine", "Some things shine in the distance", "This requries more research", Arrays.asList(WORM, SNAKE, WORM, HUGE_BEATLE), new UpdateEvent().setDiscoveredEvent(SMELTING), 1, WORLD_RADIUS / 4, WORLD_RADIUS / 4, WORLD_RADIUS / 2 - 1);

    public static Place WATER_RUIN = new Place("Ruins", "Kowlaegfe", "Kownladge!", null, new UpdateEvent().setDiscoveredEvent(PURIFY), WORLD_RADIUS / 2, (int) (WORLD_RADIUS / 1.3), WORLD_RADIUS / 4, (int) (WORLD_RADIUS / 2.7));


    public static Creature SMELTER = new Creature("Smelter", 8, 2, 4000);
    public static Creature GAINT = new Creature("Gaint", 15, 1, 4500);
    public static Place OLDARMORY = new Place("Armery", "Fight1", "Giht!", Arrays.asList(GAINT, SMELTER, SMELTER), new UpdateEvent().setDiscoveredEvent(STAB), 1, WORLD_RADIUS / 4, 1, WORLD_RADIUS / 9);

    public static Place FOODRUIN = new Place("Food Ruins", "Ayy dioritis", "Yumt!", null, new UpdateEvent().setDiscoveredEvent(PRESERVE), WORLD_RADIUS / 4, WORLD_RADIUS / 2 - 2, WORLD_RADIUS / 4, (int) (WORLD_RADIUS / 1.3));

    public static Creature OLD_MAN = new Creature("Old Man", 10, 3, 5000);
    public static Creature FARMER = new Creature("Farmer", 12, 3, 4500);
    public static Creature ARMORSMITH = new Creature("Armor Smith", 15, 3, 3500);
    public static Creature KING = new Creature("King", 15, 4, 2500);
    public static Place OLDVILLAGE1 = new Place("Village", "Ruined Village", "Ayy more fam", Arrays.asList(OLD_MAN, FARMER, ARMORSMITH), new UpdateEvent().increaseVillageMax(5), (int) (WORLD_RADIUS / 1.3), WORLD_RADIUS - 1, WORLD_RADIUS / 9, WORLD_RADIUS / 4);
    public static Place OLDVILLAGE2 = new Place("Monarch Village", "Ruined Village", "Ayy more fam", Arrays.asList(OLD_MAN, FARMER, KING), new UpdateEvent().increaseVillageMax(3), (int) (WORLD_RADIUS / 1.3), WORLD_RADIUS - 1, WORLD_RADIUS / 4, WORLD_RADIUS / 2);

    public static ArrayList<Place> ALL_PLACES = new ArrayList<>(Arrays.asList(COAL_MINE, PASTURE, OREMINE, WATER_RUIN, OLDARMORY, FOODRUIN, OLDVILLAGE1, OLDVILLAGE2));

}
