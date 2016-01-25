package com.m2dl.maf.makeafocal.model;

import java.util.ArrayList;

/**
 * Created by Marc on 22/01/2016.
 */
public class Session {
    private static Session instance;
    private User currentUser;
    private ArrayList<Photo> listePhotoAdded = new ArrayList<Photo>();
    private Photo photoToAddToMap;
    private Photo photoToVisualise;

    public Photo getPhotoToVisualise() {
        return photoToVisualise;
    }

    public void setPhotoToVisualise(Photo photoToVisualise) {
        this.photoToVisualise = photoToVisualise;
    }



    public ArrayList<Photo> getListePhotoAdded() {
        return listePhotoAdded;
    }

    public void setListePhotoAdded(ArrayList<Photo> listePhotoAdded) {
        this.listePhotoAdded = listePhotoAdded;
    }

    public Photo getPhotoToAddToMap() {
        return photoToAddToMap;
    }

    public void setPhotoToAddToMap(Photo photoToAddToMap) {
        this.photoToAddToMap = photoToAddToMap;
    }



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
