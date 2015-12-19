package io.goodway.navitia_android;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Alexis Robin
 * @version 0.6.1
 * Licensed under the Apache2 license
 */
public class BusTrip extends WayPart implements Parcelable{

    private Route route;
    private String vehicleId; //busType renommé TO USE
    private String vehicleType; // TO USE
    private ArrayList<TimedStop> stops;

    protected BusTrip(Address from, Address to, double co2Emission, String departureDateTime, String arrivalDateTime, int duration, GeoJSON geoJSON, Route route, String vehicleId, String vehicleType, ArrayList<TimedStop> stops) {
        super("Bus Trip", from, to, co2Emission, departureDateTime, arrivalDateTime, duration, geoJSON, WayPartType.BusTrip);
        this.route = route;
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.stops = stops;
    }

    protected BusTrip(Parcel in){
        super(in);
        this.wayPartType = WayPartType.BusTrip;
        route = in.readParcelable(Route.class.getClassLoader());
        vehicleId = in.readString();
        vehicleType = in.readString();
        stops = new ArrayList<>();
        in.readTypedList(stops, TimedStop.CREATOR);
    }

    @Override
    public String getLabel(Context context) {
        return context.getString(R.string.navitia_takeoff)+" "+this.getRoute().toString()+" "+context.getString(R.string.navitia_land)+ " "+ this.getTo().toString();
    }

    @Override
    public String getAction(Context context) {
        return context.getString(R.string.navitia_bus);
    }

    public Route getRoute() {
        return route;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getVehicleType() { return vehicleType; }

    public ArrayList<TimedStop> getStops() {
        return stops;
    }

    @Override
    public String toString(){
        return "Prendre la " + this.getRoute().toString() + " et descendre à " + this.getTo().toString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(route, flags);
        dest.writeString(vehicleId);
        dest.writeString(vehicleType);
        dest.writeTypedList(stops);
    }

    public static final Creator CREATOR =
            new Creator() {
                @Override
                public Object createFromParcel(Parcel in) {
                    return new BusTrip(in) {
                    };
                }

                public BusTrip[] newArray(int size) {
                    return new BusTrip[size];
                }
            };
}
