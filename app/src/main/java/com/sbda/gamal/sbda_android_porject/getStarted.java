package com.sbda.gamal.sbda_android_porject;


import android.content.Intent;
import android.os.Bundle;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;
import java.util.ArrayList;
import java.util.List;


public class getStarted extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        boolean firstboot = getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getBoolean("firstboot", true);

        if (firstboot){
            // 1) Launch the authentication activity

            // 2) Then save the state
            getSharedPreferences("BOOT_PREF", MODE_PRIVATE)
                    .edit()
                    .putBoolean("firstboot", false)
                    .commit();
        }
        else{
            onFinishButtonPressed();
        }

        String []descriptions={
                "Sign up or sign in (it takes only one minute) ","It doesn't cost anything completely free","You'll be notified when ever " +
                "you're close to a hospital","you get to relax while doing it","You get a free snack and a drink at the end","You'll feel great " +
                "because you're saving lives"
        };

        String []titles={
          "Sign up","No Money","Reminder","Relax","Yummy","Feel greatful"
        };

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard(titles[0], descriptions[0], R.drawable.login);
        ahoyOnboarderCard1.setBackgroundColor(R.color.trans);
        ahoyOnboarderCard1.setTitleColor(R.color.white);
        ahoyOnboarderCard1.setDescriptionColor(R.color.grey_200);
        ahoyOnboarderCard1.setTitleTextSize(dpToPixels(8, this));
        ahoyOnboarderCard1.setDescriptionTextSize(dpToPixels(6, this));
        ahoyOnboarderCard1.setIconLayoutParams(512, 512, 250, 0, 0, 0);

        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard(titles[1], descriptions[1], R.drawable.nomoney);
        ahoyOnboarderCard2.setBackgroundColor(R.color.trans);
        ahoyOnboarderCard2.setTitleColor(R.color.white);
        ahoyOnboarderCard2.setDescriptionColor(R.color.grey_200);
        ahoyOnboarderCard2.setTitleTextSize(dpToPixels(10, this));
        ahoyOnboarderCard2.setDescriptionTextSize(dpToPixels(6, this));
        ahoyOnboarderCard2.setIconLayoutParams(512, 512, 250, 0, 0, 0);


        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard(titles[2], descriptions[2], R.drawable.notified);
        ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard3.setTitleColor(R.color.white);
        ahoyOnboarderCard3.setDescriptionColor(R.color.grey_200);
        ahoyOnboarderCard3.setTitleTextSize(dpToPixels(10, this));
        ahoyOnboarderCard3.setDescriptionTextSize(dpToPixels(6, this));
        ahoyOnboarderCard3.setIconLayoutParams(512, 512, 250, 0, 0, 0);


        AhoyOnboarderCard ahoyOnboarderCard4 = new AhoyOnboarderCard(titles[3], descriptions[3], R.drawable.relax);
        ahoyOnboarderCard4.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard4.setTitleColor(R.color.white);
        ahoyOnboarderCard4.setDescriptionColor(R.color.grey_200);
        ahoyOnboarderCard4.setTitleTextSize(dpToPixels(10, this));
        ahoyOnboarderCard4.setDescriptionTextSize(dpToPixels(6, this));
        ahoyOnboarderCard4.setIconLayoutParams(512, 512, 250, 0, 0, 0);

        AhoyOnboarderCard ahoyOnboarderCard5 = new AhoyOnboarderCard(titles[4], descriptions[4], R.drawable.popcorn);
        ahoyOnboarderCard5.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard5.setTitleColor(R.color.white);
        ahoyOnboarderCard5.setDescriptionColor(R.color.grey_200);
        ahoyOnboarderCard5.setTitleTextSize(dpToPixels(10, this));
        ahoyOnboarderCard5.setDescriptionTextSize(dpToPixels(6, this));
        ahoyOnboarderCard5.setIconLayoutParams(512, 512, 250, 0, 0, 0);

        AhoyOnboarderCard ahoyOnboarderCard6 = new AhoyOnboarderCard(titles[5], descriptions[5], R.drawable.thumbsup);
        ahoyOnboarderCard6.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard6.setTitleColor(R.color.white);
        ahoyOnboarderCard6.setDescriptionColor(R.color.grey_200);
        ahoyOnboarderCard6.setTitleTextSize(dpToPixels(8, this));
        ahoyOnboarderCard6.setDescriptionTextSize(dpToPixels(6, this));
        ahoyOnboarderCard6.setIconLayoutParams(512, 512, 250, 0, 0, 0);




//        Typeface face = Typeface.createFromAsset(getAssets(),"@font/jelly_donuts.tff");
//        setFont(face);

        setGradientBackground();


        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);
        pages.add(ahoyOnboarderCard4);
        pages.add(ahoyOnboarderCard5);
        pages.add(ahoyOnboarderCard6);

        setOnboardPages(pages);



    }

    @Override
    public void onFinishButtonPressed() {
        Intent Login=new Intent(this,Login.class);
        startActivity(Login);
    }
}

