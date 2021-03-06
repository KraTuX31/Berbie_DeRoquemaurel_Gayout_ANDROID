package com.m2dl.maf.makeafocal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.m2dl.maf.makeafocal.controller.GPSLocationListener;
import com.m2dl.maf.makeafocal.controller.OnSearchQueryListener;
import com.m2dl.maf.makeafocal.database.Database;
import com.m2dl.maf.makeafocal.model.Photo;
import com.m2dl.maf.makeafocal.model.PhotoList;
import com.m2dl.maf.makeafocal.model.Session;
import com.m2dl.maf.makeafocal.model.User;
import com.m2dl.maf.makeafocal.util.MarkersManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback {

    private GoogleMap map;
    public static Context context;
    private GPSLocationListener gps;
    MarkersManager markersManager;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!Database.instance(this).exists()) {
            Intent intent = new Intent(MainActivity.this, ModificationPseudoActivity.class);
            startActivity(intent);
        } else {
            Session.instance().setCurrentUser(Database.instance(this).getLastUser());
        }
        setContentView(R.layout.activity_main);
        context = getBaseContext();

        markersManager = new MarkersManager(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =
                (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Session.instance().addAllPhotoToMap(new PhotoList(this));
    }

    @Override
    public void onResume(){
        super.onResume();
        if(Session.instance().getPhotoToAddToMap() != null && map != null){
            List<Photo> photoToAdd = Session.instance().getPhotoToAddToMap();
            for(Photo p : photoToAdd) {
                Pair<Float, Float> location = p.getLocation();
                // Resize image
                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(p.getImage().createScaledBitmap(p.getImage(), 120, 120, false));
                //Marker
                markersManager.addPhotoMarker(
                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(location.first, location.second))
                                .title(p.getTags().toString())
                                .snippet(p.getUser().getUserName())
                                .icon(icon))
                );
                ArrayList<Photo> newListe = Session.instance().getListePhotoAdded();
                newListe.add(p);
                Session.instance().setListePhotoAdded(newListe);
            }
            Session.instance().addAllPhotoToMap(photoToAdd);
            Session.instance().cleanPhotosToAdd();
        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.action_filter);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(
                new OnSearchQueryListener(markersManager));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.nav_filter) {
//            return true;
//        } else if (id == R.id.nav_add_location) {
//            return true;
//        } else if (id == R.id.nav_search) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user) {
            // Handle the camera action
            Intent intent = new Intent(this,ModificationPseudoActivity.class);
            startActivity(intent);
//        } else if (id == R.id.nav_filter) {

//        } else if (id == R.id.nav_add_location) {
//
//        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Treatment when the user click on Floating Photo Button of the <i>view</i>.
     * @param view Current view.
     */
    public void onPhotoButtonClick(final View view) {
        Intent intent = new Intent(MainActivity.this, TakePhotoActivity.class);
        startActivity(intent);
    }

    /**
     * Treatment when the user click on Floating "My Location" Button of the
     * <i>view</i>.
     * @param view Current view.
     */
    public void onMyLocationButtonClick(final View view) {
        gps = new GPSLocationListener(MainActivity.this);

        // Check if GPS enabled
        if(gps.canGetLocation()) {

            LatLng myLocation = new LatLng(gps.getLatitude(), gps.getLongitude());
            // \n is for new line

            map.moveCamera(
                    CameraUpdateFactory.newLatLng(myLocation));
            map.animateCamera(CameraUpdateFactory.zoomTo(17F));
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.addMarker(
                    new MarkerOptions().position(myLocation)
                            .title("Vous êtes ici"));
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }
    }


    //--------------------------------------------------------------------------
    // Get current location
    //--------------------------------------------------------------------------

    /**
     * Init map <i>googleMap</i> when Activity starts.
     * @param googleMap Map to init.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        markersManager.init(map);
        map.setMyLocationEnabled(true);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.56053780000001, 1.468691900000067), 15f));
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                for(Photo photo : Session.instance().getListePhotoAdded()) {

                    if(Math.abs(Math.abs(photo.getLocation().first) - latLng.latitude) < 0.0003 && Math.abs(photo.getLocation().second - latLng.longitude) < 0.0003){

                        Session.instance().setPhotoToVisualise(photo);
                        Intent intent = new Intent(MainActivity.this, VisualisationMarkerActivity.class);
                        startActivity(intent);
                        return;
                    }

                }
            }
        });
        onResume();
    }


}
