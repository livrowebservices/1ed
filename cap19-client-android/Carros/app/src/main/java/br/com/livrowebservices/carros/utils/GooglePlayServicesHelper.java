package br.com.livrowebservices.carros.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.LinkedHashSet;
import java.util.Set;

public class GooglePlayServicesHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {
    public static final String TAG = GooglePlayServicesHelper.class.getSimpleName();
    public static boolean LOG_ON = false;

    /**
     * Velocidade do GPS:
     * https://developers.google.com/android/reference/com/google/android/gms/location/LocationRequest
     */
    public static int LOCATION_GPS_PRIORITY = LocationRequest.PRIORITY_HIGH_ACCURACY;
    public static int LOCATION_GPS_INTERVAL_MILLIS = 60000;//60 seg
    public static int LOCATION_GPS_FASTEST_INTERVAL_MILLIS = 30000;//30seg

    private final GoogleApiClient mGoogleApiClient;
    private Set<LocationListener> locationListeners;
    private boolean gpsOn;

    public GooglePlayServicesHelper(Context context, boolean gpsOn) {

        log("GooglePlayServicesHelper(), gpsOn: " + gpsOn);

        this.gpsOn = gpsOn;
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this);

        if(gpsOn) {
            builder.addApi(LocationServices.API);
        }

        mGoogleApiClient = builder.build();

    }

    // Conecta no Google Play Services
    public void onResume(LocationListener locationListener) {
        log("connect()");
        mGoogleApiClient.connect();

        if(gpsOn) {
            addLocationListeners(locationListener);
        }
    }

    private void log(String s) {
        if(LOG_ON) {
            Log.v(TAG,s);
        }
    }

    // Desconecta do Google Play Services
    public void onPause() {
        if(mGoogleApiClient.isConnected()) {
            if(gpsOn) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
            log("disconnect()");
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        log("onConnected()");

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(LOCATION_GPS_INTERVAL_MILLIS); // 10 segundos
        mLocationRequest.setFastestInterval(LOCATION_GPS_FASTEST_INTERVAL_MILLIS); // 5 segundos
        mLocationRequest.setPriority(LOCATION_GPS_PRIORITY);

        if(gpsOn) {
            // Start GPS
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int status) {
        log("onConnectionSuspended(): " + status);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        log("onConnectionFailed(): " + connectionResult);
    }

    private void addLocationListeners(LocationListener locationListeners) {
        if(this.locationListeners == null) {
            this.locationListeners = new LinkedHashSet<>();
        }
        this.locationListeners.add(locationListeners);
    }

    public String getLastLocationString() {
        Location l = getLastLocation();
        if(l != null) {
            double latitude = l.getLatitude();
            double longitude = l.getLongitude();
            return String.format("lat/lng: %s/%s",latitude,longitude);
        } else {
            return "lat/lng: 0/0";
        }

    }

    public Location getLastLocation() {
        Location l = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        return  l;
    }

    @Override
    public void onLocationChanged(Location location) {
        log("onLocationChanged(): " + location);

        if(locationListeners != null) {
            for (LocationListener listener : locationListeners) {
                listener.onLocationChanged(location);
            }
        }
    }
}
