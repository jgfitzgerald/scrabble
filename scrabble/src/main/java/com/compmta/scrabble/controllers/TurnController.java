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
 
    /**
     * endTurn ends the turn of the player
     */
    private void endTurn() {
        // DO STUFF
    } //endTurn()

    /**
     * removeTileFromRack removes the given tile from the rack
     * @param index of the tile
     */
    public void removeTileFromRack(int index) {
        currPlayer.getRack().remove(index);
    } //removeTileFromRack(int index)

    /**
     * Checks if the word is part of the scrabble dictionary
     * 
     * @param startCoords beginning corrdinate of word
     * @param endCoords ending coordinate of word
     * @param word the word in question
     */
    public void challengeWord(int[] startCoords, int[] endCoords, String word) {
        // DO STUFF
    }
    /**
     * 
     * 
     * @param startCoords beginning coordinate of word
     * @param endCoords ending coordinate of word
     * @param word the word
     */
    public void chooseWord(int[] startCoords, int[] endCoords, String word) {
        // DO STUFF
    }

}
