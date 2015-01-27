package common.utils;

import java.util.List;

/**
 * Created by D1m11n on 23.01.2015.
 */
public class ListsUtils {

    private ListsUtils(){}

    public static boolean isEmpty(List<?>  values){
        return values==null||values.size()==0;
    }
}
