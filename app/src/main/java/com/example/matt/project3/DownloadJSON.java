package com.example.matt.project3;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class DownloadJSON extends AsyncTask<String, String, String> {
    private String urlStr;
    private Context context;
    private String line;
    private int connectionCode;
    private MainActivity main;


    DownloadJSON(Context context, MainActivity main) {
        this.context = context;
        attach(main);
    }

    @Override
    protected String doInBackground(String... params) {


        try {
            URL url = new URL(params[0] + "/pets.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.connect();

            connectionCode = connection.getResponseCode();

                InputStream input = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuffer buffer = new StringBuffer();

                line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);
                }

                return buffer.toString();

        } catch (Exception e) {

        }
        return "";
    }



    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(main != null)
        {
            //main.printTestToast();
            main.parseJSON(result);
        }

        if(connectionCode == 404)
        {
            if (main != null) {
                main.couldNotConnect(connectionCode);
            }
        }
        else
        {
            if (main != null) {
                main.couldConnect();
            }
            //Toast.makeText(context, "" + urlStr + " yay", Toast.LENGTH_SHORT).show();
        }
    }


    private void attach(MainActivity main)
    {
        this.main = main;
    }

}
