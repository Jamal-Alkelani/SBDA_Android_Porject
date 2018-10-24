package com.sbda.gamal.sbda_android_porject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;



public class Eligibility extends AppCompatActivity {

    int imageIDs[]={R.drawable.question,R.drawable.help,R.drawable.blood,R.drawable.pyramid};

    String listItemsName[]={"Can I Give Blood?","Eligibility FAQS","Travel & Donation","Keeping Blood Safe"};

    String listItemsDesc[]={"Take a quiz to check that","some interested FAQS","discover the world","Learn how to keep" +
            " blood supply safe"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligibility);
//        ListView learnListView=(ListView) findViewById(R.id.eligibility_list);
//        CustomListView customListView=new CustomListView(this.getApplicationContext(),imageIDs,listItemsName,listItemsDesc);
//        learnListView.setAdapter(customListView);

    }





}
