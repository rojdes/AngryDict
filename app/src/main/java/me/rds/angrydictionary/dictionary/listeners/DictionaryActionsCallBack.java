package me.rds.angrydictionary.dictionary.listeners;

/**
 * Created by D1m11n on 22.01.2015.
 */
public interface DictionaryActionsCallBack {


    public static int OK=0;
    public static int ERROR=-1;

    public void callback(int action, Object ... values );

}
