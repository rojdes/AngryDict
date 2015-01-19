package me.rds.angrydictionary.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.rds.angrydictionary.R;
import me.rds.angrydictionary.dictionary.DictionaryManager;
import me.rds.angrydictionary.dictionary.model.Language;
import me.rds.angrydictionary.dictionary.model.Word;

public class FragmentFactory extends Fragment {


    private static final String TAG = "FRAGMENT";
    private int index;

    public static Fragment getInstance(int index) {
        FragmentFactory f = new FragmentFactory();
        f = new FragmentFactory();
        f.index = index;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.frg, null, false);
        ((TextView) mRootView.findViewById(R.id.tv_frg)).setText(String.valueOf(index));
        ((Button) mRootView.findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSmthOnClick(v);
            }
        });
        mRootView.getWidth();
        return mRootView;
    }


    private void doSmthOnClick(View v) {
        Word w = new Word(Language.ENG, Language.UKR, "introduce", new String[]{"включати", "вводити", "знайомити", "представляти"});
//        Log.e(TAG + index, w.getTranslatesAsString());
//
//        Word w2= new Word();
//
//        w2.setTranslatesFromDB(w.getTranslatesAsString());
//        Log.e(TAG + index, w2.getTranslatesAsString());

        Log.e(TAG + index, DictionaryManager.getInstance(getActivity()).getWord("introduce").getTranslatesAsString());

    }
}
