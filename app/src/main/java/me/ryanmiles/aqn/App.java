package me.ryanmiles.aqn;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.testfairy.TestFairy;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.Places;
import me.ryanmiles.aqn.data.model.Building;
import me.ryanmiles.aqn.data.model.CraftedItem;
import me.ryanmiles.aqn.data.model.Item;
import me.ryanmiles.aqn.data.model.People;
import me.ryanmiles.aqn.data.model.Place;
import me.ryanmiles.aqn.data.model.Research;

/**
 * Created by ryanm on 5/8/2016.
 */
public class App extends Application {


    private static final String TAG = "App";

    public static void saveData(final String log) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Paper.book().write("ITEMS", Data.ALL_ITEMS);
                Paper.book().write("BUILDINGS", Data.ALL_BUILDINGS);
                Paper.book().write("CRAFTED_ITEMS", Data.ALL_CRAFTED_ITEMS);
                Paper.book().write("VILLAGE", Data.PEOPLE_LIST);
                Paper.book().write("VILLAGE_MAX", People.VILLAGE_MAX_POPULATION);
                Paper.book().write("LOG", log);
                Paper.book().write("RESEARCH", Data.ALL_RESEARCH);
                Paper.book().write("PLACES", Places.ALL_PLACES);
                Log.i(TAG, "saveData: Saving");
            }
        });
        thread.start();
    }

    public static void getData() {
        ArrayList<Item> savedItems = Paper.book().read("ITEMS", new ArrayList<Item>());
        for (Item item : Data.ALL_ITEMS) {
            for (Item savedItem : savedItems) {
                if (item.getName().equals(savedItem.getName())) {
                    item.setInfo(savedItem);
                }
            }
        }

        ArrayList<Building> savedBuildings = Paper.book().read("BUILDINGS", new ArrayList<Building>());
        for (Building building : Data.ALL_BUILDINGS) {
            for (Building savedBuilding : savedBuildings) {
                if (building.getName().equals(savedBuilding.getName())) {
                    building.setInfo(savedBuilding);
                }
            }
        }

        ArrayList<CraftedItem> savedCraftedItems = Paper.book().read("CRAFTED_ITEMS", new ArrayList<CraftedItem>());
        for (CraftedItem craftedItem : Data.ALL_CRAFTED_ITEMS) {
            for (CraftedItem savedCraftedItem : savedCraftedItems) {
                if (craftedItem.getName().equals(savedCraftedItem.getName())) {
                    craftedItem.setInfo(savedCraftedItem);
                }
            }
        }

        ArrayList<People> savedPeople = Paper.book().read("VILLAGE", new ArrayList<People>());
        for (People people : Data.PEOPLE_LIST) {
            for (People mSavedPeople : savedPeople) {
                if (people.getName().equals(mSavedPeople.getName())) {
                    people.setInfo(mSavedPeople);
                }
            }
        }

        ArrayList<Research> savedResearch = Paper.book().read("RESEARCH", new ArrayList<Research>());
        for (Research research : Data.ALL_RESEARCH) {
            for (Research mSavedResearch : savedResearch) {
                if (research.getName().equals(mSavedResearch.getName())) {
                    research.setInfo(mSavedResearch);
                }
            }
        }
        People.VILLAGE_MAX_POPULATION = Paper.book().read("VILLAGE_MAX");

        ArrayList<Place> savedPlaces = Paper.book().read("PLACES", new ArrayList<Place>());
        for (Place place : Places.ALL_PLACES) {
            for (Place mSavedPlaces : savedPlaces) {
                if (place.getName().equals(mSavedPlaces.getName())) {
                    place.setInfo(mSavedPlaces);
                }
            }
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        TestFairy.begin(this, "873cb9ae0c2b9898c9bf38457d012dd54681fbfa");
        Fabric.with(this, new Crashlytics());
        Paper.init(this);
        SharedPreferences prefs = getSharedPreferences("me.ryanmiles.aqn", MODE_PRIVATE);
        if (prefs.getBoolean("free", true)) {
            Log.d(TAG, "First Run");
            Data.FIRSTRUN = true;
            prefs.edit().putBoolean("free", false).commit();
            Paper.book().destroy();
            //TODO CHANGE PREF TO UPDATE SO IT DOESNT CRASH
        } else {
            getData();
        }
        Data.BUILDING_NEW_DATA = true;
        Data.CRAFTING_NEW_DATA = true;
        Data.RESEARCH_NEW_DATA = true;
    }


}

