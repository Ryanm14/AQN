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
            15,
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

    public static Item COAL = new Item(
            0,
            10,
            "Coal",
            "coal",
            1
    );

    public static Item RAW_ORE = new Item(
            0,
            10,
            "Raw Ore",
            "raw_ore",
            1
    );

    public static Item BRONZE = new Item(
            0,
            10,
            "Bronze",
            "bronze",
            1
    );

    public static Item LEATHER = new Item(
            0,
            20,
            "Leather",
            "leather",
            1
    );


    public static ArrayList<Item> ALL_ITEMS = new ArrayList<>(Arrays.asList(WOOD, STONE, WATER, FOOD, COAL, RAW_ORE, BRONZE, LEATHER));

    //BUILDINGS
    public static CraftedItem LEATHER_ARMOR = new CraftedItem(
            "Leather Armor",
            "leather_armor",
            new HashMap<Item, Integer>() {{
                put(LEATHER, 18);
                put(WOOD, 20);
            }},
            null,
            300
    );
    public static People TANNER = new People("Tanner", "tanner", LEATHER);
    public static Building TANNERY = new Building(
            "Tannery",
            "tannery",
            new HashMap<Item, Integer>() {{
                put(STONE, 50);
                put(WOOD, 45);
            }},
            240,
            new UpdateEvent().setDiscoveredEvent(TANNER).setDiscoveredEvent(LEATHER_ARMOR)
    );
    public static Research TANNING = new Research("Tanning", "tanning", new UpdateEvent().setDiscoveredEvent(TANNERY), 180);
    public static People BRONZE_SMELTER = new People("Bronze Smelter", "bronze_smelter", BRONZE);
    public static Research BRONZE_RESEARCH = new Research("Bronze", "bronze", new UpdateEvent().setDiscoveredEvent(BRONZE_SMELTER), 250);
    public static Building SMELTERY = new Building(
            "Smeltery",
            "smeltery",
            new HashMap<Item, Integer>() {{
                put(STONE, 75);
                put(WOOD, 20);
            }},
            135,
            new UpdateEvent().setDiscoveredEvent(BRONZE_RESEARCH)
    );
    public static Building HOUSE = new Building(
            "House",
            "house",
            new HashMap<Item, Integer>() {{
                put(STONE, 50);
                put(WOOD, 60);
            }},
            500,
            new UpdateEvent().increaseVillageMax(25)
    );

    public static Building SHACK = new Building(
            "Shack",
            "shack",
            new HashMap<Item, Integer>() {{
                put(STONE, 20);
                put(WOOD, 40);
            }},
            150,
            new UpdateEvent().increaseVillageMax(15)
    );

    public static People COAL_MINER = new People("Coal Miner", "coal_miner", COAL);
    public static Research SMELTING = new Research("Stab", "stab", new UpdateEvent().setDiscoveredEvent(SMELTERY), 200);
    public static Research MINING = new Research("Preserve", "preserve", new UpdateEvent().setDiscoveredEvent(COAL_MINER), 180);
    public static Research STAB = new Research("Stab", "stab", null, 200);
    public static Research PRESERVE = new Research("Preserve", "preserve", new UpdateEvent().setChangeMaxEvent(FOOD, 10), 200);
    public static Research PURIFY = new Research("Purify", "purify", new UpdateEvent().setChangeMaxEvent(WATER, 30), 200);
    public static Research ADVENTURE = new Research("Adventure", "adventure", null, 300);
    public static Research HOUSING = new Research("Housing", "housing", new UpdateEvent().setDiscoveredEvent(ADVENTURE).setDiscoveredEvent(SHACK).setDiscoveredEvent(HOUSE), 120);
    public static Building FOUNDATION = new Building(
            "Foundation",
            "foundation",
            new HashMap<Item, Integer>() {{
                put(STONE, 60);
                put(WOOD, 35);
            }},
            100,
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
    public static People STONE_MINER = new People("Stone Miner", "stone_miner", true, STONE);
    public static ArrayList<People> PEOPLE_LIST = new ArrayList<>(Arrays.asList(FARMER, LUMBERJACK, STONE_MINER, COAL_MINER, BRONZE_SMELTER, TANNER));

    public static int PLAYER_MAX_HEALTH = 20;
    public static int PLAYER_CURRENT_HEALTH = 1;
    public static int PLAYER_STAB_DAMAGE = 2;
    public static int PLAYER_SWING_DAMAGE = 1;
    public static ArrayList<Coordinate> WORLD_MAP;
    public static ArrayList<CraftedItem> ALL_CRAFTED_ITEMS = new ArrayList<>(Arrays.asList(BASICHATCHET, BASICPICK, SPADE, FLASK, LEATHER_ARMOR));
    public static ArrayList<Research> ALL_RESEARCH = new ArrayList<>(Arrays.asList(BUILDING, CRAFTING_BUTTON, INCREASED_STORAGE, FARMING, HOUSING, PURIFY, PRESERVE, STAB, MINING, SMELTING, BRONZE_RESEARCH, TANNING, ADVENTURE));
    public static ArrayList<Building> ALL_BUILDINGS = new ArrayList<>(Arrays.asList(WORKSHOP, TOOLBENCH, DEPOSITORY, FOUNDATION, GRANARY, SMELTERY, SHACK, HOUSE, TANNERY));

    public static void postLogText(String str) {
        EventBus.getDefault().post(new LogUpdateEvent(str));
    }

    public static void toastMessage(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

}



