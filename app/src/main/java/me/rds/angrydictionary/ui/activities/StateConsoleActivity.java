package me.rds.angrydictionary.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import me.rds.angrydictionary.R;

/**
 * Created by D1m11n on 17.03.2015.
 */
public class StateConsoleActivity extends Activity {


    private TextView mtvWordsInDB, mWordsInDBAction;
    //private TextView mtvWordsInDB, mWordsInDBAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_stat);
    }
}
