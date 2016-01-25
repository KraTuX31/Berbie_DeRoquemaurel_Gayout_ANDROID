package com.m2dl.maf.makeafocal.controller;

import android.content.Context;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;

/**
 * Created by florent on 25/01/16.
 */
public class UploadListener implements TransferListener {

    private Context c;

    public UploadListener(Context context) {
        c = context;
    }

    @Override
    public void onStateChanged(int i, TransferState transferState) {
        Toast.makeText(
                c,
                "Upload " + i + " " + transferState.name(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressChanged(int i, long l, long l1) {
        Toast.makeText(
                c,
                "Upload " + i + ", " + l + ", li",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(int i, Exception e) {
        Toast.makeText(
                c,
                "Error during upload: " + i + " --> " + e,
                Toast.LENGTH_SHORT).show();
    }
}
