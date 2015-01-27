package common;

import android.content.Context;
import android.content.res.Configuration;

public class ScreenHelper {


    private static ScreenSizeDPI mPortraitDensity;
    private static ScreenSizeDPI mLandscapeDensity;

    private static ScreenSizePx mPortraitSize;
    private static ScreenSizePx mLandscapeSize;

    private static float mPxInDpi;


    public static ScreenSizeDPI getScreenDensity(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return getLandscapeDensity(context);
        else
            return getPortraitDensity(context);
    }



    private static ScreenSizeDPI getPortraitDensity(Context context) {
        if (mPortraitDensity == null) {
            ScreenSizePx s = getPortraitSize(context);
            mPortraitDensity = new ScreenSizeDPI();
            mPortraitDensity.heightDpi = (s.mHeightPx - 0.5f) / context.getResources().getDisplayMetrics().density;
            mPortraitDensity.widthDpi = (s.mWidthPx - 0.5f) / context.getResources().getDisplayMetrics().density;
        }
        return mPortraitDensity;
    }

    private static ScreenSizeDPI getLandscapeDensity(Context context) {
        if (mLandscapeDensity == null) {
            ScreenSizePx s = getLandscapeSize(context);
            mLandscapeDensity = new ScreenSizeDPI();
            mLandscapeDensity.widthDpi = (s.mWidthPx - 0.5f) / context.getResources().getDisplayMetrics().density;
            mLandscapeDensity.heightDpi = (s.mHeightPx - 0.5F) / context.getResources().getDisplayMetrics().density;
        }
        return mLandscapeDensity;
    }


    public static ScreenSizePx getSize(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return getLandscapeSize(context);
        else
            return getPortraitSize(context);
    }

    private static ScreenSizePx getPortraitSize(Context context) {
        if (mPortraitSize == null) {
            mPortraitSize = new ScreenSizePx();
            mPortraitSize.mWidthPx = context.getResources().getDisplayMetrics().widthPixels;
            mPortraitSize.mHeightPx = context.getResources().getDisplayMetrics().heightPixels;
        }
        return mPortraitSize;
    }

    private static ScreenSizePx getLandscapeSize(Context context) {
        if (mLandscapeSize == null) {
            mLandscapeSize = new ScreenSizePx();
            mLandscapeSize.mWidthPx = context.getResources().getDisplayMetrics().widthPixels;
            mLandscapeSize.mHeightPx = context.getResources().getDisplayMetrics().heightPixels;
        }
        return mLandscapeSize;
    }

    public static float getPxInDpi(Context context) {
        return mPxInDpi;
    }

    public static class ScreenSizeDPI {

        private float widthDpi;
        private float heightDpi;


        public float getWidth() {
            return widthDpi;
        }

        public float getHeight() {
            return heightDpi;
        }
    }

    public static class ScreenSizePx {
        private float mWidthPx;
        private float mHeightPx;

        public float getWidth() {
            return mWidthPx;
        }

        public float getHeight() {
            return mHeightPx;
        }


    }

}
