package io.goodway.navitia_android;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Objects;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Address implements Parcelable {

    public static final int ADDRESS=1, USERLOCATION=2, GROUPLOCATION=3;

    protected double lat, lon;
    protected String name;
    protected int icon, nameId;
    private String secondaryText;

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

    public Address(String name, String secondaryText, double lat, double lon){
        this.name = name;
        this.secondaryText = secondaryText;
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

    public int getType(){return ADDRESS;}

    public String getName(){return name;}

    public String getSecondaryText(){return secondaryText;}

    public int getIcon(){
        return icon;
    }

    public double getLongitude(){
        return lon;
    }
    public double getLatitude(){
        return lat;
    }

    public void setName(String name){this.name = name;}

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

    public static String[] splitIso8601(String formatedTime){
        Log.d("formated time", "formated time=" + formatedTime);
        String year = formatedTime.substring(0, 4);
        String month = formatedTime.substring(4, 6);
        String day = formatedTime.substring(6, 8);
        String hour = formatedTime.substring(9, 11);
        String minute = formatedTime.substring(11, 13);
        return new String[]{year, month, day, hour, minute};
    }

    public static String toStringDuration(String formatedTime){
        String[] split = splitIso8601(formatedTime);
        String ret="";
        if(split[2]!="0"){
            ret+= split[2]+":";
        }
        ret+=split[3];
        return ret;
    }

    public static int[] splitToComponentTimes(int duration)
    {
        int hours = (int) duration / 3600;
        int remainder = (int) duration - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        int[] ints = {hours , mins , secs};
        return ints;
    }


    public static String secondToStr(Context c, int seconds){
        int[] times = splitToComponentTimes(seconds);
        String timeStr="";
        if(times[0]!=0){
            if(times[1]!=0){
                timeStr+=times[0]+":";
            }
            else{
                timeStr+=times[1]+" "+c.getString(R.string.navitia_hours);
            }
        }
        if(times[1]!=0){
            if(times[0]!=0){
                timeStr+=times[1];
            }
            else{
                timeStr+=times[1]+" "+c.getString(R.string.navitia_minutes);
            }
        }
        if(timeStr==""){timeStr="0 "+c.getString(R.string.navitia_minutes);}
        return timeStr;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Address)) {
            return false;
        }
        Address another = (Address)obj;
        return name.equals(another.name) && lat == another.lat && lon == another.lon;
    }
}