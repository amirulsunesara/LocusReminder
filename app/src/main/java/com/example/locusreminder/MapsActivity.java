package com.example.locusreminder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.locusreminder.display.MainActivity;
import com.example.locusreminder.display.PlaceAutocompleteAdapter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15F;
    private AutoCompleteTextView mSearchText;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    List<String > addresses;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds( new LatLng(-40,-168),new LatLng(71,136));
    Button maps_done_button;
    private String latitued_seleted="";
    private String longituted_selected="";
    private  String selected_place_name="";
    private String title,note_text;
    private AutoCompleteTextView google_search_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent= getIntent();
        title= intent.getStringExtra("Title");
        note_text= intent.getStringExtra("NoteText");
        addresses =new ArrayList<>();
        mSearchText = (AutoCompleteTextView)findViewById(R.id.google_search_bar);
        maps_done_button =(Button)findViewById(R.id.maps_done_button);
        maps_done_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                selected_place_name=mSearchText.getText().toString();
               LatLng latLng= getLocationFromAddress(getApplicationContext(),selected_place_name);
               if(latLng!=null){
                   latitued_seleted= Double.toString(latLng.latitude) ;
                   longituted_selected=Double.toString(latLng.longitude);
               }

                Intent intent = new Intent( MapsActivity.this, MainActivity.class);
                intent.putExtra("Latitude",latitued_seleted);
                intent.putExtra("Longitude",longituted_selected);
                intent.putExtra("selected_pace",selected_place_name);
                intent.putExtra("title",title);
                intent.putExtra("NoteText",note_text);
                startActivity(intent);
            }
        });
        getLocationPermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
   /*     mSearchText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                // TODO Auto-generated method stub

                geoLocate();
              *//*  textExecutive.showDropDown();
                textExecutive.requestFocus();*//*
                return false;
            }
        });*/
       /* mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == event.ACTION_DOWN
                        || event.getAction() == event.KEYCODE_ENTER) {
                    geoLocate();
                }
                return false;
            }
        });*/

        mSearchText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            /*    ma.getFilter().filter(s);
                ma.notifyDataSetChanged();*/
                geoLocate(String.valueOf(s));

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void init(){
        //mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this,
         //       Places.getGeoDataClient(this,null),LAT_LNG_BOUNDS,null);
       // mSearchText.setAdapter(mPlaceAutocompleteAdapter);

    }
    private void geoLocate(String searchString){

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try{
        list =geocoder.getFromLocationName(searchString,1);
        }catch (IOException e){
        }
        if(list.size() >0){
            if(addresses.size()>0){
                addresses.clear();
            }
            for (Address ad:list)
            {
               String adds= ad.getLocality() +", " + ad.getAdminArea() + ", " + ad.getCountryName();
                addresses.add(adds);
            }

           /* Log.d("maps","geo location found:"+ address.toString());
         */
            google_search_bar=(AutoCompleteTextView) findViewById(R.id.google_search_bar);

          /*  AutoCompleteTextView storeTV =
                    (AutoCompleteTextView)((Activity)context).findViewById(R.id.store);*/

            ArrayAdapter<String> adapteo = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_dropdown_item_1line, addresses);
            mSearchText.setAdapter(adapteo);
         //   moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM);
        }
    }
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionsGranted) {
                Task<Location> location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            latitued_seleted= Double.toString(currentLocation.getLatitude()) ;
                            longituted_selected=Double.toString(currentLocation.getLongitude());
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                        }
                    }
                });
            }
        } catch (SecurityException e) {

        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        MarkerOptions options = new MarkerOptions().position(latLng).title("My Location");
        mMap.addMarker(options);
        hideSoftkeyboard();
    }

    private void intiMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);

    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                intiMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionsGranted = true;
                    intiMap();
                }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mLocationPermissionsGranted) {
            getDeviceLocation();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        init();
        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(44.6488, -63.5752);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
    private void hideSoftkeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


}
