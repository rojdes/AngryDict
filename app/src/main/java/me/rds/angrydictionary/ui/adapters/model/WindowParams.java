package me.rds.angrydictionary.ui.adapters.model;

import android.graphics.Point;

/**
 * Created by D1m11n on 20.01.2015.
 */
public class WindowParams {

    public Point topLeftCorner;

    public float relativeWidth;

    public float relativeHeight;


    @Override
    public String toString() {
        return "topLeft = "  + ((topLeftCorner==null)?"(0,0)":("( "+ topLeftCorner.x + ":" + topLeftCorner.y + "), ")) +
                "relativeWidth=" + relativeWidth + ", " +
                "relativeHeight= " + relativeHeight;
    }
}
