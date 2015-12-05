package io.goodway.navitia_android;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Walking extends WayPart implements Parcelable{

    protected Walking(Address from, Address to, double co2Emission, String departureDateTime, String arrivalDateTime, int duration, GeoJSON geoJSON) {
        super("Walking", from, to, co2Emission, departureDateTime, arrivalDateTime, duration, geoJSON, WayPartType.Walking);
    }

    protected Walking(Parcel in){
        super(in);
    }

    @Override
    public String toString(){
        return "Marcher " + DataConverter.convertDurationToTime(this.getDuration()) + " jusqu'à " + this.getTo().toString();
    }

    @Override
    public String getLabel(Context context) {
        return context.getString(R.string.navitia_walking)+" "+this.getTo().toString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static final Creator CREATOR =
            new Creator() {
                @Override
                public Object createFromParcel(Parcel in) {
                    return new Walking(in) {
                    };
                }

                public Walking[] newArray(int size) {
                    return new Walking[size];
                }
            };
}
