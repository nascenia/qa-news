package com.example.roze.nasceniasqa;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.collection.LLRBNode;
import com.firebase.ui.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String PREFS_NAME="NSQA";
    Firebase mRef;
    ArrayList<String> mMessages  = new ArrayList<>();
    public static ListView ls;
    TextView tv_1,tv_2;
    int Count;
    LinearLayout ll1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        final SharedPreferences preference =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor=preference.edit();



        // firebase
        mRef = new Firebase("https://nascenia-sqa.firebaseio.com/sqaNews");


        ls = (ListView)findViewById(R.id.listView);


        ll1 = (LinearLayout)findViewById(R.id.ll_list);
        // end




        //for exit
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {

            editor.putBoolean("signIn",false);
            editor.putString("Username","");
            editor.putString("Email","");
            editor.commit();
            finish();
            Intent intent1 = new Intent(MainActivity.this,notify.class);
            stopService(intent1);

        }
        //end



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,push_msg.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            Intent intent=new Intent(MainActivity.this,exit.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent=new Intent(MainActivity.this,exit.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            //profile
            Intent intent=new Intent(MainActivity.this,profile.class);
            startActivity(intent);


        } else if (id == R.id.nav_manage) {


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent=new Intent(MainActivity.this,about.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent intent = new Intent(getApplicationContext(),exit.class);
            startActivity(intent);
            return false;
        }
        if(keyCode == KeyEvent.KEYCODE_HOME){

        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();


        final SharedPreferences preference =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor=preference.edit();


        if(preference.getBoolean("signIn", Boolean.parseBoolean(null))){


            FirebaseListAdapter<String> adapter =
                    new FirebaseListAdapter<String>(
                            this,
                            String.class,
                            R.layout.item,
                            mRef
                    ) {
                        @Override
                        protected void populateView(View view, String s, int i) {

                            TextView text = (TextView)view.findViewById(R.id.tv_bigletter);
                            String username = extractUsername(s);
                            String firstLetter = getFirstLetter(username);
                            text.setText(firstLetter);
                            Typeface type = Typeface.createFromAsset(getAssets(),"fonts/font2.otf");
                            text.setTypeface(type);


                            TextView text1 = (TextView)view.findViewById(R.id.tv_title);
                            String title = extractTitle(s);
                            text1.setText(title);
                            Typeface type1 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
                            text1.setTypeface(type1);

                            TextView text2 = (TextView)view.findViewById(R.id.tv_time);
                            String time = extractTime(s);
                            text2.setText(time);
                            Typeface type2 = Typeface.createFromAsset(getAssets(),"fonts/timebold.ttf");
                            text2.setTypeface(type2);

                            TextView text3 = (TextView)view.findViewById(R.id.tv_url);
                            String url = extractUrl(s);
                            text3.setText(url);

                            TextView text4 = (TextView)view.findViewById(R.id.tv_details);
                            String details = extractDetails(s);
                            text4.setText(details);

                            TextView text5 = (TextView)view.findViewById(R.id.tv_email);
                            String email = extractEmail(s);
                            text5.setText(email);

                            TextView text6 = (TextView)view.findViewById(R.id.tv_username);
                            text6.setText(username);


                        }
                    };
            ls.setAdapter(adapter);


            if(Integer.valueOf(preference.getString("first_m",null)) == 0){

                Intent intent12 = new Intent(MainActivity.this,notify.class);
                startService(intent12);

                editor.putString("first_m","1");
                editor.commit();

            }
            mRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if(Integer.valueOf(preference.getString("first_m",null)) == 1){

                        editor.putString("recent_count",String.valueOf(ls.getCount()));
                        editor.putString("first_m","2");
                        editor.commit();

                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView text1 = (TextView)view.findViewById(R.id.tv_time);
                    TextView text2 = (TextView)view.findViewById(R.id.tv_username);
                    TextView text3 = (TextView)view.findViewById(R.id.tv_email);
                    TextView text4 = (TextView) view.findViewById(R.id.tv_title);
                    TextView text5 = (TextView)view.findViewById(R.id.tv_url);
                    TextView text6 = (TextView)view.findViewById(R.id.tv_details);


                    editor.putString("db_time",text1.getText().toString());
                    editor.putString("db_username",text2.getText().toString());
                    editor.putString("db_email",text3.getText().toString());
                    editor.putString("db_title",text4.getText().toString());
                    editor.putString("db_url",text5.getText().toString());
                    editor.putString("db_details",text6.getText().toString());
                    editor.commit();

                    Intent intent = new Intent(MainActivity.this,display.class);
                    startActivity(intent);

                    int i = ls.getCount();
                    Toast.makeText(MainActivity.this, i+". "+text2.getText().toString(), Toast.LENGTH_SHORT).show();


                }
            });
        }

    }



    @Override
    protected void onResume() {
        super.onResume();



        final Handler handler= new Handler();
        final Timer timer = new Timer();
        Toast.makeText(getApplicationContext(),"start" , Toast.LENGTH_SHORT).show();
        final TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        if(isNetworkAvailable()){

                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0,100);

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

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
