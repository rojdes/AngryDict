package me.rds.angrydictionary.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import common.WindowHelper;
import me.rds.angrydictionary.AppIntents;
import me.rds.angrydictionary.AppPrefs;
import me.rds.angrydictionary.ui.activities.PreferencesActivity;

@SuppressLint("InflateParams")
public class ClockService extends Service {

    private static final String TAG = "CLOCK_SERVICE";


    private final Intent mTimeIntent = new Intent(AppIntents.Action.TIME);

    private final BroadcastReceiver mTimeTickReciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "TIME TICK");
            context.sendBroadcast(mTimeIntent);
        }
    };


    private Runnable mUpdateTimeTask = new Runnable()
    {   public void run()
        {
            activateDictWindow();
            mDelayHandler.postDelayed(this, AppPrefs.getPeriodShow(ClockService.this)*60*1000L);
        }
    };


    protected WindowHelper mWindowHelper = new WindowHelper();
    protected DictionaryWindow mDictionaryWindow;
    protected Handler mDelayHandler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDelayHandler.postDelayed(mUpdateTimeTask, AppPrefs.getPeriodShow(ClockService.this)*60*1000L);
        if (mDictionaryWindow==null)
            mDictionaryWindow= new DictionaryWindow(this, mWindowHelper);
        this.sendBroadcast(mTimeIntent);
        this.registerReceiver(mTimeTickReciver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null || intent.getAction() == null)
            return Service.START_STICKY;
        if (intent.getAction().equals(AppIntents.Action.WIDGET_CLICK_AM_PM))
            activateDictWindow();
        if (intent.getAction().equals(AppIntents.Action.WIDGET_CLICK_PREFS))
            onClickWidgetPrefsAction();
        return Service.START_STICKY;
    }

    private void onClickWidgetPrefsAction() {
        Intent intent = new Intent(this, PreferencesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    private void activateDictWindow() {
        if (mWindowHelper == null) {
            mWindowHelper = new WindowHelper();
            mDictionaryWindow=new DictionaryWindow(this, mWindowHelper);
        }
        if (mWindowHelper.getLastView() == null) {
           mDictionaryWindow.build();
        } /*else {
            mWindowHelper.remove(ClockService.this);
        }*/
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.e(TAG, "onTrimMemory");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mDelayHandler.removeCallbacks(mUpdateTimeTask);
        Log.e(TAG, "onLowMemory");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        mDelayHandler.removeCallbacks(mUpdateTimeTask);
        unregisterReceiver(mTimeTickReciver);
        mWindowHelper.remove(this);
        mDictionaryWindow=null;
    }

    ;

}
