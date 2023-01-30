package com.compmta.scrabble.controllers;

import com.compmta.scrabble.model.GameState;
import com.compmta.scrabble.model.PlayerInfo;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@Getter
@Component
public class GameStateController {

    private static final int INITIAL_LETTER_AMT = 7;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private static final int NUM_PASSES = 3;

    static GameState gameState;
    static HashMap<String, PlayerInfo> players;
    private final ArrayList<PlayerInfo> playerList;

    public GameStateController() {
        this.playerList = new ArrayList<PlayerInfo>();
        players = new HashMap<String, PlayerInfo>();
    }

    /**
     * @return The current game state
     */
    public static GameState getGameState() {
        return gameState;
    }

    /**
     * Adds a player with the specified name to the game
     * @param id The requested id
     * @return The PlayerInfo
     */
    public PlayerInfo joinGame(String id) {
        if (players.size() == MAX_PLAYERS) {
            throw new IllegalStateException("This game is already full.");
        }
        if (players.get(id) != null) {
            throw new IllegalArgumentException("Player name is taken.");
        }
        PlayerInfo p = new PlayerInfo(id);
        players.put(id, p);
        playerList.add(p);
        return p;
    }

    /**
     * Sets up the relevent parts of the game
     */
    public void setUpGame() {
        if (players.size() < MIN_PLAYERS) {
            throw new IllegalStateException("Can't start game with only 1 player!");
        }
        if (gameState == null) {
            gameState = GameState.initialize(playerList);
            BoardController.board = gameState.getBoard();
            for (PlayerInfo p : gameState.getPlayers()) {
                drawLetters(p, INITIAL_LETTER_AMT);
            }
        }
    } //setUpGame()

    /**
     * Draws n letters into the players rack
     * @param p The player
     * @param n The number of letters to be drawn
     */
    public void drawLetters(PlayerInfo p, int n) {
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int index = r.nextInt(gameState.getLetters().size());
            char ch = gameState.getLetters().remove(index);
            p.getRack().add(ch);
        }
        //return p;
    }

}
