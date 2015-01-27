package me.rds.angrydictionary.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import common.ScreenHelper;
import common.WindowHelper;
import me.rds.angrydictionary.AppIntents;
import me.rds.angrydictionary.R;
import me.rds.angrydictionary.dictionary.managers.DictionaryManager;
import me.rds.angrydictionary.dictionary.model.Language;
import me.rds.angrydictionary.dictionary.model.TrueWord;
import me.rds.angrydictionary.dictionary.model.Word;
import me.rds.angrydictionary.services.media.MediaIntentService;

/**
 * Created by D1m11n on 20.01.2015.
 */
public class DictionaryWindow {

    private static final String TAG="DICTIONARY_WINDOW";

    private Context mContext;
    protected WindowHelper mWindowHelper;
    protected final Intent mPlayIntent;
    protected int[] ids = new int[]{R.id.wndBtnAsk1, R.id.wndBtnAsk2, R.id.wndBtnAsk3, R.id.wndBtnAsk4};


    /** THis class not control releasing window in helper
     *
     * @param context
     * @param windowHelper
     */
    public DictionaryWindow(Context context,  WindowHelper windowHelper){
      mContext=context;
      mWindowHelper=windowHelper;
      mPlayIntent = new Intent(mContext, MediaIntentService.class);
    }


    public void build(){
       buildWindow(DictionaryManager.getInstance(mContext).getWord(Language.ENG));
    }

    private void buildWindow(final TrueWord w) {

        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.wnd_lang_ask, null, false);
        initSubViews(view,w);
        WindowHelper.Builder b = new WindowHelper.Builder(mContext, mWindowHelper, view).setListeners(new WindowHelper.OnClickListener() {

            @Override
            public void onClick(Object tag) {
                mContext.startService((Integer.valueOf((String) tag) == w.trueWordNumber)?onSelectTrueWord(w):onSelectFalseWord());
            }
        }, ids);
        ScreenHelper.ScreenSizePx px = ScreenHelper.getSize(mContext);
        b.setWindowParams(px.getWidth(), px.getHeight() / 2);
        b.setFormat(PixelFormat.TRANSPARENT);
        b.setWindowPos(0, 0);
        b.setGravity(Gravity.CENTER).build();
    }

    private void initSubViews(View view, Word w) {
        initButtons(view,w);
        ((TextView) view.findViewById(R.id.wndTvAsk)).setText("WORD IS:     \"" + w.word + "\"");

    }

    private void initButtons(View rootView, Word w){
        ((Button) rootView.findViewById(R.id.wndBtnAsk1)).setText(w.translates[0]);
        ((Button) rootView.findViewById(R.id.wndBtnAsk2)).setText(w.translates[1]);
        ((Button) rootView.findViewById(R.id.wndBtnAsk3)).setText(w.translates[2]);
        ((Button) rootView.findViewById(R.id.wndBtnAsk4)).setText(w.translates[3]);
    }

    private Intent onSelectTrueWord(Word w){
        Log.e(TAG, "onSelectTrueWord");
        mWindowHelper.remove(mContext);
        mPlayIntent.setAction(AppIntents.Action.PLAY_WORD);
        mPlayIntent.putExtra(AppIntents.Extra.PLAY_WORD, DictionaryManager.getInstance(mContext).getMP3For(w.word));
        return mPlayIntent;
    }

    private Intent onSelectFalseWord(){
        Log.e(TAG, "onSelectFalseWord");
        mPlayIntent.setAction(AppIntents.Action.PLAY_ERROR);
        return mPlayIntent;
    }
}
