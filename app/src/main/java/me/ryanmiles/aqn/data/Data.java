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
            3,
            5
    );

    public static ArrayList<Item> ALL_ITEMS = new ArrayList<>(Arrays.asList(WOOD, STONE, LEAVES));

    //BUILDINGS
    public static Building TOOLBENCH = new Building(
            "Toolbench",
            "toolbench",
            new HashMap<Item, Integer>() {{
                put(WOOD, 25);
                put(LEAVES, 9);
            }},
            false,
            null
    );

    public static Building WORKSHOP = new Building(
            "Workshop",
            "workshop",
            new HashMap<Item,Integer>() {{
                put(WOOD, 20);
                put(STONE, 10);
            }},
            true,
            new ArrayList<>(Arrays.asList((Object) TOOLBENCH)));


    public static ArrayList<Building> ALL_BUILDINGS = new ArrayList<>(Arrays.asList(WORKSHOP, TOOLBENCH));


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
                put(WOOD, 2);
            }}
    );

    public static ArrayList<CraftedItem> ALL_CRAFTED_ITEMS = new ArrayList<>(Arrays.asList(STONEPICK));
}



