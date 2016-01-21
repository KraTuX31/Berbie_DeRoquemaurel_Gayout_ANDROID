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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

/**
 * Created by florent on 17/01/16.
 */
public class TakePhotoActivity extends Activity {
    private Uri imageUri;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private ImageView image;
    private Bitmap bitmapImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags_to_image);
        image = (ImageView) findViewById(R.id.iv_photo_to_tag);
        takePhoto(image);

        Bitmap bitmapIB
        image.setImageBitmap();
        Log.d("Test", "TEST");

    }

    /**
     * Take a photo in the <i>view</i>.
     * @param view View to take a photo.
     */
    public void takePhoto(View view) {
        //Création d'un intent
        createDirIfNotExists();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        //Récupération de l'heure pour le nom fichier
        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);



        //Création du fichier image
        File photo = new File(
                Environment.getExternalStorageDirectory()
                    + "/" + R.string.maf_repository,
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
        Bitmap bitmap = null;
        Bundle extras = data.getExtras();
        switch (requestCode) {
            //Si l'activité était une prise de photo
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);

                    //ImageView imageView = (ImageView) findViewById(R.id.ImageView);
                    ContentResolver cr = getContentResolver();

                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);
                        Bitmap bmp = (Bitmap) extras.get("data");

//                        image.setImageBitmap(bitmap);
                        //Affichage de l'infobulle
//                        Toast.makeText(
//                                this,
//                                selectedImage.toString(),
//                                Toast.LENGTH_LONG)
//                                .show();



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


    public void  createDirIfNotExists() {
        File folder = new File(
                Environment.getExternalStorageDirectory() + "/" + R.string.maf_repository);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
            Toast.makeText(
                    this, R.string.repository_created, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(
                    this,R.string.repository_fail_created, Toast.LENGTH_SHORT).show();
        }
    }
}
