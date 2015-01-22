package me.rds.angrydictionary;

public interface AppIntents {






    public static interface Action{
        public static String TIME = "me.rds.angrydictionary.time";
        public static String WIDGET_CLICK_AM_PM = "me.rds.angrydictionary.widget.click.ampm";
        public static String WIDGET_CLICK_PREFS = "me.rds.angrydictionary.widget.click.prefs";
        public static String PLAY_ERROR = "me.rds.angrydictionary.play.error";
        public static String PLAY_WORD = "me.rds.angrydictionary.play.word";
        public static String LOAD="me.rds.angrydictionary.load";
        public static String LOAD_ANSWER="me.rds.angrydictionary.load.answer";
    }


    public static interface Extra {
        public static String PLAY_WORD = "me.rds.angrydictionary.word.extra";
        public static String LINK = "me.rds.angrydictionary.link";
        public static String UPDATE_DICT = "me.rds.angrydictionary.dict.update";
        public static String SERVER_RESPONSE = "me.rds.angrydictionary.server.response";
    }

}
