package me.ryanmiles.aqn.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ryanm on 5/7/2016.
 */
public class Building extends Object {
    private HashMap<Item,Integer> required;
    private ArrayList<Object> activateList;
    private boolean built = false;


    public Building(String name, String saved_name, HashMap<Item, Integer> required, boolean discovered, ArrayList<Object> activateList) {
        super(name, saved_name, discovered);
        this.required = required;
        this.activateList = activateList;
    }


    public ArrayList<Object> getActivateList() {
        return activateList;
    }

    public void setActivateList(ArrayList<Object> activateList) {
        this.activateList = activateList;
    }

    public HashMap<Item, Integer> getRequired() {
        return required;
    }

    public void setRequired(HashMap<Item, Integer> required) {
        this.required = required;
    }


    public String getContentString(){
        String content = "Needed Resources: \n";
        for(Map.Entry<Item, Integer> entry : required.entrySet()) {
            String key = entry.getKey().getName();
            int value = entry.getValue();
            content += (key + ": " + value + "\n");
        }
        return content;
    }
    public boolean build() {
        for(Map.Entry<Item, Integer> entry : required.entrySet()) {
            Item key = entry.getKey();
            int value = entry.getValue();
            if(key.getAmount() < value){
                return false;
            }
        }

        for(Map.Entry<Item, Integer> entry : required.entrySet()) {
            Item key = entry.getKey();
            int value = entry.getValue();
            key.setAmount(key.getAmount() - value);
        }

        if (activateList != null) {
            for (Object object : activateList) {
                object.setDiscovered(true);
            }
        }
        setDiscovered(false);
        built = true;
        return true;
    }

    public boolean isBuilt() {
        return built;
    }

    public void setBuilt(boolean built) {
        this.built = built;
    }

    public String getLogText() {
        return "You built a " + getName();
    }

    public void setInfo(Building info) {
        setDiscovered(info.isDiscovered());
    }
}
