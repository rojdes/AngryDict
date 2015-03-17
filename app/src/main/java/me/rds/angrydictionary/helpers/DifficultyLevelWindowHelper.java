package me.rds.angrydictionary.helpers;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import common.ScreenHelper;
import me.rds.angrydictionary.R;
import me.rds.angrydictionary.helpers.model.AbsoluteWindowParams;
import me.rds.angrydictionary.helpers.model.RelativeWindowParams;

/**
 * Created by D1m11n on 20.01.2015.
 */
public final class DifficultyLevelWindowHelper {

    protected Point topLeftCorner;

    protected Point sizeWH;

    protected static String [] listOfLevels;

    protected static ScreenHelper.ScreenSizePx mScreenSize;

    public static String [] getLevelsList(Context context){
        if (listOfLevels==null)
            listOfLevels=context.getResources().getStringArray(R.array.difficulties);
        return listOfLevels;
    }

//    /**
//     *
//     * @param level - index in array; last index is ALWAYS CUSTOM
//     * @return RelativeWindowParams
//     */
//    public static RelativeWindowParams getWindowSizeFor(Context context, int level){
//        if (level<0) return null;
//        getLevelsList(context);
//        if(level<listOfLevels.length-1)
//           return calculatePreinstalled(context, level);
//        else
//            return calculatePreinstalled(context, 1);
//    }


    public static AbsoluteWindowParams getWindowSizeFor(Context context, int level){
        if (level<0) return null;
        getLevelsList(context);
        if(level<=listOfLevels.length-1)
            return calculatePreinstalled(context, level);
        else
            return calculatePreinstalled(context, 1);
    }

//    private static RelativeWindowParams calculatePreinstalled(Context context,int level){
//        RelativeWindowParams params= new RelativeWindowParams();
//        ScreenHelper.ScreenSizePx px=ScreenHelper.getSize(context);
//        float item=(px.getHeight()/2/(listOfLevels.length -1));
//        params.topLeftCorner=new Point((int)item*level ,0);
//        params.relativeWidth=1.0F;
//        params.relativeHeight=item*(2*level + 1 )/px.getHeight();
//        return params;
//    }


    private static AbsoluteWindowParams calculatePreinstalled(Context context,int level){
        Log.e("DIFFICULTY_LEVEL", "level = " + level + ",  " + listOfLevels.length);
        AbsoluteWindowParams params= new AbsoluteWindowParams();
        ScreenHelper.ScreenSizePx px=ScreenHelper.getSize(context);
        params.width=px.getWidth();
        if(level==listOfLevels.length-1)
            params.height=px.getHeight();
        else
          params.height=(float)(px.getHeight()*level)/(listOfLevels.length-1);
        return params;
    }

    public static AbsoluteWindowParams convert(Context context,RelativeWindowParams params){
        ScreenHelper.ScreenSizePx px=ScreenHelper.getSize(context);
        AbsoluteWindowParams params1 = new AbsoluteWindowParams();
        params1.height=params.relativeHeight*px.getHeight();
        params1.width=params.relativeWidth*px.getWidth();
        return params1;

    }

}
