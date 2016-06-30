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

public class profile extends Activity {

    public static String PREFS_NAME="NSQA";

    TextView tv1,tv2,tv3,tv4,tv5,tv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams windowManager=getWindow().getAttributes();
        windowManager.dimAmount=0.60f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(R.layout.activity_profile);

        LinearLayout back=(LinearLayout)findViewById(R.id.layout_back4);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv1 =(TextView)findViewById(R.id.tvd_letter);
        tv2 =(TextView)findViewById(R.id.tvd_username);
        tv3 =(TextView)findViewById(R.id.tvd_email);
        tv4 =(TextView)findViewById(R.id.textView_p1);
        tv5 =(TextView)findViewById(R.id.textView_p2);
        tv6 =(TextView)findViewById(R.id.textView_p3);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/font2.otf");
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





        final SharedPreferences preference =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor=preference.edit();

        tv2.setText(preference.getString("Username",null));
        tv3.setText(preference.getString("Email",null));


        String s1="";
        s1 = getFirstLetter(preference.getString("Username",null).toString());

        tv1.setText(s1);



    }

    public String getFirstLetter(String s){

        String s1;
        s1 = String.valueOf(s.charAt(0));

        return s1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
