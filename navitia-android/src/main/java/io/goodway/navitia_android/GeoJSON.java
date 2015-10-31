package io.goodway.navitia_android;
/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class GeoJSON {

    private String type;
    private int length;
    private Coordinate[] coordinates;

    public GeoJSON(String type, int length, Coordinate[] coordinates) {
        this.type = type;
        this.length = length;
        this.coordinates = coordinates;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

}
