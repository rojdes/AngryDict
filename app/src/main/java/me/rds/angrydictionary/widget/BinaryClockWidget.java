package me.rds.angrydictionary.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import me.rds.angrydictionary.AppIntents;
import me.rds.angrydictionary.R;


/**
 * So internally RemoteViews is simply a set of actions that are
 * "serialized" and sent to another process.  Each time you make a call
 * to something like setDouble(), you're adding an additional action to
 * RemoteViews' internal list.
 * <p/>
 * Because there isn't a way of clearing these actions from a RemoteViews
 * object, all of your successive setImageViewBitmap() calls, along with
 * their Bitmaps, remain in the internal list, and are actually
 * "serialized" and applied each time your send it.  :(
 * <p/>
 * In this case it's best to just create a new RemoteViews object every time.
 * <p/>
 * <p/>
 * FROM : https://groups.google.com/forum/#!topic/android-developers/qQ4SV5wL7uM
 *
 * @author D1m11n
 */
public class BinaryClockWidget extends AppWidgetProvider {

    private static final String TAG = "APP_WIDGET_PROVIDER";


    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.e(TAG, "ON ENABLED");
    }


    // http://habrahabr.ru/post/114515/
    @Override
    public void onUpdate(Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        Log.e(TAG, "ON UPDATE");
//        TimeUpdater.updateTime(views);
        initViewsifNeeded(context, new RemoteViews(context.getPackageName(), R.layout.wdg_binary_clock));
//        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.e(TAG, "ON DELETED");
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        //super.onReceive(context, intent);
        Log.e(TAG, "ON RECEIVE ");
        if (intent == null || intent.getAction() == null) return;
        if (intent.getAction().equals(AppIntents.Action.TIME) || intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
            for (int appWidgetId : ids) {
                updateWidget(context, appWidgetManager, appWidgetId);
            }
        }
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wdg_binary_clock);
        WidgetTimeUpdater.updateTime(views);
        initViewsifNeeded(context, views);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.e(TAG, "ON DISABLED");
    }

    private void initViewsifNeeded(Context context, RemoteViews views) {
        Log.e(TAG, "REGISTER CLICK");
        clickAmPm(context, views);
        clickPrefs(context, views);
    }

    private void clickPrefs(Context context, RemoteViews views) {
        Intent intent = new Intent(context, ClockService.class);
        intent.setAction(AppIntents.Action.WIDGET_CLICK_PREFS);
        PendingIntent pIntent = PendingIntent.getService(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.iv_prefs, pIntent);
    }

    private void clickAmPm(Context context, RemoteViews views) {
        Intent intent = new Intent(context, ClockService.class);
        intent.setAction(AppIntents.Action.WIDGET_CLICK_AM_PM);
        PendingIntent pIntent = PendingIntent.getService(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.iv_am, pIntent);
        views.setOnClickPendingIntent(R.id.iv_pm, pIntent);
    }

    // @Override
    // public void onRestored(Context context, int[] oldWidgetIds, int[]
    // newWidgetIds) {
    // super.onRestored(context, oldWidgetIds, newWidgetIds);
    // Log.e(TAG,"ON RESTORED"); //21 API
    // }


}
