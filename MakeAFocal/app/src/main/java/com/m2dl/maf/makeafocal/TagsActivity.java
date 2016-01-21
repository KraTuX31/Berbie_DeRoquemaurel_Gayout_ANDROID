package com.m2dl.maf.makeafocal;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by florent on 21/01/16.
 */
public class TagsActivity extends Activity {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private Uri imageUri;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags_to_image);
        imageView = (ImageView) findViewById(R.id.iv_photo_to_tag);

        imageView.setImageBitmap(
                (Bitmap)getIntent().getParcelableExtra("data"));

    }



}
