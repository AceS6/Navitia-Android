package io.goodway.navitia_android;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class BusTrip extends WayPart implements Parcelable{

    private Route route;
    private String busId;
    private ArrayList<TimedStop> stops;

    protected BusTrip(Address from, Address to, double co2Emission, String departureDateTime, String arrivalDateTime, int duration, GeoJSON geoJSON, Route route, String busId, ArrayList<TimedStop> stops) {
        super("Bus Trip", from, to, co2Emission, departureDateTime, arrivalDateTime, duration, geoJSON, WayPartType.BusTrip);
        this.route = route;
        this.busId = busId;
        this.stops = stops;
    }

    protected BusTrip(Parcel in){
        super(in);
        route = in.readParcelable(Route.class.getClassLoader());
        busId = in.readString();
        stops = in.readArrayList(TimedStop.class.getClassLoader());
    }

    @Override
    public String getLabel(Context context) {
        return context.getString(R.string.navitia_takeoff)+" "+this.getRoute().toString()+" "+context.getString(R.string.navitia_land)+ " "+ this.getTo().toString();
    }

    public Route getRoute() {
        return route;
    }

    public String getBusId() {
        return busId;
    }

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
        dest.writeString(busId);
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
