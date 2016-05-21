package me.ryanmiles.aqn.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import me.ryanmiles.aqn.data.model.Building;
import me.ryanmiles.aqn.data.model.CraftedItem;
import me.ryanmiles.aqn.data.model.Item;
import me.ryanmiles.aqn.data.model.Object;

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
            45
    );

    public static Item TIN = new Item(
            false,
            0,
            35,
            "Tin",
            "tin",
            2,
            20
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

    public static ArrayList<Item> ALL_ITEMS = new ArrayList<>(Arrays.asList(WOOD, STONE, LEAVES, TIN, HIDE, MEAT));

    //BUILDINGS

    public static Building WOODPILE = new Building(
            "Wood Pile",
            "wood_pile",
            new HashMap<Item, Integer>() {{
                put(WOOD, 50);
            }},
            false,
            null,
            new HashMap<Item, Integer>() {{
                put(WOOD, 100);
            }}
    );

    public static Building STONEPILE = new Building(
            "Stone Pile",
            "stone_pile",
            new HashMap<Item, Integer>() {{
                put(STONE, 50);
            }},
            false,
            null,
            new HashMap<Item, Integer>() {{
                put(STONE, 100);
            }}
    );


    public static Building TOOLBENCH = new Building(
            "Toolbench",
            "toolbench",
            new HashMap<Item, Integer>() {{
                put(WOOD, 25);
                put(LEAVES, 9);
            }},
            false,
            new ArrayList<>(Arrays.asList((Object) WOODPILE, STONEPILE)),
            null
    );

    public static Building WORKSHOP = new Building(
            "Workshop", //Name
            "workshop", //Saved Name
            new HashMap<Item, Integer>() {{ //Required Items
                put(WOOD, 20);
                put(STONE, 10);
            }},
            true, //IsDiscovered
            new ArrayList<>(Arrays.asList((Object) TOOLBENCH)), //SetDiscovered
            null //Update Max
    );


    public static ArrayList<Building> ALL_BUILDINGS = new ArrayList<>(Arrays.asList(WORKSHOP, TOOLBENCH, WOODPILE, STONEPILE));


    //CRAFTED_ITEMS
    public static CraftedItem STONEPICK = new CraftedItem(
            "Stone Pickaxe",
            "stone_pickaxe",
            new HashMap<Item, Integer>() {{
                put(WOOD, 10);
                put(STONE, 35);
            }},
            true,
            null,
            new HashMap<Item, Integer>() {{
                put(STONE, 2);
            }}
    );

    public static CraftedItem STONEAXE = new CraftedItem(
            "Stone Axe",
            "stone_axe",
            new HashMap<Item, Integer>() {{
                put(WOOD, 15);
                put(STONE, 20);
            }},
            true,
            null,
            new HashMap<Item, Integer>() {{
                put(WOOD, 2);
            }}
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
            null
    );

    public static ArrayList<CraftedItem> ALL_CRAFTED_ITEMS = new ArrayList<>(Arrays.asList(STONEPICK, STONEAXE, STONESWORD));
}



