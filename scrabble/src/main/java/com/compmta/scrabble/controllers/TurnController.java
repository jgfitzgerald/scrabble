package com.compmta.scrabble.controllers;

//imports
import com.compmta.scrabble.controllers.DTO.GameStateInfo;
import com.compmta.scrabble.controllers.DTO.TurnInfo;
import com.compmta.scrabble.model.Board;
import com.compmta.scrabble.model.PlayerInfo;
import com.compmta.scrabble.model.Turn;
import com.compmta.scrabble.util.WordJudge;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Getter
@Setter
@Component
@Slf4j
public class TurnController {

    //instance variables
    @Autowired
    private GameStateController gsController;
    private static PlayerInfo currPlayer;
    static Board board;
    private int turnCount;

    /**
     * Takes a TurnInfo DTO and applies the given requests if applicable.
     *
     * @return null if the GameState has not changed, the GameStateInfo after the effects of the turn if otherwise
     */
    public GameStateInfo takeTurn(TurnInfo turnInfo) {
        if (turnInfo == null) { // pass
            return null;
        }

        // TO-DO extract this to a method called validateTurn()
        if (turnInfo.id().compareTo(currPlayer.getId()) != 0) {
            throw new IllegalArgumentException("It is not your turn!");
        }
        if (turnCount == 0) {
            if (!board.validateInitialMove(turnInfo.startCoords(), turnInfo.endCoords(), turnInfo.word())) {
                throw new IllegalArgumentException("Invalid initial move request. Please try again.");
            }
        } else if (!board.validateMove(turnInfo.startCoords(), turnInfo.endCoords(), turnInfo.word())) {
            throw new IllegalArgumentException("Invalid move request. Please try again.");
        }
        for (char c : turnInfo.word().toCharArray()) {
            // MAKE ARRAY OF LETTERS ON PATH
            if (!currPlayer.getRack().contains(c)) {
                throw new IllegalArgumentException("Invalid word choice, rack or word path does not contain 1 or more letters.");
            }
        }

        Turn newMove = new Turn(turnInfo.id(), turnInfo.word(), turnInfo.startCoords(), turnInfo.endCoords(), /*WordJudge.scoreMove(turnInfo)*/0);
        GameStateController.getGameState().getTurnLog().add(newMove);
        GameStateController.players.get(turnInfo.id()).updateScore(newMove.getScore());

        for (char c : turnInfo.word().toCharArray()) {
            this.removeTileFromRack(currPlayer.getRack().indexOf(c));
        }

        board.applyTurn(newMove);

        gsController.getGameState().drawLetters(currPlayer);

        this.endTurn();

        return new GameStateInfo(gsController.getGameState().getId(), board, gsController.getPlayerList());
    } //startTurn()

    /**
     * removeTileFromRack removes the given tile from the rack
     * @param index of the tile
     */
    public void removeTileFromRack(int index) {
        this.currPlayer.getRack().remove(index);
    }

    /**
     * Checks if the word is part of the scrabble dictionary
     * 
     * @param startCoords beginning coordinate of word
     * @param endCoords ending coordinate of word
     * @param word the word in question
     * @param i the index of the word that was part of a previous turn
     */
    public void challengeWord(int[] startCoords, int[] endCoords, String word, int i) {
        if (!WordJudge.verifyWord(word)) {
            board.removeWord(startCoords, endCoords, i);
        }
    }

    /**
     * Ends the turn of the current player.
     */
    public void endTurn() {
        if (gsController.getPlayerList().indexOf(currPlayer) == gsController.getPlayerList().size()) {
            this.setCurrPlayer(gsController.getPlayerList().get(0));
        } else {
            int next = gsController.getPlayerList().indexOf(currPlayer) + 1;
            this.setCurrPlayer(gsController.getPlayerList().get(next));
        }
        turnCount++;

        if (gsController.getGameState().checkEndConditions()) {
            gsController.endGame();
        }
    }

    static void setCurrPlayer(PlayerInfo in) {
        currPlayer = in;
    }
}
