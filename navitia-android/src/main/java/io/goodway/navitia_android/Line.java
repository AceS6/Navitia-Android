package io.goodway.navitia_android;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Line {

    private String id;
    private String name;
    private String color;

    private String networkId;

    public Line(String id, String name, String color, String networkId) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.networkId = networkId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getNetworkId() {
        return networkId;
    }

    @Override
    public String toString(){
        return "ligne " + this.name;
    }

}
