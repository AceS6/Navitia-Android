package io.goodway.navitia_android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public abstract class WayPart implements Parcelable{

    private String type = "WayPart";

    private Address from;
    private Address to;
    private double co2Emission;

    private String departureDateTime;
    private String arrivalDateTime;
    private int duration;

    private GeoJSON geoJSON;

    protected WayPart(String type, double co2Emission, String departureDateTime, String arrivalDateTime, int duration){
        this.type = type;
        this.co2Emission = co2Emission;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.duration = duration;
        this.from = null;
        this.to = null;
        this.geoJSON = null;
    }

    protected WayPart(String type, Address from, Address to, double co2Emission, String departureDateTime, String arrivalDateTime, int duration, GeoJSON geoJSON){
        this.type = type;
        this.from = from;
        this.to = to;
        this.co2Emission = co2Emission;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.duration = duration;
        this.geoJSON = geoJSON;
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
        dest.writeString(type);
        dest.writeParcelable(from, flags);
        dest.writeParcelable(to, flags);
        dest.writeDouble(co2Emission);
        dest.writeString(departureDateTime);
        dest.writeString(arrivalDateTime);
        dest.writeInt(duration);
        dest.writeParcelable(geoJSON, flags);
    }

}
