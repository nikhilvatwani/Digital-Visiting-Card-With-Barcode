package com.phpmysql.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ChangingActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String123";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changing);
        sharedpreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if(sharedpreferences.contains(PREFS_KEY)){
            Log.d("here","main");
            Intent intent = new Intent(ChangingActivity.this,MainActivity.class);
            startActivity(intent);
        }else{
            Bundle dataBundle = new Bundle();
            dataBundle.putInt("id", 0);
            Log.d("here","display");
            Intent intent = new Intent(ChangingActivity.this,DisplayContacts.class);
            intent.putExtras(dataBundle);
            startActivity(intent);
        }
    }
}
