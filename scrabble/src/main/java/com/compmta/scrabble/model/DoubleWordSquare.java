package com.compmta.scrabble.model;

import com.compmta.scrabble.util.Letter;

public class DoubleWordSquare extends Square {

    public static final String LABEL = "DW";
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
        if (index == word.length() && this.getLetter() == DEFAULT) {
            return score;
        } else if (index == word.length()) {
            return 0;
        } else {
            return Letter.map.get(word.charAt(index)).getBaseScore();
        }
    }

}
