package com.m2dl.maf.makeafocal.model;

import android.graphics.Bitmap;
import android.location.Location;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public class Photo {
    private Bitmap image; // The Bitmap image of the photo
    private Location location; // The position of the photo
    private Set<Tag> tags; // Tags of the photo
    private User user; // THe user which take the photo

    /**
     * Create an empty photo
     * TODO : search usefull constructors
     */
    public Photo() {
        tags = new HashSet<>();
        image = null;
    }

    public Photo (final Bitmap bitmap) {
        image = bitmap;
        tags = new HashSet<>();
    }

    public void addTag(Tag...tags) {
        for (Tag t : tags) {
            this.tags.add(t);
        }
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
