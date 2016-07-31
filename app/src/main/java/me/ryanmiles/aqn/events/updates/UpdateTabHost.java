package me.ryanmiles.aqn.events.updates;

/**
 * Created by Ryan Miles on 7/15/2016.
 */
public class UpdateTabHost {
    private String name;

    public UpdateTabHost(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UpdateTabHost{" +
                "name='" + name + '\'' +
                '}';
    }

}
