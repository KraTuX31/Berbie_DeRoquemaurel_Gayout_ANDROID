package com.m2dl.maf.makeafocal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import com.m2dl.maf.makeafocal.model.Photo;
import com.m2dl.maf.makeafocal.model.Tag;
import com.m2dl.maf.makeafocal.model.User;
import com.m2dl.maf.makeafocal.model.Zone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Database extends SQLiteOpenHelper {
    private static Map<Context, Database> instances;
    private static final String DATABASE_NAME = "makeafocal.db";
    private static Context context;

    public static Database instance(Context c) {
        if (instances == null) {
            instances = new HashMap<>();
        }
        if (instances.get(c) == null) {
            instances.put(c, new Database(c));
        }
        context = c;
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
                "create table photo " +
                "(id integer primary key autoincrement not null, path text, longitude float, latitude float, user integer)"
       );
        db.execSQL("create table tag (id integer primary key autoincrement not null, tagName text, x float, y float, size float)");
        db.execSQL("create table photo_tag (idTag integer, idPhoto integer)");
        db.execSQL("create table user (id integer primary key autoincrement not null, userName text)");

        // Alters table
     /*   db.execSQL("alter table photos_tags add constraint pkidtagphoto primary key (idTag, idPhoto)");
        db.execSQL("alter table photos_tags add constraint fkidtag foreign key(idTag) references (tag)");
        db.execSQL("alter table photos_tags add constraint fkphoto foreign key(idPhoto references (photo)");
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new RuntimeException("Not implemented");
    }

    public boolean insertPhoto (Photo p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("path", p.getPath().toString());
        contentValues.put("longitude", p.getLocation().first);
        contentValues.put("latitude", p.getLocation().second);
        contentValues.put("user", p.getUser().getId());

        p.setId(db.insert("photo", null, contentValues));

        insertTagsReferences(p, p.getTags());

        return true;
    }

    private void insertTagsReferences(Photo p, Set<Tag> tags) {
        SQLiteDatabase db = this.getWritableDatabase();

        for(Tag t : tags) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("idTag", t.getId());
            contentValues.put("idPhoto", p.getId());
            db.insert("photo_tag", null, contentValues);
        }
    }

    public int numberOfPhoto(){
        SQLiteDatabase db = this.getReadableDatabase();

        return (int) DatabaseUtils.queryNumEntries(db, "photo");
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

    public void insertTag(Tag t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tagName", t.getTagName());
        contentValues.put("x", t.getZone().getX());
        contentValues.put("y", t.getZone().getY());
        contentValues.put("size", t.getZone().getSize());
        t.setId(db.insert("tag", null, contentValues));
    }

    public Cursor getTag(String tagName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select * from tag where tagName='"+tagName+"'", null );
    }

    public Tag getTag(final int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery( "select * from tag " +
                "where id='"+id+"'", null);
        cur.moveToFirst();
        Tag t = new Tag(cur.getString(cur.getColumnIndex("tagName")), new Zone(0, 0, 0));
        t.setId(id);
        return t;
    }

    public Photo getPhoto(final long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery( "select * from photo " +
                "where id='"+id+"'", null);
        cur.moveToFirst();
        return new Photo(cur.getString(cur.getColumnIndex("path")),
                        new Pair<>(cur.getFloat(cur.getColumnIndex("longitude")), cur.getFloat(cur.getColumnIndex("latitude"))
                                ),
                        new User(context, cur.getInt(cur.getColumnIndex("user"))));
    }

    public void createUser(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", u.getUserName());
        u.setId(db.insert("user", null, contentValues));
    }

    public List<Photo> getAllPhotos() {
        List<Photo> ret = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select * from photo", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            String path = res.getString(res.getColumnIndex("path"));
            User u = new User(context, res.getInt(res.getColumnIndex("user")));
            Photo p = new Photo(path, new Pair<>(res.getFloat(res.getColumnIndex("longitude")), res.getFloat(res.getColumnIndex("latitude"))), u);
            p.setId(res.getInt(res.getColumnIndex("id")));
            SQLiteDatabase db2 = this.getReadableDatabase();

            Cursor res2 =  db2.rawQuery( "select * from photo_tag where idPhoto="+p.getId(), null );
            res2.moveToFirst();
            while(!res2.isAfterLast()) {
                p.addTag(new Tag(context, res2.getInt(res2.getColumnIndex("idTag"))));
                res2.moveToNext();
            }
            ret.add(p);

            res.moveToNext();
        }
        return ret;
    }

    public User getUser(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery( "select * from user " +
                "where id="+id, null);
        cur.moveToFirst();
        return new User(cur.getString(cur.getColumnIndex("userName")));
    }

    public User getLastUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery( "select * from user order by id desc", null);
        cur.moveToFirst();
        return new User(cur.getString(cur.getColumnIndex("userName")), cur.getInt(cur.getColumnIndex("id")));
    }

    public boolean pseudoExists(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery( "select * from User where userName='"+userName+"'", null );
        return (c.getCount() != 0);
    }

    public boolean exists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery( "select * from User", null );
        return (c.getCount() > 0);
    }
}