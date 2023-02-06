/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: WordJudge
 */

 //package
package com.compmta.scrabble.util;

//imports
import com.compmta.scrabble.controllers.DTO.TurnInfo;
import com.compmta.scrabble.controllers.GameStateController;
import com.compmta.scrabble.model.*;


public class WordJudge {

    /**
     * scoreWord scores the word
     * that the player has just played.
     * 
     * @param move the turn information for the player has just played
     * @return the score of the word inc. modifiers
     */
    /*public static int scoreMove(TurnInfo move) {

        //TODO implement scoring words

        String word = move.word();
        int row = move.row();
        int col = move.column();
        boolean isHorizontal = move.isHorizontal();

        int score = 0;

        int i = row;
        int j = col;

        if (!isHorizontal) {
            // find if word builds off another word to the left
        } else {
            while ()
            // find if word builds off another word above
            // run through "primary word" twice and add score
            // run through word again, for each, see if there are letters on right or left and score those words
        }

        return score;
    }*/

    /*public int scoreWord(String word, int row, int col, boolean isHorizontal) {

    }*/

    /**
     * verifyWord verifies the word that the player has just played
     * is a valid scrabble word.
     * 
     * @param word the word that the player has just played
     * @return if the word is a valid Scrabble word
     */
    public static boolean verifyWord(String word) {
        return com.compmta.scrabble.model.Dictionary.dictionary.contains(word);
    } //verifyWord(String word)
}
