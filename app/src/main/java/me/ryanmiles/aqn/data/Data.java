package me.ryanmiles.aqn.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import me.ryanmiles.aqn.data.model.Building;
import me.ryanmiles.aqn.data.model.CraftedItem;
import me.ryanmiles.aqn.data.model.Creature;
import me.ryanmiles.aqn.data.model.Item;
import me.ryanmiles.aqn.data.model.Place;
import me.ryanmiles.aqn.events.UpdateEvent;

/**
 * Created by ryanm on 5/7/2016.
 */
public class Data {


    //ITEMS
    public static Item WOOD = new Item(
            true,
            0,
            50,
            "Wood",
            "wood",
            1,
            0
    );

    public static Item STONE = new Item(
            true,
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

    public static Item TIN = new Item(
            false,
            0,
            15,
            "Tin",
            "tin",
            2,
            10
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

    public static Item TIN_BULLION = new Item(
            false,
            0,
            15,
            "Tin Bullion",
            "tin_bullion",
            1,
            0
    );

    public static ArrayList<Item> ALL_ITEMS = new ArrayList<>(Arrays.asList(WOOD, STONE, LEAVES, TIN, HIDE, MEAT, TIN_BULLION));

    //BUILDINGS

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

    public static Building STONEPILE = new Building(
            "Stone Pile",
            "stone_pile",
            new HashMap<Item, Integer>() {{
                put(STONE, 50);
            }},
            false,
            new UpdateEvent().setChangeMaxEvent(STONE, 100).setDiscoveredEvent(BASIC_SMELTERY)
    );


    public static Building TOOLBENCH = new Building(
            "Toolbench",
            "toolbench",
            new HashMap<Item, Integer>() {{
                put(WOOD, 25);
                put(LEAVES, 9);
            }},
            false,
            new UpdateEvent().setDiscoveredEvent(WOODPILE).setDiscoveredEvent(STONEPILE)
    );

    public static Building WORKSHOP = new Building(
            "Workshop", //Name
            "workshop", //Saved Name
            new HashMap<Item, Integer>() {{ //Required Items
                put(WOOD, 20);
                put(STONE, 10);
            }},
            true, //IsDiscovered
            new UpdateEvent().setDiscoveredEvent(TOOLBENCH)
    );


    public static ArrayList<Building> ALL_BUILDINGS = new ArrayList<>(Arrays.asList(WORKSHOP, TOOLBENCH, WOODPILE, STONEPILE, BASIC_SMELTERY));


    //CRAFTED_ITEMS
    public static CraftedItem STONEPICK = new CraftedItem(
            "Stone Pickaxe",
            "stone_pickaxe",
            new HashMap<Item, Integer>() {{
                put(WOOD, 10);
                put(STONE, 35);
            }},
            true,
            new UpdateEvent().setAddIncrementEvent(STONE, 2)
    );

    public static CraftedItem STONEAXE = new CraftedItem(
            "Stone Axe",
            "stone_axe",
            new HashMap<Item, Integer>() {{
                put(WOOD, 15);
                put(STONE, 20);
            }},
            true,
            new UpdateEvent().setAddIncrementEvent(WOOD, 2)
    );

    public static CraftedItem STONESWORD = new CraftedItem(
            "Stone Sword",
            "stone_sword",
            new HashMap<Item, Integer>() {{
                put(WOOD, 30);
                put(STONE, 35);
            }},
            true,
            null
    );

    public static ArrayList<CraftedItem> ALL_CRAFTED_ITEMS = new ArrayList<>(Arrays.asList(STONEPICK, STONEAXE, STONESWORD));


    //
    public static Creature AV_RAT = new Creature("Dust Rat", 5, 1);
    public static Creature AV_WILD_BAT = new Creature("Wild Bat", 10, 2);

    public static Place AV = new Place("An Abandoned Mine", "What a dusty place.", Arrays.asList(AV_RAT, AV_WILD_BAT, AV_WILD_BAT, AV_RAT), null);
    public static int PLAYER_MAX_HEALTH = 20;
    public static int PLAYER_CURRENT_HEALTH = 1;
    public static int PLAYER_STAB_DAMAGE = 2;
    public static int PLAYER_SWING_DAMAGE = 1;
}



