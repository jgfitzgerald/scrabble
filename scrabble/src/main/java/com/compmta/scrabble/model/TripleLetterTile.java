/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: TripleLetterTile
 */

//package
package com.compmta.scrabble.model;

//imports
import com.compmta.scrabble.util.Letter;

public class TripleLetterTile extends Tile {

    /**
     * Constructor
     * Creates a new instance of TripleLetterTile
     * @param i row
     * @param j column
     */
    public TripleLetterTile(int i, int j) {
        super(i, j);
    } //Contructor

    /**
     * Overrides the parent method
     * triples the score of the letter
     */
    @Override
    public int effect(Tile[] word, int score, int index) {
        if (index == word.length) return score + (Letter.map.get(word[index].getLetter()).getBaseScore()) * 3;
        else return 0;
    } //effect(Tile[] word, int score, int index)
}
