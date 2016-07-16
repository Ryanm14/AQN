package me.ryanmiles.aqn.events;

/**
 * Created by ryanm on 5/7/2016.
 */
public class DataUpdateEvent {
    private boolean updateStorage;
    private String logText = "";

    public DataUpdateEvent(boolean updateStorage, String logText) {
        this.updateStorage = updateStorage;
        this.logText = logText;
    }

    public boolean UpdateStorage() {
        return updateStorage;
    }

    public void setUpdateStorage(boolean updateStorage) {
        this.updateStorage = updateStorage;
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    @Override
    public String toString() {
        return "DataUpdateEvent{" +
                "updateStorage=" + updateStorage +
                ", logText='" + logText + '\'' +
                '}';
    }
}
