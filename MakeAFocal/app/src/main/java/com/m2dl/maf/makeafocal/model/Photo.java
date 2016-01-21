package com.m2dl.maf.makeafocal.model;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.util.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public class Photo extends Model {
    private Bitmap image; // The Bitmap image of the photo
    private Pair<Double, Double> location; // The position of the photo
    private Set<Tag> tags; // Tags of the photo
    private User user; // THe user which take the photo
    private String path;

    /**
     * Create an empty photo
     * TODO : search usefull constructors
     */
    public Photo(Context c, String path, Pair<Double, Double> location, User u) {
        super(c);
        this.tags = new HashSet<>();
        this.path = path;
        this.location = location;
        this.user = u;
    }

    public Photo(Context c, final int id) {
        super(c, id);
        Cursor cur = db.getPhoto(id);
        user = new User(c, cur.getInt(cur.getColumnIndex("u")));
        path = cur.getString(cur.getColumnIndex("path"));
        location =
                new Pair<>(cur.getDouble(cur.getColumnIndex("latitude")),
                            cur.getDouble(cur.getColumnIndex("longitude")));
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    @Override
    public void create() {
        for(Tag t : tags) {
            t.create();
        }
        db.insertPhoto(this);
    }

    @Override
    public void delete() {
        // TODO
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Pair<Double, Double> getLocation() {
        return location;
    }

    public void setLocation(Pair<Double, Double> location) {
        this.location = location;
    }
}
