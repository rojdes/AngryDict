package me.rds.angrydictionary.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import common.ScreenHelper;
import common.WindowHelper;
import me.grantland.widget.AutofitHelper;
import me.rds.angrydictionary.AppConsts;
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
    private static final int ID_PROGRESS =R.id.wndPb ;
    protected static final int[] IDS_BUTTONS = new int[]{R.id.wndBtnAsk1, R.id.wndBtnAsk2, R.id.wndBtnAsk3, R.id.wndBtnAsk4};
    private Context mContext;
    protected WindowHelper mWindowHelper;
    protected final Intent mPlayIntent;

    private ProgressBar mpbProgress;




    /** THis class not control releasing window in helper
     *
     * @param context
     * @param windowHelper
     */
    public DictionaryWindow(Context context, WindowHelper windowHelper) {
        mContext = context;
        mWindowHelper = windowHelper;
        mPlayIntent = new Intent(mContext, MediaIntentService.class);
    }

    public void build(){
       buildWindow(DictionaryManager.getInstance(mContext).getWord(Language.ENG));
    }

    private void buildWindow(final TrueWord w) {
        final View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.wnd_lang_ask, null, false);
        initSubViews(view,w);
        new WindowHelper.Builder(mContext, mWindowHelper, view).setListeners(new WindowHelper.OnClickListener() {

            @Override
            public void onClick(Object tag) {
                doOnClick(tag,w,view);
            }
        }, IDS_BUTTONS)
                .setWindowParams(ScreenHelper.getPxInDpi(mContext, true)* AppConsts.MIN_WINDOW_WIDTHdp+1, ScreenHelper.getPxInDpi(mContext,false)*AppConsts.MIN_WINDOW_HEIGHTdp+1)
                .setFormat(PixelFormat.TRANSPARENT)
                .setGravity(Gravity.CENTER)
                .build();
    }


    private void doOnClick(Object tag, TrueWord w, final View view){
        boolean res=Integer.valueOf((String) tag) == w.trueWordNumber;

        mContext.startService(res?onSelectTrueWord(w):onSelectFalseWord());
    }


    private void initSubViews(View view, Word w) {
        initButtons(view,w);
        initTitle(view,w);
        initProgress(view);

    }

    private void initProgress(View view) {
        mpbProgress =(ProgressBar)view.findViewById(ID_PROGRESS);
        mpbProgress.setVisibility(View.GONE);
    }

    private void initTitle(View view, Word w){
        TextView vv=(TextView) view.findViewById(R.id.wndTvAsk);
        vv.setText("WORD IS: \"" + w.word + "\"");
        AutofitHelper.create(vv);
    }

    private void initButtons(View rootView, Word w){
        Button b0=(Button) rootView.findViewById(R.id.wndBtnAsk1);
        Button b1=(Button) rootView.findViewById(R.id.wndBtnAsk2);
        Button b2=(Button) rootView.findViewById(R.id.wndBtnAsk3);
        Button b3=(Button) rootView.findViewById(R.id.wndBtnAsk4);
        AutofitHelper.create(b0);
        AutofitHelper.create(b1);
        AutofitHelper.create(b2);
        AutofitHelper.create(b3);
        b0.setText(w.translates[0]);
        b1.setText(w.translates[1]);
        b2.setText(w.translates[2]);
        b3.setText(w.translates[3]);
    }

    private Intent onSelectTrueWord(Word w){
        Log.e(TAG, "onSelectTrueWord");
        releaseWindow();
        mPlayIntent.setAction(AppIntents.Action.PLAY_WORD);
        mPlayIntent.putExtra(AppIntents.Extra.PLAY_WORD, DictionaryManager.getInstance(mContext).getMP3For(w.word));
        return mPlayIntent;
    }

    private Intent onSelectFalseWord(){
        Log.e(TAG, "onSelectFalseWord");
        mPlayIntent.setAction(AppIntents.Action.PLAY_ERROR);
        return mPlayIntent;
    }

    private void releaseWindow(){
        mWindowHelper.remove(mContext);
        mpbProgress=null;
    }
}
