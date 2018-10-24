package com.sbda.gamal.sbda_android_porject;

import android.graphics.PointF;

public class Hospital {

    private PointF coords;
    private String name;

    public Hospital(PointF p,String n){
        setCoords(p);
        setName(n);
    }

    public PointF getCoords() {
        return coords;
    }

    public void setCoords(PointF coords) {
        this.coords = coords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
