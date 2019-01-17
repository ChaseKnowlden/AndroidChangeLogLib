package com.example.changeloglibrary;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {

    public static boolean isConnected(Context context) {

        if (context==null) return false;

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager!=null){
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if (activeNetworkInfo == null)
                return false;

            return ((activeNetworkInfo != null) && (activeNetworkInfo
                    .isConnectedOrConnecting()));
        }

        return false;
    }
}
