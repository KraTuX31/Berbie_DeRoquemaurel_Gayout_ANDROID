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
                "create table photos " +
                "(id integer primary key, path text, longitude float, latitude float, user id)"
       );
        db.execSQL("create table tags (id integer, tagName text, x float, y float, size float)");
        db.execSQL("create table photos_tags (idTag integer, idPhoto integer)");
        db.execSQL("create table user (id integer, userName text)");

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

    public void insertTag(Tag t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tagName", t.getTagName());
        contentValues.put("x", t.getZone().getX());
        contentValues.put("y", t.getZone().getY());
        contentValues.put("size", t.getZone().getSize());
        t.setId(db.insert("tags", null, contentValues));
    }

    public Cursor getTag(String tagName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select * from tags where tagName='"+tagName+"'", null );
    }

    public Cursor getPhoto(final int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select path, longitude, latitude, user.id as u from photo,user " +
                "where user=user.id and id='"+id+"'", null);
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

        Cursor res =  db.rawQuery( "select * from photos", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            String path = res.getString(res.getColumnIndex("path"));
            User u = new User(context, res.getInt(res.getColumnIndex("user")));
            Photo p = new Photo(path, new Pair<>(0d,0d), u);
            p.setId(res.getInt(res.getColumnIndex("id")));
            Cursor res2 =  db.rawQuery( "select * from tags where id_photo="+p.getId(), null );
            res2.moveToFirst();
            while(!res2.isAfterLast()) {
                Tag t = new Tag(context, res.getString(res.getColumnIndex("tagName")),
                        new Zone(0, 0, 0));
                t.setId(res.getInt(res.getColumnIndex("id")));
                p.addTag(t);
                res2.moveToNext();
            }
            ret.add(p);

            res.moveToNext();
        }
        return ret;
    }
}