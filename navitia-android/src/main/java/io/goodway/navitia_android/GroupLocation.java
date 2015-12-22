package io.goodway.navitia_android;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by antoine on 19/12/15.
 */
public class GroupLocation extends Address implements Parcelable{

    private int id;
    private boolean shared;
    private String a_name, fname;
    private String groupName;
    // a_name is the name given by the user to the address
    // name is the name of the address itself
    public GroupLocation(int id, String s_name, String a_name, String groupName, double lat, double lon){
        this.id = id;
        this.name = s_name;
        this.a_name = a_name;
        this.groupName = groupName;
        this.lat = lat;
        this.lon = lon;
    }

    public int getType(){return Address.GROUPLOCATION;}

    public String getA_name(){return a_name;}

    public void setA_name(String a_name){this.a_name=a_name;}

    public String toString(){
        return name+" ("+fname+")";
    }

    public GroupLocation(Parcel in){
        super(in);
        id = in.readInt();
        groupName = in.readString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof GroupLocation)) {
            return false;
        }
        GroupLocation another = (GroupLocation)obj;
        Log.d("user location shared="+shared+" "+another.shared, "user location equals : "+super.equals(obj));
        return super.equals(obj) && shared==another.shared;
    }

    public int getId(){return id;}

    public boolean shared(){return shared;}
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id);
        dest.writeString(groupName);
    }

    public static final Creator CREATOR =
            new Creator() {

                @Override
                public Object createFromParcel(Parcel in) {
                    return new GroupLocation(in);
                }

                public GroupLocation[] newArray(int size) {
                    return new GroupLocation[size];
                }
            };
}
