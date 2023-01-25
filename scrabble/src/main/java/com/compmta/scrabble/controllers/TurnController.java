/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: TurnController
 */

//package
package com.compmta.scrabble.controllers;

//imports
import com.compmta.scrabble.model.PlayerInfo;
import com.compmta.scrabble.model.Turn;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

//generates getters and setters
@Getter
@Setter

@Component

public class TurnController {

    //instance variables
    private PlayerInfo currPlayer;

    /**
     * startTurn begins the turn of the player
     * 
     * @return the current turn of the player
     */
    public Turn startTurn() {
        // DO STUFF
        return new Turn(currPlayer.getId());
    } //startTurn()
 

    private void endTurn() {
        // DO STUFF
    }

    public void removeTileFromRack(int index) {
        currPlayer.getRack().remove(index);
    }

    public void challengeWord(int[] startCoords, int[] endCoords, String word) {
        // DO STUFF
    }

    public void chooseWord(int[] startCoords, int[] endCoords, String word) {
        // DO STUFF
    }

}
