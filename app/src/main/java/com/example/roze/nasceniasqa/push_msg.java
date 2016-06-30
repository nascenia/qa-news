package com.example.roze.nasceniasqa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class push_msg extends AppCompatActivity {

    public static String PREFS_NAME="NSQA";


    TextView tv1,tv2,tv3;
    EditText et1,et2,et3;
    Button btn1;

    Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_msg);
        final SharedPreferences preference =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor=preference.edit();


        InitializeAll();

        mRef = new Firebase("https://nascenia-sqa.firebaseio.com/sqaNews");




        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv1.setTypeface(type);

        Typeface type1 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv2.setTypeface(type1);

        Typeface type3 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        tv3.setTypeface(type3);

        Typeface type4 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        et1.setTypeface(type4);

        Typeface type5 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        et2.setTypeface(type5);

        Typeface type6 = Typeface.createFromAsset(getAssets(),"fonts/font6.ttf");
        et3.setTypeface(type6);

        Typeface type7 = Typeface.createFromAsset(getAssets(),"fonts/neo_bold.ttf");
        btn1.setTypeface(type7);

        //Toast.makeText(push_msg.this, preference.getString("push_title",null), Toast.LENGTH_SHORT).show();


            et1.setText(preference.getString("push_title",null));
            et2.setText(preference.getString("push_url",null));
            et3.setText(preference.getString("push_details",null));


    }

    private void InitializeAll(){

        et1 = (EditText) findViewById(R.id.editText);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        btn1 = (Button)findViewById(R.id.button);
        tv1 = (TextView)findViewById(R.id.textView_m1);
        tv2 = (TextView)findViewById(R.id.textView_m2);
        tv3 = (TextView)findViewById(R.id.textView_m3);

    }

    @Override
    protected void onStart() {
        super.onStart();

        final SharedPreferences preference =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor=preference.edit();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1,s2,s3;
                s1 = et1.getText().toString();
                s2 = et2.getText().toString();
                s3 = et3.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy         HH.mm.ss");
                String currentDateandTime = sdf.format(new Date());

                if(s1.equals("")||s2.equals("")||s3.equals("")||s1.equals(" ")||s2.equals(" ")||s3.equals(" ")){
                    Toast.makeText(push_msg.this, "Empty Text Fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    String msg = "";
                    msg = msg.concat(currentDateandTime+";0"+preference.getString("Username",null)+";1"
                    +preference.getString("Email",null)+";2"+s1+";3"+s2+";4"+s3+";5");

                    mRef.push().setValue(msg);

                    Toast.makeText(push_msg.this, msg, Toast.LENGTH_SHORT).show();

                    msg = "";

                    editor.putString("push_title","");
                    editor.putString("push_url","");
                    editor.putString("push_details","");
                    editor.commit();

                    et1.setText("");
                    et2.setText("");
                    et3.setText("");



                    finish();
                }

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        final SharedPreferences preference =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor=preference.edit();

        //Toast.makeText(push_msg.this, "onPause", Toast.LENGTH_SHORT).show();


            editor.putString("push_title",et1.getText().toString());
            editor.putString("push_url",et2.getText().toString());
            editor.putString("push_details",et3.getText().toString());
            editor.commit();

            //Toast.makeText(push_msg.this, "not null", Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        final SharedPreferences preference =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor=preference.edit();



    }
}
