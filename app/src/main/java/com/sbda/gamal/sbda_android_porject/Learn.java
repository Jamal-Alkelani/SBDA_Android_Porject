package com.sbda.gamal.sbda_android_porject;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


public class Learn extends AppCompatActivity {

    private LinearLayout l1,l2,l3,l4,l5;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        l1=(LinearLayout) findViewById(R.id.how_blood_used);
        l2=(LinearLayout) findViewById(R.id.who_blood_helps);
        l3=(LinearLayout) findViewById(R.id.types_of_donation);
        l4=(LinearLayout) findViewById(R.id.how_donation_works);
        l5=(LinearLayout) findViewById(R.id.beign_a_blood_donor);

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HowYourBloodIsUsed_learn.class));
            }
        });


        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),whoYourBloodHelps_learn.class));
            }
        });

        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),typesOfDonation_learn.class));
            }
        });

        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HowDonationWorks_learn.class));
            }
        });

        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),BeingAbloodDonor_learn.class));
            }
        });

    }








}
