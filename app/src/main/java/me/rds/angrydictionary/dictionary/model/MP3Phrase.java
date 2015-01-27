package me.rds.angrydictionary.dictionary.model;

/**
 * Created by D1m11n on 14.01.2015.
 */
public class MP3Phrase {


    public long id;

    public String wordOrSentences;

    public String mp3;

    public MP3Phrase() {
    }

    public MP3Phrase(String wordOrSentences, String mp3) {
        this.wordOrSentences = wordOrSentences;
        this.mp3 = mp3;
    }

}
