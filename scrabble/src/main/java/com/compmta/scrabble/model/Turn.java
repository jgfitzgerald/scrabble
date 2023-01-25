/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: Turn
 */

//package
package com.compmta.scrabble.model;

//imports
import lombok.Getter;
import lombok.Setter;

//generates getters and setters
@Setter
@Getter

public class Turn {

    //instance variables
    private String playerId;
    private String word;
    private int[] startCoords;
    private int[] endCoords;
    private int score;

    /**
     * Constructor 
     * creates new turn for player
     * @param playerId takes player ID
     */
    public Turn(String playerId) {
        this.playerId = playerId;
        this.word = null;
        this.startCoords = new int[Tile.COORDS];
        this.endCoords = new int[Tile.COORDS];
        this.score = 0;
    } //Turn(String playerID)
}
