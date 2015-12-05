package io.goodway.navitia_android;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Address implements Parcelable {

    protected double lat, lon;
    protected String name;
    protected int icon, nameId;

    public Address(){

    }

    public Address(String name, int icon){
        this.name = name;
        this.icon = icon;
    }

    public Address(int name, int icon){
        this.nameId = name;
        this.icon = icon;
    }

    public Address(String name, double lat, double lon){
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public Address(String name, int icon, double lat, double lon){
        this.name = name;
        this.icon = icon;
        this.lat = lat;
        this.lon = lon;
    }

    public Address(Parcel in){
        name = in.readString();
        icon = in.readInt();
        nameId = in.readInt();
        lat = in.readDouble();
        lon = in.readDouble();
    }

    public String getName(Context c){
        return (name != null) ? name : c.getString(nameId);
    }

    public int getIcon(){
        return icon;
    }

    public double getLongitude(){
        return lon;
    }
    public double getLatitude(){
        return lat;
    }

    public void setLatitude(double lat){
        this.lat = lat;
    }

    public void setLongitude(double lon){
        this.lon = lon;
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(icon);
        dest.writeInt(nameId);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
    }

    public static final Creator CREATOR =
            new Creator() {

                @Override
                public Object createFromParcel(Parcel in) {
                    return new Address(in);
                }

                public Address[] newArray(int size) {
                    return new Address[size];
                }
            };

    public static String[] toHumanTime(String formatedTime){
        String year = formatedTime.substring(0, 4);
        String month = formatedTime.substring(4, 6);
        String day = formatedTime.substring(6, 9);
        String hour = formatedTime.substring(9, 11);
        String minute = formatedTime.substring(11, 13);
        return new String[]{year, month, day, hour, minute};
    }
}