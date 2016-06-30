package com.example.roze.nasceniasqa;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Roze on 6/25/2016.
 */
public class notify extends Service {
    Firebase mRef;
    public static String PREFS_NAME="NSQA";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate()
    {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this, 0,
                notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        Notification notification=new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logg)
                .setContentText("Service")
                .setContentIntent(pendingIntent).build();

        startForeground(999, notification);


    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){


        final MainActivity mm = null;
        final SharedPreferences preference =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor=preference.edit();
        mRef = new Firebase("https://nascenia-sqa.firebaseio.com/sqaNews");

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (preference.getBoolean("signIn", Boolean.parseBoolean(null))) {

                    if(Integer.valueOf(preference.getString("first_n",null)) == 0){

                        editor.putString("first_n","1");
                        editor.commit();

                    }
                    else if(Integer.valueOf(preference.getString("first_n",null)) == 1){
                        int tempo = Integer.valueOf(preference.getString("recent_count",null))+1;
                        editor.putString("recent_count",String.valueOf(tempo));
                        editor.commit();


                    }


                    if (Integer.valueOf(preference.getString("recent_count",null)) > Integer.valueOf(preference.getString("pre_count",null))) {

                        final SharedPreferences preference =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        final SharedPreferences.Editor editor=preference.edit();

                        editor.putString("pre_count",preference.getString("recent_count", null));
                        editor.commit();


                        int mId = 0;
                        String data = dataSnapshot.getValue().toString();
                        String n_time = extractTime(data);
                        String n_username = extractUsername(data);
                        String n_email = extractEmail(data);
                        String n_title = extractTitle(data);
                        String n_url = extractUrl(data);
                        String n_details = extractDetails(data);


                        editor.putString("db_time", n_time);
                        editor.putString("db_username", n_username);
                        editor.putString("db_email", n_email);
                        editor.putString("db_title", n_title);
                        editor.putString("db_url", n_url);
                        editor.putString("db_details", n_details);
                        editor.commit();

                        NotificationCompat.Builder mBuilder =
                                (NotificationCompat.Builder) new NotificationCompat.Builder(notify.this)
                                        .setSmallIcon(R.drawable.logg)
                                        .setContentTitle(n_title)
                                        .setContentText(n_username);
// Creates an explicit intent for an Activity in your app
                        Intent resultIntent = new Intent(notify.this, display.class);

                        //Toast.makeText(notify.this, "notific", Toast.LENGTH_SHORT).show();

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(notify.this);
// Adds the back stack for the Intent (but not the Intent itself)
                        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent =
                                stackBuilder.getPendingIntent(
                                        0,
                                        PendingIntent.FLAG_UPDATE_CURRENT
                                );
                        mBuilder.setContentIntent(resultPendingIntent);
                        NotificationManager mNotificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.

                        mNotificationManager.notify(mId, mBuilder.build());

                        Vibrator vib = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                        vib.vibrate(100);


                    }

                    //////////////////////////////////////////////////////////////


                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                int tempo = Integer.valueOf(preference.getString("recent_count",null))-1;
                editor.putString("recent_count",String.valueOf(tempo));
                editor.putString("pre_count",String.valueOf(tempo));
                editor.commit();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public String getFirstLetter(String s){

        String s1;
        s1 = String.valueOf(s.charAt(0));

        return s1;
    }
    public String extractTime(String s){

        char[] c = new char[s.length()];
        String s1;
        int j = 0;

        for(int i = 0; i<s.length() ;i++){
            if(s.charAt(i) == ';'&& s.charAt(i+1) == '0'){
                break;
            }
            else{
                c[i] =s.charAt(i);
                j++;
            }
        }
        char[] c1 = new char[j];
        c1 = c;

        s1= String.valueOf(c1);


        return s1;
    }
    public String extractUsername(String s){

        char[] c = new char[s.length()];
        String s1;
        int j = 0;
        int k = 0;
        int z = 0;

        for(int i = 0; i<s.length() ;i++){
            if(s.charAt(i) == ';'&& s.charAt(i+1) == '0'){
                k=1;
            }
            if(s.charAt(i+2) == ';'&& s.charAt(i+3) == '1'){
                break;
            }
            else if(k == 1){
                c[z] =s.charAt(i+2);
                j++;
                z++;
            }
        }
        char[] c1 = new char[j];
        c1 = c;

        s1= String.valueOf(c1);


        return s1;
    }
    public String extractEmail(String s){
        char[] c = new char[s.length()];
        String s1;
        int j = 0;
        int k = 0;
        int z = 0;

        for(int i = 0; i<s.length() ;i++){
            if(s.charAt(i) == ';'&& s.charAt(i+1) == '1'){
                k=1;
            }
            if(s.charAt(i+2) == ';'&& s.charAt(i+3) == '2'){
                break;
            }
            else if(k == 1){
                c[z] =s.charAt(i+2);
                j++;
                z++;
            }
        }
        char[] c1 = new char[j];
        c1 = c;

        s1= String.valueOf(c1);


        return s1;
    }
    public String extractTitle(String s){

        char[] c = new char[s.length()];
        String s1;
        int j = 0;
        int k = 0;
        int z = 0;

        for(int i = 0; i<s.length() ;i++){
            if(s.charAt(i) == ';'&& s.charAt(i+1) == '2'){
                k=1;
            }
            if(s.charAt(i+2) == ';'&& s.charAt(i+3) == '3'){
                break;
            }
            else if(k == 1){
                c[z] =s.charAt(i+2);
                j++;
                z++;
            }
        }
        char[] c1 = new char[j];
        c1 = c;

        s1= String.valueOf(c1);


        return s1;
    }

    public String extractUrl(String s){
        char[] c = new char[s.length()];
        String s1;
        int j = 0;
        int k = 0;
        int z = 0;

        for(int i = 0; i<s.length() ;i++){
            if(s.charAt(i) == ';'&& s.charAt(i+1) == '3'){
                k=1;
            }
            if(s.charAt(i+2) == ';'&& s.charAt(i+3) == '4'){
                break;
            }
            else if(k == 1){
                c[z] =s.charAt(i+2);
                j++;
                z++;
            }
        }
        char[] c1 = new char[j];
        c1 = c;

        s1= String.valueOf(c1);


        return s1;
    }
    private String extractDetails(String s){
        char[] c = new char[s.length()];
        String s1;
        int j = 0;
        int k = 0;
        int z = 0;

        for(int i = 0; i<s.length() ;i++){
            if(s.charAt(i) == ';'&& s.charAt(i+1) == '4'){
                k=1;
            }
            if(s.charAt(i+2) == ';'&& s.charAt(i+3) == '5'){
                break;
            }
            else if(k == 1){
                c[z] =s.charAt(i+2);
                j++;
                z++;
            }
        }
        char[] c1 = new char[j];
        c1 = c;

        s1= String.valueOf(c1);


        return s1;
    }
}
