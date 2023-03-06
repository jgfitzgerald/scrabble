package com.compmta.scrabble.model;

import com.compmta.scrabble.util.Letter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TripleLetterSquare extends Square {

    public static final String LABEL = "TL";
    private char letter;
    private static final char DEFAULT = '\u0000';


    /**
     *
     * @param word the word the player has played
     * @param score the score of the word
     * @param index the index to check
     * @return the updated score
     */
    @Override
    public int effect(String word, int score, int index) {
        if (index != word.length() && this.getLetter() == DEFAULT) {
            return Letter.map.get(word.charAt(index)).getBaseScore() * 3;
        } else if (index != word.length()) {
            return Letter.map.get(word.charAt(index)).getBaseScore();
        } else return 0;
    }
}
