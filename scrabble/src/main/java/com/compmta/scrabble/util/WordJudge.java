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
    public static int scoreMove(TurnInfo move) {
        String word = move.word();
        int[] start = move.startCoords();
        int[] end = move.endCoords();
        Board board = GameStateController.getGameState().getBoard();

        int score = 0;
        int index = 0;
        int direction;

        // prevents repeated code blocks
        if (start[0] == end[0]) {
            direction = 1;
        } else {
            direction = 0;
        }

        for (int i = start[direction]; i <= end[direction]; i++) {
            if (direction == 1) {
                score = board.getTile(start[direction], index).effect(word,score,index);
            } else {
                score = board.getTile(index,start[direction]).effect(word,score,index);
            }
            index++;
        }

        for (int i = start[direction]; i <= end[direction]; i++) {
            score = board.getTile(start[direction], index).effect(word,score,word.length());
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
