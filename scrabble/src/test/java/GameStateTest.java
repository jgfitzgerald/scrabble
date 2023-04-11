package com.compmta.scrabble.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    private GameState gameState;
    private ArrayList<PlayerInfo> players;

    @BeforeEach
    void setUp() {
        gameState = new GameState();
        players = new ArrayList<>();
        players.add(new PlayerInfo("player1"));
        players.add(new PlayerInfo("player2"));
    }

    @Test
    void testAddPlayer() {
        gameState.addPlayer(players.get(0));
        gameState.addPlayer(players.get(1));
        assertEquals(players, gameState.getPlayers());
        assertEquals(players.get(0), gameState.getPlayerMap().get("player1"));
        assertEquals(players.get(1), gameState.getPlayerMap().get("player2"));
    }

    @Test
    void testSetStatus() {
        gameState.setStatus(GameStatus.IN_PROGRESS);
        assertEquals(GameStatus.IN_PROGRESS, gameState.getStatus());
    }

    @Test
    void testInitializeLetters() {
        gameState.initializeLetters();
        assertFalse(gameState.getLetters().isEmpty());
    }

    @Test
    void testDrawLetters() {
        PlayerInfo p = new PlayerInfo("player1");
        gameState.setLetters(new ArrayList<>());
        gameState.drawLetters(p);
        assertTrue(p.getRack().isEmpty());

        gameState.initializeLetters();
        gameState.drawLetters(p);
        assertFalse(gameState.getLetters().isEmpty());
        assertEquals(7, p.getRack().size());
    }
    @Test
    void testCheckEndConditions() {
        gameState.setPlayers(players);
        PlayerInfo p1 = gameState.getPlayers().get(0);
        PlayerInfo p2 = gameState.getPlayers().get(1);
        p1.getRack().add('a');
        p2.getRack().add('b');
        players.add(p1);
        players.add(p2);
        gameState.initializeLetters();
        assertTrue(gameState.checkEndConditions());

        p1.getRack().clear();
        assertTrue(gameState.checkEndConditions());

        p2.getRack().clear();
        assertTrue(gameState.checkEndConditions());

//test when both players vote to end game
        p1.setVote(true);
        p2.setVote(true);
        assertFalse(gameState.checkEndConditions());

    }

    @Test
    void testUnplayedLetterScores() {
        gameState.setPlayers(players);
        PlayerInfo p1 = gameState.getPlayers().get(0);
        PlayerInfo p2 = gameState.getPlayers().get(1);
        p1.getRack().add('a');
        p2.getRack().add('b');

        gameState.initializeLetters();

        gameState.unplayedLetterScores();
        assertEquals(-1, p1.getTotalScore());
        assertEquals(-3, p2.getTotalScore());

        p1.getRack().clear();
        gameState.unplayedLetterScores();
        assertEquals(2, p1.getTotalScore());
        assertEquals(-6, p2.getTotalScore());

        p2.getRack().clear();
        gameState.unplayedLetterScores();
        assertEquals(2, p1.getTotalScore());
        assertEquals(-6, p2.getTotalScore());
    }
}
