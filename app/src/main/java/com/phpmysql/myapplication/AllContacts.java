package com.phpmysql.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllContacts extends AppCompatActivity {
    DBHelper mydb = new DBHelper(this);
    private ListView obj;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);
        //Toast.makeText(this,"here",Toast.LENGTH_SHORT).show();
        ArrayList array_list = mydb.getAllCotacts();
        ArrayList all_ids = mydb.getAllIds();
        AllContactsAdapter arrayAdapter=new AllContactsAdapter(this,array_list,all_ids);
        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        Log.d("idinall","here");
        mContext = this;
       // String phn = DBHelper.getPhone(Integer.parseInt(all_ids.get(1).toString()));
        //Toast.makeText(mContext,phn,Toast.LENGTH_SHORT).show();
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;
                //Toast.makeText(mContext,id_To_Search+"",Toast.LENGTH_SHORT).show();
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(),ActualContact.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }
}
