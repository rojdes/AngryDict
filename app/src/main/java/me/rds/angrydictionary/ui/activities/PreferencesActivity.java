package me.rds.angrydictionary.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import common.ScreenHelper;
import common.views.ResizableView;
import common.views.LockableScrollView;
import me.rds.angrydictionary.AppConsts;
import me.rds.angrydictionary.AppIntents;
import me.rds.angrydictionary.AppPrefs;
import me.rds.angrydictionary.R;
import me.rds.angrydictionary.services.media.MediaIntentService;
import me.rds.angrydictionary.services.network.UpdateDBService;
import me.rds.angrydictionary.ui.adapters.DifficultyAdapter;
import me.rds.angrydictionary.ui.adapters.model.DifficultyLevel;

public class PreferencesActivity extends ActionBarActivity {



    private static final String TAG="PREFS_ACTIVITY";

    private static final String TAG_PERIOD = "p";
    private static final String TAG_ANSWER = "a";
    private static final String TAG_TRANSPARENCY = "t";

    private SeekBar msbPeriodShow, msbAnswerTime, msbTransparency;
    private TextView mtvPeriodShow, mtvAnswerClick, mtvTransparency;
    private Spinner mspnDifficulty;

    private RelativeLayout mrltScreenPhantom;
    private ResizableView mWindowView;


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

    private AdapterView.OnItemSelectedListener mOnItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.e(TAG, "POSITION IS  = " + position + "; and is level is = " + DifficultyLevel.getLevelsList(PreferencesActivity.this)[position]);
            Log.e(TAG," NEW  = " + DifficultyLevel.getParamsOf(PreferencesActivity.this, position).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };



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
        mspnDifficulty=(Spinner)findViewById(R.id.spn_prefs_difficulty);
        mWindowView= (ResizableView)findViewById(R.id.rsw_prefs);
    }

    private void engineViews() {
        msbPeriodShow.setMax(AppConsts.MAX_PERIOD_SHOW - 1);
        msbAnswerTime.setMax(AppConsts.MAX_TIME_ANSWER - 1);
        msbPeriodShow.setProgress(AppPrefs.getPeriodShow(this) - 1);
        msbAnswerTime.setProgress(AppPrefs.getTimeAnswer(this) - 1);
        msbTransparency.setMax(AppConsts.TRANSPARENCY);
        msbTransparency.setProgress(AppPrefs.getTransparency(this));
        msbPeriodShow.setTag(TAG_PERIOD);
        msbAnswerTime.setTag(TAG_ANSWER);
        msbTransparency.setTag(TAG_TRANSPARENCY);
        msbPeriodShow.setOnSeekBarChangeListener(mSeekBarChangelistener);
        msbAnswerTime.setOnSeekBarChangeListener(mSeekBarChangelistener);
        msbTransparency.setOnSeekBarChangeListener(mSeekBarChangelistener);
        mspnDifficulty.setAdapter(DifficultyAdapter.getInstance(this));
        mspnDifficulty.setOnItemSelectedListener(mOnItemSelected);
        ((ResizableView)findViewById(R.id.rsw_prefs)).setScrollViewContainer((LockableScrollView) findViewById(R.id.scrollView));
    }

    @Override
    protected void onResume() {
        super.onResume();
        buildScreenPhantom();
        mtvPeriodShow.setText(String.valueOf(AppPrefs.getPeriodShow(this)));
        mtvAnswerClick.setText(String.valueOf(AppPrefs.getTimeAnswer(this)));
        mtvTransparency.setText(String.valueOf(AppPrefs.getTransparency(this)));
        //fakeTestMp3();

  //      ListsUtils.printAll(TAG,DictionaryManager.getInstance(this).getAvailableList());
    }

    private void fakeTestMp3() {
        ///storage/sdcard0/language/mp3/recognize.mp3
        Intent mPlayIntent = new Intent(this, MediaIntentService.class);
        mPlayIntent.setAction(AppIntents.Action.PLAY_WORD);
        mPlayIntent.putExtra(AppIntents.Extra.PLAY_FILE, "recognize.mp3");
        startService(mPlayIntent);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

   }

    private void buildScreenPhantom(){
       Log.e(TAG, "measured = " + mrltScreenPhantom.getMeasuredWidth() + ", real = " + mrltScreenPhantom.getWidth());
       mrltScreenPhantom.getLayoutParams().height=getHeight();
       mrltScreenPhantom.requestLayout();
    }

    private int getHeight(){
        int h= AppPrefs.getPhantomScreenHeight(this);
        if (h>0)
            return h;
        ScreenHelper.ScreenSizePx pxs=ScreenHelper.getSize(this);
        h= (int)(pxs.getHeight()*mrltScreenPhantom.getMeasuredWidth()/pxs.getWidth());
        AppPrefs.setPhantomScreenHeight(this, h);
        return h;
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppPrefs.setPeriodShow(this, Integer.valueOf(mtvPeriodShow.getText().toString()));
        AppPrefs.setTimeAnswer(this, Integer.valueOf(mtvAnswerClick.getText().toString()));
        AppPrefs.setTransparency(this, Integer.valueOf(mtvTransparency.getText().toString()));
    }
}
