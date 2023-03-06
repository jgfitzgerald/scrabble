package com.compmta.scrabble.controllers;

//imports

import com.compmta.scrabble.controllers.DTO.ChallengeInfo;
import com.compmta.scrabble.controllers.DTO.GameStateInfo;
import com.compmta.scrabble.controllers.DTO.TurnInfo;
import com.compmta.scrabble.controllers.DTO.WordInfo;
import com.compmta.scrabble.model.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.compmta.scrabble.model.GameStatus.*;

@Getter
@Setter
@Component
@Slf4j
public class TurnController {

    //instance variables
    @Autowired
    private GameStateController gsController;
    static Board board;
    static PlayerInfo currPlayer;
    private TurnInfo currentTurn;
    private List<WordInfo> words;
    private int score;

    private boolean notInitial;
    static ArrayList<String> challengers;
    private Thread turnThread;

    private CountDownLatch latch;

    /**
     * Takes a TurnInfo DTO and applies the given requests if applicable.
     *
     * @return GameStateInfo after the effects of the turn
     */

    //TODO priority list (in order of highest to lowest): Testing and refactoring
    public GameStateInfo takeTurn(TurnInfo turnInfo) throws InterruptedException {
        if (gsController.getGameState().getStatus() != IN_PROGRESS) {
            throw new IllegalStateException("Cannot place command as game is " + gsController.getGameState().getStatus());
        }
        if (turnInfo == null) { // passed turn
            gsController.getGameState().getTurnLog().add(null);
            return null;
        }
        if (gsController.getGameState().getPlayerMap().get(turnInfo.id()) == null) {
            throw new IllegalArgumentException("This player is not in the game.");
        }
        if (turnInfo.id().compareTo(currPlayer.getId()) != 0) {
            throw new IllegalArgumentException("It is not your turn!");
        }
        if (!notInitial) {
            if (!board.validateInitialMove(turnInfo)) {
                throw new IllegalArgumentException("Invalid initial move request. Please try again.");
            }
        } else if (!board.validateMove(turnInfo)) {
            throw new IllegalArgumentException("Invalid move request. Please try again.");
        }

        char[] word = turnInfo.word().clone();

        if (!turnInfo.blankIndexes().isEmpty()) {
            if (Collections.frequency(currPlayer.getRack(), ' ') < turnInfo.blankIndexes().size()) {
                throw new IllegalArgumentException("Invalid argument. Too many blank letters were specified.");
            }
            for (Integer i : turnInfo.blankIndexes()) {
                word[i] = Board.DEFAULT;
            }
        }
        for (char c : word) {
            if (c != Board.DEFAULT && !currPlayer.getRack().contains(c)) {
                throw new IllegalArgumentException("Invalid word choice, rack does not contain 1 or more required letters: " + c);
            }
        }

        latch = new CountDownLatch(1);
        turnThread = Thread.currentThread();
        currentTurn = turnInfo;
        words = board.detectWords(turnInfo);
        score = board.scoreMove(turnInfo);

        for (WordInfo w : words) {
            System.out.println(w);
        }

        System.out.println("Removing tiles from rack");
        for (char c : word) {
            if (c != Board.DEFAULT) {
                currPlayer.getRack().remove(currPlayer.getRack().indexOf(c));
            }
        }

        Turn newMove = new Turn(turnInfo);
        newMove.setScore(score);
        currPlayer.updateScore(score);

        System.out.println("Applying turn");
        board.applyTurn(newMove);

        System.out.println("Adding turn to log");
        gsController.getGameState().getTurnLog().add(newMove);

        if (currPlayer.getRack().isEmpty()) {
            currPlayer.updateScore(50);
        }

        gsController.getGameState().setStatus(CHALLENGE);
        return new GameStateInfo(gsController.getGameState().getId(), board, gsController.getGameState().getPlayers());
    } //startTurn()

