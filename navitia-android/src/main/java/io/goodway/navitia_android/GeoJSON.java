package io.goodway.navitia_android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class GeoJSON implements Parcelable{

    private String type;
    private int length;
    private Coordinate[] coordinates;

    public GeoJSON(String type, int length, Coordinate[] coordinates) {
        this.type = type;
        this.length = length;
        this.coordinates = coordinates;
    }

    public GeoJSON(Parcel in){
        type = in.readString();
        length = in.readInt();
        in.readTypedArray(coordinates, Coordinate.CREATOR);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeInt(length);
        dest.writeTypedArray(coordinates, flags);
    }

    public static final Creator<GeoJSON> CREATOR = new Creator<GeoJSON>() {
        @Override
        public GeoJSON createFromParcel(Parcel in) {
            return new GeoJSON(in);
        }

        @Override
        public GeoJSON[] newArray(int size) {
            return new GeoJSON[size];
        }
    };
}
