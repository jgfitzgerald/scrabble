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
    private static final int NUM_LETTERS = 27;
    private static final int MAX_PLAYERS = 4;

    static GameState gameState;
    private final HashMap<String, PlayerInfo> players;
    private final ArrayList<PlayerInfo> playerList;

    public GameStateController() {
        this.playerList = new ArrayList<PlayerInfo>();
        this.players = new HashMap<String, PlayerInfo>();
    }

    static GameState getGameState() {
        return gameState;
    }

    public PlayerInfo joinGame(String id) {
        if (players.size() == MAX_PLAYERS) {
            throw new IllegalArgumentException("This game is already full.");
        }
        if (players.get(id) != null) {
            throw new IllegalArgumentException("Player name is taken.");
        }
        PlayerInfo p = new PlayerInfo(id);
        players.put(id, p);
        playerList.add(p);
        return p;
    }

    public void setUpGame() {
        if (gameState == null) {
            gameState = GameState.initialize(playerList);
            BoardController.board = gameState.getBoard();
            for (PlayerInfo p : gameState.getPlayers()) {
                drawLetters(p, INITIAL_LETTER_AMT);
            }
        }
    }

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
