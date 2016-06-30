package com.example.roze.nasceniasqa;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.ActivityChooserView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class exit extends Activity {

    public static String PREFS_NAME="NSQA";
    public String Exit="exit";
    TextView tv1,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams windowManager=getWindow().getAttributes();
        windowManager.dimAmount=0.60f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(R.layout.activity_exit);



        tv1 = (TextView)findViewById(R.id.textView_e1);
        tv2 = (TextView)findViewById(R.id.textView_e2);


        LinearLayout back=(LinearLayout)findViewById(R.id.layout_back4);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final android.widget.Button exit= (Button)findViewById(R.id.button_ok_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }
        });


        //for font types
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv1.setTypeface(type);

        Typeface type1 = Typeface.createFromAsset(getAssets(),"fonts/neo_bold.ttf");
        exit.setTypeface(type1);

        Typeface type3 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv2.setTypeface(type3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
