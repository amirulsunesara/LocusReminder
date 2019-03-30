package com.example.locusreminder.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

public class LocationService extends Service {

    private LocationManager locManager;
    private LocationListener locListener;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {

        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent intent = new Intent("locationCoordinates");
                intent.putExtra("longitudeCoordinate",location.getLongitude());
                intent.putExtra("latitudeCoordinate",location.getLatitude());
                sendBroadcast(intent);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        locManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,0, locListener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locManager != null){

            locManager.removeUpdates(locListener);
        }
    }


}
