package me.rds.angrydictionary.ui.activities.adapters.model;

import android.content.Context;
import android.graphics.Point;

import common.ScreenHelper;
import me.rds.angrydictionary.R;

/**
 * Created by D1m11n on 20.01.2015.
 */
public final class DifficultyLevel {

    protected Point topLeftCorner;

    protected Point sizeWH;

    protected static String [] listOfLevels;

    protected static ScreenHelper.ScreenSizePx mScreenSize;

    public static String [] getLevelsList(Context context){
        if (listOfLevels==null)
            listOfLevels=context.getResources().getStringArray(R.array.difficulties);
        return listOfLevels;
    }

    /**
     *
     * @param level - index in array; last index is ALWAYS CUSTOM
     * @return WindowParams
     */
    public static  WindowParams getParamsOf(Context context,int level){
        getLevelsList(context);
        WindowParams params= new WindowParams();
        if(level<listOfLevels.length-1){
            params.topLeftCorner=new Point(0,0);
            params.relativeWidth=1.0F;
            params.relativeHeight=1.0F/(listOfLevels.length -level); //CHECK
        }else{
            //ADD FROM USER
        }
        return params;
    }

    private static void getScreenSize(Context context){

    }


    public static class WindowParams{

        public Point topLeftCorner;

        public float relativeWidth;

        public float relativeHeight;
    }





}
