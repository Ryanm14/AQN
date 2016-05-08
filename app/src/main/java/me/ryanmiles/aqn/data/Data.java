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
            1
    );

    public static Item STONE = new Item(
            true,
            0,
            50,
            "Stone",
            "stone",
            1
    );
    public static ArrayList<Item> ALL_ITEMS = new ArrayList<>(Arrays.asList(WOOD,STONE));


    public static Building WORKSHOP = new Building(
            "Workshop",
            "workshop",
            new HashMap<Item,Integer>() {{
                put(WOOD, 10);
                put(STONE, 10);
            }},
            true
    );

    public static ArrayList<Building> ALL_BUILDINGS = new ArrayList<>(Arrays.asList(WORKSHOP));

}



