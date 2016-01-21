package com.m2dl.maf.makeafocal.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.m2dl.maf.makeafocal.R;
import com.m2dl.maf.makeafocal.TakePhotoActivity;
import com.m2dl.maf.makeafocal.model.Tag;

import java.sql.Array;
import java.util.List;

/**
 * Created by florent on 21/01/16.
 */
public class TagsArrayAdapter extends ArrayAdapter<Tag> {

    private TextView textView;

    public TagsArrayAdapter(final Context context, final List<Tag> tags) {
        super(context, android.R.layout.simple_expandable_list_item_1, tags);
    }

    public View geView(int position, View convertView, ViewGroup parent) {
        LinearLayout view;
        Tag tag = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.tag_item_list, parent, false);
        } else {
            view = (LinearLayout) convertView;
        }

        textView = (TextView) convertView.findViewById(R.id.tag_item);
        textView.setText(tag.toString());

        return convertView;
    }
}
