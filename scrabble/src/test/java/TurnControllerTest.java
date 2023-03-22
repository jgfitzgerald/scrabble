package com.compmta.scrabble.controllers;


import com.compmta.scrabble.controllers.DTO.*;
import com.compmta.scrabble.model.*;
import org.apache.tomcat.jni.Thread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.Mockito.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;

import java.util.*;
import java.util.List;

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
    private GameState gameState;
    private String gameID;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockGsController = new GameStateController();
        gameState = mockGsController.newGame();
        gameID = gameState.getId();
        player1 = mockGsController.joinGame(gameID, "player1");
        player2 = mockGsController.joinGame(gameID,"player2");
        player3 = mockGsController.joinGame(gameID,"player3");
        player4 = mockGsController.joinGame(gameID,"player4");
        mockGsController.setUpGame(gameState.getId());
        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        mockGsController.getGameDatabase().get(gameID).setCurrPlayer(player1);

        HashMap<String, PlayerInfo> playerMap = mockGsController.getGameDatabase().get(gameID).getPlayerMap();
        //        players vote to start
        playerMap.get(player1.getId()).setVote(true);
        playerMap.get(player2.getId()).setVote(true);
        playerMap.get(player3.getId()).setVote(true);

    }

    @Test
    public void testTakeTurnWhenTurnInfoIsNull() throws InterruptedException {
        assertNull(turnController.takeTurn(null));
    }

    @Test
    public void testTakeTurnWhenChallengerExists() throws InterruptedException {
        mockGsController.setUpGame(gameID);
        challengers.add("player1");
        challengers.add("player4");
        mockGsController.getGameDatabase().get(gameID).setChallengers(challengers);
        char[] testChar = {'t', 'e', 's', 't'};
        ArrayList<Character> testLetters = new ArrayList<Character>();
        for (char c : testChar) {
            testLetters.add(c);
        }
        //      reset letter variable in PlayerInfo to the test letters
        ReflectionTestUtils.setField(player1, "rack", testLetters);
        List<Integer> blankIndexes = new ArrayList<Integer>();
        assertNotNull(turnController.takeTurn(new TurnInfo(gameID,"player1", testChar, 7, 7, true, blankIndexes)));
    }

    @Test
    public void testTakeTurnWhenPlayerIsNotInGame() throws InterruptedException {
        char[] testChar = {'t', 'e', 's', 't'};
        List<Integer> blankIndexes = new ArrayList<Integer>();
        blankIndexes.add(1);
        blankIndexes.add(0);
        assertThrows(IllegalArgumentException.class, () -> turnController.takeTurn(new TurnInfo(gameID,"player5", testChar, 0, 0, true, blankIndexes)));
    }

    @Test
    public void testTakeTurnWhenNotCurrPlayerTurn() throws InterruptedException {
        mockGsController.getGameDatabase().get(gameID).setChallengers(challengers);
        char[] testChar = {'t', 'e', 's', 't'};
        List<Integer> blankIndexes = new ArrayList<Integer>();
        blankIndexes.add(1);
        blankIndexes.add(0);
        assertThrows(IllegalArgumentException.class, () -> turnController.takeTurn(new TurnInfo(gameID,"player2", testChar, 0, 0, true, blankIndexes)));
    }

    @Test
    void testTakeTurnWithInvalidInitialMoveRequest() {
        mockGsController.getGameDatabase().get(gameID).setChallengers(challengers);
        char[] testChar = {'t', 'e', 's', 't'};
        List<Integer> blankIndexes = new ArrayList<Integer>();
        blankIndexes.add(1);
        blankIndexes.add(0);
        TurnInfo turnInfo = new TurnInfo(gameID,"player1", testChar, 7, 7, true, Arrays.asList(0));
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

//      reset letter variable in PlayerInfo to the test letters
        ReflectionTestUtils.setField(player1, "rack", testLetters);

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        TurnInfo turnInfo = new TurnInfo(gameID,"player1", testChar, 7, 7, true, blankIndexes);
        assertNotNull(turnController.takeTurn(turnInfo));
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

        turnController.exchangeLetters(gameID,"player1", testChar);
        Object newCharSet = ReflectionTestUtils.getField(player1, "rack");
        ArrayList<Character> newTestLetters = (ArrayList) newCharSet;
        assertNotEquals(testLetters, newTestLetters);

    }

    @Test
    void testChallengeWord(){
        char[] testChar = {'s', 't', 'a', 'p', 'l', 'e', 'r'};
        ArrayList<Character> testLetters = new ArrayList<Character>();
        for (char c : testChar) {
            testLetters.add(c);
        }

        //      reset letter variable in PlayerInfo to the test letters
        ReflectionTestUtils.setField(player1, "rack", (ArrayList)testLetters.clone());

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);

        ChallengeInfo challengeInfo = new ChallengeInfo(gameID, "player1");
        List<WordInfo> words = new ArrayList<>();
        words.add(new WordInfo("strap", 7,7, true));
        words.add(new WordInfo("sap", 7,7, false));
        mockGsController.getGameDatabase().get(gameID).setWords(words);
        CountDownLatch latch = new CountDownLatch(1);
        mockGsController.getGameDatabase().get(gameID).setLatch(latch);
        turnController.challengeWord(challengeInfo);
        assertFalse(mockGsController.getGameDatabase().get(gameID).getChallengers().isEmpty());

    }

    @Test
    void testChallengePhase() throws InterruptedException{
        char[] testChar = {'s', 't', 'a', 'p', 'l', 'e', 'r'};
        ArrayList<Character> testLetters = new ArrayList<Character>();
        for (char c : testChar) {
            testLetters.add(c);
        }
        List<Integer> blankIndexes = new ArrayList<Integer>();

//      reset letter variable in PlayerInfo to the test letters
        ReflectionTestUtils.setField(player1, "rack", testLetters);

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        TurnInfo turnInfo = new TurnInfo(gameID,"player1", testChar, 7, 7, true, blankIndexes);

        // Create a mock CountDownLatch
        CountDownLatch mockLatch = mock(CountDownLatch.class);

        mockGsController.getGameDatabase().get(gameID).setLatch(mockLatch);
        turnController.challengePhase(turnInfo);
    }

    @Test
    public void testChallengePhase_interrupted() throws InterruptedException {
        char[] testChar = {'s', 't', 'a', 'p', 'l', 'e', 'r'};
        char[] turnChar = {'t','a','p'};
        char[] mockTurnChar = {'l','a','p'};
        ArrayList<Character> testLetters = new ArrayList<Character>();
        for (char c : testChar) {
            testLetters.add(c);
        }
        List<Integer> blankIndexes = new ArrayList<Integer>();

//      reset letter variable in PlayerInfo to the test letters
        ReflectionTestUtils.setField(player1, "rack", testLetters);

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);

        List<WordInfo> words = new ArrayList<>();
        words.add(new WordInfo("strap", 7,7, true));
        words.add(new WordInfo("sap", 7,7, false));
        mockGsController.getGameDatabase().get(gameID).setWords(words);


        TurnInfo turnInfo = new TurnInfo(gameID,"player1", turnChar, 7, 7, true, blankIndexes);

        TurnInfo mockTurnInfo = new TurnInfo(gameID,"player1", mockTurnChar, 7, 7, false, blankIndexes);
        mockGsController.getGameDatabase().get(gameID).getTurnLog().add(new Turn(mockTurnInfo));
        // Create a mock CountDownLatch
        CountDownLatch mockLatch = mock(CountDownLatch.class);
        mockGsController.getGameDatabase().get(gameID).setLatch(mockLatch);

        // Set up the mock to throw an InterruptedException when await() is called
        doThrow(new InterruptedException()).when(mockLatch).await(5, TimeUnit.SECONDS);

        // Call the challengePhase method
        turnController.challengePhase(turnInfo);

        assertEquals(GameStatus.IN_PROGRESS, mockGsController.getGameDatabase().get(gameID).getStatus());


    }


}