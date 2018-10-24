package com.sbda.gamal.sbda_android_porject;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.w3c.dom.Text;

public class UserProfile extends AppCompatActivity {

    String emailPref;
    String url="https://sbda.000webhostapp.com/connect/Android/getData.php";
    private JSONArray result;
    User user;
//    user profile views
    TextView username;
    TextView usernameF;
//    TextView password;
    TextView mobile;
    TextView age;
    TextView weight;
    TextView weightF;
    TextView tall;
    TextView tallF;
    TextView city;
    TextView diseases;
    TextView email;
    TextView bloodgroup;
    DatePicker birthdate;
    DatePicker donationDate;
    ToggleButton gender;
    Button update;
    Context c;
    private boolean isEdatibleEnabled=true;
    UserProfile x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        emailPref = getSharedPreferences("email", MODE_PRIVATE).getString("email", "");

        username=(TextView) findViewById(R.id.username);
        usernameF=(TextView) findViewById(R.id.usernameF);
        mobile=(TextView) findViewById(R.id.mobile);
        age=(TextView) findViewById(R.id.age);
        weight=(TextView) findViewById(R.id.weight);
        weightF=(TextView) findViewById(R.id.weightF);
        tall=(TextView) findViewById(R.id.tall);
        tallF=(TextView) findViewById(R.id.tallF);
        city=(TextView) findViewById(R.id.city);
        diseases=(TextView) findViewById(R.id.diseases);
        email=(TextView) findViewById(R.id.email);
        birthdate=(DatePicker) findViewById(R.id.birthdate);
        donationDate=(DatePicker) findViewById(R.id.donationDate);
        bloodgroup=(TextView) findViewById(R.id.bloodgroup);
        gender=(ToggleButton) findViewById(R.id.gender);
        update=(Button) findViewById(R.id.update);
        c=this.getApplicationContext();

//        setEditable(!isEdatibleEnabled);
        ImageView back=(ImageView) findViewById(R.id.back);
        ImageView edit=(ImageView) findViewById(R.id.edit);


        x=this;
        //download from database
        final Downloader d=new Downloader(c,url,x);
        d.execute();
        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //download from database
                final Downloader d=new Downloader(c,url,x);
                d.execute();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setEditable(!isEdatibleEnabled);
            }
        });

        update.performClick();
    }

    private void setEditable(boolean b) {
        username.setEnabled(b);
         username.setEnabled(b);
         usernameF.setEnabled(b);
         mobile.setEnabled(b);
         age.setEnabled(b);
         weight.setEnabled(b);
         weightF.setEnabled(b);
         tall.setEnabled(b);
         tallF.setEnabled(b);
         city.setEnabled(b);
         diseases.setEnabled(b);
         email.setEnabled(b);
         bloodgroup.setEnabled(b);
         birthdate.setEnabled(b);
         donationDate.setEnabled(b);
         gender.setEnabled(b);
         update.setEnabled(b);
        isEdatibleEnabled=b;
    }
}

