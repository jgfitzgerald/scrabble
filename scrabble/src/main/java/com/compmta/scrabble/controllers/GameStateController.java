package com.compmta.scrabble.controllers;

import com.compmta.scrabble.model.GameState;
import com.compmta.scrabble.model.PlayerInfo;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

import static com.compmta.scrabble.model.GameStatus.*;

@Getter
@Component
public class GameStateController {

    private static final int INITIAL_LETTER_AMT = 7;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private static final int NUM_PASSES = 3;

    private GameState gameState;
    static HashMap<String, PlayerInfo> players;

    public GameStateController() {
        players = new HashMap<>();
    }

    /**
     * @return The current game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Adds a player with the specified name to the game
     * @param id The requested id
     * @return The PlayerInfo
     */
    public PlayerInfo joinGame(String id) {
        if (gameState == null) {
            gameState = new GameState();
        }
        if (players.size() == MAX_PLAYERS) {
            throw new IllegalStateException("This game is already full.");
        }
        if (players.get(id) != null) {
            throw new IllegalArgumentException("Player name is taken.");
        }
        PlayerInfo p = new PlayerInfo(id);
        players.put(p.getId(), p);
        gameState.addPlayer(p);
        return p;
    }

    /**
     * Sets up the relevent parts of the game
     */
    public void setUpGame() {
        if (gameState.getPlayers().size() < MIN_PLAYERS) {
            throw new IllegalStateException("Can't start game with only 1 player!");
        }
        if (gameState.getStatus() == PENDING) {
            gameState.initialize();
            gameState.setPlayerMap(players);
            TurnController.board = gameState.getBoard();
            for (PlayerInfo p : gameState.getPlayers()) {
                gameState.drawLetters(p);
            }
            TurnController.setCurrPlayer(gameState.getPlayers().get(0));
            TurnController.setChallengers(new ArrayList<>());
        }
    } //setUpGame()

    /**
     * Switches the player's vote attribute.
     * Will automatically start the game if game is pending and all player's votes are true.
     * Will end the game on the next turn if game is in progress and all player's votes are false.
     * @param id The id of the player who voted
     */
    void vote(String id) {
        for (PlayerInfo p : gameState.getPlayers()) {
            if (p.getId().equals(id)) {
                p.setVote(!p.getVote());
            }
        }
        if (gameState.getStatus() == PENDING) {
            int i = 0;
            for (PlayerInfo p : gameState.getPlayers()) {
                if (p.getVote()) {
                    i++;
                }
            }
            if (i == gameState.getPlayers().size()) {
                this.setUpGame();
            }
        }
        if (gameState.getStatus() == IN_PROGRESS) {
            int i = 0;
            for (PlayerInfo p : gameState.getPlayers()) {
                if (!p.getVote()) {
                    i++;
                }
            }
            if (i == gameState.getPlayers().size()) {
                this.endGame();
            }
        }
    }

    void endGame() {
        this.getGameState().setStatus(FINISHED);
    }

}
