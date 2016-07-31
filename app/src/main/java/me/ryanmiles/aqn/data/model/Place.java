package me.ryanmiles.aqn.data.model;

import java.util.ArrayList;
import java.util.List;

import me.ryanmiles.aqn.events.UpdateEvent;

/**
 * Created by Ryan Miles on 5/28/2016.
 */
public class Place {
    private String name;
    private String desc;
    private String finishedDesc;
    private List<Creature> creatureList;
    private boolean completed = false;
    private UpdateEvent mEvent;

    public Place(String name, String desc, String finishedDesc, List<Creature> creatureList, UpdateEvent mEvent) {
        this.name = name;
        this.desc = desc;
        this.creatureList = creatureList;
        this.finishedDesc = finishedDesc;
        this.mEvent = mEvent;
    }

    public String getFinishedDesc() {
        return finishedDesc;
    }

    public void setFinishedDesc(String finishedDesc) {
        this.finishedDesc = finishedDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public UpdateEvent getmEvent() {
        return mEvent;
    }

    public void setmEvent(UpdateEvent mEvent) {
        this.mEvent = mEvent;
    }

    public void postEvent() {
        if (mEvent != null) {
            mEvent.post();
        }
        completed = true;
    }

    public void setInfo(Place info) {
        completed = info.isCompleted();
    }

    public List<Creature> getCreatureList() {
        return creatureList;
    }

    public void setCreatureList(ArrayList<Creature> creatureList) {
        this.creatureList = creatureList;
    }
}
