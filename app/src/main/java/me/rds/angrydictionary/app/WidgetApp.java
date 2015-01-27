package me.rds.angrydictionary.app;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.io.File;

import me.rds.angrydictionary.AppConsts;
import me.rds.angrydictionary.AppPrefs;
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
        Fabric.with(this, new Crashlytics());
        mApp = this;
        onFirstStartApk();
        startService(new Intent(this, ClockService.class));
    }

    private void onFirstStartApk() {
       // if (AppPrefs.isFirstStart(this)) {
             generateFoldersIfNeeded();
            AppPrefs.setWasFirstStart(this);
            Log.e(TAG, "is first start = " + AppPrefs.isFirstStart(this));
      //  }
    }

    private void generateFoldersIfNeeded(){
        File f = new File(AppConsts.EXT_FOLDER_MP3);
        if (!f.exists())
            f.mkdirs();
        File f2 = new File(AppConsts.EXT_FOLDER_DB);
        if (!f2.exists())
            f2.mkdirs();
    }
}
