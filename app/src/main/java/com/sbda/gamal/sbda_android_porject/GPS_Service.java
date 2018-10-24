package com.sbda.gamal.sbda_android_porject;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.graphics.PointF;
import android.location.Location;

import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class GPS_Service extends Service {

    private static final String TAG = "XXXXXXXXXXX";
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 0;
    public float minDistanceToNotifiy=1;
    private ArrayList<Hospital> hospitals=new ArrayList<Hospital>();
    boolean canSendNotification;
    boolean isServiceRunning=false;
    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };
    private LocationManager mLocationManager = null;

    public GPS_Service(Context c){

    }

    public GPS_Service(){

    }
    @Override
    public void onCreate() {

        Log.e(TAG, "onCreate");
        registerHospitals();

        initializeLocationManager();
        startServiceWithNotification();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    private void registerHospitals() {
        Hospital A=new Hospital(new PointF((float)32.223560, (float)35.264890),"National Hospital");
        Hospital B=new Hospital(new PointF((float)32.225232, (float)35.241461),"Raffidia Hospital");
        Hospital C=new Hospital(new PointF((float)32.220986, (float)35.245106),"Nablus Professinal Hospital");
        Hospital D=new Hospital(new PointF((float)32.223286, (float)35.256263),"St. Luke’s Hospital");
        Hospital E=new Hospital(new PointF((float)32.223560, (float)35.264890),"Arabian professional Hospital");
        Hospital F=new Hospital(new PointF((float)32.230315, (float)35.256951),"United Women Hospital");
        hospitals.add(A);
        hospitals.add(B);
        hospitals.add(C);
        hospitals.add(D);
        hospitals.add(E);
        hospitals.add(F);


    }

    void startServiceWithNotification() {
        if (isServiceRunning) {return;}
        isServiceRunning = true;

//        Intent notificationIntent = new Intent(getApplicationContext(), GPS_Service.class);
//        notificationIntent.setAction(getPackageName()+".ACTION_MAIN");  // A string containing the action name
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//
//
//
//        Notification notification = new NotificationCompat.Builder(this)
//                .setContentTitle(getResources().getString(R.string.app_name))
//                .setTicker(getResources().getString(R.string.app_name))
//                .setContentText("Sometext")
//                .setContentIntent(contentPendingIntent)
//                .setOngoing(true)
////                .setDeleteIntent(contentPendingIntent)  // if needed
//                .build();
//        notification.flags = notification.flags | Notification.FLAG_NO_CLEAR;     // NO_CLEAR makes the notification stay when the user performs a "delete all" command
//        startForeground(1997, notification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;

    }

    @Override
    public void onDestroy()
    {

        Log.e("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent(".RestartSensor");
        sendBroadcast(broadcastIntent);
//        Log.e(TAG, "onDestroy");
//        Intent xx=new Intent(getApplicationContext(),GPS_Service.class);
//        startService(xx);
//        super.onDestroy();
//        if (mLocationManager != null) {
//            for (int i = 0; i < mLocationListeners.length; i++) {
//                try {
//                    mLocationManager.removeUpdates(mLocationListeners[i]);
//                } catch (Exception ex) {
//                    Log.i(TAG, "fail to remove location listners, ignore", ex);
//                }
//            }
//        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }



        @Override
        public void onLocationChanged(Location location)
        {

            canSendNotification=getSharedPreferences("canSendNotification",MODE_PRIVATE).getBoolean("canSendNotification",true);
            minDistanceToNotifiy=getSharedPreferences("minDistanceToNotifiy",MODE_PRIVATE).getInt("minDistanceToNotifiy",1);
            double distanceInKm=0,closestDistance=99999;
            String closestHospitalName="";
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            for(int i=0;i<hospitals.size();i++)
            {
                distanceInKm =getDistanceFromLatLonInKm(mLastLocation.getLatitude(),mLastLocation.getLongitude(),hospitals.get(i).getCoords().x,
                        hospitals.get(i).getCoords().y);
                if(distanceInKm<closestDistance)
                {
                    closestHospitalName=hospitals.get(i).getName();
                    closestDistance=distanceInKm;
                }

            }
            if(closestDistance<=minDistanceToNotifiy && canSendNotification){
                canSendNotification=false;
                sendNotification(closestHospitalName);
            }

            Toast.makeText(getApplicationContext(),mLastLocation.toString()+closestDistance,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }
        @Override
        public void onProviderEnabled(String provider)
        {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.e(TAG, "onStatusChanged: " + provider);
        }

        public double getDistanceFromLatLonInKm(double lat1,double lon1,double lat2,double lon2) {
            double R = 6371; // Radius of the earth in km
            double dLat = deg2rad(lat2-lat1);  // deg2rad below
            double dLon = deg2rad(lon2-lon1);
            double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                            Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                    Math.sin(dLon/2) * Math.sin(dLon/2)
                    ;
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            double d = R * c; // Distance in km
            return d;
        }

        public double deg2rad(double deg) {
            return deg * (Math.PI/180);
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceTask = new Intent(getApplicationContext(),this.getClass());
        restartServiceTask.setPackage(getPackageName());
        PendingIntent restartPendingIntent =PendingIntent.getService(getApplicationContext(), 1,restartServiceTask, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager myAlarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        myAlarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartPendingIntent);
        Log.e("xx","onTask Removed called");
        super.onTaskRemoved(rootIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void sendNotification(String hospitalName){
        String notMessage="You're near to "+hospitalName+", come in and donate ♥,Remember each time you donate you save 3 people lives";
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "0")
                .setSmallIcon(R.drawable.bloodlogo)
                .setContentTitle("Call For Help")
                .setAutoCancel(true)
                .setContentText(notMessage)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notMessage))
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationManager.IMPORTANCE_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, mBuilder.build());
    }
}
