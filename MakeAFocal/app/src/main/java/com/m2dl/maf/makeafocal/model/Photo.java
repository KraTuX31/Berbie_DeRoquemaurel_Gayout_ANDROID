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
    public Photo(String path, Pair<Double, Double> location, User u) {
        super();
        this.tags = new HashSet<>();
        this.path = path;
        this.location = location;
        this.user = u;
        tags = new HashSet<>();
        image = null;
    }

    public Photo(Context c, final int id) {
        super(c, id);
        Photo p = getDb(c).getPhoto(id);
        user = p.getUser();
        path = p.getPath();
        location = p.getLocation();
    }


    public Photo (final Bitmap bitmap) {
        super();
        image = bitmap;
        tags = new HashSet<>();
    }

    public Photo() {
        super();
        tags = new HashSet<>();
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void addTag(Tag...tags) {
        for (Tag t : tags) {
            this.tags.add(t);
        }
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    @Override
    public void create(Context c) {
        for(Tag t : tags) {
            t.create(c);
        }
        getDb(c).insertPhoto(this);
    }

    public Bitmap getImage() {
        return image;
    }

    @Override
    public void delete(Context c) {
        // TODO
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLocation(Pair<Double, Double> location) {
        this.location = location;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Pair<Double, Double> getLocation() {
        return location;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
