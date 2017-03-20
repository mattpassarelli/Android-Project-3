package com.example.matt.project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private static final int APP_BAR_ALPHA = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getBackground().setAlpha(APP_BAR_ALPHA);
        setSupportActionBar(toolbar);

        spinner = (Spinner) findViewById(R.id.spinner);

        //for when the internet connects
        //spinner.setEnabled(true);
        //spinner.setVisibility(View.VISIBLE);

        //for when internet doesn't connect
        //spinner.setEnabled(false);
        //spinner.setVisibility(View.INVISIBLE);


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


    //TODO: Method for checking network connectivity
    //TODO: Create permission requesting method
    //TODO: Setup threading JSON download
    //TODO: parse JSON
    //TODO: Do stuff with the parsed info (background, etc)

}
