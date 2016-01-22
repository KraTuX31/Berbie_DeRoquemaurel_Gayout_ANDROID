package com.m2dl.maf.makeafocal.model;

import android.content.Context;

import com.m2dl.maf.makeafocal.database.Database;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public class PhotoList extends ArrayList<Photo> {
    private Database db;

    public PhotoList(Context c) {
        db = Database.instance(c);

        for(Photo p : db.getAllPhotos()) {
            add(p);
        }
    }

    public Set<Tag> getAllTags() {
        Set<Tag> ret = new HashSet<>();

        for(Photo p : this) {
            ret.addAll(p.getTags());
        }

        return ret;
    }
}
