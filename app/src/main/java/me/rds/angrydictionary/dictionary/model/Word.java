package me.rds.angrydictionary.dictionary.model;


import android.text.TextUtils;

import java.util.StringTokenizer;

public class Word {


    private static final String SEPARATOR = "::";

    public long id;

    public WordType wordType;

    public Language primaryLang;

    public Language secondaryLang;

    public String word;

    public String[] translates;


    public Word() {
    }

    public Word(WordType type, Language primaryLang, Language secondaryLang, String word, String... translates) {
        wordType = type;
        this.word = word;
        this.primaryLang = primaryLang;
        this.secondaryLang = secondaryLang;
        this.translates = translates;
    }

    public Word(WordType type, String word, Language primaryLang, Language secondaryLang) {
        this(type, primaryLang, secondaryLang, word, new String[]{""});
    }

    public Word(Language primaryLang, Language secondaryLang, String word) {
        this(WordType.OTHER, primaryLang, secondaryLang, word, new String[]{""});
    }

    public Word(Language primaryLang, Language secondaryLang, String word, String... translates) {
        this(WordType.OTHER, primaryLang, secondaryLang, word, translates);
    }

    public String getTranslatesAsString() {
        if (translates == null || translates.length == 0) return "";
        StringBuilder bb = new StringBuilder();
        for (int i = 0; i < translates.length; i++)
            bb.append(translates[i] + SEPARATOR);
        return bb.delete(bb.length() - 2, bb.length()).toString();
    }

    public void setTranslatesFromDB(String array) {
        if (TextUtils.isEmpty(array))
            translates = new String[0];
        else if (!array.contains(SEPARATOR))
            translates = new String[]{array};
        else {
            StringTokenizer tt = new StringTokenizer(array, SEPARATOR);
            translates = new String[tt.countTokens()];
            int i = 0;
            while (tt.hasMoreTokens())
                translates[i++] = tt.nextToken();
        }
    }
}
