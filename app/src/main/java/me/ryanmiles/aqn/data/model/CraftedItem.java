package me.ryanmiles.aqn.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ryanm on 5/7/2016.
 */
public class CraftedItem extends Object {
    private HashMap<Item, Integer> required;
    private HashMap<Item, Integer> addIncrement;
    private ArrayList<Object> activateList;
    private boolean crafted = false;


    public CraftedItem(String name, String saved_name, HashMap<Item, Integer> required, boolean discovered, ArrayList<Object> activateList, HashMap<Item, Integer> addIncrement) {
        super(name, saved_name, discovered);
        this.required = required;
        this.activateList = activateList;
        this.addIncrement = addIncrement;
    }

    public HashMap<Item, Integer> getAddIncrement() {
        return addIncrement;
    }

    public void setAddIncrement(HashMap<Item, Integer> addIncrement) {
        this.addIncrement = addIncrement;
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


    public String getContentString() {
        String content = "Needed Resources: \n";
        for (Map.Entry<Item, Integer> entry : required.entrySet()) {
            String key = entry.getKey().getName();
            int value = entry.getValue();
            content += (key + ": " + value + "\n");
        }
        return content;
    }

    public boolean craft() {
        for (Map.Entry<Item, Integer> entry : required.entrySet()) {
            Item key = entry.getKey();
            int value = entry.getValue();
            if (key.getAmount() < value) {
                return false;
            }
        }

        for (Map.Entry<Item, Integer> entry : required.entrySet()) {
            Item key = entry.getKey();
            int value = entry.getValue();
            key.setAmount(key.getAmount() - value);
        }

        if (activateList != null) {
            for (Object object : activateList) {
                object.setDiscovered(true);
            }
        }

        if (addIncrement != null) {
            for (Map.Entry<Item, Integer> entry : addIncrement.entrySet()) {
                Item key = entry.getKey();
                int value = entry.getValue();
                key.setIncrement(value);
            }
        }
        setDiscovered(false);
        crafted = true;
        return true;
    }

    public boolean isCrafted() {
        return crafted;
    }

    public void setCrafted(boolean crafted) {
        this.crafted = crafted;
    }

    public String getLogText() {
        return "You crafted a " + getName();
    }

    public void setInfo(CraftedItem info) {
        setDiscovered(info.isDiscovered());
    }
}
