package io.goodway.navitia_android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class Route implements Parcelable{

    private String id;
    private String name;

    private Line line;

    public Route(String id, String name, Line line) {
        this.id = id;
        this.name = name;
        this.line = line;
    }

    public Route(Parcel in){
        id = in.readString();
        name = in.readString();
        line = in.readParcelable(Line.class.getClassLoader());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Line getLine() {
        return line;
    }

    @Override
    public String toString(){
        return this.getLine().toString() + " direction " + this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeParcelable(line, flags);
    }
    public static final Creator CREATOR =
            new Creator() {
                @Override
                public Object createFromParcel(Parcel in) {
                    return new Route(in) {
                    };
                }

                public Route[] newArray(int size) {
                    return new Route[size];
                }
            };
}
