package me.ryanmiles.aqn.data.model;

import java.util.HashMap;
import java.util.Map;

import me.ryanmiles.aqn.events.UpdateEvent;

/**
 * Created by ryanm on 5/7/2016.
 */
public class Building extends Object {
    private HashMap<Item, Integer> required;
    private boolean built = false;
    private UpdateEvent event;
    private int timeToComplete = 0;
    private boolean beingBuilt = false;
    private long startTime = 0;
    private int currentProgress = 0;
    private boolean readyForCompletion = false;

    public Building(String name, String saved_name, HashMap<Item, Integer> required, boolean discovered, int timeToComplete, UpdateEvent event) {
        super(name, saved_name, discovered);
        this.required = required;
        this.event = event;
        this.timeToComplete = timeToComplete;
    }

    //Default
    public Building(String name, String saved_name, HashMap<Item, Integer> required, int timeToComplete, UpdateEvent event) {
        super(name, saved_name, false);
        this.required = required;
        this.event = event;
        this.timeToComplete = timeToComplete;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int progress) {
        currentProgress = progress;
    }

    public int getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(int timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public boolean isBeingBuilt() {
        return beingBuilt;
    }

    public void setBeingBuilt(boolean beingBuilt) {
        this.beingBuilt = beingBuilt;
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


    public String getContentString() {
        String content = "Needed Resources: \n";
        for (Map.Entry<Item, Integer> entry : required.entrySet()) {
            String key = entry.getKey().getName();
            int value = entry.getValue();
            content += (key + ": " + value + "\n");
        }
        content += "Time needed to complete: " + timeToComplete + " Seconds.";
        return content;
    }

    public boolean checkRequiredItems() {
        for (Map.Entry<Item, Integer> entry : required.entrySet()) {
            Item key = entry.getKey();
            int value = entry.getValue();
            if (key.getAmount() < value) {
                return false;
            }
        }
        return true;
    }

    public void removeRequiredItems() {
        for (Map.Entry<Item, Integer> entry : required.entrySet()) {
            Item key = entry.getKey();
            int value = entry.getValue();
            key.setAmount(key.getAmount() - value);
        }
    }

    public void build() {
        if (event != null) {
            event.post();
        }
        setDiscovered(false);
        beingBuilt = false;
        built = true;
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
        timeToComplete = info.getTimeToComplete();
        currentProgress = info.getCurrentProgress();
        beingBuilt = info.isBeingBuilt();
        startTime = info.getStartTime();
        readyForCompletion = info.isReadyForCompletion();
    }

    public String startingLogText() {
        return "The builder started construction on a " + getName();
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isReadyForCompletion() {
        return readyForCompletion;
    }

    public void setReadyForCompletion(boolean readyForCompletion) {
        this.readyForCompletion = readyForCompletion;
    }
}
