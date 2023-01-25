/**
 * Group4: Scrabble
 * COMP4721: Software Design
 * Class: GameState
 */

//package
package com.compmta.scrabble.model;

//imports
import com.compmta.scrabble.util.Letter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

//generates a no argument constructor, getters, and setters
@NoArgsConstructor
@Getter
@Setter

public class GameState {

    //Instance variables
    private String id;
    private ArrayList<PlayerInfo> players;
    private int numPlayers;
    private GameStatus status;
    private ArrayList<Character> letters;
    private HashSet<String> playedWords;
    private ArrayList<Turn> turnLog;
    private Board board;
    private static Dictionary dictionary;

    /**
     * 
     * @param p
     */
    public void addPlayer(PlayerInfo p) {
        players.add(p);
    }

    /**
     * 
     * @param status
     */
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    /**
     * 
     * @param players
     * @return
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
     * 
     */
    public void initializeLetters() {
        this.letters = new ArrayList<Character>();
        for (Letter l : Letter.values()) {
            int n = l.getInitialAmount();
            for (int i = 0; i < n; i++) {
                letters.add(l.getLetter());
            }
        }
    }

}
