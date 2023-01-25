/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: TripleWordTile
 */

//package
package com.compmta.scrabble.model;

//import
import com.compmta.scrabble.util.Letter;

public class TripleWordTile extends Tile {

    /**
     * Constructor
     * Creates a new instance of TripleWordTile
     * @param i row
     * @param j column
     */
    public TripleWordTile(int i, int j) {
        super(i, j);
    } //Constructor

    /**
     * Overrides parent method
     * Triples the score of the word
     */
    @Override
    public int effect(Tile[] word, int score, int index) {
        if (index == word.length) {
            return score * 3;
        } else {
            return score + Letter.map.get(word[index].getLetter()).getBaseScore();
        }
    } //effect(Tile[] word, int score, int index)

}
