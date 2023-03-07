package com.compmta.scrabble.controllers;


import com.compmta.scrabble.controllers.DTO.TurnInfo;
import com.compmta.scrabble.model.Board;
import com.compmta.scrabble.model.GameState;
import com.compmta.scrabble.model.GameStatus;
import com.compmta.scrabble.model.PlayerInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static com.compmta.scrabble.model.GameStatus.FINISHED;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TurnControllerTest {
    @InjectMocks
    private TurnController turnController = new TurnController();
    @Mock
    private GameStateController gsController;

    private GameStateController mockGsController;
    private ArrayList<String> challengers = new ArrayList<>();

    private PlayerInfo player1;
    private PlayerInfo player2;
    private PlayerInfo player3;

    private PlayerInfo player4;

    @BeforeEach
    public void setUp() {
        mockGsController = new GameStateController();
        MockitoAnnotations.openMocks(this);
        player1 = mockGsController.joinGame("player1");
        player2 = mockGsController.joinGame("player2");
        player3 = mockGsController.joinGame("player3");
        player4 = mockGsController.joinGame("player4");
        mockGsController.setUpGame();
        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        turnController.setCurrPlayer(player1);

    }

    @Test
    public void testTakeTurnWhenTurnInfoIsNull() throws InterruptedException {
        assertNull(turnController.takeTurn(null));
    }

    @Test
    public void testTakeTurnWhenChallengerExists() throws InterruptedException {
        gsController.setUpGame();
        challengers.add("player1");
        challengers.add("player4");
        turnController.setChallengers(challengers);
        char[] testChar = {'t', 'e', 's', 't'};
        List<Integer> blankIndexes = new ArrayList<Integer>();
        blankIndexes.add(1);
        blankIndexes.add(0);
        assertNull(turnController.takeTurn(new TurnInfo("player1", testChar, 0, 0, true, blankIndexes)));
    }

    @Test
    public void testTakeTurnWhenPlayerIsNotInGame() throws InterruptedException {
        char[] testChar = {'t', 'e', 's', 't'};
        List<Integer> blankIndexes = new ArrayList<Integer>();
        blankIndexes.add(1);
        blankIndexes.add(0);
        assertThrows(IllegalArgumentException.class, () -> turnController.takeTurn(new TurnInfo("player5", testChar, 0, 0, true, blankIndexes)));
    }

    @Test
    public void testTakeTurnWhenNotCurrPlayerTurn() throws InterruptedException {
        turnController.setChallengers(challengers);
        char[] testChar = {'t', 'e', 's', 't'};
        List<Integer> blankIndexes = new ArrayList<Integer>();
        blankIndexes.add(1);
        blankIndexes.add(0);
        assertThrows(IllegalArgumentException.class, () -> turnController.takeTurn(new TurnInfo("player2", testChar, 0, 0, true, blankIndexes)));
    }

    @Test
    void testTakeTurnWithInvalidInitialMoveRequest() {
        turnController.setChallengers(challengers);
        char[] testChar = {'t', 'e', 's', 't'};
        List<Integer> blankIndexes = new ArrayList<Integer>();
        blankIndexes.add(1);
        blankIndexes.add(0);
        TurnInfo turnInfo = new TurnInfo("player1", testChar, 7, 7, true, Arrays.asList(0));
        assertThrows(IllegalArgumentException.class, () -> turnController.takeTurn(turnInfo));
    }

        @Test
    void testTakeTurnWithValidRequest() throws InterruptedException {
        char[] testChar = {'s', 'c', 'r', 'a', 'p'};
        ArrayList<Character> testLetters = new ArrayList<Character>();
        for (char c : testChar) {
            testLetters.add(c);
        }
        List<Integer> blankIndexes = new ArrayList<Integer>();
//      reset letter variable in game state to the test letters
        GameState gs = mockGsController.getGameState() ;
        ReflectionTestUtils.setField(gs, "letters", testLetters);

//      reset letter variable in PlayerInfo to the test letters
        ReflectionTestUtils.setField(player1, "rack", testLetters);

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        TurnInfo turnInfo = new TurnInfo("player1", testChar, 7, 7, true, blankIndexes);
        assertNotNull(turnController.takeTurn(turnInfo));
    }

    @Test
    void testEndTurn(){
//      end player1 turn
        turnController.endTurn();
        char[] testChar = {'t', 'e', 's', 't'};
        List<Integer> blankIndexes = new ArrayList<Integer>();
        blankIndexes.add(1);
        blankIndexes.add(0);
        assertThrows(IllegalArgumentException.class, () -> turnController.takeTurn(new TurnInfo("player1", testChar, 0, 0, true, blankIndexes)));
    }

    @Test
    void testExchangeLetters(){
        char[] testChar = {'s', 'c', 'r', 'a', 'p', 'x', 't'};
        ArrayList<Character> testLetters = new ArrayList<Character>();
        for (char c : testChar) {
            testLetters.add(c);
        }

        //      reset letter variable in PlayerInfo to the test letters
        ReflectionTestUtils.setField(player1, "rack", (ArrayList)testLetters.clone());

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);

        turnController.exchangeLetters("player1", testChar);
        Object newCharSet = ReflectionTestUtils.getField(player1, "rack");
        ArrayList<Character> newTestLetters = (ArrayList) newCharSet;
        assertNotEquals(testLetters, newTestLetters);

    }

    @Test
    void testEndGame(){
        turnController.endGame();
        Object gameStatus = ReflectionTestUtils.getField(mockGsController.getGameState(), "status");
        assertEquals(GameStatus.FINISHED, gameStatus);
    }

}