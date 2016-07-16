package me.ryanmiles.aqn.data;

import android.content.Context;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import me.ryanmiles.aqn.data.model.Building;
import me.ryanmiles.aqn.data.model.Coin;
import me.ryanmiles.aqn.data.model.Coordinate;
import me.ryanmiles.aqn.data.model.CraftedItem;
import me.ryanmiles.aqn.data.model.Creature;
import me.ryanmiles.aqn.data.model.Item;
import me.ryanmiles.aqn.data.model.Loot;
import me.ryanmiles.aqn.data.model.Place;
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
    //ITEMS
    public static Item WOOD = new Item(
            false,
            0,
            50,
            "Wood",
            "wood",
            1,
            0
    );

    public static Item STONE = new Item(
            false,
            0,
            50,
            "Stone",
            "stone",
            1,
            0
    );

    public static Item LEAVES = new Item(
            false,
            0,
            25,
            "Leaves",
            "leaves",
            2,
            25
    );


    public static Item HIDE = new Item(
            false,
            0,
            25,
            "Hide",
            "hide",
            4,
            25
    );

    public static Item MEAT = new Item(
            false,
            0,
            35,
            "Meat",
            "meat",
            1,
            0
    );


    public static ArrayList<Item> ALL_ITEMS = new ArrayList<>(Arrays.asList(WOOD, STONE, LEAVES, HIDE, MEAT));

    //BUILDINGS
/*
    public static Building BASIC_SMELTERY = new Building(
            "Basic Smeltery",
            "basic_smeltery",
            new HashMap<Item, Integer>() {{
                put(STONE, 85);
                put(WOOD, 50);
            }},
            false,
            null
    );

    public static Building WOODPILE = new Building(
            "Wood Pile",
            "wood_pile",
            new HashMap<Item, Integer>() {{
                put(WOOD, 50);
            }},
            false,
            new UpdateEvent().setChangeMaxEvent(WOOD, 100)
    );
*/
    public static Building STONEPILE = new Building(
            "Stone Pile",
            "stone_pile",
            new HashMap<Item, Integer>() {{
                put(STONE, 10);
            }},
            true,
            90,
            null
    );


    public static Building TOOLBENCH = new Building(
            "Toolbench",
            "toolbench",
            new HashMap<Item, Integer>() {{
                put(WOOD, 25);
                put(LEAVES, 9);
            }},
            20,
            new UpdateEvent().setDiscoveredEvent(STONEPILE)
    );

    public static Building WORKSHOP = new Building(
            "Workshop", //Name
            "workshop", //Saved Name
            new HashMap<Item, Integer>() {{ //Required Items
                put(WOOD, 20);
                put(STONE, 10);
            }},
            true, //IsDiscovered
            25,
            new UpdateEvent().setDiscoveredEvent(TOOLBENCH)
    );


    public static ArrayList<Building> ALL_BUILDINGS = new ArrayList<>(Arrays.asList(WORKSHOP, TOOLBENCH, STONEPILE));

    public static Loot REFINED_COPPER = new Loot(
            false,
            0,
            15,
            "Refined Copper",
            "refined_copper",
            3,
            5,
            true
    );

    //CRAFTED_ITEMS
    public static CraftedItem STONEPICK = new CraftedItem(
            "Stone Pickaxe",
            "stone_pickaxe",
            new HashMap<Item, Integer>() {{
                put(WOOD, 10);
                put(STONE, 35);
            }},
            true,
            new UpdateEvent().setAddIncrementEvent(STONE, 2),
            45
    );

    public static CraftedItem STONEAXE = new CraftedItem(
            "Stone Axe",
            "stone_axe",
            new HashMap<Item, Integer>() {{
                put(WOOD, 15);
                put(STONE, 20);
            }},
            true,
            new UpdateEvent().setAddIncrementEvent(WOOD, 2),
            45
    );

    public static CraftedItem STONESWORD = new CraftedItem(
            "Stone Sword",
            "stone_sword",
            new HashMap<Item, Integer>() {{
                put(WOOD, 30);
                put(STONE, 35);
            }},
            true,
            null,
            60
    );

    public static CraftedItem COPPERPICK = new CraftedItem(
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

    public static ArrayList<CraftedItem> ALL_CRAFTED_ITEMS = new ArrayList<>(Arrays.asList(STONEPICK, STONEAXE, STONESWORD, COPPERAXE, COPPERPICK, COPPERSWORD));

    //Loot


    public static Loot COPPER = new Loot(
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



