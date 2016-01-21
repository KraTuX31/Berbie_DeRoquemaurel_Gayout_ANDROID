package com.m2dl.maf.makeafocal.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public class Photo extends Model {
    private Bitmap image; // The Bitmap image of the photo
    private Location location; // The position of the photo
    private Set<Tag> tags; // Tags of the photo
    private User user; // THe user which take the photo
    private Uri uri;

    /**
     * Create an empty photo
     * TODO : search usefull constructors
     */
    public Photo(Context c) {
        super(c);
        this.tags = new HashSet<>();
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

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
