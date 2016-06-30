package com.example.roze.nasceniasqa;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class about extends Activity {


    public static String PREFS_NAME="NSQA";
    TextView tv1,tv2,tv3,tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams windowManager=getWindow().getAttributes();
        windowManager.dimAmount=0.60f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(R.layout.activity_about);



        LinearLayout back=(LinearLayout)findViewById(R.id.layout_back4);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv1 = (TextView)findViewById(R.id.textView_a1);
        tv2 = (TextView)findViewById(R.id.textView_a2);
        tv3 = (TextView)findViewById(R.id.textView_a3);
        tv4 = (TextView)findViewById(R.id.textView_a4);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/font1.otf");
        tv1.setTypeface(type);

        Typeface type1 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv2.setTypeface(type1);

        Typeface type3 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv3.setTypeface(type3);

        Typeface type4 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv4.setTypeface(type4);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
