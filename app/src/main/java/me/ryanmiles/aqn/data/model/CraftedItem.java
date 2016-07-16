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
    private int timeToComplete = 0;
    private boolean beingCrafted = false;
    private long startTime = 0;
    private int currentProgress = 0;
    private boolean readyForCompletion = false;
    private boolean repeatable;


    public CraftedItem(String name, String saved_name, HashMap<Item, Integer> required, boolean discovered, UpdateEvent event, int timeToComplete) {
        super(name, saved_name, discovered);
        this.required = required;
        this.event = event;
        this.timeToComplete = timeToComplete;
    }

    public CraftedItem(String name, String saved_name, HashMap<Item, Integer> required, UpdateEvent event, int timeToComplete) { //Default False
        super(name, saved_name, false);
        this.required = required;
        this.event = event;
        this.timeToComplete = timeToComplete;
    }

    public int getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(int timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public boolean isBeingCrafted() {
        return beingCrafted;
    }

    public void setBeingCrafted(boolean beingCrafted) {
        this.beingCrafted = beingCrafted;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public boolean isReadyForCompletion() {
        return readyForCompletion;
    }

    public void setReadyForCompletion(boolean readyForCompletion) {
        this.readyForCompletion = readyForCompletion;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
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
        beingCrafted = false;
        crafted = true;
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

    public String startingLogText() {
        return "You started working on a " + getName();
    }

    public void setInfo(CraftedItem info) {
        setDiscovered(info.isDiscovered());
        setCrafted(info.isCrafted());
        timeToComplete = info.getTimeToComplete();
        currentProgress = info.getCurrentProgress();
        beingCrafted = info.isBeingCrafted();
        startTime = info.getStartTime();
        readyForCompletion = info.isReadyForCompletion();
    }

    @Override
    public String toString() {
        return "CraftedItem{" +
                super.toString() +
                "required=" + required +
                ", event=" + event +
                ", crafted=" + crafted +
                ", timeToComplete=" + timeToComplete +
                ", beingCrafted=" + beingCrafted +
                ", startTime=" + startTime +
                ", currentProgress=" + currentProgress +
                ", readyForCompletion=" + readyForCompletion +
                ", repeatable=" + repeatable +
                '}';
    }

}
