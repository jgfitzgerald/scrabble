/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: DoubleLetterTile
 */

//package
package com.compmta.scrabble.model;

//imports
import com.compmta.scrabble.util.Letter;

public class DoubleLetterTile extends Tile {

    /**
     * Contructor
     * Creates a double letter tile at the given row and column
     * 
     * @param i row
     * @param j column
     */
    public DoubleLetterTile(int i, int j) {
        super(i, j);
    } //Constructor

    /**
     * effect changes the score of the given letter 
     * to double its original value.
     */
    @Override
    public int effect(Tile[] word, int score, int index) {
        if (index == word.length) return score + (Letter.map.get(word[index].getLetter()).getBaseScore()) * 2;
        else return 0;
    } //effect(Tile[] word, int score, int index)
}
