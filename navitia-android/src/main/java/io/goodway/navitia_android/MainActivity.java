package io.goodway.navitia_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author Alexis Robin
 * @version 0.6
 * Licensed under the Apache2 license
 */
public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair("from", "-1.560425;47.205066"));
        pairs.add(new Pair("to", "-1.518612;47.282206"));
        pairs.add(new Pair("datetime", "20151031T0800"));
/*
        Request.getWays(new Action<Way>() {

                            @Override
                            public void action(Way e) {
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("Way label", e.getLabel());
                                Log.d("Way", e.toString());

                                Log.d("oldDuration","" + e.getDuration());
                                e.updateDuration(new Coordinate(47.233626, -1.534651));
                                Log.d("NewDuration","" + e.getDuration());
                            }

                        }, pairs);
                        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
