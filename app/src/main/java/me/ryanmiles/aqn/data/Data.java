package me.ryanmiles.aqn.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by ryanm on 5/7/2016.
 */
public class Data {

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

}



