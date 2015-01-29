package common.utils;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by D1m11n on 23.01.2015.
 */
public class ListsUtils {

    private ListsUtils(){}

    public static boolean isEmpty(List<?>  values){
        return values==null||values.size()==0;
    }


    /** Print in Log item value using <b>.toString()</> methid or print <i>"list is empty"<i/> if null or size = 0
     *
     * @param tag using for print all to Log
     * @param values
     *
     */
    public static void printAll(String tag, List<?>  values){
        Log.e(tag,"LIST IS =");
        if (isEmpty(values)) Log.e(tag,"list is empty");

        else
            for(int i=0; i<values.size(); i++)
                Log.e(tag,values.get(i).toString());
        Log.e(tag,"LIST ENDED");
    }

    public static <T> void updateList(List<T>  dest, List<T>  src) {
        if(isEmpty(dest)||isEmpty(src)) return;
        for(int i=0; i<dest.size(); i++){
            if(dest.contains(src.get(i)))
                dest.add(src.get(i));
        }
   }
}