package com.compmta.scrabble.model;

import com.compmta.scrabble.controllers.DTO.GameStateInfo;
import com.compmta.scrabble.controllers.GameStateController;
import com.compmta.scrabble.util.Letter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
public class GameState {
    private String id;
    private ArrayList<PlayerInfo> players;
    private HashMap<String, PlayerInfo> playerMap;
    private int numPlayers;
    private GameStatus status;
    private ArrayList<Character> letters;
    private HashSet<String> playedWords;
    private ArrayList<Turn> turnLog;
    private Board board;

    private static Dictionary dictionary;

    private static final int RACK_SIZE = 7;

    public void addPlayer(PlayerInfo p) {
        players.add(p);
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    /**
     * Initializes all game elements
     * @param players List of players
     * @return Initialized game state
     */
    public static GameState initialize(ArrayList<PlayerInfo> players) {
        GameState gs = new GameState();
        gs.setId(UUID.randomUUID().toString());
        gs.setPlayers(players);
        gs.setNumPlayers(players.size());
        gs.setStatus(GameStatus.IN_PROGRESS);
        gs.setBoard(new Board());
        gs.initializeLetters();
        gs.setPlayedWords(new HashSet<String>());
        gs.setTurnLog(new ArrayList<Turn>());
        File dict = new File("docs/Collins Scrabble Words (2019).txt");
        try {
            dictionary = new Dictionary(dict);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return gs;
    }

    /**
     * Initializes the letter bag
     */
    public void initializeLetters() {
        this.letters = new ArrayList<Character>();
        for (Letter l : Letter.getMap().values()) {
            int n = l.getInitialAmount();
            for (int i = 0; i < n; i++) {
                letters.add(l.getLetter());
            }
        }
    }

    /**
     * removeTileFromRack removes the given tile from the rack
     * @param index of the tile
     */
    public void removeTileFromRack(String id, int index) {
        playerMap.get(id).getRack().remove(index);
    }

    /**
     * Draws letters into the players rack until it is full
     * @param p The player
     */
    public void drawLetters(PlayerInfo p) {
        Random r = new Random();
        while (p.getRack().size() != RACK_SIZE && !letters.isEmpty()) {
            int index = r.nextInt(letters.size());
            char ch = letters.remove(index);
            p.getRack().add(ch);
        }
        //return p;
    }

    /**
     * Check that all letters have been drawn, and that one player has no letters left.
     * @return true if the games end conditions have been met, false otherwise
     */
    public boolean checkEndConditions() {
        if (letters.isEmpty()) {
            for (PlayerInfo p : players) {
                if (p.getRack().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

}
