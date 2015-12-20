package io.goodway.navitia_android;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by antoine on 19/12/15.
 */
public class UserLocation extends Address implements Parcelable{

    private int id;
    private boolean shared;
    private String a_name, fname;
    // a_name is the name given by the user to the address
    // name is the name of the address itself
    public UserLocation(String name, String a_name, String fname, double lat, double lon, boolean shared){
        this.name = name;
        this.a_name = a_name;
        this.fname = fname;
        this.lat = lat;
        this.lon = lon;
        this.shared = shared;
    }

    public UserLocation(int id, String name, String a_name, String fname, double lat, double lon, boolean shared){
        this.id=id;
        this.name = name;
        this.a_name = a_name;
        this.fname = fname;
        this.lat = lat;
        this.lon = lon;
        this.shared = shared;
    }

    public UserLocation(Address address, int id, String name, boolean shared){
        this.id = id;
        this.a_name = address.name;
        this.name = name;
        this.lat = address.lat;
        this.lon = address.lon;
        this.shared = shared;
    }

    public UserLocation(UserLocation other){
        this.id = other.id;
        this.a_name = other.a_name;
        this.name = other.name;
        this.lat = other.lat;
        this.lon = other.lon;
        this.shared = other.shared;
    }

    public int getType(){return Address.USERLOCATION;}

    public void setShared(boolean shared){this.shared = shared;}

    public String getA_name(){return a_name;}

    public void setA_name(String a_name){this.a_name=a_name;}

    public String toString(){
        return name+" ("+fname+")";
    }

    public UserLocation(Parcel in){
        super(in);
        shared = in.readByte() != 0;
        id = in.readInt();
        fname = in.readString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof UserLocation)) {
            return false;
        }
        UserLocation another = (UserLocation)obj;
        Log.d("user location shared="+shared+" "+another.shared, "user location equals : "+super.equals(obj));
        return super.equals(obj) && shared==another.shared;
    }

    public int getId(){return id;}

    public boolean shared(){return shared;}
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (shared ? 1 : 0));
        dest.writeInt(id);
        dest.writeString(fname);
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
