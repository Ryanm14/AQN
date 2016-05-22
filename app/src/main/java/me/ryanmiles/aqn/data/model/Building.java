package me.ryanmiles.aqn.data.model;

import java.util.HashMap;
import java.util.Map;

import me.ryanmiles.aqn.events.UpdateEvent;

/**
 * Created by ryanm on 5/7/2016.
 */
public class Building extends Object {
    private HashMap<Item,Integer> required;
    private boolean built = false;
    private UpdateEvent event;

    public Building(String name, String saved_name, HashMap<Item, Integer> required, boolean discovered, UpdateEvent event) {
        super(name, saved_name, discovered);
        this.required = required;
        this.event = event;
    }

    public HashMap<Item, Integer> getRequired() {
        return required;
    }

    public void setRequired(HashMap<Item, Integer> required) {
        this.required = required;
    }

    public UpdateEvent getEvent() {
        return event;
    }

    public void setEvent(UpdateEvent event) {
        this.event = event;
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
        if (event != null) {
            event.post();
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
        setBuilt(info.isBuilt());
    }
}
