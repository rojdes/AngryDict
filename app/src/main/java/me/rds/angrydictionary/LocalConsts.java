package me.rds.angrydictionary;

import android.os.Environment;

public interface LocalConsts {

    public static String EXT_FOLDER_MP3 = Environment.getExternalStorageDirectory().toString() + "/language/mp3/";

    public static String EXT_FOLDER_DB = Environment.getExternalStorageDirectory().toString() + "/language/db/";

    public static int MAX_PERIOD_SHOW = 10;

    public static int MAX_TIME_ANSWER = 5;

    public static String SERVER_IP = "192.168.0.105";

    public static int SERVER_PORT = 20000;

    public static int TRANSPARENCY = 100;

    public static final String LINK_LIST="https://www.dropbox.com/s/slhojiy2ujta8vt/list?dl=0";

    public static final String LINK_ALT_LIST="https://dl.dropboxusercontent.com/s/slhojiy2ujta8vt/list?dl=0";

}
