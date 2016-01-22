package com.m2dl.maf.makeafocal;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.m2dl.maf.makeafocal.adapter.TagsArrayAdapter;
import com.m2dl.maf.makeafocal.model.Tag;

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
    private List<Tag> tags = new ArrayList<>();
    private TagsArrayAdapter tagsAdapter;
    /** View containing the list of existing tags. */
    private ListView listView;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags_to_image);
        imageView = (ImageView) findViewById(R.id.iv_photo_to_tag);
        listView = (ListView) findViewById(R.id.list_tags_added);

        tagsAdapter = new TagsArrayAdapter(this, tags);
        listView.setAdapter(tagsAdapter);

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
        File photo = new File(
            Environment.getExternalStorageDirectory() + "/" + R.string.maf_repository,
            String.valueOf(seconds)+ ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);

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
                Bitmap bitmap;
                try {
                    bitmap = android.provider.MediaStore.Images.Media
                            .getBitmap(cr, selectedImage);

                    imageView.setImageBitmap(bitmap);
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
                tags.add(new Tag(null, tag, null));
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
}
