package com.example.matt.project3;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class DownloadJSON extends AsyncTask<String, String, String> {
    private String urlStr;
    private int connectionCode;
    private MainActivity main;


    DownloadJSON(MainActivity main) {
        attach(main);
    }

    @Override
    protected String doInBackground(String... params) {


        try {
            URL url = new URL(params[0] + "/pets.json");
            urlStr= url.toString();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.connect();

            connectionCode = connection.getResponseCode();

                InputStream input = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder buffer = new StringBuilder();

            String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                    Log.d("Response: ", "> " + line);
                }

                return buffer.toString();

        } catch (Exception ignored) {

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

        if(connectionCode != 200)
        {
            if (main != null) {
                main.couldNotConnect(connectionCode, urlStr);
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
