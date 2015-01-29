package me.rds.angrydictionary;

import android.os.Environment;

public interface AppConsts {

    public static String EXT_FOLDER_MP3 = Environment.getExternalStorageDirectory().toString() + "/language/mp3/";

    public static String EXT_FOLDER_DB = Environment.getExternalStorageDirectory().toString() + "/language/db/";

    public static int MAX_PERIOD_SHOW = 10;

    public static int MAX_TIME_ANSWER = 5;

    public static int MIN_WINDOW_WIDTHdp=200;

    public static int MIN_WINDOW_HEIGHTdp=220;

    public static String SERVER_IP = "192.168.0.105";

    public static int SERVER_PORT = 20000;

    public static int TRANSPARENCY = 100;

    public static String DROPBOX_HOST="https://dl.dropboxusercontent.com/s/";

    public static final String LINK_DB_LIST =DROPBOX_HOST + "3s2perzfoww5qdi/db_link?dl=0";

    //public static final String LINK_PRIMARY_DB =DROPBOX_HOST +"3omsy8izc7zmphx/dictionary_primary.db?dl=0";

}