    /**
     * Ends the turn of the current player.
     */
    private void endTurn() {
        for (PlayerInfo p : gsController.getGameState().getPlayers()) {
            if (p.getTotalScore() > 0) {
                notInitial = true;
            }
        }
        gsController.getGameState().drawLetters(currPlayer);
        if (gsController.getGameState().getPlayers().indexOf(currPlayer) == gsController.getGameState().getPlayers().size()-1) {
            this.setCurrPlayer(gsController.getGameState().getPlayers().get(0));
        } else {
            int next = gsController.getGameState().getPlayers().indexOf(currPlayer) + 1;
            this.setCurrPlayer(gsController.getGameState().getPlayers().get(next));
            if (challengers.contains(currPlayer.getId())) {
                this.endTurn();
            }
        }

        if (gsController.getGameState().checkEndConditions()) {
            this.endGame();
        }
    }

    static void setCurrPlayer(PlayerInfo in) {
        currPlayer = in;
    }

    static void setChallengers(ArrayList<String> in) {
        challengers = in;
    }

    /**
     * Exchanges the specified letters and draws new ones into the player's rack
     * @param id The player id
     * @param toExchange An array of characters to be exchanged
     */
    public void exchangeLetters(String id, char[] toExchange) {
        if (gsController.getGameState().getStatus() != IN_PROGRESS) {
            throw new IllegalStateException("Cannot place command as game is " + gsController.getGameState().getStatus());
        }
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

    /**
     * Passes the turn of the current player.
     * @param name The current player
     */
    public void passTurn(String name) {
        if (gsController.getGameState().getStatus() != IN_PROGRESS) {
            throw new IllegalStateException("Cannot place command as game is " + gsController.getGameState().getStatus());
        }
        if (name.compareTo(currPlayer.getId()) != 0) {
            throw new IllegalArgumentException("It is not your turn!");
        }

        gsController.getGameState().getTurnLog().add(null);
        this.endTurn();
    }

    /**
     * Ends the game. Sets the status to finished, preventing further requests.
     * Sets TurnController's currPlayer attribute to the winner, or null if there is no winner.
     */
    private void endGame() {
        gsController.getGameState().setStatus(FINISHED);
        gsController.getGameState().unplayedLetterScores();
        PlayerInfo currWinner = null;
        int maxScore = 0;
        for (PlayerInfo p : gsController.getGameState().getPlayers()) {
            if (p.getTotalScore() > maxScore) {
                currWinner = p;
                maxScore = currWinner.getTotalScore();
            }
            if (maxScore == 0) currPlayer = null; else currPlayer = p;
        }
    }

    public void challengePhase(TurnInfo turnInfo) {
        try {
            System.out.println("Five seconds to challenge.");
            latch.await(5, TimeUnit.SECONDS);
            gsController.getGameState().setStatus(IN_PROGRESS);
            this.endTurn();
        } catch (InterruptedException e) {
            // return tiles to rack
            board.removeWord(turnInfo);
            currPlayer.updateScore(-1 * board.scoreMove(turnInfo));
            System.out.println("Successfully challenged");
            for (char c : turnInfo.word()) {
                if (c != Board.DEFAULT) {
                    currPlayer.getRack().add(c);
                }
            }
            gsController.getGameState().getTurnLog().remove(gsController.getGameState().getTurnLog().size()-1);
            gsController.getGameState().setStatus(IN_PROGRESS);
            this.endTurn();
        }
    }

    public void challengeWord(ChallengeInfo challengerInfo) {
        System.out.println("In challenge word");
        for (WordInfo w : words) {
            System.out.println(w);
        }
        Challenge challenge = new Challenge(words);
        if (!challenge.isValid()) {
            // word is valid, interrupt takeTurn and continue
            System.out.println("invalid challenge");
            challengers.add(challengerInfo.challengerId());
            latch.countDown();
        } else {
            System.out.println("valid challenge");
            // word is invalid, interrupt takeTurn and cancel
            latch.countDown();
            turnThread.interrupt();
        }
    }
}
