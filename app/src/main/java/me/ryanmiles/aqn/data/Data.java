package me.ryanmiles.aqn.data;

import android.content.Context;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import me.ryanmiles.aqn.data.model.Building;
import me.ryanmiles.aqn.data.model.Coordinate;
import me.ryanmiles.aqn.data.model.CraftedItem;
import me.ryanmiles.aqn.data.model.Item;
import me.ryanmiles.aqn.data.model.People;
import me.ryanmiles.aqn.data.model.Research;
import me.ryanmiles.aqn.events.LogUpdateEvent;
import me.ryanmiles.aqn.events.UpdateEvent;

/**
 * Created by ryanm on 5/7/2016.
 */
public class Data {

    public static boolean CRAFTING_NEW_DATA = false;
    public static boolean BUILDING_NEW_DATA = false;
    public static boolean RESEARCH_NEW_DATA = false;

    //Save
    public static boolean OPENFOREST = false;
    public static boolean FIRSTRUN = false;
    public static boolean OPENBUILDINGS = false;
    public static boolean OPENCRAFTING = false;
    public static boolean OPENVILLAGE = false;
    public static boolean OPENRESEARCH = false;

    //ITEMS
    public static Item WOOD = new Item(
            0,
            50,
            "Wood",
            "wood",
            1
    );

    public static Item STONE = new Item(
            0,
            50,
            "Stone",
            "stone",
            1
    );

    public static Item WATER = new Item(
            0,
            10,
            "Water",
            "water",
            1
    );

    public static Item FOOD = new Item(
            0,
            5,
            "Food",
            "food",
            1
    );


    public static ArrayList<Item> ALL_ITEMS = new ArrayList<>(Arrays.asList(WOOD, STONE, WATER, FOOD));

    //BUILDINGS
    public static Research ADVENTURE = new Research("Adventure", "adventure", null, 300);
    public static Research HOUSING = new Research("Housing", "housing", new UpdateEvent().setDiscoveredEvent(ADVENTURE), 120);
    public static Building FOUNDATION = new Building(
            "Foundation",
            "foundation",
            new HashMap<Item, Integer>() {{
                put(STONE, 20);
                put(WOOD, 20);
            }},
            true,
            2,
            new UpdateEvent().setDiscoveredEvent(HOUSING).updateTabHost("Village")
    );

    public static Building GRANARY = new Building(
            "Granary",
            "granary",
            new HashMap<Item, Integer>() {{
                put(WOOD, 80);
                put(STONE, 40);
            }},
            90,
            new UpdateEvent().setDiscoveredEvent(FOUNDATION).setChangeMaxEvent(FOOD, 10)
    );

    public static Building DEPOSITORY = new Building(
            "Depository",
            "depository",
            new HashMap<Item, Integer>() {{
                put(STONE, 35);
                put(WOOD, 35);
            }},
            60,
            new UpdateEvent().setChangeMaxEvent(WOOD, 100).setChangeMaxEvent(STONE, 100).setDiscoveredEvent(GRANARY)
    );

    /* public static Building SHACK = new Building(
             "Depository",
             "depository",
             new HashMap<Item, Integer>() {{
                 put(STONE, 20);
                 put(WOOD, 20);
             }},
             45,
             null
     );
     */


    //CRAFTED_ITEMS
    public static CraftedItem BASICHATCHET = new CraftedItem(
            "Basic Hatchet",
            "basic_hatchet",
            new HashMap<Item, Integer>() {{
                put(WOOD, 30);
                put(STONE, 30);
            }},
            new UpdateEvent().setAddIncrementEvent(WOOD, 2),
            60
    );


    public static CraftedItem BASICPICK = new CraftedItem(
            "Basic Pick",
            "basic_pick",
            new HashMap<Item, Integer>() {{
                put(WOOD, 30);
                put(STONE, 45);
            }},
            new UpdateEvent().setAddIncrementEvent(STONE, 2),
            60
    );

    public static Building TOOLBENCH = new Building(
            "Toolbench",
            "toolbench",
            new HashMap<Item, Integer>() {{
                put(WOOD, 30);
            }},
            60,
            new UpdateEvent().setDiscoveredEvent(BASICHATCHET).setDiscoveredEvent(BASICPICK)
    );

    public static Building WORKSHOP = new Building(
            "Workshop", //Name
            "workshop", //Saved Name
            new HashMap<Item, Integer>() {{ //Required Items
                put(WOOD, 20);
                put(STONE, 10);
            }},
            true, //IsDiscovered
            30,
            new UpdateEvent().setDiscoveredEvent(TOOLBENCH)
    );

