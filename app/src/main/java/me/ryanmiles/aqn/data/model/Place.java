package me.ryanmiles.aqn.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.ryanmiles.aqn.events.UpdateEvent;

/**
 * Created by Ryan Miles on 5/28/2016.
 */
public class Place {
    private String name;
    private String letter;
    private String desc;
    private String finishedDesc;
    private List<Creature> creatureList;
    private boolean completed = false;
    private UpdateEvent mEvent;
    private int x;
    private int y;


    public Place(String name, String desc, String finishedDesc, List<Creature> creatureList, UpdateEvent mEvent, int minx, int maxx, int miny, int maxy) {
        Random rng = new Random();
        this.name = name;
        this.desc = desc;
        this.creatureList = creatureList;
        this.finishedDesc = finishedDesc;
        this.mEvent = mEvent;
        letter = name.substring(0, 1);
        x = minx + rng.nextInt(maxx - minx);
        y = miny + rng.nextInt(maxy - miny);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
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

    public void setInfo(Place info) {
        completed = info.isCompleted();
    }

    public List<Creature> getCreatureList() {
        return creatureList;
    }

    public void setCreatureList(ArrayList<Creature> creatureList) {
        this.creatureList = creatureList;
    }

    public void postEvent() {
        if (mEvent != null) {
            mEvent.post();
        }
        completed = true;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", letter='" + letter + '\'' +
                ", desc='" + desc + '\'' +
                ", finishedDesc='" + finishedDesc + '\'' +
                ", creatureList=" + creatureList +
                ", completed=" + completed +
                ", mEvent=" + mEvent +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
