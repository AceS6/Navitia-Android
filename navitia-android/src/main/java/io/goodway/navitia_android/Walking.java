package io.goodway.navitia_android;

import android.os.Parcel;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Walking extends WayPart {

    protected Walking(Address from, Address to, double co2Emission, String departureDateTime, String arrivalDateTime, int duration, GeoJSON geoJSON) {
        super("Walking", from, to, co2Emission, departureDateTime, arrivalDateTime, duration, geoJSON);
    }

    protected Walking(Parcel in){
        super(in);
    }

    @Override
    public String toString(){
        return "Marcher " + DataConverter.convertDurationToTime(this.getDuration()) + " jusqu'à " + this.getTo().toString();
    }

    public static final Creator CREATOR =
            new Creator() {
                @Override
                public Object createFromParcel(Parcel in) {
                    return new Walking(in) {
                    };
                }

                public Walking[] newArray(int size) {
                    return new Walking[size];
                }
            };
}