    public static CraftedItem FLASK = new CraftedItem(
            "Flask",
            "flask",
            new HashMap<Item, Integer>() {{
                put(WOOD, 30);
            }},
            null,
            60
    );

    public static CraftedItem SPADE = new CraftedItem(
            "Spade",
            "spade",
            new HashMap<Item, Integer>() {{
                put(WOOD, 20);
                put(STONE, 20);
            }},
            new UpdateEvent().setDiscoveredEvent(FLASK),
            60
    );

    public static Research FARMING = new Research("Farming", "farming", new UpdateEvent().setDiscoveredEvent(SPADE), 120);
    public static Research INCREASED_STORAGE = new Research("Increased Storage", "increased_storage", new UpdateEvent().setDiscoveredEvent(DEPOSITORY), 90);
    public static Research CRAFTING_BUTTON = new Research("Crafting", "crafting", new UpdateEvent().setDiscoveredEvent(INCREASED_STORAGE).setDiscoveredEvent(FARMING), 45);
    public static Research BUILDING = new Research("Building", "building", true, new UpdateEvent().setDiscoveredEvent(CRAFTING_BUTTON), 30);


    //People

    public static People FARMER = new People("Farmer", "farmer", true, FOOD);
    public static People LUMBERJACK = new People("Lumberjack", "lumberjack", true, WOOD);
    public static People MINER = new People("Miner", "miner", true, STONE);
    public static ArrayList<People> PEOPLE_LIST = new ArrayList<>(Arrays.asList(FARMER, LUMBERJACK, MINER));

    /*
       //World Data

       public static Place AV = new Place("An Abandoned Mine", "What a dusty place.", "You managed to avoid the spiderwebs.", Arrays.asList(AV_RAT, AV_WILD_BAT, AV_WILD_BAT, AV_RAT), Arrays.asList(COPPER, LINEN_JACKET, RUSTED_BRACELET), new Coin(2, 5), new UpdateEvent().setDiscoveredEvent(COPPERAXE).setDiscoveredEvent(COPPERPICK).setDiscoveredEvent(COPPERSWORD));

       public static Creature DV_Scorpion = new Creature("Dusty Scorpion", 5, 2);
       public static Creature DV_Wild_Hog = new Creature("Wild Hog", 10, 3);
       public static Place DV = new Place("A Dusty Village", "Is anyone here?", "Luckily the village was abandoned.", Arrays.asList(DV_Scorpion, DV_Scorpion, DV_Wild_Hog), Arrays.asList(LINEN_HELMET, WORNOUT_SANDALS, DRIED_HIDE), new Coin(5, 8), null);

       public static Creature C_WORM = new Creature("Worm", 5, 1);
       public static Creature C_BEAR = new Creature("Wild Bear", 10, 3);
       public static Creature HUGE_WORM = new Creature("Wild Hog", 25, 4);
       public static Place CAVE = new Place("An Unwelcoming Cave", "Looks empty.", "You quickly ran back to the entrance.", Arrays.asList(C_WORM, C_BEAR, HUGE_WORM), Arrays.asList(MAP_SHARD, SMALL_LUCK_CHARM, DRIED_HIDE), new Coin(5, 15), null);

       public static Creature A_HOBO = new Creature("Ravaging Hobo", 8, 3);
       public static Place OLD_AVENUE = new Place("An Old Avenue", "Half the road is eroded.", "You got away safely.", Arrays.asList(A_HOBO, A_HOBO), Arrays.asList(MAP_SHARD, WORNOUT_SANDALS, ENGINE_GEAR), new Coin(5, 8), null);
    */
    public static int PLAYER_MAX_HEALTH = 20;
    public static int PLAYER_CURRENT_HEALTH = 1;
    public static int PLAYER_STAB_DAMAGE = 2;
    public static int PLAYER_SWING_DAMAGE = 1;
    public static ArrayList<Coordinate> WORLD_MAP;
    public static ArrayList<CraftedItem> ALL_CRAFTED_ITEMS = new ArrayList<>(Arrays.asList(BASICHATCHET, BASICPICK, SPADE, FLASK));
    public static ArrayList<Research> ALL_RESEARCH = new ArrayList<>(Arrays.asList(BUILDING, CRAFTING_BUTTON, INCREASED_STORAGE, FARMING, HOUSING));
    public static ArrayList<Building> ALL_BUILDINGS = new ArrayList<>(Arrays.asList(WORKSHOP, TOOLBENCH, DEPOSITORY, FOUNDATION, GRANARY));

    public static void postLogText(String str) {
        EventBus.getDefault().post(new LogUpdateEvent(str));
    }

    public static void toastMessage(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

}



