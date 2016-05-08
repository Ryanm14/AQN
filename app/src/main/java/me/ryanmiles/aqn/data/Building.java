package me.ryanmiles.aqn.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ryanm on 5/7/2016.
 */
public class Building {
    private String name;
    private String saved_name;
    private HashMap<Item,Integer> required;
    private boolean discovered;

    public Building(String name, String saved_name, HashMap<Item, Integer> required, boolean discovered) {
        this.name = name;
        this.saved_name = saved_name;
        this.required = required;
        this.discovered = discovered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaved_name() {
        return saved_name;
    }

    public void setSaved_name(String saved_name) {
        this.saved_name = saved_name;
    }

    public HashMap<Item, Integer> getRequired() {
        return required;
    }

    public void setRequired(HashMap<Item, Integer> required) {
        this.required = required;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
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
        return true;
    }

    public String getLogText() {
        return "You built a " + name;
    }
}
