package com.compmta.scrabble.model;

import com.compmta.scrabble.controllers.DTO.TurnInfo;
import com.compmta.scrabble.controllers.DTO.WordInfo;
import com.compmta.scrabble.util.Letter;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

import static com.compmta.scrabble.model.GameStatus.PENDING;

@Getter
@Setter
public class GameState {
    private String id;
    private ArrayList<PlayerInfo> players;
    private HashMap<String, PlayerInfo> playerMap;
    private GameStatus status;
    private ArrayList<Character> letters;
    private ArrayList<Turn> turnLog;
    private Board board;
    public static Dictionary dictionary;

    // Turn instance variables
    private PlayerInfo currPlayer;
    private TurnInfo currentTurn;
    private List<WordInfo> words;
    private int score;
    private boolean notInitial;
    private ArrayList<String> challengers;
    private Thread turnThread;
    private CountDownLatch latch;
    private static final int RACK_SIZE = 7;

    public GameState() {
        status = PENDING;
        players = new ArrayList<>();
        playerMap = new HashMap<>();
        this.setId(UUID.randomUUID().toString());
    }

    public void addPlayer(PlayerInfo p) {
        playerMap.put(p.getId(), p);
        players.add(p);
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    /**
     * Initializes all game elements
     */
    public void initialize() {
        this.setStatus(GameStatus.IN_PROGRESS);
        this.setBoard(new Board());
        this.initializeLetters();
        this.setTurnLog(new ArrayList<Turn>());
        File dict = new File("docs/Collins Scrabble Words (2019).txt");
        try {
            dictionary = new Dictionary(dict);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Initializes the letter bag
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
        for (PlayerInfo p : players) {
            if (p.getVote()) {
                return false;
            }
        }
        return true;
    }

    /**
     * When the game ends, each player's score is reduced by the sum of his or her unplayed letters.
     * In addition, if a player has used all of his or her letters, the sum of the other players' unplayed letters is added to that player's score.
     */
    public void unplayedLetterScores() {
        int scoreReduce = 0;
        for (PlayerInfo p : players) {
            if (!p.getRack().isEmpty()) {
                for (char c : p.getRack()) {
                    p.updateScore(-1 * Letter.map.get(c).getBaseScore());
                    scoreReduce += Letter.map.get(c).getBaseScore();
                    System.out.print("reducing score");
                }
            }
        }
        for (PlayerInfo p : players) {
            if (p.getRack().isEmpty()) {
                p.updateScore(scoreReduce);
            }
        }
    }

}
