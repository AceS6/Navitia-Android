package io.goodway.navitia_android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by antoine on 12/5/15.
 */
public enum WayPartType implements Parcelable {
    BusTrip, Transfer, Walking, Waiting;

    public static final Parcelable.Creator<WayPartType> CREATOR = new Creator<WayPartType>() {

        @Override
        public WayPartType[] newArray(int size) {
            return new WayPartType[size];
        }

        @Override
        public WayPartType createFromParcel(Parcel source) {
            return WayPartType.values()[source.readInt()];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ordinal());
    }
}