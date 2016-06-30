package com.example.roze.nasceniasqa;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class signIn extends Activity {

    public static String PREFS_NAME="NSQA";

    EditText et1,et2;
    TextView tv1,tv2,tv3,tv4;
    Button btn1;

    double cc = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_in);
        final SharedPreferences preference =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor=preference.edit();

        InitializeAll();



        //for font types
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/font1.otf");
        tv2.setTypeface(type);

        Typeface type1 = Typeface.createFromAsset(getAssets(),"fonts/font4.ttf");
        tv1.setTypeface(type1);

        Typeface type3 = Typeface.createFromAsset(getAssets(),"fonts/timenormal.ttf");
        tv3.setTypeface(type3);

        Typeface type4 = Typeface.createFromAsset(getAssets(),"fonts/timenormal.ttf");
        tv4.setTypeface(type4);

        Typeface type5 = Typeface.createFromAsset(getAssets(),"fonts/timenormal.ttf");
        et1.setTypeface(type5);

        Typeface type6 = Typeface.createFromAsset(getAssets(),"fonts/timenormal.ttf");
        et2.setTypeface(type6);

        Typeface type7 = Typeface.createFromAsset(getAssets(),"fonts/neo_bold.ttf");
        btn1.setTypeface(type7);
/////////////////////////////////////////////////////////////////////////////////////////



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1,s2;
                s1 = et1.getText().toString();
                s2 = et2.getText().toString();

                if(s1.equals("")|s2.equals("")){
                    Toast.makeText(signIn.this, "Please Enter Username and Email", Toast.LENGTH_SHORT).show();
                }
                else {
                    editor.putString("Username",s1);
                    editor.putString("Email",s2);
                    editor.putBoolean("signIn",true);
                    editor.commit();


                    Intent intent = new Intent(signIn.this,MainActivity.class);
                    startActivity(intent);


                }
            }
        });


    }

    private void InitializeAll(){
        et1 = (EditText)findViewById(R.id.editText_username);
        et2 = (EditText)findViewById(R.id.editText_email);
        btn1 = (Button)findViewById(R.id.button2);
        tv1 = (TextView)findViewById(R.id.textView);
        tv2 = (TextView)findViewById(R.id.textView_nascenia);
        tv3 = (TextView)findViewById(R.id.textView3);
        tv4 = (TextView)findViewById(R.id.textView4);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {

            finish();
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
            Intent intent = new Intent(signIn.this,MainActivity.class);
            startActivity(intent);
        }
        else {
            editor.putString("ListCount","0");
            editor.commit();
            Intent intent = new Intent(signIn.this,notify.class);
            stopService(intent);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        final SharedPreferences preference =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor=preference.edit();

        if(preference.getBoolean("signIn", Boolean.parseBoolean(null))){
            Intent intent = new Intent(signIn.this,MainActivity.class);
            startActivity(intent);
        }
        else{
            editor.putString("pre_count","0");
            editor.putString("first_m","0");
            editor.putString("first_n","0");
            editor.putString("recent_count","0");
            editor.commit();

            Toast.makeText(signIn.this, preference.getString("pre_count",null)+" "+preference.getString("recent_count",null), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(signIn.this,notify.class);
            stopService(intent);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}
