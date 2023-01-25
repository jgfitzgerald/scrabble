/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: PlayerInfo
 */

//package
package com.compmta.scrabble.model;

//imports
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

// generates getters and setters
@Getter
@Setter

public class PlayerInfo {

    //instance variables
    private String id;
    private ArrayList<Character> rack;

    /**
     * Constructor
     * Sets the players id and creates their rack.
     * @param id takes the player id
     */
    public PlayerInfo(String id) {
        this.id = id;
        this.rack = new ArrayList<Character>();
    } //Constructor

}
