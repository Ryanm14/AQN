package me.ryanmiles.aqn.data.model;

import me.ryanmiles.aqn.events.UpdateEvent;

/**
 * Created by Ryan Miles on 7/28/2016.
 */
public class Research extends Object {
    private UpdateEvent event;
    private boolean researched = false;
    private int timeToComplete = 0;
    private boolean beingResearched = false;
    private long startTime = 0;
    private int currentProgress = 0;
    private boolean readyForCompletion = false;
    private boolean repeatable;


    public Research(String name, String saved_name, boolean discovered, UpdateEvent event, int timeToComplete) {
        super(name, saved_name, discovered);
        this.event = event;
        this.timeToComplete = timeToComplete;
    }

    public Research(String name, String saved_name, UpdateEvent event, int timeToComplete) { //Default False
        super(name, saved_name, false);
        this.event = event;
        this.timeToComplete = timeToComplete;
    }

    public UpdateEvent getEvent() {
        return event;
    }

    public void setEvent(UpdateEvent event) {
        this.event = event;
    }

    public boolean isResearched() {
        return researched;
    }

    public void setResearched(boolean researched) {
        this.researched = researched;
    }

    public int getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(int timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public boolean isBeingResearched() {
        return beingResearched;
    }

    public void setBeingResearched(boolean beingResearched) {
        this.beingResearched = beingResearched;
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

    public String getContentString() {
        return "Time needed to complete: " + timeToComplete + " Seconds.";
    }

    public void research() {
        if (event != null) {
            event.post();
        }
        setDiscovered(false);
        beingResearched = false;
        researched = true;
    }

    public String getLogText() {
        return "You researched: " + getName();
    }

    public String startingLogText() {
        return "You started researching: " + getName();
    }

    public void setInfo(Research info) {
        setDiscovered(info.isDiscovered());
        setResearched(info.isResearched());
        timeToComplete = info.getTimeToComplete();
        currentProgress = info.getCurrentProgress();
        beingResearched = info.isBeingResearched();
        startTime = info.getStartTime();
        readyForCompletion = info.isReadyForCompletion();
    }

}
