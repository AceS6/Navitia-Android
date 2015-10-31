package io.goodway.navitia_android;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Waiting extends WayPart {

    protected Waiting(double co2Emission, String departureDateTime, String arrivalDateTime, int duration) {
        super("Waiting", co2Emission, departureDateTime, arrivalDateTime, duration);
    }

    @Override
    public String toString(){
        return "Attendre " + DataConverter.convertDurationToTime(this.getDuration());
    }

    @Override
    public void updateDuration(Coordinate c){

    }
}
