package common;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * http://stackoverflow.com/questions/19846541/what-is-windowmanager-in-android
 *
 * @author User
 */
public class WindowHelper {

    private View mLastView;

    public View getLastView() {
        return mLastView;
    }

    /**
     * <b>Need <uses-permission
     * android:name="android.permission.SYSTEM_ALERT_WINDOW"/> </b>
     *
     * @param context
     * @param idResLayout
     * @return
     */
    public void createSimple(Context context, int idResLayout) {
        new Builder(context, WindowHelper.this, idResLayout).build();
    }

    public void remove(Context context) {
        if (mLastView == null) {
            Log.e("WindowHelper", "lastView is null");
            return;
        }
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.removeView(mLastView);
        mLastView = null;
    }

    public interface OnClickListener {

        public void onClick(Object tag);
    }

    public static class Builder {
        private WindowHelper mHelper;
        private int mWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        private int mHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        private int mWindowType = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        private int mFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        private int mFormat = PixelFormat.TRANSLUCENT;
        private int mGravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        private int pX = 0, pY = 0;
        private Context mContext;
        private OnClickListener mOnClickListener;
        private int mTag = -1;

        private android.view.View.OnClickListener mViewClickListener = new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mOnClickListener != null) {
                    mOnClickListener.onClick(mTag > 0 ? v.getTag(mTag) : v
                            .getTag());
                }
            }
        };

        public Builder(Context context, int layoutId) {
            mHelper = new WindowHelper();
            mContext = context;
            mHelper.mLastView = ((LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(layoutId, null, false);
        }

        /**
         * @param context
         * @param helper   if you want remove view after using - use this
         * @param layoutId
         */
        public Builder(Context context, WindowHelper helper, int layoutId) {
            mHelper = helper == null ? new WindowHelper() : helper;
            mContext = context;
            mHelper.mLastView = ((LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(layoutId, null, false);
        }

        public Builder(Context context, WindowHelper helper, View view) {
            mHelper = helper == null ? new WindowHelper() : helper;
            mContext = context;
            mHelper.mLastView = view;
        }

        /**
         * like WindowManager.LayoutParams.WRAP_CONTENT or in px
         *
         * @param layoutWidth  width or from LayoutParams
         * @param LayoutHeight height  or from LayoutParams
         * @return
         */
        public Builder setWindowParams(int layoutWidth, int LayoutHeight) {
            mWidth = layoutWidth;
            mHeight = LayoutHeight;
            return this;
        }

        public Builder setWindowParams(float width, float height) {
            mWidth = (int) width;
            mHeight = (int) height;
            return this;
        }

        /**
         * @param type from WindowManager.Type_*
         * @return
         */
        public Builder setWindowType(int type) {
            mWindowType = type;
            return this;
        }

        /**
         * @param flag from WindowManager.LayoutParams.FLAG_*
         * @return
         */
        public Builder setFlag(int flag) {
            mFlags = flag;
            return this;
        }

        /**
         * @param gravity like Gravity.TOP | Gravity.CENTER_HORIZONTAL;
         * @return
         */
        public Builder setGravity(int gravity) {
            mGravity = gravity;
            return this;
        }


        public Builder setWindowPos(int x, int y) {
            pX = x;
            pY = y;
            return this;
        }

        /**
         * like  PixelFormat.TRANSLUCENT;
         *
         * @return
         */
        public Builder setFormat(int format) {
            mFormat = format;
            return this;
        }


        /**
         * To use listener, you need set tag for all ids of elements
         */
        public Builder setListeners(OnClickListener listener, int[] idsOfClicking) {
            return setListeners(listener, -1, idsOfClicking);
        }


        /**
         * To use listener, you need set tag for all ids of elements
         *
         * @param listener
         * @param tagKey        is key > 0 for getting tag from <b>any</b> id
         * @param idsOfClicking
         */
        public Builder setListeners(OnClickListener listener, int tagKey,
                                    int... idsOfClicking) {
            if (listener == null || idsOfClicking == null
                    || idsOfClicking.length == 0)
                return this;
            mTag = tagKey;
            mOnClickListener = listener;
            for (int i : idsOfClicking) {
                mHelper.mLastView.findViewById(i).setOnClickListener(
                        mViewClickListener);
            }
            return this;
        }

        public WindowHelper build() {
            WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                    mWidth,
                    mHeight,
                    mWindowType,
                    mFlags,
                    mFormat);
            p.gravity = mGravity;
            p.x = pX;
            p.y = pY;
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            windowManager.addView(mHelper.getLastView(), p);
            return mHelper;
        }
    }

}
