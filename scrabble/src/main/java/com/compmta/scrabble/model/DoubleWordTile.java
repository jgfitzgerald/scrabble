/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: DoubleWordTile
 */

//package
package com.compmta.scrabble.model;

//imports
import com.compmta.scrabble.util.Letter;

public class DoubleWordTile extends Tile {

    /**
     * Constructor
     * Constructor for creating a DoubleWordTile
     * Takes the row and column of the tile
     * 
     * @param i row
     * @param j column
     */
    public DoubleWordTile(int i, int j) {
        super(i, j);
    } //Constructor

    /**
     * Doubles the score of the word that is on the tile
     */
    @Override
    public int effect(Tile[] word, int score, int index) {
        if (index == word.length) {
            return score * 2;
        } else {
            return score + Letter.map.get(word[index].getLetter()).getBaseScore();
        }
    } //effect(Tile[] word, int score, int index)

}
