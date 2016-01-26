package com.m2dl.maf.makeafocal.model;

import android.content.Context;

import com.m2dl.maf.makeafocal.database.Database;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public class User extends Model {
    private String userName;

    /**
     * Create a user
     * @param username The name of the user
     */
    public User(String username) {
        super();
        this.userName = username;
    }

    public User(String username, final int id) {
        super();
        this.userName = username;
        this.id = id;
    }

    public User(Context c, final int id) {
        super(c, id);
        User u = getDb(c).getUser(id);
        setUserName(u.getUserName());
        setId(u.getId());;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void create(Context c) {
        getDb(c).createUser(this);
    }

    @Override
    public void delete(Context c) {

    }

    @Override
    public void update(Context c) {
        throw new RuntimeException("Not implemeted");
    }

    public boolean pseudoExists(Context c) {
        return getDb(c).pseudoExists(this.userName);
    }
}
