package com.m2dl.maf.makeafocal;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.m2dl.maf.makeafocal.adapter.TagsArrayAdapter;
import com.m2dl.maf.makeafocal.controller.OnImageTouchListener;
import com.m2dl.maf.makeafocal.model.Photo;
import com.m2dl.maf.makeafocal.model.Tag;
import com.m2dl.maf.makeafocal.model.Zone;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by florent on 17/01/16.
 */
public class TakePhotoActivity extends Activity {
    /** URI of the image file. */
    private Uri imageUri;
    /** Container for the photo taken. */
    private ImageView imageView;
    /** List of tags. */
    private TagsArrayAdapter tagsAdapter;
    /** View containing the list of existing tags. */
    private ListView listView;
    private TextView textViewTags;
    private Photo photo;
    private Tag tmpTag;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public enum State {
        INIT, SELECTIONNING, TAGGING
    }

    private State state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags_to_image);

        state = State.INIT;

        photo = new Photo();
        textViewTags = (TextView) findViewById(R.id.tv_tags_added);

        imageView = (ImageView) findViewById(R.id.iv_photo_to_tag);
        OnImageTouchListener imageViewListener = new OnImageTouchListener(this, photo, tmpTag);
        imageView.setOnTouchListener(imageViewListener);
        imageView.setOnLongClickListener(imageViewListener);

//        listView = (ListView) findViewById(R.id.list_tags_added);
//        tagsAdapter = new TagsArrayAdapter(this, new ArrayList<>(photo.getTags()));
//        listView.setAdapter(tagsAdapter);

        takePhoto();
    }



    /**
     * Take a photo in the <i>view</i>.
     */
    public void takePhoto() {
        //Création d'un intent
        createDirIfNotExists();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        //Récupération de l'heure pour le nom fichier
        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);

        //Création du fichier image
        File filePhoto = new File(
            Environment.getExternalStorageDirectory() + "/" + R.string.maf_repository,
            String.valueOf(seconds)+ ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filePhoto));
        imageUri = Uri.fromFile(filePhoto);

        //On lance l'intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    //On a reçu le résultat d'une activité
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        //Si l'activité était une prise de photo
        case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = imageUri;
                getContentResolver().notifyChange(selectedImage, null);

                ContentResolver cr = getContentResolver();
                try {
                    photo.setImage(android.provider.MediaStore.Images.Media
                            .getBitmap(cr, selectedImage));

                    imageView.setImageBitmap(photo.getImage());
                } catch (Exception e) {
                    Toast.makeText(
                            this,
                            R.string.error_take_photo,
                            Toast.LENGTH_SHORT)
                            .show();
                    Log.e("Camera", e.toString());
                }
            }
        }
    }

    public void onAddTagButtonClick(final View view) {
        TextView tv = (TextView) findViewById(R.id.tv_add_new_tag);
        String txt = tv.getText().toString();
        if (!txt.isEmpty()) {
            for (String tag : txt.split(" ")) {
                photo.addTag(new Tag(tag, null));
                tagsAdapter.notifyDataSetChanged();
            }
        }
    }

    public boolean  createDirIfNotExists() {
        File folder = new File(
            Environment.getExternalStorageDirectory() + "/" + R.string.maf_repository);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }

        return success;
    }

    public TagsArrayAdapter getTagsAdapter() {
        return tagsAdapter;
    }

    public TextView getTextViewTags() {
        return textViewTags;
    }
}
