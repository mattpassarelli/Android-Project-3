package com.example.matt.project3;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class CheckNetwork {
    private Context context;

    CheckNetwork(Context context) {
        this.context = context;
    }

    boolean isNetworkReachable() {
        ConnectivityManager mManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = mManager.getActiveNetworkInfo();
        return current != null && (current.getState() == NetworkInfo.State.CONNECTED);
    }

    boolean isWifiReachable() {
        ConnectivityManager mManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = mManager.getActiveNetworkInfo();
        return current != null && (current.getType() == ConnectivityManager.TYPE_WIFI);
    }
}