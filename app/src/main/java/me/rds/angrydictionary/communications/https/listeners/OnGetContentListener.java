package me.rds.angrydictionary.communications.https.listeners;

/**
 * Created by D1m11n on 20.01.2015.
 */
public interface OnGetContentListener {


    /**
     *
     * @param msg or null
     * @param error if msg == null or null
     */
    public void onGetContent(String msg, Exception error);
}
