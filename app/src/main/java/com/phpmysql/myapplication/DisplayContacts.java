package com.phpmysql.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.Button;
import android.view.Menu;
import android.widget.Toast;
import android.content.Intent;
import android.app.AlertDialog;
import android.view.MenuItem;
import android.content.DialogInterface;

import java.util.ArrayList;

public class DisplayContacts extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;
    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String123";
    SharedPreferences sharedpreferences;
    TextView name ;
    TextView phone;
    TextView email;
    TextView street;
    TextView place;
    int id_To_Update = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contacts);
        int Value = 0;
        name = (TextView) findViewById(R.id.editTextName);
        phone = (TextView) findViewById(R.id.editTextPhone);
        email = (TextView) findViewById(R.id.editTextStreet);
        street = (TextView) findViewById(R.id.editTextEmail);
        place = (TextView) findViewById(R.id.editTextCity);

        mydb = new DBHelper(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            Value = extras.getInt("id");
        if(Value>0){
            Cursor rs = mydb.getData(Value);
            id_To_Update = Value;
            rs.moveToFirst();
            String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
            String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
            String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
            String stree = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_STREET));
            String plac = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));
            if (!rs.isClosed())  {
                rs.close();
            }

            /*Bundle dataBundle = new Bundle();
            dataBundle.putString("name", nam);
            dataBundle.putString("phone", phon);
            dataBundle.putString("email", emai);
            dataBundle.putString("street", stree);
            dataBundle.putString("place", plac);

            Intent intent = new Intent(this,ActualContact.class);
            intent.putExtras(dataBundle);
            startActivity(intent);*/


            Button b = (Button)findViewById(R.id.button1);
            //b.setVisibility(View.INVISIBLE);
            name.setText((CharSequence)nam);
           // name.setFocusable(false);
            //name.setClickable(false);

            phone.setText((CharSequence)phon);
            //phone.setFocusable(false);
            //phone.setClickable(false);

            email.setText((CharSequence)emai);
            //email.setFocusable(false);
            //email.setClickable(false);

            street.setText((CharSequence)stree);
            //street.setFocusable(false);
            //street.setClickable(false);

            place.setText((CharSequence)plac);
            //place.setFocusable(false);
            //place.setClickable(false);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
   /* public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_contact, menu);
            } else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }*/
    /*public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Contact:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                phone.setEnabled(true);
                phone.setFocusableInTouchMode(true);
                phone.setClickable(true);

                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);

                street.setEnabled(true);
                street.setFocusableInTouchMode(true);
                street.setClickable(true);

                place.setEnabled(true);
                place.setFocusableInTouchMode(true);
                place.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }*/
    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        Bundle bundle = new Bundle();
        Log.d("run","inside run");
        if(extras !=null) {
            Log.d("run","inside if1");
            int Value = extras.getInt("id");
            if(Value>0){
                Log.d("run","inside if2");
                if(mydb.updateContact(id_To_Update,name.getText().toString(),
                        phone.getText().toString(), email.getText().toString(),
                        street.getText().toString(), place.getText().toString())){
                    Log.d("run","inside if3");
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Log.d("run","inside else3");
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{
                Log.d("run","inside else2");
                sharedpreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                if(mydb.insertContact(name.getText().toString(), phone.getText().toString(),
                        email.getText().toString(), street.getText().toString(),
                        place.getText().toString())){
                    Log.d("run","inside if4");
                   /* bundle.putString(DBHelper.CONTACTS_COLUMN_NAME,name.getText().toString());
                    bundle.putString(DBHelper.CONTACTS_COLUMN_PHONE,phone.getText().toString());
                    bundle.putString(DBHelper.CONTACTS_COLUMN_EMAIL,email.getText().toString());
                    bundle.putString(DBHelper.CONTACTS_COLUMN_STREET,street.getText().toString());
                    bundle.putString(DBHelper.CONTACTS_COLUMN_CITY,place.getText().toString());*/
                    editor.putString(DBHelper.CONTACTS_COLUMN_NAME,name.getText().toString());
                    editor.putString(DBHelper.CONTACTS_COLUMN_PHONE,phone.getText().toString());
                    editor.putString(DBHelper.CONTACTS_COLUMN_EMAIL,email.getText().toString());
                    editor.putString(DBHelper.CONTACTS_COLUMN_STREET,street.getText().toString());
                    editor.putString(DBHelper.CONTACTS_COLUMN_CITY,place.getText().toString());
                    editor.putInt(PREFS_KEY,10);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Log.d("run","inside else4");
                    Toast.makeText(getApplicationContext(), "not done",
                            Toast.LENGTH_SHORT).show();
                }
                Log.d("run","outside all");
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

}
