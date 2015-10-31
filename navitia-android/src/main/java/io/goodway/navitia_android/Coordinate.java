package io.goodway.navitia_android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Coordinate implements Parcelable {

    protected double lat, lon;

    public Coordinate(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public Coordinate(Parcel in){
        lat = in.readDouble();
        lon = in.readDouble();
    }


    public double getLongitude(){
        return lat;
    }
    public double getLatitude(){
        return lon;
    }

    public void setLatitude(double lat){
        this.lat = lat;
    }
    public void setLongitude(double lon){
        this.lon = lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lon);
    }

    public static final Creator CREATOR =
            new Creator() {

                @Override
                public Object createFromParcel(Parcel in) {
                    return new Coordinate(in);
                }

                public Coordinate[] newArray(int size) {
                    return new Coordinate[size];
                }
            };

}