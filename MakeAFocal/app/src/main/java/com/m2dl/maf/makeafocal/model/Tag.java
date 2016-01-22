package com.m2dl.maf.makeafocal.model;

import android.content.Context;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public class Tag extends Model {
    private String tagName;
    private Zone zone;

    public Tag(Zone zone) {
        super(null);
        this.zone = zone;
        tagName = "";
    }

    public Tag(final String tagName, final Zone zone) {
        super(null);
        this.tagName = tagName;
        this.zone = zone;
    }
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

    public Tag(Context c, final int id) {
        super(c, id);
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void create() {

    }

    @Override
    public void delete() {

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
    public String toString() {
        return "#" + tagName;
    }
}
