package com.compmta.scrabble.model;

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
     * Draws n letters into the players rack
     * @param p The player
     * @param n The number of letters to be drawn
     */
    public void drawLetters(PlayerInfo p, int n) {
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int index = r.nextInt(letters.size());
            char ch = letters.remove(index);
            p.getRack().add(ch);
        }
        //return p;
    }

    /**
     * removeTileFromRack removes the given tile from the rack
     * @param index of the tile
     */
    public void removeTileFromRack(String id, int index) {
        playerMap.get(id).getRack().remove(index);
    }



}
