package com.m2dl.maf.makeafocal.model;

/**
 * Created by Marc on 22/01/2016.
 */
public class Session {
    private static Session instance;
    private User currentUser;

    public Photo getPhotoToAddToMap() {
        return photoToAddToMap;
    }

    public void setPhotoToAddToMap(Photo photoToAddToMap) {
        this.photoToAddToMap = photoToAddToMap;
    }

    private Photo photoToAddToMap;

    public static Session instance() {
        if(instance == null) {
            instance = new Session();
        }

        return instance;
    }

    private Session() {

    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
