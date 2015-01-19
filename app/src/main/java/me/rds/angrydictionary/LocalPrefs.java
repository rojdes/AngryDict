package me.rds.angrydictionary;

/**
 * Created by D1m11n on 12.01.2015.
 */

import android.content.Context;

import com.securepreferences.SecurePreferences;

public class LocalPrefs {

    private static SecurePreferences mPrefs;
    private static SecurePreferences.Editor mEditor;

    public static void setWasFirstStart(Context context) {
        initPrefsIfNeeded(context);
        mEditor.putBoolean(Key.FIRST_START, false).apply();
    }

    public static boolean isFirstStart(Context context) {
        initPrefsIfNeeded(context);
        return mPrefs.getBoolean(Key.FIRST_START, true);
    }

    public static void setTimeAnswer(Context context, int value) {
        initPrefsIfNeeded(context);
        mEditor.putInt(Key.TIME_ANSWER, value).apply();
    }

    public static int getTimeAnswer(Context context) {
        initPrefsIfNeeded(context);
        return mPrefs.getInt(Key.TIME_ANSWER, LocalConsts.MAX_PERIOD_SHOW);
    }

    public static void setPeriodShow(Context context, int value) {
        initPrefsIfNeeded(context);
        mEditor.putInt(Key.PERIOD, value).apply();
    }

    public static int getPeriodShow(Context context) {
        initPrefsIfNeeded(context);
        return mPrefs.getInt(Key.PERIOD, LocalConsts.MAX_TIME_ANSWER);
    }


    public static void setServerIp(Context context, String value) {
        initPrefsIfNeeded(context);
        mEditor.putString(Key.SERVER_IP, value).apply();
    }

    public static String getServerIp(Context context) {
        initPrefsIfNeeded(context);
        return mPrefs.getString(Key.SERVER_IP, LocalConsts.SERVER_IP);
    }

    public static void setServerPort(Context context, int value) {
        initPrefsIfNeeded(context);
        mEditor.putInt(Key.SERVER_PORT, value).apply();
    }

    public static int getServerPort(Context context) {
        initPrefsIfNeeded(context);
        return mPrefs.getInt(Key.SERVER_PORT, LocalConsts.SERVER_PORT);
    }

    public static void setTransparency(Context context, int value) {
        if (value < 0) return;
        initPrefsIfNeeded(context);
        mEditor.putInt(Key.TRANSPARENCY, value > 100 ? 100 : value).apply();
    }

    public static int getTransparency(Context context) {
        initPrefsIfNeeded(context);
        return mPrefs.getInt(Key.TRANSPARENCY, LocalConsts.TRANSPARENCY);
    }


    private static void initPrefsIfNeeded(Context context) {
        if (mPrefs == null) {
            mPrefs = new SecurePreferences(context);
            mEditor = mPrefs.edit();
        }
    }


    interface Key {
        public static String FIRST_START = "first_start";
        public static String PERIOD = "period";
        public static String TIME_ANSWER = "time_answer";
        public static String SERVER_IP = "ip";
        public static String SERVER_PORT = "port";
        public static String TRANSPARENCY = "transparency";

    }

}
