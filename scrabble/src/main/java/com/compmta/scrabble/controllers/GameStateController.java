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

    private HashMap<String, GameState> gameDatabase;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    public GameStateController() {
        gameDatabase = new HashMap<>();
    }

    public GameState newGame() {
        GameState newGame = new GameState();
        gameDatabase.put(newGame.getId(), newGame);
        return newGame;
    }

    /**
     * Adds a player with the specified name to the game
     * @param playerId The requested id
     * @return The PlayerInfo
     */
    public PlayerInfo joinGame(String gameId, String playerId) {
        if (gameDatabase.get(gameId).getPlayers().size() == MAX_PLAYERS) {
            throw new IllegalStateException("This game is already full.");
        }
        if (gameDatabase.get(gameId).getPlayerMap().get(playerId) != null) {
            throw new IllegalArgumentException("Player name is taken.");
        }
        PlayerInfo p = new PlayerInfo(playerId);
        gameDatabase.get(gameId).addPlayer(p);
        return p;
    }

    /**
     * Sets up the relevent parts of the game
     */

    public void setUpGame(String gameId) {
        if (gameDatabase.get(gameId).getPlayers().size() < MIN_PLAYERS) {
            throw new IllegalStateException("Can't start game with only 1 player!");
        }
        if (gameDatabase.get(gameId).getStatus() == PENDING) {
            gameDatabase.get(gameId).initialize();
            for (PlayerInfo p : gameDatabase.get(gameId).getPlayers()) {
                gameDatabase.get(gameId).drawLetters(p);
            }
            gameDatabase.get(gameId).setCurrPlayer(gameDatabase.get(gameId).getPlayers().get(0));
            gameDatabase.get(gameId).setChallengers(new ArrayList<>());
        }
    } //setUpGame()

    /**
     * Switches the player's vote attribute.
     * Will automatically start the game if game is pending and all player's votes are true.
     * @param playerId The id of the player who voted
     */
    void vote(String gameId, String playerId) {
        for (PlayerInfo p : gameDatabase.get(gameId).getPlayers()) {
            if (p.getId().equals(playerId)) {
                p.setVote(!p.getVote());
            }
        }
        if (gameDatabase.get(gameId).getStatus() == PENDING) {
            int i = 0;
            for (PlayerInfo p : gameDatabase.get(gameId).getPlayers()) {
                if (p.getVote()) {
                    i++;
                }
            }
            if (i == gameDatabase.get(gameId).getPlayers().size()) {
                this.setUpGame(gameId);
            }
        }
    }

}
