package io.goodway.navitia_android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Way implements Parcelable{

    private String label;

    private double co2Emission;
    private String departureDateTime;
    private String arrivalDateTime;
    private int duration;

    private ArrayList<WayPart> parts;
    private int currentPartKey;
    private Address from, to;

    private String imgUrl;

    public Way(String label, Address from, Address to, double co2Emission, String departureDateTime, String arrivalDateTime, int duration, ArrayList<WayPart> parts) {
        this.label = label;
        this.from = from;
        this.to = to;
        this.co2Emission = co2Emission;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.duration = duration;
        this.parts = parts;
        this.currentPartKey = 0;
        imgUrl = null;
    }

    public Way(Parcel in){
        label = in.readString();
        from = in.readParcelable(Address.class.getClassLoader());
        to = in.readParcelable(Address.class.getClassLoader());
        parts = new ArrayList<WayPart>();
        in.readTypedList(parts, WayPart.CREATOR);
        co2Emission = in.readDouble();
        departureDateTime = in.readString();
        arrivalDateTime = in.readString();
        duration = in.readInt();
        imgUrl = in.readString();
    }

    public String getLabel() {
        return label;
    }

    public Address getFrom(){return from;}

    public Address getTo(){return to;}

    public double getCo2Emission() {
        return co2Emission;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<WayPart> getParts() {
        return parts;
    }

    public String getImgUrl(){return imgUrl;}

    public void setImgUrl(String imgUrl){this.imgUrl = imgUrl;}

    public void updateDuration(Coordinate c){

        this.getParts().get(this.currentPartKey).updateDuration(c);

        int durationUpdated = 0;
        for(WayPart wayPart : this.getParts()){
            durationUpdated += wayPart.getDuration();
        }
        this.duration = durationUpdated;

    }

    @Override
    public String toString(){
        String ret = "";
        for(WayPart wayPart : this.getParts()){
           ret += wayPart.toString() + "\n";
        }
        return ret;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeParcelable(from, flags);
        dest.writeParcelable(to, flags);
        dest.writeTypedList(parts);
        dest.writeDouble(co2Emission);
        dest.writeString(departureDateTime);
        dest.writeString(arrivalDateTime);
        dest.writeInt(duration);
        dest.writeString(imgUrl);
    }

    public static final Creator CREATOR =
            new Creator() {
                @Override
                public Object createFromParcel(Parcel in) {
                    return new Way(in);
                }

                public Way[] newArray(int size) {
                    return new Way[size];
                }
            };
}
