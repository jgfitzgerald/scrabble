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

import java.util.ArrayList;

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
     * @return GameStateInfo after the effects of the turn
     */

    //TODO priority list (in order of highest to lowest): setting blank letters, scoring, challenging words
    public GameStateInfo takeTurn(TurnInfo turnInfo) {
        if (turnInfo == null) { // passed turn
            gsController.getGameState().getTurnLog().add(null);
            return null;
        }

        System.out.println("Checking curr player");
        if (turnInfo.id().compareTo(currPlayer.getId()) != 0) {
            throw new IllegalArgumentException("It is not your turn!");
        }
        System.out.println("Checking Turn count");
        if (turnCount == 0) {
            if (!board.validateInitialMove(turnInfo)) {
                throw new IllegalArgumentException("Invalid initial move request. Please try again.");
            }
        } else if (!board.validateMove(turnInfo)) {
            throw new IllegalArgumentException("Invalid move request. Please try again.");
        }

        System.out.println("Getting letters on path");
        ArrayList<Character> lettersOnPath = board.getLettersOnPath(turnInfo);

        System.out.println("Checking letters");
        for (char c : turnInfo.word().toCharArray()) {
            if (!currPlayer.getRack().contains(c) && !lettersOnPath.contains(c)) {
                throw new IllegalArgumentException("Invalid word choice, rack or word path does not contain 1 or more letters.");
            }
        }

        System.out.println("Making new Turn");
        Turn newMove = new Turn(turnInfo);
        gsController.getGameState().getTurnLog().add(newMove);

        // TODO add logic for challenging words here

        newMove.setScore(board.scoreMove(turnInfo));
        currPlayer.updateScore(newMove.getScore());

        System.out.println("Removing tiles from rack");
        for (char c : turnInfo.word().toCharArray()) {
            if (!lettersOnPath.contains(c)){
                this.removeTileFromRack(currPlayer.getRack().indexOf(c));
            } else { // prevents letters on path from being double counted
                lettersOnPath.remove(lettersOnPath.indexOf(c));
            }
        }

        System.out.println("Applying turn");
        board.applyTurn(newMove);


        gsController.getGameState().drawLetters(currPlayer);

        System.out.println("Ending Turn");
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

    public void challengeWord(TurnInfo turn, ArrayList<Character> path) {
        //FIXME needs to check ALL words played in the turn, not just primary word
        if (!WordJudge.verifyWord(turn.word())) {
            board.removeWord(turn.word(), turn.row(), turn.column(), turn.isHorizontal(), path);
        }
    }

    /**
     * Ends the turn of the current player.
     */
    public void endTurn() {
        if (gsController.getPlayerList().indexOf(currPlayer) == gsController.getPlayerList().size()-1) {
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

    public void exchangeLetters(String id, char[] toExchange) {
        if (id.compareTo(currPlayer.getId()) != 0) {
            throw new IllegalArgumentException("It is not your turn!");
        }
        for (char c : toExchange) {
            if (!currPlayer.getRack().contains(c)) {
                throw new IllegalArgumentException("One or more letters specified are not in the current player's rack.");
            }
        }

        for (char c : toExchange) {
            gsController.getGameState().getLetters().add(currPlayer.getRack().remove(currPlayer.getRack().indexOf(c)));
        }

        gsController.getGameState().getTurnLog().add(null);
        gsController.getGameState().drawLetters(currPlayer);

        this.endTurn();
    }

    public void passTurn(String name) {
        if (name.compareTo(currPlayer.getId()) != 0) {
            throw new IllegalArgumentException("It is not your turn!");
        }

        gsController.getGameState().getTurnLog().add(null);
        this.endTurn();
    }
}
