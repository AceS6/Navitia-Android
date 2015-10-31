package io.goodway.navitia_android;

import android.os.Parcel;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Stop extends Address {

    private String stopId;

    public Stop(String name, double lat, double lon, String stopId){
        super(name, lat, lon);
        this.stopId = stopId;
    }

    public Stop(Parcel in){
        super(in);
        stopId = in.readString();
    }

    public String getStopId() {
        return stopId;
    }

    @Override
    public String toString(){
        return "arrêt " + super.toString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(stopId);
    }

    public static final Creator CREATOR =
            new Creator() {

                @Override
                public Object createFromParcel(Parcel in) {
                    return new Stop(in);
                }

                public Stop[] newArray(int size) {
                    return new Stop[size];
                }
            };
}
