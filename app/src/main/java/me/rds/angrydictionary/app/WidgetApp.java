package me.rds.angrydictionary.app;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import java.io.File;

import me.rds.angrydictionary.LocalConsts;
import me.rds.angrydictionary.LocalPrefs;
import me.rds.angrydictionary.widget.ClockService;

public class WidgetApp extends Application {


    private static final String TAG = "WIDGET_APP";

    private static WidgetApp mApp;

    public static WidgetApp getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        onFirstStartApk();
        startService(new Intent(this, ClockService.class));
    }

    private void onFirstStartApk() {
        if (LocalPrefs.isFirstStart(this)) {
           generateFoldersIfNeeded();
            LocalPrefs.setWasFirstStart(this);
            Log.e(TAG, "is first start = " + LocalPrefs.isFirstStart(this));
        }
    }

    private void generateFoldersIfNeeded(){
        File f = new File(LocalConsts.EXT_FOLDER_MP3);
        if (!f.exists())
            f.mkdirs();
        File f2 = new File(LocalConsts.EXT_FOLDER_DB);
        if (!f2.exists())
            f2.mkdirs();
    }
}
