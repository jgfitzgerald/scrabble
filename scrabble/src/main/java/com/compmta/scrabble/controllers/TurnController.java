package com.compmta.scrabble.controllers;

import com.compmta.scrabble.model.PlayerInfo;
import com.compmta.scrabble.model.Turn;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class TurnController {

    private PlayerInfo currPlayer;

    public Turn startTurn() {
        // DO STUFF
        return new Turn(currPlayer.getId());
    }

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
