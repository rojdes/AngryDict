package me.rds.angrydictionary;

public interface LocalIntents {


    public static String ACTION_TIME = "me.rds.angrydictionary.time";
    public static String ACTION_WIDGET_CLICK_AM_PM = "me.rds.angrydictionary.widget.click.ampm";
    public static String ACTION_WIDGET_CLICK_PREFS = "me.rds.angrydictionary.widget.click.prefs";
    public static String ACTION_PLAY_ERROR = "me.rds.angrydictionary.play.error";
    public static String ACTION_PLAY_WORD = "me.rds.angrydictionary.play.word";
    public static String EXTRA_PLAY_WORD = "me.rds.angrydictionary.word.extra";

    public static interface Extra {
    }

}
