package com.m2dl.maf.makeafocal.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Vibrator;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.m2dl.maf.makeafocal.R;
import com.m2dl.maf.makeafocal.TakePhotoActivity;
import com.m2dl.maf.makeafocal.model.Photo;
import com.m2dl.maf.makeafocal.model.Tag;
import com.m2dl.maf.makeafocal.model.Zone;

/**
 * Created by florent on 22/01/16.
 */
public class OnImageTouchListener implements View.OnTouchListener, View.OnLongClickListener {

    private Tag tag;
    private TakePhotoActivity context;
    private boolean isLongClick;
    private int x;
    private int y;
    private int width;
    private int height;

    public OnImageTouchListener(TakePhotoActivity activity) {
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
        width = v.getWidth();
        height = v.getHeight();


        return false;
    }

    private void displayPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.new_tag);

        final AutoCompleteTextView input = new AutoCompleteTextView(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String txt = input.getText().toString();
                if (!txt.isEmpty()) {

                    for (String t: txt.split(" ")) {
                        tag = new Tag(t, new Zone(x, y, 250));
                        context.getPhoto().addTag(tag);
                        context.getTextViewTags().append(tag.toString() + ", ");
                    }
                    String tvText = context.getTextViewTags().getText().toString();
                    if (txt.endsWith(", ")) {
                        context.getTextViewTags().setText(
                                tvText.substring(0, tvText.lastIndexOf(",")));
                    }
                }

                //Produit en croix
                int posx = (x * context.getImageView().getWidth()) / width;
                int posy = (y * context.getImageView().getHeight()) / height;
                //Toast.makeText(context, "posx : "+x+"posy : "+y, Toast.LENGTH_SHORT).show();
                context.getCanvas().drawCircle(posx,posy,200,context.getPaint());

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
        if (context.getPhoto().getImage() == null) {
            return false;
        }
        Vibrator vibrator = (Vibrator)
                this.context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
        displayPopup();

        return false;
    }
}
