package me.ryanmiles.aqn.data.model;

/**
 * Created by ryanm on 5/8/2016.
 */
public class Object {
    private String name;
    private String saved_name;
    private boolean discovered;

    public Object(String name, String saved_name, boolean discovered) {
        this.name = name;
        this.saved_name = saved_name;
        this.discovered = discovered;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaved_name() {
        return saved_name;
    }

    public void setSaved_name(String saved_name) {
        this.saved_name = saved_name;
    }

    @Override
    public String toString() {
        return "Object{" +
                "name='" + name + '\'' +
                ", saved_name='" + saved_name + '\'' +
                ", discovered=" + discovered +
                '}';
    }
}
