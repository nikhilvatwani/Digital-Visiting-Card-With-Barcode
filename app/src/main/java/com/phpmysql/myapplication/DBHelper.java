package com.phpmysql.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

/**
 * Created by DELL on 07/02/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    private HashMap hp;
    static DBHelper help;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
        help = this;
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table contacts"+"(id integer primary key,name text,phone text,email text,street text,place text)");
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
    public boolean insertContact(String name, String phone, String email, String street,String place){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.insert("contacts",null,contentValues);
        return true;
    }
    public Cursor getData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from contacts where id="+id+"",null);
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }
    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCotacts(){
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from contacts",null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllIds(){
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from contacts",null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PHONE)));
            res.moveToNext();
        }
        return array_list;
    }

    public static String getPhone(int id){
        SQLiteDatabase db = help.getWritableDatabase();
        Cursor res = db.rawQuery("select * from contacts where id="+id,null);
        String phn = res.getString(res.getColumnIndex(CONTACTS_COLUMN_PHONE));
        Log.d("dbhelper",phn);
        return phn;
    }
}
