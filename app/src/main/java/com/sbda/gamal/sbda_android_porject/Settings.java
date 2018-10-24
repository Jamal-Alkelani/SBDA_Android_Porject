package com.sbda.gamal.sbda_android_porject;

import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    private Switch enableNotifi;
    private NumberPicker np;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        enableNotifi=(Switch) findViewById(R.id.switch1);
        np=(NumberPicker) findViewById(R.id.notifi_distance);
        save=(Button) findViewById(R.id.save);

        boolean notifiPref=getSharedPreferences("canSendNotification",MODE_PRIVATE).getBoolean("canSendNotification",true);
        enableNotifi.setChecked(notifiPref);
        np.setMaxValue(400);
        np.setMinValue(1);
        int distancePref=getSharedPreferences("minDistanceToNotifiy",MODE_PRIVATE).getInt("minDistanceToNotifiy",1);
        np.setValue(distancePref);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSharedPreferences("canSendNotification",MODE_PRIVATE)
                        .edit()
                        .putBoolean("canSendNotification",enableNotifi.isChecked())
                        .commit();

                getSharedPreferences("minDistanceToNotifiy",MODE_PRIVATE)
                        .edit()
                        .putInt("minDistanceToNotifiy",np.getValue())
                        .commit();

                onBackPressed();
            }
        });
    }
}
