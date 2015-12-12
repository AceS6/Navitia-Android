package io.goodway.navitia_android;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Alexis Robin
 * @version 0.6.1
 * Licensed under the Apache2 license
 */
public class Request {

    public static void getWays(Action a, ArrayList<Pair> pairs, Address from, Address to, ErrorAction error){
        try{
            new GetWays(a, pairs, from, to, error).execute();
            Log.d("getWays", "getWays");
        }
        catch(IllegalStateException e){
            Log.e(e.getMessage(), "exception");
        }

    }

    private static class GetWays extends AsyncTask<Pair, Way, Integer>{

    /*
        wiki : http://wiki.openstreetmap.org/wiki/Nominatim
        street=<housenumber> <streetname>
        city=<city>
        county=<county>
        state=<state>
        country=<country>
        postalcode=<postalcode>
        use q= if you don't know whether the user type an address, a city a county or whatever
    */

        private final String QUERY = "http://api.navitia.io/v1/journeys?";
        private Action action;
        private ErrorAction error;
        private ArrayList<Pair> pairs;
        private Address from, to;

        /**
         *
         * @param action The method to apply on each Place which is returned by nominatim
         * @param pairs A set of keys and values to provide to the request. Each map will be triggered in a different request
         * @see Action
         */
        public GetWays(Action action, ArrayList<Pair> pairs, Address from, Address to, ErrorAction error){
            this.action = action;
            this.error = error;
            this.pairs = pairs;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Integer doInBackground(Pair... params) {
            StringBuilder jsonResult = new StringBuilder();
            StringBuilder sb = new StringBuilder(QUERY);
            int nbJourneys=0;
                for(Pair p : pairs){
                    sb.append(p.first+"=" + p.second+"&");
                    Log.d("p.first="+p.first+" & o.second"+p.second, "pairs");
                }

            Log.d("url", sb.toString());

                try {
                    URL url = new URL(sb.toString());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    Authenticator.setDefault(new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("5822318f-3a97-4cd8-827e-11382099531c", "".toCharArray());
                        }
                    });

                    InputStreamReader in = new InputStreamReader(conn.getInputStream());

                    BufferedReader jsonReader = new BufferedReader(in);
                    String lineIn;
                    while ((lineIn = jsonReader.readLine()) != null) {
                        jsonResult.append(lineIn);
                    }

                    try {
                        JSONObject jsonObj = new JSONObject(jsonResult.toString());
                        JSONArray journeys = jsonObj.getJSONArray("journeys");
                        nbJourneys = journeys.length();

                        for (int i = 0; i < nbJourneys; i++) {

                            JSONObject journey = journeys.getJSONObject(i);
                            String arrivalDateTime = journey.optString("arrival_date_time");
                            String departureDateTime = journey.optString("departure_date_time");
                            double co2Emission = 0;
                            if(journey.has("co2_emission")) {
                                co2Emission = journey.getJSONObject("co2_emission").optDouble("value");
                            }

                            String label = journey.optString("type");
                            int duration = journey.optInt("duration");

                            Log.d("duration",""+ duration);

                            ArrayList<WayPart> parts = new ArrayList<WayPart>();
                            JSONArray sections = journey.getJSONArray("sections");
                            int nbSections = sections.length();

                            WayPart tmpWayPart = null;
                            for (int j = 0; j < nbSections; j++) {

                                JSONObject section = sections.getJSONObject(j);
                                tmpWayPart = getWayPart(section);

                                if(tmpWayPart != null)
                                    parts.add(tmpWayPart);

                            }

                            publishProgress(new Way(label, from, to, co2Emission, departureDateTime, arrivalDateTime, duration, parts));
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return nbJourneys;
        }


        @Override
        protected void onProgressUpdate(Way...progress){
            action.action(progress[0]);
            Log.d(progress[0].toString(), "entity");
        }

        protected void onPostExecute(Integer result){
            if(result==0){error.action(result);}
        }


        private Address getSimpleAddress(JSONObject addr){

            Address ret = null;

            try {

                JSONObject address = addr.getJSONObject("address");

                String label = address.getString("label");
                double lat = Double.parseDouble(address.getJSONObject("coord").getString("lat"));
                double lon = Double.parseDouble(address.getJSONObject("coord").getString("lon"));

                ret = new Address(label, lat, lon);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ret;

        }

        private Stop getStop(JSONObject stop){

            Stop ret = null;

            try {

                JSONObject stop_point = stop.getJSONObject("stop_point");

                String label = stop_point.getString("label");
                double lat = Double.parseDouble(stop_point.getJSONObject("coord").getString("lat"));
                double lon = Double.parseDouble(stop_point.getJSONObject("coord").getString("lon"));
                String id = stop_point.getString("id");

                ret = new Stop(label, lat, lon, id);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ret;

        }

        private TimedStop getTimedStop(JSONObject timedStop){
            TimedStop ret = null;

            try {

                JSONObject stop_point = timedStop.getJSONObject("stop_point");

                String label = stop_point.getString("label");
                double lat = Double.parseDouble(stop_point.getJSONObject("coord").getString("lat"));
                double lon = Double.parseDouble(stop_point.getJSONObject("coord").getString("lon"));
                String id = stop_point.getString("id");
                String arrival_date_time = timedStop.getString("arrival_date_time");
                String departure_date_time = timedStop.getString("departure_date_time");

                ret = new TimedStop(label, lat, lon, id, arrival_date_time, departure_date_time);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ret;

        }

        private Address getAddress(JSONObject obj){

            Address ret = null;

            try {

                String type = obj.getString("embedded_type");

                if(type.equals("address")){

                    ret = getSimpleAddress(obj);

                } else if(type.equals("stop_point")){

                    ret = getStop(obj);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ret;
        }

        private GeoJSON getGeoJSON(JSONObject obj){

            GeoJSON ret = null;

            try {

                if(obj.getString("type") == "LineString"){

                    int length = obj.getJSONArray("properties").getJSONObject(0).getInt("length");
                    JSONArray coord = obj.getJSONArray("coordinates");

                    Coordinate[] coordinates = new Coordinate[coord.length()];
                    for(int k = 0; k < coord.length(); k++) {
                        coordinates[k] = new Coordinate(coord.getJSONArray(k).getDouble(0), coord.getJSONArray(k).getDouble(1));
                    }

                    ret = new GeoJSON("LineString", length, coordinates);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ret;
        }

        private Waiting getWaiting(JSONObject section){

            Waiting ret = null;

            try {

                String departureDateTime = section.getString("departure_date_time");
                String arrivalDateTime = section.getString("arrival_date_time");
                int duration = section.getInt("duration");

                ret = new Waiting(0, departureDateTime, arrivalDateTime, duration);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ret;

        }

        private Walking getWalking(JSONObject section){

            Walking ret = null;

            try {

                JSONObject f = section.getJSONObject("from");
                JSONObject t = section.getJSONObject("to");
                JSONObject g = section.getJSONObject("geojson");

                Address from = getAddress(f);
                Address to = getAddress(t);
                GeoJSON geoJSON = getGeoJSON(g);

                String departureDateTime = section.getString("departure_date_time");
                String arrivalDateTime = section.getString("arrival_date_time");
                int duration = section.getInt("duration");

                ret = new Walking(from, to, 0, departureDateTime, arrivalDateTime, duration, geoJSON);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ret;

        }

        private Transfer getTransfer(JSONObject section){

            Transfer ret = null;

            try {

                JSONObject f = section.getJSONObject("from");
                JSONObject t = section.getJSONObject("to");
                JSONObject g = section.getJSONObject("geojson");

                Address from = getAddress(f);
                Address to = getAddress(t);
                GeoJSON geoJSON = getGeoJSON(g);

                String departureDateTime = section.getString("departure_date_time");
                String arrivalDateTime = section.getString("arrival_date_time");
                int duration = section.getInt("duration");

                ret = new Transfer(from, to, 0, departureDateTime, arrivalDateTime, duration, geoJSON);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ret;

        }

        private BusTrip getBus(JSONObject section){

            BusTrip ret = null;

            try {

                JSONObject f = section.getJSONObject("from");
                JSONObject t = section.getJSONObject("to");
                JSONObject g = section.getJSONObject("geojson");
                JSONObject d = section.getJSONObject("display_informations");
                JSONArray l = section.getJSONArray("links");
                JSONArray s = section.getJSONArray("stop_date_times");

                Address from = getAddress(f);
                Address to = getAddress(t);
                GeoJSON geoJSON = getGeoJSON(g);

                String departureDateTime = section.getString("departure_date_time");
                String arrivalDateTime = section.getString("arrival_date_time");
                int duration = section.getInt("duration");

                String lineId = l.getJSONObject(1).getString("id");
                String lineName= d.getString("code");
                String lineColor= d.getString("color");
                String networkId= l.getJSONObject(5).getString("id");
                Line line = new Line(lineId, lineName, lineColor, networkId);

                String routeId = l.getJSONObject(2).getString("id");
                String routeName= d.getString("headsign");
                Route route = new Route(routeId, routeName, line);

                String vehicleId = l.getJSONObject(0).getString("id");
                String vehicleType = l.getJSONObject(4).getString("id");

                ArrayList<TimedStop> stops = new ArrayList<>();
                for(int i = 0; i < s.length(); i++) {
                    stops.add(getTimedStop(s.getJSONObject(i)));
                }

                ret = new BusTrip(from, to, 0, departureDateTime, arrivalDateTime, duration, geoJSON, route, vehicleId, vehicleType, stops);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("way",""+ ret.getRoute().getLine().getName());

            return ret;

        }

        private WayPart getWayPart(JSONObject section){

            WayPart ret = null;

            try {

                String type = section.getString("type");

                if(type.equals("street_network")){

                    ret = getWalking(section);

                } else if(type.equals("public_transport")){

                    ret = getBus(section);

                } else if(type.equals("waiting")){

                    ret = getWaiting(section);

                } else if(type.equals("transfer")){

                    ret = getTransfer(section);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ret;

        }

    }
}
