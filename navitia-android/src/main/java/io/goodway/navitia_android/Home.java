package io.goodway.navitia_android;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Home extends Address implements Parcelable {

    public Home(String name, int icon){
        super(name, icon);
    }
    public Home(String name, int icon, double lat, double lon){
        super(name, icon, lat, lon);
    }
}