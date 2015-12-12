package io.goodway.navitia_android;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alexis on 31/10/2015.
 */
public class Transfer extends WayPart implements Parcelable{

    protected Transfer(Address from, Address to, double co2Emission, String departureDateTime, String arrivalDateTime, int duration, GeoJSON geoJSON) {
        super("Transfer", from, to, co2Emission, departureDateTime, arrivalDateTime, duration, geoJSON, WayPartType.Transfer);
    }

    protected Transfer(Parcel in){
        super(in);
        this.wayPartType = WayPartType.Transfer;
    }

    @Override
    public String toString(){
        return "Tranfert: Marcher jusqu'à l'arret" + this.getTo().toString();
    }

    @Override
    public String getLabel(Context context) {
        return context.getString(R.string.navitia_transfer)+" "+getTo().toString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static final Creator CREATOR =
            new Creator() {
                @Override
                public Object createFromParcel(Parcel in) {
                    return new Transfer(in) {
                    };
                }

                public Transfer[] newArray(int size) {
                    return new Transfer[size];
                }
            };
}
