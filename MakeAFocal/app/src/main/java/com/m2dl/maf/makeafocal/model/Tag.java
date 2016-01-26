package com.m2dl.maf.makeafocal.model;

import android.content.Context;
import android.database.Cursor;

import com.m2dl.maf.makeafocal.database.Database;

/**
 * Created by aroquemaurel on 21/01/16.
 */
public class Tag extends Model {
    private String tagName;
    private Zone zone;

    public Tag(Zone zone) {
        super();
        this.zone = zone;
        tagName = "";
    }

    /**
     * Create a tag
     * @param tagName The name of the tag
     * @param zone The position of the tag in the picture
     */
    public Tag(final String tagName, final Zone zone) {
        super();
        this.tagName = tagName;
        this.zone = zone;
    }

       public Tag(Context c, final int id) {
        super(c, id);
        Tag t = Database.instance(c).getTag(id);
        setId(t.getId());
       setTagName(t.getTagName());
       setZone(t.getZone());
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

    @Override
    public void create(Context c) {
        Cursor cur = (Cursor) getDb(c).getTag(tagName);
        cur.moveToFirst();

        if(cur.getCount() == 0) {
            getDb(c).insertTag(this);
        } else {
            id = cur.getInt(cur.getColumnIndex("id"));
        }
    }

    @Override
    public void delete(Context c) {
	}

    @Override
    public void update(Context c) {

    }

}
