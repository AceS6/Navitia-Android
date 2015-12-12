package io.goodway.navitia_android;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public abstract class WayPart implements Parcelable{

    protected String type = "WayPart";

    protected Address from;
    protected Address to;
    protected double co2Emission;

    protected String departureDateTime;
    protected String arrivalDateTime;
    protected int duration;

    protected GeoJSON geoJSON;
    protected WayPartType wayPartType;

    protected WayPart(String type, double co2Emission, String departureDateTime, String arrivalDateTime, int duration, WayPartType wayPartType){
        this.type = type;
        this.co2Emission = co2Emission;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.duration = duration;
        this.from = null;
        this.to = null;
        this.geoJSON = null;
        this.wayPartType = wayPartType;
    }

    protected WayPart(String type, Address from, Address to, double co2Emission, String departureDateTime, String arrivalDateTime, int duration, GeoJSON geoJSON, WayPartType wayPartType){
        this.type = type;
        this.from = from;
        this.to = to;
        this.co2Emission = co2Emission;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.duration = duration;
        this.geoJSON = geoJSON;
        this.wayPartType = wayPartType;
    }

    protected WayPart(Parcel in){
        type = in.readString();
        from = in.readParcelable(Address.class.getClassLoader());
        to = in.readParcelable(Address.class.getClassLoader());
        co2Emission = in.readDouble();
        departureDateTime = in.readString();
        arrivalDateTime = in.readString();
        duration = in.readInt();
        geoJSON = in.readParcelable(GeoJSON.class.getClassLoader());
    }

    public abstract String getLabel(Context context);

    public String getType() {
        return type;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public double getCo2Emission() {
        return co2Emission;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
         this.duration = duration;
    }

    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    public GeoJSON getGeoJSON() {
        return geoJSON;
    }

    public WayPartType getWayPartType(){return wayPartType;}

    public void updateDuration(Coordinate c){
        double fromLat = this.getFrom().getLatitude();
        double fromLon = this.getFrom().getLongitude();
        double toLat = this.getTo().getLatitude();
        double toLon = this.getTo().getLongitude();

        double distanceTotale = DataConverter.distance(fromLat, fromLon, toLat, toLon, "K");
        double distanceParcourue = DataConverter.distance(fromLat, fromLon, c.getLatitude(), c.getLongitude(), "K");
        this.duration = DataConverter.tempsRestant(distanceTotale, distanceParcourue, this.duration);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(wayPartType, flags);
        dest.writeString(type);
        dest.writeParcelable(from, flags);
        dest.writeParcelable(to, flags);
        dest.writeDouble(co2Emission);
        dest.writeString(departureDateTime);
        dest.writeString(arrivalDateTime);
        dest.writeInt(duration);
        dest.writeParcelable(geoJSON, flags);
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

    public static final Creator CREATOR =
            new Creator() {
                @Override
                public Object createFromParcel(Parcel in) {
                    WayPartType type = in.readParcelable(WayPartType.class.getClassLoader());
                    switch (type){
                        case BusTrip:
                            return new BusTrip(in);
                        case Transfer:
                            return new Transfer(in);
                        case Walking:
                            return new Walking(in);
                        case Waiting:
                            return new Waiting(in);
                        default:
                            return null;
                    }
                }

                public WayPart[] newArray(int size) {
                    return new WayPart[size];
                }
            };
}
