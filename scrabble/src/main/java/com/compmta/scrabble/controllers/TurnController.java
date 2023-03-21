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

import java.util.Collections;
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


    /**
     * Takes a TurnInfo DTO and applies the given requests if applicable.
     *
     * @return GameStateInfo after the effects of the turn
     */

    //TODO priority list (in order of highest to lowest): Testing and refactoring
    public GameStateInfo takeTurn(TurnInfo turnInfo) throws InterruptedException {
        String gameId = turnInfo.gameId();
        GameState game = gsController.getGameDatabase().get(gameId);
        if (gsController.getGameDatabase().get(gameId).getStatus() != IN_PROGRESS) {
            throw new IllegalStateException("Cannot place command as game is " + gsController.getGameDatabase().get(gameId).getStatus());
        }
        if (turnInfo == null) { // passed turn
            gsController.getGameDatabase().get(gameId).getTurnLog().add(null);
            return null;
        }
        if (gsController.getGameDatabase().get(gameId).getPlayerMap().get(turnInfo.playerId()) == null) {
            throw new IllegalArgumentException("This player is not in the game.");
        }
        if (turnInfo.playerId().compareTo(game.getCurrPlayer().getId()) != 0) {
            throw new IllegalArgumentException("It is not your turn!");
        }
        if (!gsController.getGameDatabase().get(gameId).isNotInitial()) {
            if (!game.getBoard().validateInitialMove(turnInfo)) {
                throw new IllegalArgumentException("Invalid initial move request. Please try again.");
            }
        } else if (!game.getBoard().validateMove(turnInfo)) {
            throw new IllegalArgumentException("Invalid move request. Please try again.");
        }

        char[] word = turnInfo.word().clone();

        if (!turnInfo.blankIndexes().isEmpty()) {
            if (Collections.frequency(game.getCurrPlayer().getRack(), ' ') < turnInfo.blankIndexes().size()) {
                throw new IllegalArgumentException("Invalid argument. Too many blank letters were specified.");
            }
            for (Integer i : turnInfo.blankIndexes()) {
                word[i] = Board.DEFAULT;
            }
        }
        for (char c : word) {
            if (c != Board.DEFAULT && !game.getCurrPlayer().getRack().contains(c)) {
                throw new IllegalArgumentException("Invalid word choice, rack does not contain 1 or more required letters: " + c);
            }
        }

        gsController.getGameDatabase().get(turnInfo.gameId()).setLatch(new CountDownLatch(1));
        gsController.getGameDatabase().get(turnInfo.gameId()).setTurnThread(Thread.currentThread());
        gsController.getGameDatabase().get(turnInfo.gameId()).setWords(game.getBoard().detectWords(turnInfo));

        for (WordInfo w : gsController.getGameDatabase().get(turnInfo.gameId()).getWords()) {
            System.out.println(w);
        }

        System.out.println("Removing tiles from rack");
        for (char c : word) {
            if (c != Board.DEFAULT) {
                gsController.getGameDatabase().get(turnInfo.gameId()).getCurrPlayer().getRack().remove(gsController.getGameDatabase().get(turnInfo.gameId()).getCurrPlayer().getRack().indexOf(c));
            }
        }

        int score = game.getBoard().scoreMove(turnInfo);

        Turn newMove = new Turn(turnInfo);
        newMove.setScore(score);
        gsController.getGameDatabase().get(turnInfo.gameId()).getCurrPlayer().updateScore(score);

        System.out.println("Applying turn");
        gsController.getGameDatabase().get(turnInfo.gameId()).getBoard().applyTurn(newMove);

        System.out.println("Adding turn to log");
        gsController.getGameDatabase().get(gameId).getTurnLog().add(newMove);

        if (gsController.getGameDatabase().get(turnInfo.gameId()).getCurrPlayer().getRack().isEmpty()) {
            gsController.getGameDatabase().get(turnInfo.gameId()).getCurrPlayer().updateScore(50);
        }

        gsController.getGameDatabase().get(gameId).setStatus(CHALLENGE);
        return new GameStateInfo(gsController.getGameDatabase().get(gameId).getId(), gsController.getGameDatabase().get(gameId).getStatus(), gsController.getGameDatabase().get(gameId).getBoard(), gsController.getGameDatabase().get(gameId).getPlayerMap());
    } //startTurn()

    /**
     * Ends the turn of the current player.
     */
    private void endTurn(String gameId) {
        for (PlayerInfo p : gsController.getGameDatabase().get(gameId).getPlayers()) {
            if (p.getTotalScore() > 0) {
                gsController.getGameDatabase().get(gameId).setNotInitial(true);
            }
        }
        gsController.getGameDatabase().get(gameId).drawLetters(gsController.getGameDatabase().get(gameId).getCurrPlayer());
        if (gsController.getGameDatabase().get(gameId).getPlayers().indexOf(gsController.getGameDatabase().get(gameId).getCurrPlayer()) == gsController.getGameDatabase().get(gameId).getPlayers().size()-1) {
            gsController.getGameDatabase().get(gameId).setCurrPlayer(gsController.getGameDatabase().get(gameId).getPlayers().get(0));
        } else {
            int next = gsController.getGameDatabase().get(gameId).getPlayers().indexOf(gsController.getGameDatabase().get(gameId).getCurrPlayer()) + 1;
            gsController.getGameDatabase().get(gameId).setCurrPlayer(gsController.getGameDatabase().get(gameId).getPlayers().get(next));
            if (gsController.getGameDatabase().get(gameId).getChallengers().contains(gsController.getGameDatabase().get(gameId).getCurrPlayer().getId())) {
                gsController.getGameDatabase().get(gameId).getChallengers().remove(gsController.getGameDatabase().get(gameId).getCurrPlayer().getId());
                this.endTurn(gameId);
            }
        }

        if (gsController.getGameDatabase().get(gameId).checkEndConditions()) {
            gsController.getGameDatabase().get(gameId).endGame();
        }
    }

    /**
     * Exchanges the specified letters and draws new ones into the player's rack
     * @param id The player id
     * @param toExchange An array of characters to be exchanged
     */
    public void exchangeLetters(String gameId, String id, char[] toExchange) {
        if (gsController.getGameDatabase().get(gameId).getStatus() != IN_PROGRESS) {
            throw new IllegalStateException("Cannot place command as game is " + gsController.getGameDatabase().get(gameId).getStatus());
        }
        if (id.compareTo(gsController.getGameDatabase().get(gameId).getCurrPlayer().getId()) != 0) {
            throw new IllegalArgumentException("It is not your turn!");
        }
        for (char c : toExchange) {
            if (!gsController.getGameDatabase().get(gameId).getCurrPlayer().getRack().contains(c)) {
                throw new IllegalArgumentException("One or more letters specified are not in the current player's rack.");
            }
        }

        for (char c : toExchange) {
            gsController.getGameDatabase().get(gameId).getLetters().add(gsController.getGameDatabase().get(gameId).getCurrPlayer().getRack().remove(gsController.getGameDatabase().get(gameId).getCurrPlayer().getRack().indexOf(c)));
        }

        gsController.getGameDatabase().get(gameId).getTurnLog().add(null);
        gsController.getGameDatabase().get(gameId).drawLetters(gsController.getGameDatabase().get(gameId).getCurrPlayer());

        this.endTurn(gameId);
    }

    /**
     * Passes the turn of the current player.
     * @param name The current player
     */
    public void passTurn(String gameId, String name) {
        if (gsController.getGameDatabase().get(gameId).getStatus() != IN_PROGRESS) {
            throw new IllegalStateException("Cannot place command as game is " + gsController.getGameDatabase().get(gameId).getStatus());
        }
        if (name.compareTo(gsController.getGameDatabase().get(gameId).getCurrPlayer().getId()) != 0) {
            throw new IllegalArgumentException("It is not your turn!");
        }

        gsController.getGameDatabase().get(gameId).getTurnLog().add(null);
        this.endTurn(gameId);
    }

    public void challengePhase(TurnInfo turnInfo) {
        String gameId = turnInfo.gameId();
        try {
            System.out.println("Five seconds to challenge.");
            gsController.getGameDatabase().get(gameId).getLatch().await(5, TimeUnit.SECONDS);
            gsController.getGameDatabase().get(gameId).setStatus(IN_PROGRESS);
            this.endTurn(gameId);
        } catch (InterruptedException e) {
            // return tiles to rack
            gsController.getGameDatabase().get(gameId).getBoard().removeWord(turnInfo);
            gsController.getGameDatabase().get(gameId).getCurrPlayer().updateScore(-1 * gsController.getGameDatabase().get(gameId).getBoard().scoreMove(turnInfo));
            System.out.println("Successfully challenged");
            for (char c : turnInfo.word()) {
                if (c != Board.DEFAULT) {
                    gsController.getGameDatabase().get(gameId).getCurrPlayer().getRack().add(c);
                }
            }
            gsController.getGameDatabase().get(gameId).getTurnLog().remove(gsController.getGameDatabase().get(gameId).getTurnLog().size() - 1);
            gsController.getGameDatabase().get(gameId).setStatus(IN_PROGRESS);
            this.endTurn(gameId);
        }
    }

    public void challengeWord(ChallengeInfo challengerInfo) {
        System.out.println("In challenge word");
        for (WordInfo w : gsController.getGameDatabase().get(challengerInfo.gameId()).getWords()) {
            System.out.println(w);
        }
        Challenge challenge = new Challenge(gsController.getGameDatabase().get(challengerInfo.gameId()).getWords());
        if (!challenge.isValid()) {
            // word is valid, interrupt takeTurn and continue
            System.out.println("invalid challenge");
            gsController.getGameDatabase().get(challengerInfo.gameId()).getChallengers().add(challengerInfo.challengerId());
            gsController.getGameDatabase().get(challengerInfo.gameId()).getLatch().countDown();
        } else {
            System.out.println("valid challenge");
            // word is invalid, interrupt takeTurn and cancel
            gsController.getGameDatabase().get(challengerInfo.gameId()).getTurnThread().interrupt();
            gsController.getGameDatabase().get(challengerInfo.gameId()).getLatch().countDown();
        }
    }
}
