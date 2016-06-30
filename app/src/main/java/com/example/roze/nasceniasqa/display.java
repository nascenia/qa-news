package com.example.roze.nasceniasqa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class display extends AppCompatActivity {

    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12;

    public static String PREFS_NAME="NSQA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        InitializeAll();

        final SharedPreferences preference =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor=preference.edit();

        tv1.setText(preference.getString("db_time",null).toString());
        tv2.setText(preference.getString("db_username",null).toString());
        tv3.setText(preference.getString("db_email",null).toString());
        tv4.setText(preference.getString("db_title",null).toString());
        tv5.setText(preference.getString("db_url",null).toString());
        tv6.setText(preference.getString("db_details",null).toString());




    }
    private void InitializeAll(){

        tv1 = (TextView)findViewById(R.id.tvdb_time);
        tv2 = (TextView)findViewById(R.id.tvdb_username);
        tv3 = (TextView)findViewById(R.id.tvdb_email);
        tv4 = (TextView)findViewById(R.id.tvdb_title);
        tv5 = (TextView)findViewById(R.id.tvdb_url);
        tv6 = (TextView)findViewById(R.id.tvdb_details);

        tv7 = (TextView)findViewById(R.id.textView_f1);
        tv8 = (TextView)findViewById(R.id.textView_f2);
        tv9 = (TextView)findViewById(R.id.textView_f3);
        tv10 = (TextView)findViewById(R.id.textView_f4);
        tv11 = (TextView)findViewById(R.id.textView_f5);
        tv12 = (TextView)findViewById(R.id.textView_f6);

        //for font types
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv1.setTypeface(type);

        Typeface type1 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv2.setTypeface(type1);

        Typeface type3 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv3.setTypeface(type3);

        Typeface type4 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv4.setTypeface(type4);

        Typeface type5 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv5.setTypeface(type5);

        Typeface type6 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv6.setTypeface(type6);

        Typeface type7 = Typeface.createFromAsset(getAssets(),"fonts/timebold.ttf");
        tv7.setTypeface(type7);

        Typeface type8 = Typeface.createFromAsset(getAssets(),"fonts/timebold.ttf");
        tv8.setTypeface(type8);

        Typeface type9 = Typeface.createFromAsset(getAssets(),"fonts/timebold.ttf");
        tv9.setTypeface(type9);

        Typeface type10 = Typeface.createFromAsset(getAssets(),"fonts/timebold.ttf");
        tv10.setTypeface(type10);

        Typeface type11 = Typeface.createFromAsset(getAssets(),"fonts/timebold.ttf");
        tv11.setTypeface(type11);

        Typeface type12 = Typeface.createFromAsset(getAssets(),"fonts/timebold.ttf");
        tv12.setTypeface(type12);
/////////////////////////////////////////////////////////////////////////////////////////


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            return false;
        }
        if(keyCode == KeyEvent.KEYCODE_HOME){

        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
