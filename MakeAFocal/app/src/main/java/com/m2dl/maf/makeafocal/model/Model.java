package com.m2dl.maf.makeafocal.model;

import android.content.Context;

import com.m2dl.maf.makeafocal.database.Database;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public abstract class Model {
    protected long id;

    public Model() {
    }

    public Model(Context c, final int id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public abstract void create(Context c);
    public abstract void delete(Context c);
    public abstract void update(Context c);

    public Database getDb(Context c) {
        return Database.instance(c);
    }
}
