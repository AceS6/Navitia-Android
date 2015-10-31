package io.goodway.navitia_android;

import android.os.Parcel;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class TimedStop extends Stop {

    private String arrival_date_time;
    private String departure_date_time;

    public TimedStop(String name, double lat, double lon, String stopId, String arrival_date_time, String departure_date_time) {
        super(name, lat, lon, stopId);
        this.departure_date_time = departure_date_time;
        this.arrival_date_time = arrival_date_time;
    }

    public TimedStop(Parcel in) {
        super(in);
        departure_date_time = in.readString();
        arrival_date_time = in.readString();
    }

    public String getArrival_date_time() {
        return arrival_date_time;
    }

    public String getDeparture_date_time() {
        return departure_date_time;
    }

    @Override
    public String toString(){
        return "arrêt " + super.toString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(departure_date_time);
        dest.writeString(arrival_date_time);
    }

    public static final Creator CREATOR =
            new Creator() {

                @Override
                public Object createFromParcel(Parcel in) {
                    return new TimedStop(in);
                }

                public TimedStop[] newArray(int size) {
                    return new TimedStop[size];
                }
            };
}
