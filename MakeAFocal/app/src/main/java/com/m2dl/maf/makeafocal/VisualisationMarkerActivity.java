package com.m2dl.maf.makeafocal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.m2dl.maf.makeafocal.model.Photo;
import com.m2dl.maf.makeafocal.model.Session;

import org.w3c.dom.Text;

public class VisualisationMarkerActivity extends AppCompatActivity {

    private TextView author;
    private TextView listeTags;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualisation_marker);

        author = (TextView) findViewById(R.id.visualiseAutheur);
        listeTags = (TextView) findViewById(R.id.visualiseTags);
        image = (ImageView) findViewById(R.id.visualiseImage);
        //On récupère la photo a afficher
        Photo photoToDisplay = Session.instance().getPhotoToVisualise();
        //On affiche les infos
        author.setText(photoToDisplay.getUser().getUserName());
        if(!photoToDisplay.getTags().toString().equals("[]")){
            listeTags.setText(photoToDisplay.getTags().toString());
        } else {
            listeTags.setText("Aucun tags");
        }
        image.setImageBitmap(photoToDisplay.getImage());


    }
}
