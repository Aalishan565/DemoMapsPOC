package com.demomapspoc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by aalishan on 5/5/16.
 */
public class NetworkManager {
    // get type
    public final static int GET_TYPE = 0;
    // post type
    public final static int POST_TYPE = 1;
    //
    public final static String[] METHOD = {"GET", "POST", "PUT", "DELETE"};

    /**
     * This method is responsible for checking network availabiity
     *
     * @param context
     * @return network availability
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
