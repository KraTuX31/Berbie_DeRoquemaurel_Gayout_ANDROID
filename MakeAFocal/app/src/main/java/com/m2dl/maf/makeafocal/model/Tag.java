package com.m2dl.maf.makeafocal.model;

import android.content.Context;
import android.util.Pair;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public class Tag extends Model {
    private String tagName;
    private Zone zone;

    /**
     * Create a tag
     * @param tagName The name of the tag
     * @param position The position of the tag in the picture
     */
    public Tag(Context c, String tagName, Zone zone) {
        super(c);
        this.tagName = tagName;
        this.zone = zone;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public void create() {

    }

    @Override
    public void delete() {

    }
}
