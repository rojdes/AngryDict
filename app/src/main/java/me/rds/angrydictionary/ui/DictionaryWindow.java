package me.rds.angrydictionary.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import common.WindowHelper;
import me.grantland.widget.AutofitHelper;
import me.rds.angrydictionary.AppIntents;
import me.rds.angrydictionary.AppPrefs;
import me.rds.angrydictionary.R;
import me.rds.angrydictionary.dictionary.managers.DictionaryManager;
import me.rds.angrydictionary.dictionary.model.Language;
import me.rds.angrydictionary.dictionary.model.TrueWord;
import me.rds.angrydictionary.dictionary.model.Word;
import me.rds.angrydictionary.helpers.DifficultyLevelWindowHelper;
import me.rds.angrydictionary.helpers.model.AbsoluteWindowParams;
import me.rds.angrydictionary.services.media.MediaIntentService;
import me.rds.angrydictionary.ui.toasts.DictToast;

/**
 * Created by D1m11n on 20.01.2015.
 */
public class DictionaryWindow {

    private static final String TAG="DICTIONARY_WINDOW";
    private static final int ID_PROGRESS_BAR =R.id.wnd_pb;
    private static final int ID_PROGRESS_COUNTER =R.id.wnd_tv_counter;
    private static final int ID_PROGRESS =R.id.wnd_rlt_progress;

    protected static final int[] IDS_BUTTONS = new int[]{R.id.wnd_btn_ask1, R.id.wnd_btn_ask2, R.id.wnd_btn_ask3, R.id.wnd_btn_ask4};
    private Context mContext;
    protected WindowHelper mWindowHelper;
    protected final Intent mPlayIntent;



    //private ProgressBar mpbProgress;
    private TextView mtvProgressCounter;
    private RelativeLayout mrltProgress;
    private Button [] mbtnsAnswers = new Button[4];
    private int mTransitionCount=0;


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
        if (w==null)
            return;
        final View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.wnd_lang_ask, null, false);
        initSubViews(view, w);
        AbsoluteWindowParams params = DifficultyLevelWindowHelper.getWindowSizeFor(mContext, AppPrefs.getSystemWindowLevel(mContext));
        Log.e(TAG, "params = " + params.toString());

        new WindowHelper.Builder(mContext, mWindowHelper, view).setListeners(new WindowHelper.OnClickListener() {

            @Override
            public void onClick(Object tag) {
                doOnClick(tag, w, view);
            }
        }, IDS_BUTTONS)
                .setWindowParams(params.width, params.height)
                .setFormat(PixelFormat.TRANSPARENT)
                .setGravity(Gravity.CENTER)
                .build();
    }


    private void doOnClick(Object tag, TrueWord w, final View view){
        boolean res=Integer.valueOf((String) tag) == w.trueWordNumber;
        if (!res)
           startCountDown(view);
         else
            releaseWindow();
        mContext.startService(res ? onSelectTrueWord(w) : onSelectFalseWord());
    }

    private void startCountDown(final View view){
        for (int i=0; i<4; i++){
            mbtnsAnswers[i].setEnabled(false);
        }
        mrltProgress.setVisibility(View.VISIBLE);
        new CountDownTimer(AppPrefs.getTimeAnswer(mContext)*1000L, 500L) {

            public void onTick(long millisUntilFinished) {
                mtvProgressCounter.setText("" + (int) (millisUntilFinished/1000));
            }

            public void onFinish() {
                mrltProgress.setVisibility(View.GONE);
                for (int i=0; i<4; i++){
                    mbtnsAnswers[i].setEnabled(true);
                }
            }
        }.start();
    }

    private void initSubViews(View view, Word w) {
        initRootLayout(view);
        initButtons(view, w);
        initTitle(view, w);
        initProgress(view);

    }

    private void initRootLayout(View view) {
       View v=view.findViewById(R.id.wnd_root_layout);
        int alpha=255 * AppPrefs.getTransparency(mContext) / 100;

        ColorDrawable cl1= new ColorDrawable(mContext.getResources().getColor(R.color.transtion_item1)+alpha*0x1000000);
        ColorDrawable cl2= new ColorDrawable(mContext.getResources().getColor(R.color.transtion_item2)+alpha*0x1000000);
        TransitionDrawable transition = new TransitionDrawable( new Drawable[]{cl1,cl2});
        if(Build.VERSION.SDK_INT<16)
           v.setBackgroundDrawable(transition);
        else
            v.setBackground(transition);
        mTransitionCount=0;
        startCyclicTransition(transition);

    }

    private void startCyclicTransition(final TransitionDrawable trans){
        if (mTransitionCount >=0) {
            if(mTransitionCount%2==0) {
                trans.startTransition(2000);
            }else {
                trans.reverseTransition(2000);
            }
            mTransitionCount++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startCyclicTransition(trans);
                }
            }, 2000);
        }
    }

    private void initProgress(View view) {
        //mpbProgress =(ProgressBar)view.findViewById(ID_PROGRESS_BAR);
        mtvProgressCounter=(TextView)view.findViewById(ID_PROGRESS_COUNTER);
        mrltProgress=(RelativeLayout)view.findViewById(ID_PROGRESS);
        mrltProgress.setVisibility(View.GONE);
    }

    private void initTitle(View view, Word w){
        TextView vv=(TextView) view.findViewById(R.id.wndTvAsk);
        vv.setText("WORD IS: \"" + w.word + "\"");
        AutofitHelper.create(vv);
    }

    private void initButtons(View rootView, Word w){
        mbtnsAnswers[0]=(Button) rootView.findViewById(R.id.wnd_btn_ask1);
        mbtnsAnswers[1]=(Button) rootView.findViewById(R.id.wnd_btn_ask2);
        mbtnsAnswers[2]=(Button) rootView.findViewById(R.id.wnd_btn_ask3);
        mbtnsAnswers[3]=(Button) rootView.findViewById(R.id.wnd_btn_ask4);
        for (int i=0; i<4; i++){
            AutofitHelper.create(mbtnsAnswers[i]);
            mbtnsAnswers[i].setText(w.translates[i]);
        }
    }

    private Intent onSelectTrueWord(TrueWord w){
        Log.e(TAG, "onSelectTrueWord");
        mPlayIntent.setAction(AppIntents.Action.PLAY_WORD);
        String mp3=DictionaryManager.getInstance(mContext).getMP3For(w.word);
        Log.e("DICTIONARY", "MP3 is = " + mp3  +  ", word is " + w.word);
        mPlayIntent.putExtra(AppIntents.Extra.PLAY_FILE, mp3);
        DictToast.createToast(mContext, w.word + " = " + w.translates[w.trueWordNumber]);
        return mPlayIntent;
    }

    private Intent onSelectFalseWord(){
        Log.e(TAG, "onSelectFalseWord");
        mPlayIntent.setAction(AppIntents.Action.PLAY_ERROR);
        return mPlayIntent;
    }

    private void releaseWindow(){
        mWindowHelper.remove(mContext);
        //mpbProgress=null;
        mtvProgressCounter=null;
        mrltProgress=null;
        mTransitionCount=-1;
        for (int i=0; i<4;i ++)
            mbtnsAnswers[i]=null;
    }
}
