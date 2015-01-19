package me.rds.angrydictionary.dictionary.model;

public enum Language {


    RUS('r'),
    UKR('u'),
    ENG('e'),
    OTHER('o');

    private char key;

    private Language(char key) {
        this.key = key;
    }

    public static Language getFrom(char key) {
        switch (key) {
            case 'r':
                return RUS;
            case 'u':
                return UKR;
            case 'e':
                return ENG;
            default:
                return OTHER;
        }
    }

    public char getKey() {
        return key;
    }


}
