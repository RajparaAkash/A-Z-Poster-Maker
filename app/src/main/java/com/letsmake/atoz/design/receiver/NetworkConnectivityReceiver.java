package com.letsmake.atoz.design.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.letsmake.atoz.design.Application;

public class NetworkConnectivityReceiver extends BroadcastReceiver {

    public static NetworkConnectivityListener networkConnectivityListener;

    public interface NetworkConnectivityListener {
        void onNetworkConnectionChanged(boolean z);
    }

    public static boolean isConnected() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) Application.getInstance().getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        boolean z = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        if (networkConnectivityListener != null) {
            networkConnectivityListener.onNetworkConnectionChanged(z);
        }
    }
}
