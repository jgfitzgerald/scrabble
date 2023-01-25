/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: RegularTile
 */

//package
package com.compmta.scrabble.model;

//imports
import com.compmta.scrabble.util.Letter;

public class RegularTile extends Tile {

    //instance variables
    private char letter;
    private int[] coordinates;

    /**
     * Constructor
     * Creates a new RegularTile
     * 
     * @param i row
     * @param j column
     */
    public RegularTile(int i, int j) {
        super(i, j);
    } //Constructor

    /**
     * effect overrides the parent method
     * returns the score of the word the player has played
     */
    @Override
    public int effect(Tile[] word, int score, int index) {
        return score + Letter.map.get(word[index].getLetter()).getBaseScore();
    }//effect(Tile[] word, int score, int index)

}
