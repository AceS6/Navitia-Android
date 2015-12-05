package io.goodway.navitia_android;

import android.os.Parcelable;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Work extends Address implements Parcelable {

    public Work(String name, int icon){
        super(name, icon);
    }
    public Work(String name, int icon, double lat, double lon){
        super(name, icon, lat, lon);
    }
}