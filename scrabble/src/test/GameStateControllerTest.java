package com.compmta.scrabble.controllers;

import com.compmta.scrabble.model.GameState;
import com.compmta.scrabble.model.PlayerInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateControllerTest {

    private GameStateController gameStateController;

    @BeforeEach
    void setUp() {
        gameStateController = new GameStateController();
    }

    @Test
    void testJoinGame() {
        PlayerInfo player1 = gameStateController.joinGame("player1");
        assertNotNull(player1);
        assertEquals("player1", player1.getId());
    }

    @Test
    void testJoinGameWhenGameIsFull() {
        for (int i = 0; i < GameStateController.MAX_PLAYERS; i++) {
            gameStateController.joinGame("player" + (i + 1));
        }
        assertThrows(IllegalStateException.class, () -> gameStateController.joinGame("player5"));
    }

    @Test
    void testJoinGameWithSameName() {
        PlayerInfo player1 = gameStateController.joinGame("player1");
        assertThrows(IllegalArgumentException.class, () -> gameStateController.joinGame("player1"));
    }

    @Test
    void testSetUpGameWithLessThanMinPlayers() {
        gameStateController.joinGame("player1");
        assertThrows(IllegalStateException.class, () -> gameStateController.setUpGame());
    }

    @Test
    void testSetUpGame() {
        for (int i = 0; i < GameStateController.MAX_PLAYERS; i++) {
            gameStateController.joinGame("player" + (i + 1));
        }
        gameStateController.setUpGame();
        GameState gameState = gameStateController.getGameState();
        assertNotNull(gameState);
        assertEquals(GameState.initialize(gameStateController.playerList).getBoard(), gameState.getBoard());
        assertEquals(gameStateController.players, gameState.getPlayerMap());
        for (PlayerInfo p : gameState.getPlayers()) {
            assertEquals(GameStateController.INITIAL_LETTER_AMT, p.getLetters().size());
        }
        assertNotNull(TurnController.getCurrPlayer());
        assertTrue(TurnController.getChallengers().isEmpty());
    }

    @Test
    void testVoteToEnd() {
        for (int i = 0; i < GameStateController.MAX_PLAYERS; i++) {
            gameStateController.joinGame("player" + (i + 1));
        }
        gameStateController.setUpGame();
        gameStateController.voteToEnd("player1");
        assertTrue(gameStateController.getGameState().getPlayers().stream().allMatch(PlayerInfo::getVote));
    }

}
