package com.compmta.scrabble.model;

import com.compmta.scrabble.util.Letter;

public class DoubleLetterSquare extends Square {
    public static final String LABEL = "DL";

    private static final char DEFAULT = '\u0000';
    private char letter;

    /**
     *
     * @param word the word the player has played
     * @param score the score of the word
     * @param index the index to check
     * @return the updated score
     */
    @Override
    public int effect(String word, int score, int index) {
        if (index > word.length()) {
            throw new IllegalArgumentException("Invalid index specified.");
        }
        if (index != word.length() && this.getLetter() == DEFAULT) {
            return Letter.map.get(word.charAt(index)).getBaseScore() * 2;
        } else if (index != word.length()) {
            return Letter.map.get(word.charAt(index)).getBaseScore();
        } else return 0;
    }
}
