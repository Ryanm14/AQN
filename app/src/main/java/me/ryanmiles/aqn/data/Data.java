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
import me.ryanmiles.aqn.events.LogUpdateEvent;
import me.ryanmiles.aqn.events.UpdateEvent;

/**
 * Created by ryanm on 5/7/2016.
 */
public class Data {


    public static boolean CRAFTING_NEW_DATA = false;
    public static boolean BUILDING_NEW_DATA = false;

    //Save
    public static boolean OPENFOREST = false;
    public static boolean FIRSTRUN = false;
    public static boolean OPENBUILDINGS = false;
    public static boolean OPENCRAFTING = false;
    public static boolean OPENVILLAGE = false;

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

    public static Item HIDE = new Item(
            0,
            25,
            "Hide",
            "hide",
            1
    );

    public static Item FOOD = new Item(
            0,
            35,
            "Meat",
            "meat",
            1
    );


    public static ArrayList<Item> ALL_ITEMS = new ArrayList<>(Arrays.asList(WOOD, STONE, HIDE, FOOD));

    //BUILDINGS

    public static Building DEPOSITORY = new Building(
            "Depository",
            "depository",
            new HashMap<Item, Integer>() {{
                put(STONE, 35);
                put(WOOD, 35);
            }},
            100,
            new UpdateEvent().setChangeMaxEvent(WOOD, 100).setChangeMaxEvent(STONE, 100)
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
    public static Building FOUNDATION = new Building(
            "Foundation",
            "foundation",
            new HashMap<Item, Integer>() {{
                put(STONE, 70);
            }},
            145,
            null
    );


    public static Building TOOLBENCH = new Building(
            "Toolbench",
            "toolbench",
            new HashMap<Item, Integer>() {{
                put(WOOD, 45);
            }},
            35,
            new UpdateEvent().setDiscoveredEvent(DEPOSITORY)
    );

    public static Building WORKSHOP = new Building(
            "Workshop", //Name
            "workshop", //Saved Name
            new HashMap<Item, Integer>() {{ //Required Items
                put(WOOD, 20);
                put(STONE, 10);
            }},
            true, //IsDiscovered
            35,
            new UpdateEvent().setDiscoveredEvent(TOOLBENCH)
    );


    public static ArrayList<Building> ALL_BUILDINGS = new ArrayList<>(Arrays.asList(WORKSHOP, TOOLBENCH, DEPOSITORY, FOUNDATION));



    //CRAFTED_ITEMS
    public static CraftedItem BASICHATCHET = new CraftedItem(
            "Basic Hatchet",
            "basic_hatchet",
            new HashMap<Item, Integer>() {{
                put(WOOD, 30);
                put(STONE, 30);
            }},
            true,
            new UpdateEvent().setAddIncrementEvent(WOOD, 2),
            60
    );
    public static CraftedItem FLOORPLANS = new CraftedItem(
            "Floor Plans",
            "floor_plans",
            new HashMap<Item, Integer>() {{
                put(WOOD, 5);
            }},
            true,
            new UpdateEvent().setDiscoveredEvent(FOUNDATION),
            200
    );

    public static CraftedItem BASICPICK = new CraftedItem(
            "Basic Pick",
            "basic_pick",
            new HashMap<Item, Integer>() {{
                put(WOOD, 30);
                put(STONE, 45);
            }},
            true,
            null,
            60
    );

    public static CraftedItem BASICHAMMER = new CraftedItem(
            "Basic Hammer",
            "basic_hammer",
            new HashMap<Item, Integer>() {{
                put(WOOD, 35);
                put(STONE, 40);
            }},
            true,
            new UpdateEvent().setDiscoveredEvent(DEPOSITORY).setDiscoveredEvent(FLOORPLANS).setDiscoveredEvent(BASICPICK),
            45
    );





   /* public static CraftedItem COPPERPICK = new CraftedItem(
            "Copper Pickaxe",
            "Copper_pickaxe",
            new HashMap<Item, Integer>() {{
                put(WOOD, 25);
                put(REFINED_COPPER, 7);
            }},
            new UpdateEvent().setAddIncrementEvent(STONE, 5),
            125
    );

    public static CraftedItem COPPERAXE = new CraftedItem(
            "Copper Axe",
            "Copper_axe",
            new HashMap<Item, Integer>() {{
                put(WOOD, 30);
                put(REFINED_COPPER, 5);
            }},
            new UpdateEvent().setAddIncrementEvent(WOOD, 5),
            125
    );

    public static CraftedItem COPPERSWORD = new CraftedItem(
            "Copper Sword",
            "copper_sword",
            new HashMap<Item, Integer>() {{
                put(WOOD, 20);
                put(REFINED_COPPER, 10);
            }},
            null,
            135
    );
    */

    public static ArrayList<CraftedItem> ALL_CRAFTED_ITEMS = new ArrayList<>(Arrays.asList(BASICHAMMER, BASICHATCHET, FLOORPLANS));
    //People

    public static People FARMER = new People("Farmer", "farmer", true, FOOD);
    public static People LUMBERJACK = new People("Lumberjack", "lumberjack", true, WOOD);
    public static People MINER = new People("Miner", "miner", true, STONE);
    public static ArrayList<People> PEOPLE_LIST = new ArrayList<>(Arrays.asList(FARMER, LUMBERJACK, MINER));
    //Loot6


    /*   public static Loot COPPER = new Loot(
               false,
               0,
               15,
               "Raw Copper",
               "raw_copper",
               3,
               5,
               true
       );

       public static Loot LINEN_JACKET = new Loot(
               false,
               "Rugged Linen Jacket",
               "linen_jacket",
               80
       );

       public static Loot RUSTED_BRACELET = new Loot(
               false,
               "Rusted Bracelet",
               "rusted_bracelet",
               25
       );

       public static Loot LINEN_HELMET = new Loot(
               false,
               "Rugged Linen Helmet",
               "linen_helmet",
               80
       );

       public static Loot WORNOUT_SANDALS = new Loot(
               false,
               "Worn out Sandals",
               "wornout_sandals",
               50
       );

       public static Loot SMALL_LUCK_CHARM = new Loot(
               false,
               "Small Luck Charm",
               "small_luck_charm",
               25
       );

       public static Loot MAP_SHARD = new Loot(
               false,
               "Map Shard",
               "map_shard",
               40
       );

       public static Loot ENGINE_GEAR = new Loot(
               false,
               "Engine Gear",
               "engine_gear",
               45
       );

       public static Loot DRIED_HIDE = new Loot(
               false,
               0,
               15,
               "Dried Hide",
               "dried_hide",
               3,
               5,
               true
       );

       public static ArrayList<Loot> ALL_LOOT = new ArrayList<>(Arrays.asList(COPPER, REFINED_COPPER, LINEN_JACKET, RUSTED_BRACELET, LINEN_HELMET, WORNOUT_SANDALS, SMALL_LUCK_CHARM, MAP_SHARD, ENGINE_GEAR, DRIED_HIDE));

       //World Data
       public static Creature AV_RAT = new Creature("Coal Rat", 5, 1);
       public static Creature AV_WILD_BAT = new Creature("Wild Bat", 10, 2);
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

    public static void postLogText(String str){
        EventBus.getDefault().post(new LogUpdateEvent(str));
    }
    public static void toastMessage(Context context, String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }


}



