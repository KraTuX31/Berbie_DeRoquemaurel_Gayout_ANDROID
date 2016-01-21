package com.m2dl.maf.makeafocal.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Map;


public class Database extends SQLiteOpenHelper {
    public static Map<Context, Database> instances;
    public static final String DATABASE_NAME = "makeafocal.db";

    public static Database instance(Context c) {
        if (instances.get(c) == null) {
            instances.put(c, new Database(c));
        }

        return instances.get(c);
    }

    /**
     * The current context
     * @param context The context of database
     */
    private Database(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
//        db.execSQL(
  //              "create table contacts " +
    //                    "(id integer primary key, name text,phone text,email text, street text,place text)"
      //  );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new RuntimeException("Not implemented");
    }

    public boolean insertContact  (String name, String phone, String email, String street,String place)
    {
       /* SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.insert("contacts", null, contentValues);*/
        return true;
    }

    public Cursor getData(int id){
/*        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;*/
        return null;
    }

    public int numberOfRows(){
/*        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
        */
        return 0;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
     /*   SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        */
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        /*SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
                */
        return null;
    }

    public ArrayList<String> getAllCotacts()
    {
     /*   ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
        */
        return null;
    }
}