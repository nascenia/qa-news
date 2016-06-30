package com.example.roze.nasceniasqa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Nascenia on 6/27/2016.
 */
public class receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo ni=(NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
        if(ni!=null && ni.getState()== NetworkInfo.State.CONNECTED) {

            Intent intent1 = new Intent(context,notify.class);
            context.startService(intent1);
        } else if(intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,Boolean.FALSE)) {

            Intent intent2 = new Intent(context,notify.class);
            context.stopService(intent2);
        }

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null && info.isConnected()) {


            Intent intent1 = new Intent(context,notify.class);
            context.startService(intent1);


        }
        else {

            Intent intent2 = new Intent(context,notify.class);
            context.stopService(intent2);
        }
    }
}
