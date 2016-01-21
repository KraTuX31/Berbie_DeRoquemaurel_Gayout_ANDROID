package com.m2dl.maf.makeafocal.model;

import android.content.Context;

import com.m2dl.maf.makeafocal.database.Database;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public abstract class Model {
    protected Database db;
    protected long id;

    public Model(Context c) {
        db = Database.instance(c);
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public abstract void create();
    public abstract void delete();
}
