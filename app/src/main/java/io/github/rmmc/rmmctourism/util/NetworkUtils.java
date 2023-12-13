package io.github.rmmc.rmmctourism.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utility class for network-related operations.
 */
public class NetworkUtils {

    /**
     * Checks if the device is currently connected to a network.
     *
     * @param context The application context
     * @return true if the device is connected to a network, false otherwise
     */
    public static boolean isNetworkConnected(Context context) {
        // Get the ConnectivityManager service
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            // Get information about the currently active network
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            // Check if the active network is available and connected or connecting
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }

        // Return false if the ConnectivityManager is not available
        return false;
    }
}
