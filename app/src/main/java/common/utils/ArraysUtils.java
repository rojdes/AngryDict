package common.utils;

/**
 * Created by D1m11n on 23.01.2015.
 */
public class ArraysUtils {

    private ArraysUtils(){}

    public static boolean isEmpty(Object ... values){
        return values==null||values.length==0;
    }
}
