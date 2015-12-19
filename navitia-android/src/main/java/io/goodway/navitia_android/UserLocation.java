package io.goodway.navitia_android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by antoine on 19/12/15.
 */
public class UserLocation extends Address implements Parcelable{

    private boolean shared;

    public UserLocation(String name, double lat, double lon, boolean shared){
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.shared = shared;
    }

    public UserLocation(Parcel in){
        super(in);
        shared = in.readByte() != 0;
    }

    public boolean shared(){return shared;}
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (shared ? 1 : 0));
    }

    public static final Creator CREATOR =
            new Creator() {

                @Override
                public Object createFromParcel(Parcel in) {
                    return new UserLocation(in);
                }

                public UserLocation[] newArray(int size) {
                    return new UserLocation[size];
                }
            };
}
