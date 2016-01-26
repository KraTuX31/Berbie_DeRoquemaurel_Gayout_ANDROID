package com.m2dl.maf.makeafocal.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public class Photo extends Model {
    /** The Bitmap image of the photo. */
    private Bitmap image;
    /** The position of the photo. */
    private Pair<Float, Float> location;
    /** Tags of the photo. */
    private Set<Tag> tags;
    /** The user which take the photo. */
    private User user;
    /** Path to photo. */
    private String path;
    /** Date when photo has beed captured. */
    private String date;


    /**
     * Create an empty photo
     * TODO : search usefull constructors
     */
    public Photo(String path, Pair<Float, Float> location, User u) {
        super();
        this.tags = new HashSet<>();
        this.path = path;
        File f = new File(path);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        image = BitmapFactory.decodeFile(f.getAbsolutePath(), bmOptions);
        image = Bitmap.createScaledBitmap(image, 120, 130, true);

        this.location = location;
        this.user = u;
        tags = new HashSet<>();
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
            //t.create(c);
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

    @Override
    public void update(Context c) {

    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLocation(Pair<Float, Float> location) {
        this.location = location;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Pair<Float, Float> getLocation() {
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

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Photo{" +
//                "image=" + image +
//                ", location=" + location +
//                ", tags=" + tags +
//                ", user=" + user +
                ", path='" + path + '\'' +
//                ", date='" + date + '\'' +
                '}';
    }
}
