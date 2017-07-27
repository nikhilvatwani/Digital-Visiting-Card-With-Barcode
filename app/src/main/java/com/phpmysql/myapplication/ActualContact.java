package com.phpmysql.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActualContact extends AppCompatActivity {
    int id_To_Update = 0;
    DBHelper mydb = new DBHelper(this);
    public String phon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int Value = 0;
        DBHelper mydb = new DBHelper(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            Value = extras.getInt("id");

        if(Value>0){
            Cursor rs = mydb.getData(Value);
            id_To_Update = Value;
            rs.moveToFirst();
            String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
            getSupportActionBar().setTitle(nam.toUpperCase());
            phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
            String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
            String stree = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_STREET));
            String plac = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));
            if (!rs.isClosed())  {
                rs.close();
            }
           // Toast.makeText(this,nam,Toast.LENGTH_LONG).show();

            TextView name = (TextView)findViewById(R.id.name_value);
            name.setText(nam);

            TextView phone = (TextView)findViewById(R.id.phone_value);
            phone.setText(phon);

            TextView email = (TextView)findViewById(R.id.address_value);
            email.setText(emai);

            TextView street = (TextView)findViewById(R.id.email_value);
            street.setText(stree);

            TextView place = (TextView)findViewById(R.id.city_value);
            place.setText(plac);
            ImageView imageView = (ImageView)findViewById(R.id.imageView2);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Log.d("idinadap",all_ids.get(pos).toString());
                    //String phn = all_ids.get(pos).toString();
                    //Toast.makeText(mContext,phn,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+phon));
                    startActivity(intent);
                }
            });
            ImageView imageView1 = (ImageView)findViewById(R.id.imageView3);
            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Log.d("idinadap",all_ids.get(pos).toString());
                    //String phn = all_ids.get(pos).toString();
                    //Toast.makeText(mContext,phn,Toast.LENGTH_SHORT).show();
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    startActivity(emailIntent);
                }
            });
        }



    }

    public boolean onCreateOptionsMenu(Menu menu) {
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
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Contact:
                Bundle extras = new Bundle();
                extras.putInt("id",id_To_Update);
                Intent intent = new Intent(this,DisplayContacts.class);
                intent.putExtras(extras);
                startActivity(intent);
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
    }

}
