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
    public static WindowParams getParamsOf(Context context,int level){
        if (level<0) return null;
        getLevelsList(context);
        if(level<listOfLevels.length-1)
           return calculatePreinstalled(context, level);
        else
            return getCustomParams(context);
    }

    private static WindowParams calculatePreinstalled(Context context,int level){
        WindowParams params= new WindowParams();
        ScreenHelper.ScreenSizePx px=ScreenHelper.getSize(context);
        float item=(px.getHeight()/2/(listOfLevels.length -1));
        params.topLeftCorner=new Point((int)item*level ,0);
        params.relativeWidth=1.0F;
        params.relativeHeight=item*(2*level + 1 )/px.getHeight();
        return params;
    }

    private static WindowParams getCustomParams(Context context) {
        return null;
    }


    private static void getScreenSize(Context context){

    }







}
