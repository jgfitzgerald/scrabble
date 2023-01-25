/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: Tile
 */

//package
package com.compmta.scrabble.model;

//imports
import lombok.Getter;
import lombok.Setter;

//auto-generates getters and setters
@Getter
@Setter

public abstract class Tile {

    //class constants
    public final static int COORDS = 2;

    //instance variables
    private char letter;
    private int[] coordinates;

    /**
     * Constructor
     * Initializes instance variables
     * Takes the row and column of the tile and 
     * sends it to setCoordinates to set the coordinates of
     * the tile.
     */
    public Tile(int i, int j) {
        this.coordinates = new int[COORDS];
        this.setCoordinates(i, j);
        letter = '!';
    } //Constructor

    /**
     * setCoordinates sets the coordinates of the tile
     * 
     * @param i the row of the tile
     * @param j the column of the tile
     */
    public void setCoordinates(int i, int j) {
        coordinates[0] = i;
        coordinates[1] = j;
    } //setCoordinates(int i, int j)

    /**
     * effect returns the score of the word after
     * the necessary effect's have been applied.
     * 
     * @param word the word the player has played
     * @param score the score of the word
     * @param index
     * @return the score after the effect has been applied
     */
    public abstract int effect(Tile[] word, int score, int index);

}
