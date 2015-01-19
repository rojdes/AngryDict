package me.rds.angrydictionary.dictionary.model;

/**
 * Created by D1m11n on 14.01.2015.
 */
public class AudioPhrase {


    public long id;

    public String wordOrSentences;

    public String mp3;

    public AudioPhrase() {
    }

    public AudioPhrase(String wordOrSentences, String mp3) {
        this.wordOrSentences = wordOrSentences;
        this.mp3 = mp3;
    }

}
