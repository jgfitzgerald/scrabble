package com.compmta.scrabble.model;

import com.compmta.scrabble.util.Letter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TripleWordSquare extends Square {

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
        if (index == word.length()) {
            return score * 3;
        } else {
            return score + Letter.map.get(word.charAt(index)).getBaseScore();
        }
    }

}
