package me.rds.angrydictionary.dictionary.model;

import android.text.TextUtils;

import java.util.StringTokenizer;

/**
 * Created by D1m11n on 14.01.2015.
 */
public class PhraseUsage {

    private static final String SEPARATOR = "::";

    public long id;

    public String wordOrSentences;

    public String[] verbForms;

    public String example;

    public WordType type;

    public String description;


    public String getVerbFormsAsString() {
        if (verbForms == null || verbForms.length == 0) return "";
        StringBuilder bb = new StringBuilder();
        for (int i = 0; i < verbForms.length; i++)
            bb.append(verbForms[i] + SEPARATOR);
        return bb.delete(bb.length() - 2, bb.length()).toString();
    }

    public void setVerbFormsFromDB(String array) {
        if (TextUtils.isEmpty(array))
            verbForms = new String[0];
        else if (!array.contains(SEPARATOR))
            verbForms = new String[]{array};
        else {
            StringTokenizer tt = new StringTokenizer(array, SEPARATOR);
            verbForms = new String[tt.countTokens()];
            int i = 0;
            while (tt.hasMoreTokens())
                verbForms[i++] = tt.nextToken();
        }
    }


}
