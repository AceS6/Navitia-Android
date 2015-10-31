package io.goodway.navitia_android;
import java.util.concurrent.TimeUnit;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class DataConverter {

    private DataConverter(){}

    public static String convertDurationToTime(int seconds){

        String ret = "";

        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
        //long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);

        if(day > 0){
            ret += day + " ";
            if(day == 1){
                ret += "jour";
            } else{
                ret += "jours";
            }
            ret += " ";
        }

        if(hours > 0){
            ret += hours + " ";
            if(hours == 1){
                ret += "heure";
            } else{
                ret += "heures";
            }
            ret += " ";
        }

        if(minute > 0){
            ret += minute + " ";
            if(minute == 1){
                ret += "minute";
            } else{
                ret += "minutes";
            }
            ret += " ";
        }

        return ret;
    }

    public static int tempsRestant(double distanceTotale, double distanceParcourue, int tempsTotal){

        int tempsRestant = tempsTotal;
        double tempsPasse =  (tempsTotal * distanceParcourue)/ distanceTotale;
        tempsRestant -= tempsPasse;

        return tempsRestant;
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
