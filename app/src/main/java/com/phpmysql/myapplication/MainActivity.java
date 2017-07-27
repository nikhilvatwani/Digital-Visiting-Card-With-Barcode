package com.phpmysql.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String";
    SharedPreferences sharedPreferences;
    public String[] pos = new String[20];
    public Map<String,?> keys;
    DBHelper mydb;
    String text2qr;
    ImageView image;
    String str;
    public int i = 0;
    String[] values;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        keys = sharedPreferences.getAll();
        str = sharedPreferences.getString(DBHelper.CONTACTS_COLUMN_NAME,"")+"//"+
                sharedPreferences.getString(DBHelper.CONTACTS_COLUMN_PHONE,"")+"//"+
                sharedPreferences.getString(DBHelper.CONTACTS_COLUMN_EMAIL,"")+"//"+
                sharedPreferences.getString(DBHelper.CONTACTS_COLUMN_STREET,"")+"//"+
                sharedPreferences.getString(DBHelper.CONTACTS_COLUMN_CITY,"");
        Log.d("heere",str);
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("Key",entry.getKey().toString());
            pos[i] = entry.getValue().toString();
        }

        mydb = new DBHelper(this);
        final Activity activity = this;
        image = (ImageView)findViewById(R.id.imageView2);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("hello","clicked");
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
        Button generate = (Button)findViewById(R.id.button2);
        generate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                text2qr = str;
                Log.d("str",str);
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(str.toString(), BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);
                }catch(WriterException e){
                    e.printStackTrace();
                }
            }
        });
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllContacts.class);
                startActivity(intent);
            }
        });

    }
   protected void onActivityResult(int requestCode,int resultCode,Intent data){


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
      // Log.d("hello",result.getContents().toString());

        if(result !=null){
            if(result.getContents() == null){
                Log.d("hello","canceled");
                Toast.makeText(this,"You cancelled Scan",Toast.LENGTH_SHORT).show();
            }else{
                values = result.getContents().toString().split("//");
                if(mydb.insertContact(values[0], values[1],
                        values[2], values[3],
                        values[4])){
                    Log.d("hello",result.getContents());
                    Toast.makeText(this,"Contact Added",Toast.LENGTH_SHORT).show();
                }

            }
        }else{
            super.onActivityResult(requestCode, resultCode,data);
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.item1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DisplayContacts.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}
