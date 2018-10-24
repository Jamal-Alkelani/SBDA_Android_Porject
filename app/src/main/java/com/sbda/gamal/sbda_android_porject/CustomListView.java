package com.sbda.gamal.sbda_android_porject;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gamal on 3/28/2018.
 */

public class CustomListView extends BaseAdapter {

    Context c;
    LayoutInflater li;
    int [] imageIDs;
    String []listItemsName;
    String []listItemsDesc;

    public CustomListView(Context c,int[] images,String[] names,String[] desc){
        this.c=c;
         li= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageIDs=images;
        listItemsName=names;
        listItemsDesc=desc;
    }
    @Override
    public int getCount() {
        return imageIDs.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view=li.inflate(R.layout.custom_listview_row,null);

        ImageView imageView_icon=(ImageView) view.findViewById(R.id.icon);
        TextView textView_name=(TextView) view.findViewById(R.id.name);
        TextView textView_desc=(TextView) view.findViewById(R.id.desc);

        imageView_icon.setImageResource(imageIDs[i]);
        textView_name.setText(listItemsName[i]);
        textView_desc.setText(listItemsDesc[i]);

        return view;

    }
}

