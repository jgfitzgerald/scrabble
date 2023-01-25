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
     * addPlayer adds the players info to the array 
     * of players
     * 
     * @param playerInfo takes the player information
     */
    public void addPlayer(PlayerInfo playerInfo) {
        players.add(playerInfo);
    } //addPlayer(PlayerInfo playerInfo)

    /**
     * setStatus sets the current game status
     * 
     * @param status takes the current game status
     */
    public void setStatus(GameStatus status) {
        this.status = status;
    } //setStatus(GameStatus status)

    /**
     * insitialize starts the game and creates the relevent game objects
     * 
     * @param players takes the players that are currently in the game
     * @return the game state
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
    } //initialize(ArrayList<PlayerInfo> players)

    /**
     * initialize letter creates all of the letters in the game
     */
    public void initializeLetters() {
        this.letters = new ArrayList<Character>();
        for (Letter l : Letter.values()) {
            int n = l.getInitialAmount();
            for (int i = 0; i < n; i++) {
                letters.add(l.getLetter());
            }
        }
    } //initializeLetters()

}
