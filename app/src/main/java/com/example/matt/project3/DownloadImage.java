package com.example.matt.project3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class DownloadImage extends AsyncTask<String, String, Bitmap> {
    private MainActivity main;

    DownloadImage(MainActivity main)
    {
        attach(main);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return downloadImage(params[0]);
    }

    private Bitmap downloadImage(String param) {
        URL url;
        try {
            url = new URL(param);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

            InputStream is = httpCon.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead, totalBytesRead = 0;
            byte[] data = new byte[2048];

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
                totalBytesRead += nRead;
                publishProgress("" + totalBytesRead);
            }

            byte[] image = buffer.toByteArray();
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected  void onPostExecute(Bitmap result) {
        if(result != null) {
            //Toast.makeText(main, "Image Downloaded", Toast.LENGTH_SHORT).show();
            if(main != null)
            {
                main.setBackground(result);
            }
        }
        else
        {
            Toast.makeText(main, "Download failed" + "\n" + "Please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    private void attach(MainActivity main)
    {
        this.main = main;
    }
}


