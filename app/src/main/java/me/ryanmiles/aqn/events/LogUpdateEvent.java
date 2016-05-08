package me.ryanmiles.aqn.events;

/**
 * Created by ryanm on 5/7/2016.
 */
public class LogUpdateEvent {
    private String logString;

    public LogUpdateEvent(String logString) {
        this.logString = logString;
    }

    public String getLogString() {
        return logString;
    }

    public void setLogString(String logString) {
        this.logString = logString;
    }
}
