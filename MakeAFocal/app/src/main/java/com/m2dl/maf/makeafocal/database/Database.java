package com.m2dl.maf.makeafocal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.m2dl.maf.makeafocal.model.Photo;
import com.m2dl.maf.makeafocal.model.Tag;
import com.m2dl.maf.makeafocal.model.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


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
        // Creates table
        db.execSQL(
                "create table photos " +
                "(id integer primary key, path text, longitude float, latitude fkiat, user text)"
       );
        db.execSQL("create table tags (id integer, tagName text, x float, y float, size float)");
        db.execSQL("create table photos_tags (idTag integer, idPhoto integer)");
        db.execSQL("create table user (id integer, userName text)");

        // Alters table
        db.execSQL("alter table photos_tags add primary key (idTag, idPhoto)");
        db.execSQL("alter table photos_tags add foreign key(idTag) references (tag)");
        db.execSQL("alter table photos_tags add foreign key(idPhoto references (photo)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new RuntimeException("Not implemented");
    }

    public boolean insertPhoto (Photo p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("path", p.getUri().toString());
        contentValues.put("longitude", p.getLocation().getLongitude());
        contentValues.put("latitude", p.getLocation().getLatitude());
        contentValues.put("user", p.getUser().getUserName());

        p.setId(db.insert("photos", null, contentValues));

        insertTagsReferences(p, p.getTags());

        return true;
    }

    private void insertTagsReferences(Photo p, Set<Tag> tags) {
        SQLiteDatabase db = this.getWritableDatabase();

        for(Tag t : tags) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("idTag", t.getId());
            contentValues.put("idPhoto", p.getId());
            db.insert("photos_tags", null, contentValues);
        }
    }

    public Cursor getData(int id){
/*        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;*/
        return null;
    }

    public int numberOfPhoto(){
        SQLiteDatabase db = this.getReadableDatabase();

        return (int) DatabaseUtils.queryNumEntries(db, "photos");
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

    public void insertTag(Tag t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tagName", t.getTagName());
        contentValues.put("x", t.getZone().getPosition().first);
        contentValues.put("y", t.getZone().getPosition().second);
        contentValues.put("size", t.getZone().getSize());
        t.setId(db.insert("tags", null, contentValues));
    }

    public Object getTag(String tagName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select * from tags where tagName='"+tagName+"'", null );
    }

    public void createUser(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", u.getUserName());
        u.setId(db.insert("user", null, contentValues));
    }
}