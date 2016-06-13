package me.ryanmiles.aqn;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;
import me.ryanmiles.aqn.data.Data;
import me.ryanmiles.aqn.data.model.Building;
import me.ryanmiles.aqn.data.model.CraftedItem;
import me.ryanmiles.aqn.data.model.Item;
import me.ryanmiles.aqn.data.model.Loot;

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
                Paper.book().write("LOOT", Data.ALL_LOOT);
                Paper.book().write("LOG", log);
                Log.d(TAG, "saveData: Saving");
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

        ArrayList<Loot> savedLoot = Paper.book().read("LOOT", new ArrayList<Loot>());
        for (Loot loot : Data.ALL_LOOT) {
            for (Loot mSavedLoot : savedLoot) {
                if (loot.getName().equals(mSavedLoot.getName())) {
                    loot.setInfo(mSavedLoot);
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this);
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
        Paper.init(this);
        SharedPreferences prefs = getSharedPreferences("me.ryanmiles.aqn", MODE_PRIVATE);
        if (prefs.getBoolean("firstrunv2", true)) {
            Log.d(TAG, "First Run");
            prefs.edit().putBoolean("firstrunv2", false).commit();
            //TODO CHANGE PREF TO UPDATE SO IT DOESNT CRASH
        } else {
            getData();
        }
    }


}

