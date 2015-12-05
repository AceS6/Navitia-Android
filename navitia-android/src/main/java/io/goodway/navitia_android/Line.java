package io.goodway.navitia_android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Line implements Parcelable {

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

    public Line(Parcel in){
        id = in.readString();
        name = in.readString();
        color = in.readString();
        networkId = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(color);
        dest.writeString(networkId);
    }

    public static final Creator CREATOR =
            new Creator() {
                @Override
                public Object createFromParcel(Parcel in) {
                    return new Line(in) {
                    };
                }

                public Line[] newArray(int size) {
                    return new Line[size];
                }
            };
}
