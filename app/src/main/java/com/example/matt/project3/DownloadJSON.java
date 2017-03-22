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
    String line;
    int connectionCode;


    DownloadJSON(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        try {
            URL url = new URL(params[0]);
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
        if(connectionCode == 404)
        {
            Toast.makeText(context, "fuck you", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "yay", Toast.LENGTH_SHORT).show();
        }
    }
}
