package me.rds.angrydictionary.dictionary.model;

public enum WordType {


    NOUN('n'),
    VERB('v'),
    OTHER('o');


    private char key;

    private WordType(char key) {
        this.key = key;
    }

    public static WordType getFrom(char key) {
        switch (key) {
            case 'n':
                return NOUN;
            case 'v':
                return VERB;
            default:
                return OTHER;
        }
    }

    public char getKey() {
        return key;
    }
}
