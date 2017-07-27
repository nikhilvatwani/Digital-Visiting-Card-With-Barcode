package com.phpmysql.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Niknom on 4/10/2017.
 */

public class AllContactsAdapter extends BaseAdapter {

    ArrayList array_list;
    ArrayList all_ids;
    Context mContext;
    int pos;
    AllContactsAdapter(Context mContext, ArrayList array_list,ArrayList all_ids){
        this.array_list = array_list;
        this.mContext = mContext;
        this.all_ids = all_ids;
    }

    @Override
    public int getCount() {
        return array_list.size();
    }

    @Override
    public Object getItem(int i) {
        return array_list.get(i).toString();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.all_contacts,viewGroup,false);
        TextView textView = (TextView)v.findViewById(R.id.text);
        textView.setText(array_list.get(i).toString());
        ImageView imageView = (ImageView)v.findViewById(R.id.imageView2);
        imageView.setTag(i);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("idinadap",all_ids.get(pos).toString());
                //String phn = all_ids.get(pos).toString();
                //Toast.makeText(mContext,phn,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+all_ids.get(Integer.parseInt(view.getTag()+"")).toString()));
                mContext.startActivity(intent);
            }
        });
        ImageView imageView1 = (ImageView)v.findViewById(R.id.imageView3);
        imageView1.setTag(i);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("idinadap",all_ids.get(pos).toString());
                //String phn = all_ids.get(pos).toString();
                //Toast.makeText(mContext,phn,Toast.LENGTH_SHORT).show();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                mContext.startActivity(emailIntent);
            }
        });

        return v;
    }
}
