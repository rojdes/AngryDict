package me.rds.angrydictionary.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import common.ScreenHelper;
import me.rds.angrydictionary.LocalConsts;
import me.rds.angrydictionary.LocalPrefs;
import me.rds.angrydictionary.R;

public class PreferencesActivity extends ActionBarActivity {



    private static final String TAG="PREFS_ACTIVITY";

    private static final String TAG_PERIOD = "p";
    private static final String TAG_ANSWER = "a";
    private static final String TAG_TRANSPARENCY = "t";


    private SeekBar.OnSeekBarChangeListener mSeekBarChangelistener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getTag().toString()) {
                case TAG_PERIOD:
                    mtvPeriodShow.setText(String.valueOf(progress + 1));
                    break;
                case TAG_ANSWER:
                    mtvAnswerClick.setText(String.valueOf(progress + 1));
                    break;
                case TAG_TRANSPARENCY:
                    mtvTransparency.setText(String.valueOf(progress));
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };


    private SeekBar msbPeriodShow, msbAnswerTime, msbTransparency;
    private TextView mtvPeriodShow, mtvAnswerClick, mtvTransparency;

    private RelativeLayout mrltScreenPhantom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        findViews();
        engineViews();
    }

    private void findViews() {
        msbPeriodShow = (SeekBar) findViewById(R.id.sb_prefs_show_time);
        msbAnswerTime = (SeekBar) findViewById(R.id.sb_prefs__click_answer_time);
        msbTransparency=(SeekBar)findViewById(R.id.sb_prefs_transparency);
        mtvPeriodShow = (TextView) findViewById(R.id.tv_prefs_period);
        mtvAnswerClick = (TextView) findViewById(R.id.tv_prefs_click_answer);
        mtvTransparency=(TextView)findViewById(R.id.tv_prefs_transparency);
        mrltScreenPhantom =(RelativeLayout)findViewById(R.id.rlt_prefs_screen);
    }

    private void engineViews() {
        msbPeriodShow.setMax(LocalConsts.MAX_PERIOD_SHOW - 1);
        msbAnswerTime.setMax(LocalConsts.MAX_TIME_ANSWER - 1);
        msbPeriodShow.setProgress(LocalPrefs.getPeriodShow(this) - 1);
        msbAnswerTime.setProgress(LocalPrefs.getTimeAnswer(this) - 1);
        msbTransparency.setMax(LocalConsts.TRANSPARENCY);
        msbTransparency.setProgress(LocalPrefs.getTransparency(this));
        msbPeriodShow.setTag(TAG_PERIOD);
        msbAnswerTime.setTag(TAG_ANSWER);
        msbTransparency.setTag(TAG_TRANSPARENCY);
        msbPeriodShow.setOnSeekBarChangeListener(mSeekBarChangelistener);
        msbAnswerTime.setOnSeekBarChangeListener(mSeekBarChangelistener);
        msbTransparency.setOnSeekBarChangeListener(mSeekBarChangelistener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        buildScreenPhantom();
        mtvPeriodShow.setText(String.valueOf(LocalPrefs.getPeriodShow(this)));
        mtvAnswerClick.setText(String.valueOf(LocalPrefs.getTimeAnswer(this)));
        mtvTransparency.setText(String.valueOf(LocalPrefs.getTransparency(this)));
    }


    private void buildScreenPhantom(){
       Log.e(TAG, "measured = " + mrltScreenPhantom.getMeasuredWidth() + ", real = " + mrltScreenPhantom.getWidth());
       mrltScreenPhantom.getLayoutParams().height=getHeight();
       mrltScreenPhantom.requestLayout();
    }

    private int getHeight(){
//        int height=LocalPrefs.getPhantomScreenHeight(this);
//        if (height>0)
//            return height;
        ScreenHelper.ScreenSizePx pxs=ScreenHelper.getSize(this);
        int h= (int)(pxs.getHeight()*mrltScreenPhantom.getMeasuredWidth()/pxs.getWidth());
        LocalPrefs.setPhantomScreenHeight(this,h);
        return h;
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalPrefs.setPeriodShow(this, Integer.valueOf(mtvPeriodShow.getText().toString()));
        LocalPrefs.setTimeAnswer(this, Integer.valueOf(mtvAnswerClick.getText().toString()));
        LocalPrefs.setTransparency(this, Integer.valueOf(mtvTransparency.getText().toString()));
    }
}
