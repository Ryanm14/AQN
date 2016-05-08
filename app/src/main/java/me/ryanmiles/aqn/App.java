package me.ryanmiles.aqn;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

import io.paperdb.Paper;
import me.ryanmiles.aqn.data.Building;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.Item;

/**
 * Created by ryanm on 5/8/2016.
 */
public class App extends Application {


    private static final String TAG = "App";

    public static void saveData(String log) {
        Paper.book().write("ITEMS", Data.ALL_ITEMS);
        Paper.book().write("BUILDINGS", Data.ALL_BUILDINGS);
        Paper.book().write("LOG", log);
        Log.d(TAG, "saveData: Saving");
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
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
        SharedPreferences prefs = getSharedPreferences("me.ryanmiles.aqn", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            prefs.edit().putBoolean("firstrun", false).apply();
        } else {
            getData();
        }
    }

}

