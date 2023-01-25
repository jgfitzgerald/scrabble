/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: WordJudge
 */

 //package
package com.compmta.scrabble.util;

//imports
import com.compmta.scrabble.model.Tile;


public class WordJudge {

    /**
     * scoreWord scores the word
     * that the player has just played.
     * 
     * @param word the word that the player has just played
     * @return the score of the word
     */
    public static int scoreWord(Tile[] word) {
        int score = 0;
        for (int i = 0; i < word.length; i++) {
            Tile curr = word[i];
            score = curr.effect(word, score, i);
        }
        for (int i = 0; i < word.length; i++) {
            Tile curr = word[i];
            score = curr.effect(word, score, word.length);
        }
        return score;
    } //scoreWord(Tile[] word)

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
