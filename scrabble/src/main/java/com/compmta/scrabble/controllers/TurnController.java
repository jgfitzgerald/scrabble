package com.compmta.scrabble.controllers;

//imports

import com.compmta.scrabble.controllers.DTO.GameStateInfo;
import com.compmta.scrabble.controllers.DTO.TurnInfo;
import com.compmta.scrabble.model.Board;
import com.compmta.scrabble.model.PlayerInfo;
import com.compmta.scrabble.model.Turn;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Getter
@Setter
@Component
@Slf4j
public class TurnController {

    //instance variables
    @Autowired
    private GameStateController gsController;
    static PlayerInfo currPlayer;
    static Board board;
    private int turnCount;
    static ArrayList<String> challengers;

    /**
     * Takes a TurnInfo DTO and applies the given requests if applicable.
     *
     * @return GameStateInfo after the effects of the turn
     */

    //TODO priority list (in order of highest to lowest): fixing detect word, challenging, ending the game
    public GameStateInfo takeTurn(TurnInfo turnInfo) throws InterruptedException {
        if (turnInfo == null) { // passed turn
            gsController.getGameState().getTurnLog().add(null);
            return null;
        }
        if (challengers.contains(turnInfo.id())) {
            challengers.remove(challengers.indexOf(turnInfo.id()));
            return null;
        }

        System.out.println(turnInfo);
        if (gsController.getGameState().getPlayerMap().get(turnInfo.id()) == null) {
            throw new IllegalArgumentException("This player is not in the game.");
        }
        if (turnInfo.id().compareTo(currPlayer.getId()) != 0) {
            throw new IllegalArgumentException("It is not your turn!");
        }
        if (turnCount == 0) {
            if (!board.validateInitialMove(turnInfo)) {
                throw new IllegalArgumentException("Invalid initial move request. Please try again.");
            }
        } else if (!board.validateMove(turnInfo)) {
            throw new IllegalArgumentException("Invalid move request. Please try again.");
        }

        char[] word = turnInfo.word();
        int row = turnInfo.row();
        int col = turnInfo.column();
        boolean isHorizontal = turnInfo.isHorizontal();

        if (Collections.frequency(currPlayer.getRack(), ' ') != turnInfo.blanks().size()) {
            throw new IllegalArgumentException("Invalid argument. Too many or two few blank letters were specified.");
        }

        if (!turnInfo.blanks().isEmpty()) {
            int i = 0;
            for (char c : word) {
                if (turnInfo.blanks().get(c) != null && row == turnInfo.blanks().get(c)[0] && col == turnInfo.blanks().get(c)[1]) {
                    word[i] = Board.DEFAULT;
                }
                i++;
                if (!isHorizontal) row++; else col++;
            }
        }

        System.out.println(new String(word));

        for (char c : word) {
            if (c != Board.DEFAULT && !currPlayer.getRack().contains(c)) {
                throw new IllegalArgumentException("Invalid word choice, rack does not contain 1 or more required letters: " + c);
            }
        }

        System.out.println("Removing tiles from rack");
        System.out.println(new String(word));
        for (char c : word) {
            if (c != Board.DEFAULT) {
                currPlayer.getRack().remove(currPlayer.getRack().indexOf(c));
            }
        }

        System.out.println("Making new Turn");
        Turn newMove = new Turn(turnInfo);
        gsController.getGameState().getTurnLog().add(newMove);

        for (char c : turnInfo.blanks().keySet()) {
            board.setBlankLetter(turnInfo.blanks().get(c)[0], turnInfo.blanks().get(c)[1], c);
            currPlayer.getRack().remove(currPlayer.getRack().indexOf(' '));
        }

        //TODO fix scoring (again)
        //int score = board.scoreMove(turnInfo);
        newMove.setScore(0);
        currPlayer.updateScore(0);

        if (currPlayer.getRack().isEmpty()) {
            currPlayer.updateScore(50);
        }

        System.out.println("Applying turn");
        board.applyTurn(newMove);

        gsController.getGameState().drawLetters(currPlayer);

        System.out.println("Ending Turn");
        this.endTurn();
        turnCount++;

        return new GameStateInfo(gsController.getGameState().getId(), board, gsController.getPlayerList());
    } //startTurn()

    //FIXME this doesn't work yet lets not test it mkay
    /*public boolean challengeWord(ChallengeInfo challenge) {

        String challenger = challenge.challengerId();
        String player = challenge.playerId();
        TurnInfo turn = new TurnInfo(player, challenge.word(), challenge.row(), challenge.column(), challenge.isHorizontal());
        if (player != currPlayer.getId()) {
            throw new IllegalArgumentException("It is not this player's turn.");
        }
        ArrayList<WordInfo> words = board.detectWords(turn);
        int i = 0;
        while (Dictionary.verifyWord(words.get(i).word()) && i < words.size()) {
            i++;
        }
        if (i < words.size()) {
            ArrayList<Character> path = board.getLettersOnPath(turn);
            ArrayList<Character> removed = board.removeWord(turn.word(), turn.row(), turn.column(), turn.isHorizontal(), path);

            // return to rack
            int returned = currPlayer.getRack().size() - removed.size();
            for (int j = returned; j < currPlayer.getRack().size(); j++) {
                gsController.getGameState().getLetters().add(currPlayer.getRack().remove(j));
            }
            for (char c : removed) {
                currPlayer.getRack().add(c);
            }

            currPlayer.updateScore(-1 * board.scoreMove(turn));
            gsController.getGameState().getTurnLog().remove(gsController.getGameState().getTurnLog().size()-1);
            gsController.getGameState().getTurnLog().add(null);
            return true;

        } else {
            challengers.add(challenger);
            return false;
        }
    }*/

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

        if (gsController.getGameState().checkEndConditions()) {
            gsController.endGame();
        }
    }

    static void setCurrPlayer(PlayerInfo in) {
        currPlayer = in;
    }

    static void setChallengers(ArrayList<String> in) {
        challengers = in;
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
