package io.goodway.navitia_android;

/**
 * Created by Alexis on 31/10/2015.
 */
public class Transfer extends WayPart {

    protected Transfer(Address from, Address to, double co2Emission, String departureDateTime, String arrivalDateTime, int duration, GeoJSON geoJSON) {
        super("Transfer", from, to, co2Emission, departureDateTime, arrivalDateTime, duration, geoJSON);
    }

    @Override
    public String toString(){
        return "Tranfert: Marcher " + DataConverter.convertDurationToTime(this.getDuration()) + " jusqu'au bon abri bus " + this.getTo().toString();
    }
}
