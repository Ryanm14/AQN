package me.ryanmiles.aqn.data.model;

import java.util.HashMap;
import java.util.Map;

import me.ryanmiles.aqn.events.UpdateEvent;

/**
 * Created by ryanm on 5/7/2016.
 */
public class CraftedItem extends Object {
    private HashMap<Item, Integer> required;
    private UpdateEvent event;
    private boolean crafted = false;


    public CraftedItem(String name, String saved_name, HashMap<Item, Integer> required, boolean discovered, UpdateEvent event) {
        super(name, saved_name, discovered);
        this.required = required;
        this.event = event;
    }

    public UpdateEvent getEvent() {
        return event;
    }

    public void setEvent(UpdateEvent event) {
        this.event = event;
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

        if (event != null) {
            event.post();
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
        setCrafted(info.isCrafted());
    }
}
