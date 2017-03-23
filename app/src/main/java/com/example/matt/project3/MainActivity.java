package com.example.matt.project3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private static final int APP_BAR_ALPHA = 80;
    private CheckNetwork check;
    private boolean network, wifi;
    private SharedPreferences prefs;
    private URL url;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        check = new CheckNetwork(this);
        doNetworkCheck();
        doWirelessCheck();
        checkConnections();


        spinner = (Spinner) findViewById(R.id.spinner);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String temp = prefs.getString("url", null);

        try {
            if(temp != null) {
                url = new URL(prefs.getString("url", getString(R.string.error)));
            }else
            {
                url = new URL("http://www.tetonsoftware.com/pets/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        new DownloadJSON(MainActivity.this).execute(url.toString());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                if (checkConnections())
                {
                    try {
                        url = new URL(prefs.getString("url", getString(R.string.error)));
                        new DownloadJSON(MainActivity.this).execute(url.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //Toast.makeText(MainActivity.this, "doing stuff", Toast.LENGTH_SHORT).show();
                }

            }
        };

        prefs.registerOnSharedPreferenceChangeListener(listener);

        //Toast.makeText(this, url.toString(), Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getBackground().setAlpha(APP_BAR_ALPHA);
        setSupportActionBar(toolbar);


        //for when the internet connects
        //spinner.setEnabled(true);
        //spinner.setVisibility(View.VISIBLE);

        //for when internet doesn't connect
        //spinner.setEnabled(false);
        //spinner.setVisibility(View.INVISIBLE);


    }


    private boolean checkConnections() {
        if (!network && !wifi) {
            Toast.makeText(this, R.string.network_unavailable, Toast.LENGTH_SHORT).show();
            return true;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent myIntent = new Intent(this, SettingsActivity.class);
                startActivity(myIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doNetworkCheck() {
        network = check.isNetworkReachable();
        //Toast.makeText(this, "Cellular is " + network, Toast.LENGTH_SHORT).show();
    }

    public void doWirelessCheck() {
        wifi = check.isWifiReachable();
        //Toast.makeText(this, "Wireless is " + wifi, Toast.LENGTH_SHORT).show();
    }


    //TODO: Setup threading JSON download
    //TODO: parse JSON
    //TODO: Do stuff with the parsed info (background, etc)

}
