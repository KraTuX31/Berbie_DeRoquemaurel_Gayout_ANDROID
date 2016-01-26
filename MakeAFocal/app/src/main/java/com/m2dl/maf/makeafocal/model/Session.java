package com.m2dl.maf.makeafocal.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 22/01/2016.
 */
public class Session {
    private static Session instance;
    private User currentUser;
    private ArrayList<Photo> listePhotoAdded = new ArrayList<Photo>();
    private List<Photo> photosToAddToMap;
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

    public List<Photo> getPhotoToAddToMap() {
        return photosToAddToMap;
    }

    public void addPhotoToMap(Photo photo) {
        photosToAddToMap.add(photo);
    }

    public void addAllPhotoToMap(List<Photo> photos) {
        photosToAddToMap.addAll(photos);
    }

    public static Session instance() {
        if(instance == null) {
            instance = new Session();
        }

        return instance;
    }

    private Session() {
        photosToAddToMap = new ArrayList<Photo>();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void cleanPhotosToAdd() {
        this.photosToAddToMap.clear();
    }
}
