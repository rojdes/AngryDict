package me.rds.angrydictionary.widget;

import android.view.View;
import android.widget.RemoteViews;

import java.util.Calendar;

import me.rds.angrydictionary.R;

class WidgetTimeUpdater {


    public static void updateTime(RemoteViews view) {
        updateAmPm(Calendar.getInstance(), view);
        updateHour(Calendar.getInstance(), view);
        updateMinutes(Calendar.getInstance(), view);
    }

    private static void updateAmPm(Calendar cc, RemoteViews view) {
        if (cc.get(Calendar.AM_PM) == Calendar.AM) {
            view.setViewVisibility(R.id.iv_am, View.VISIBLE);
            view.setViewVisibility(R.id.iv_pm, View.GONE);
        } else {
            view.setViewVisibility(R.id.iv_am, View.GONE);
            view.setViewVisibility(R.id.iv_pm, View.VISIBLE);
        }
    }


    private static void updateHour(Calendar cc, RemoteViews view) {
        int hour = cc.get(Calendar.HOUR);
        view.setViewVisibility(R.id.tv_hour8_off, ((hour & 8) > 0) ? View.GONE : View.VISIBLE);
        view.setViewVisibility(R.id.tv_hour8_on, ((hour & 8) > 0) ? View.VISIBLE : View.GONE);
        view.setViewVisibility(R.id.tv_hour4_off, ((hour & 4) > 0) ? View.GONE : View.VISIBLE);
        view.setViewVisibility(R.id.tv_hour4_on, ((hour & 4) > 0) ? View.VISIBLE : View.GONE);
        view.setViewVisibility(R.id.tv_hour2_off, ((hour & 2) > 0) ? View.GONE : View.VISIBLE);
        view.setViewVisibility(R.id.tv_hour2_on, ((hour & 2) > 0) ? View.VISIBLE : View.GONE);
        view.setViewVisibility(R.id.tv_hour1_off, ((hour & 1) > 0) ? View.GONE : View.VISIBLE);
        view.setViewVisibility(R.id.tv_hour1_on, ((hour & 1) > 0) ? View.VISIBLE : View.GONE);
    }


    private static void updateMinutes(Calendar cc, RemoteViews view) {
        int minutes = cc.get(Calendar.MINUTE);
        view.setViewVisibility(R.id.tv_minute32_off, ((minutes & 32) > 0) ? View.GONE : View.VISIBLE);
        view.setViewVisibility(R.id.tv_minute32_on, ((minutes & 32) > 0) ? View.VISIBLE : View.GONE);
        view.setViewVisibility(R.id.tv_minute16_off, ((minutes & 16) > 0) ? View.GONE : View.VISIBLE);
        view.setViewVisibility(R.id.tv_minute16_on, ((minutes & 16) > 0) ? View.VISIBLE : View.GONE);
        view.setViewVisibility(R.id.tv_minute8_off, ((minutes & 8) > 0) ? View.GONE : View.VISIBLE);
        view.setViewVisibility(R.id.tv_minute8_on, ((minutes & 8) > 0) ? View.VISIBLE : View.GONE);
        view.setViewVisibility(R.id.tv_minute4_off, ((minutes & 4) > 0) ? View.GONE : View.VISIBLE);
        view.setViewVisibility(R.id.tv_minute4_on, ((minutes & 4) > 0) ? View.VISIBLE : View.GONE);
        view.setViewVisibility(R.id.tv_minute2_off, ((minutes & 2) > 0) ? View.GONE : View.VISIBLE);
        view.setViewVisibility(R.id.tv_minute2_on, ((minutes & 2) > 0) ? View.VISIBLE : View.GONE);
        view.setViewVisibility(R.id.tv_minute1_off, ((minutes & 1) > 0) ? View.GONE : View.VISIBLE);
        view.setViewVisibility(R.id.tv_minute1_on, ((minutes & 1) > 0) ? View.VISIBLE : View.GONE);
    }


}
