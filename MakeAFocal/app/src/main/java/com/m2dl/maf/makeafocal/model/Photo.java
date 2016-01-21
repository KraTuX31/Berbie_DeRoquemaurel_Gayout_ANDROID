package com.m2dl.maf.makeafocal.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;

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

    }

    @Override
    public void delete() {
        // TODO
    }
}
