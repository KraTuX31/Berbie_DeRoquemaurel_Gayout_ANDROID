package com.m2dl.maf.makeafocal.model;

import android.content.Context;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public class User extends Model {
    private String userName;

    /**
     * Create a user
     * @param username The name of the user
     */
    public User(Context c, String username) {
        super(c);
        this.userName = username;
    }

    public User(Context c, final int id) {
        super(c, id);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void create() {
        db.createUser(this);
    }

    @Override
    public void delete() {

    }
}
