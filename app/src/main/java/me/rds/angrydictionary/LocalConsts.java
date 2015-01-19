package me.rds.angrydictionary;

import android.os.Environment;

public interface LocalConsts {

    public static String EXT_FOLDER_MP3 = Environment.getExternalStorageDirectory().toString() + "/language/mp3/";

    public static int MAX_PERIOD_SHOW = 10;

    public static int MAX_TIME_ANSWER = 5;

    public static String SERVER_IP = "192.168.0.105";

    public static int SERVER_PORT = 20000;

    public static int TRANSPARENCY = 100;

}
