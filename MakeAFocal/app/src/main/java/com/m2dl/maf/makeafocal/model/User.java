package com.m2dl.maf.makeafocal.model;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public class User implements IModel {
    private String userName;

    /**
     * Create a user
     * @param username The name of the user
     */
    public User(String username) {
        this.userName = username;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
