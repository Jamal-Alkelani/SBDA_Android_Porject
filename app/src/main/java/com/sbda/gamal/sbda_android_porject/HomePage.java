package com.sbda.gamal.sbda_android_porject;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.support.v4.view.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class HomePage extends AppCompatActivity {
    Intent mServiceIntent;
    private GPS_Service mSensorService;
    private ViewPager vp;
    Context ctx;
    private int vpindex=0;

    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        setContentView(R.layout.activity_home_page);

        vp=(ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(this.getApplicationContext());
        vp.setAdapter(viewPagerAdapter);

        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);
        boolean canSendNotification=getSharedPreferences("canSendNotification",MODE_PRIVATE).getBoolean("canSendNotification",true);

       int minDistanceToNotifiy=getSharedPreferences("minDistanceToNotifiy",MODE_PRIVATE).getInt("minDistanceToNotifiy",0);
        Log.e("PREF",canSendNotification+",,,"+minDistanceToNotifiy);



//        startService(new Intent(this,GPS_Service.class));
        mSensorService = new GPS_Service(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
            Log.e("xx","service started");
        }

        LinearLayout learn=(LinearLayout) findViewById(R.id.learn);
        LinearLayout settings=(LinearLayout) findViewById(R.id.settings);
        LinearLayout eligibility=(LinearLayout) findViewById(R.id.eligibility);
        LinearLayout Profile=(LinearLayout) findViewById(R.id.Profile);

        learn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Learn.class));
            }
        });

        eligibility.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Eligibility.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Settings.class));
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserProfile.class));
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.e ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.e ("isMyServiceRunning?", false+"");
        return false;
    }

    @Override
    protected void onStop() {
        Log.e("xx","stop is called");
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.e("MAINACT", "onDestroy!");
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {

    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            HomePage.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    vp.setCurrentItem(++vpindex%3);
                }
            });
        }
    }
}
