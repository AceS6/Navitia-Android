package io.goodway.navitia_android;
/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Walking extends WayPart {

    protected Walking(Address from, Address to, double co2Emission, String departureDateTime, String arrivalDateTime, int duration, GeoJSON geoJSON) {
        super("Walking", from, to, co2Emission, departureDateTime, arrivalDateTime, duration, geoJSON);
    }

    @Override
    public String toString(){
        return "Marcher " + DataConverter.convertDurationToTime(this.getDuration()) + " jusqu'à " + this.getTo().toString();
    }
}
