package com.m2dl.maf.makeafocal.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.m2dl.maf.makeafocal.TakePhotoActivity;
import com.m2dl.maf.makeafocal.model.Photo;
import com.m2dl.maf.makeafocal.model.Tag;
import com.m2dl.maf.makeafocal.model.Zone;

/**
 * Created by florent on 22/01/16.
 */
public class OnImageTouchListener implements View.OnTouchListener, View.OnLongClickListener {

    private Photo photo;
    private Tag tag;
    private TakePhotoActivity context;
    private boolean isLongClick;
    private int x;
    private int y;

    public OnImageTouchListener(TakePhotoActivity activity, final Photo photo, final Tag tmpTag) {
        this.photo = photo;
        this.tag = tmpTag;
        context = activity;
        isLongClick = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isLongClick) {
            isLongClick = false;
        } else {
            x = (int) event.getX();
            y = (int) event.getY();

            Log.d("IMG", "touch: (" + x + "," + y + ")");
        }


        return false;
    }

    private void displayPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Title");

// Set up the input
        final AutoCompleteTextView input = new AutoCompleteTextView(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String txt = input.getText().toString();
                if (!txt.isEmpty()) {
                    for (String t: txt.split(" ")) {
                        tag.setTagName(t);
                        photo.addTag(tag);
                        context.getTextViewTags().append(tag.toString() + ",");
                    }
                    String tvText = context.getTextViewTags().getText().toString();
                    if (txt.endsWith(",")) {
                        context.getTextViewTags().setText(tvText.substring(0, tvText.length()-2));
                    }
//                    context.getTagsAdapter().notifyDataSetChanged();
                }

            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public boolean onLongClick(View v) {
        if (photo.getImage() == null) {
            return false;
        }
        Vibrator vibrator = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        vibrator.vibrate(100);
        Log.d("IMG", "longClick: (" + x + "," + y + ")");
        tag = new Tag(new Zone(x, y, 250));
        displayPopup();

        return false;
    }
}
