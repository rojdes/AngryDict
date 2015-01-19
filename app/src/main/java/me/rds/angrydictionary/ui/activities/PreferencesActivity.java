package me.rds.angrydictionary.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import me.rds.angrydictionary.LocalConsts;
import me.rds.angrydictionary.LocalPrefs;
import me.rds.angrydictionary.R;

public class PreferencesActivity extends ActionBarActivity {


    private static final String TAG_PERIOD = "p";
    private static final String TAG_ANSWER = "a";
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
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private SeekBar msbPeriodShow, msbAnswerTime;
    private TextView mtvPeriodShow, mtvAnswerClick;

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
        mtvPeriodShow = (TextView) findViewById(R.id.tv_prefs_period);
        mtvAnswerClick = (TextView) findViewById(R.id.tv_prefs_click_answer);
    }

    private void engineViews() {
        msbPeriodShow.setMax(LocalConsts.MAX_PERIOD_SHOW - 1);
        msbAnswerTime.setMax(LocalConsts.MAX_TIME_ANSWER - 1);
        msbPeriodShow.setProgress(LocalPrefs.getPeriodShow(this) - 1);
        msbAnswerTime.setProgress(LocalPrefs.getTimeAnswer(this) - 1);

        msbPeriodShow.setTag(TAG_PERIOD);
        msbAnswerTime.setTag(TAG_ANSWER);
        msbPeriodShow.setOnSeekBarChangeListener(mSeekBarChangelistener);
        msbAnswerTime.setOnSeekBarChangeListener(mSeekBarChangelistener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mtvPeriodShow.setText(String.valueOf(LocalPrefs.getPeriodShow(this)));
        mtvAnswerClick.setText(String.valueOf(LocalPrefs.getTimeAnswer(this)));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalPrefs.setPeriodShow(this, Integer.valueOf(mtvPeriodShow.getText().toString()));
        LocalPrefs.setTimeAnswer(this, Integer.valueOf(mtvAnswerClick.getText().toString()));
    }
}
