package com.m2dl.maf.makeafocal;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.m2dl.maf.makeafocal.controller.GPSLocationListener;
import com.m2dl.maf.makeafocal.controller.OnImageTouchListener;
import com.m2dl.maf.makeafocal.model.Photo;
import com.m2dl.maf.makeafocal.model.Session;
import com.m2dl.maf.makeafocal.model.User;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by florent on 17/01/16.
 */
public class TakePhotoActivity extends Activity {
    /** URI of the image file. */
    private Uri imageUri;
    /** Container for the photo taken. */
    private ImageView imageView;
    private TextView textViewTags;
    private Photo photo;
    /** Tools for drawing **/
    private Canvas canvas;
    private Paint paint = new Paint();
    private Bitmap mutableBitmap;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags_to_image);

        photo = new Photo();
        photo.setUser(Session.instance().getCurrentUser()); // On set l'user courant

        textViewTags = (TextView) findViewById(R.id.tv_tags_added);

        imageView = (ImageView) findViewById(R.id.iv_photo_to_tag);
        OnImageTouchListener imageViewListener = new OnImageTouchListener(this);
        imageView.setOnTouchListener(imageViewListener);
        imageView.setOnLongClickListener(imageViewListener);

        takePhoto();
        applyLocationToPhoto();
    }

    /**
     *
     */
    public void takePhoto() {
        // Create intent to take photo
        createDirIfNotExists();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        // Get date of the photo
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        photo.setDate(sdf.format(new Date()));

        // File to save photo
        File filePhoto = new File(
            Environment.getExternalStorageDirectory()
                + "/" + R.string.maf_repository,
            String.valueOf(
                    Calendar.getInstance().get(Calendar.SECOND)) + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filePhoto));
        imageUri = Uri.fromFile(filePhoto);

        photo.setPath(imageUri.getPath());

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void onAcceptButtonClick(View v) {
        // TODO remove Toast: only use to test
        Toast.makeText(this, photo.toString(), Toast.LENGTH_LONG).show();
        //photo.setImage(workingBitmap);
        Session.instance().setPhotoToAddToMap(photo);
        // TODO photo.create(this);
        finish();
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

                    BitmapFactory.Options myOptions = new BitmapFactory.Options();
                    myOptions.inDither = true;
                    myOptions.inScaled = false;
                    myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
                    myOptions.inPurgeable = true;


                    //Mise en place du canevas pour dessiner les tags
                    Bitmap bitmap = photo.getImage();

                    Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
                    mutableBitmap = workingBitmap.copy(workingBitmap.getConfig(), true);
                    paint.setAntiAlias(true);
                    paint.setColor(0x99000000);
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                    canvas = new Canvas(mutableBitmap);
                    imageView.setAdjustViewBounds(true);
                    imageView.setImageBitmap(mutableBitmap);
                    photo.setImage(mutableBitmap);


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

    public boolean  createDirIfNotExists() {
        File folder = new File(
            Environment.getExternalStorageDirectory() + "/" + R.string.maf_repository);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }

        return success;
    }

    public void applyLocationToPhoto() {
        GPSLocationListener gps = new GPSLocationListener(TakePhotoActivity.this);

        // Check if GPS enabled
        if(gps.canGetLocation()) {
            photo.setLocation(new Pair<>(gps.getLatitude(), gps.getLongitude()));
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }
    }

    public Photo getPhoto() {
        return photo;
    }

    public TextView getTextViewTags() {
        return textViewTags;
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public Paint getPaint() {
        return paint;
    }
    public ImageView getImageView() { return imageView;}
}
