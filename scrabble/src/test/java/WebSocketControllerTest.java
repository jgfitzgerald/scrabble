/* Application Integration Testing */
package com.compmta.scrabble.controllers;

import com.compmta.scrabble.controllers.DTO.*;
import com.compmta.scrabble.model.GameState;
import com.compmta.scrabble.model.GameStatus;
import com.compmta.scrabble.model.PlayerInfo;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebSocketControllerTest {

    @Mock
    private GameStateController gameStateController;

    @Mock
    private TurnController turnController;

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    private WebSocketController webSocketController;

    private GameStateController mockGsController;

    private PlayerInfo player1;
    private PlayerInfo player2;
    private PlayerInfo player3;
    private PlayerInfo player4;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockGsController = new GameStateController();
        MockitoAnnotations.openMocks(this);
        player1 = mockGsController.joinGame("player1");
        player2 = mockGsController.joinGame("player2");
        player3 = mockGsController.joinGame("player3");
        mockGsController.setUpGame();
        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        ReflectionTestUtils.setField(webSocketController, "game", mockGsController);
        turnController.setCurrPlayer(player1);
    }

    @Test
    void testJoin() throws Exception {
        PlayerId playerId = new PlayerId("player4");
        ResponseEntity<GameStateInfo> responseEntity = webSocketController.join(playerId);
        assertNotNull(responseEntity.getStatusCode());
    }
    @Test
    void testJoinWithChosenPlayer() throws Exception {
        PlayerId playerId = new PlayerId("player1");
        assertThrows(IllegalArgumentException.class, () -> webSocketController.join(playerId));
    }
    @Test
    void testJoinWhenReachedMaxPlayer() throws Exception {
        PlayerId playerId = new PlayerId("player4");
        webSocketController.join(playerId);
        PlayerId playerId5 = new PlayerId("player5");
        assertThrows(IllegalStateException.class, () -> webSocketController.join(playerId5));
    }

    @Test
    void testJoinWhenGameIsInProgress() throws Exception {
        PlayerId playerId = new PlayerId("player4");
        ReflectionTestUtils.setField(mockGsController.getGameState(), "status", GameStatus.IN_PROGRESS);
        when(gameStateController.getGameState()).thenReturn(mockGsController.getGameState());
        ResponseEntity<GameStateInfo> responseEntity = webSocketController.join(playerId);
        assertNotNull(responseEntity.getStatusCode());
    }
    @Test
    void testGetGame() throws Exception {
        ReflectionTestUtils.setField(mockGsController.getGameState(), "status", GameStatus.PENDING);
        when(gameStateController.getGameState()).thenReturn(mockGsController.getGameState());
        ResponseEntity<GameStateInfo> responseEntity = webSocketController.getGame();
        GameStateInfo mockGSI = new GameStateInfo(mockGsController.getGameState().getId(), mockGsController.getGameState().getStatus(), mockGsController.getGameState().getBoard(), mockGsController.getGameState().getPlayerMap());
        assertEquals(mockGSI, responseEntity.getBody());
    }


    @Test
    void testGetCurrPlayer() throws Exception {
        PlayerInfo playerInfo = mockGsController.joinGame("player4");
        ReflectionTestUtils.setField(turnController, "currPlayer", playerInfo);
        ResponseEntity<PlayerInfo> responseEntity = webSocketController.getCurrPlayer();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(playerInfo, responseEntity.getBody());
    }


    @Test
    void testPlaceWord() {
        char[] testChar = {'s', 'c', 'r', 'a', 'p'};
        ArrayList<Character> testLetters = new ArrayList<Character>();
        for (char c : testChar) {
            testLetters.add(c);
        }
        List<Integer> blankIndexes = new ArrayList<Integer>();

//      reset letter variable in PlayerInfo to the test letters
        ReflectionTestUtils.setField(player1, "rack", testLetters);

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        TurnInfo turnInfo = new TurnInfo("player1", testChar, 7, 7, true, blankIndexes);

        ReflectionTestUtils.setField(mockGsController.getGameState(), "status", GameStatus.IN_PROGRESS);
        when(gameStateController.getGameState()).thenReturn(mockGsController.getGameState());

        ResponseEntity<Void> responseEntity = webSocketController.placeWord(turnInfo);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testPlaceWordWithUnknownPlayer(){
        char[] testChar = {'s', 'c', 'r', 'a', 'p'};
        List<Integer> blankIndexes = new ArrayList<Integer>();
        TurnInfo turnInfo = new TurnInfo("unknown", testChar, 7, 7, true, blankIndexes);
        ResponseEntity<Void> responseEntity = webSocketController.placeWord(turnInfo);
        assertNotEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testPassTurn() {
        PlayerId playerId = new PlayerId("player1");
        ResponseEntity<Void> responseEntity = webSocketController.passTurn(playerId);
        verify(turnController, times(1)).passTurn(playerId.id());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testPassTurnWhenGameHasEnded() {
        ReflectionTestUtils.setField(mockGsController.getGameState(), "status", GameStatus.FINISHED);
        when(gameStateController.getGameState()).thenReturn(mockGsController.getGameState());
        PlayerId playerId = new PlayerId("player1");
        ResponseEntity<Void> responseEntity = webSocketController.passTurn(playerId);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testPassTurnWithInvalidGame() throws Exception {
        PlayerId playerId = new PlayerId("player1");
        GameStateInfo mockGSI = new GameStateInfo(mockGsController.getGameState().getId(), mockGsController.getGameState().getStatus(), mockGsController.getGameState().getBoard(), mockGsController.getGameState().getPlayerMap());
        ReflectionTestUtils.setField(webSocketController, "game", gameStateController);
        ResponseEntity<Void> responseEntity = webSocketController.passTurn(playerId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testExchangeLetters() {
        char[] testChar = {'s', 'c', 'r', 'a', 'p', 'x', 't'};
        ArrayList<Character> testLetters = new ArrayList<Character>();
        for (char c : testChar) {
            testLetters.add(c);
        }

        //      reset letter variable in PlayerInfo to the test letters
        ReflectionTestUtils.setField(player1, "rack", (ArrayList)testLetters.clone());

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        LettersInfo lettersInfo = new LettersInfo("player1", testChar);
        ResponseEntity<Void> responseEntity = webSocketController.exchangeLetters(lettersInfo);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testExchangeLettersWhenGameHasEnded(){
        ReflectionTestUtils.setField(mockGsController.getGameState(), "status", GameStatus.FINISHED);
        when(gameStateController.getGameState()).thenReturn(mockGsController.getGameState());
        char[] testChar = {'s', 'c', 'r', 'a', 'p', 'x', 't'};
        ArrayList<Character> testLetters = new ArrayList<Character>();
        for (char c : testChar) {
            testLetters.add(c);
        }

        //      reset letter variable in PlayerInfo to the test letters
        ReflectionTestUtils.setField(player1, "rack", (ArrayList)testLetters.clone());

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        LettersInfo lettersInfo = new LettersInfo("player1", testChar);
        ResponseEntity<Void> responseEntity = webSocketController.exchangeLetters(lettersInfo);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testVoteToEnd() throws Exception{
        String gameId = (String) ReflectionTestUtils.getField(mockGsController.getGameState(), "id");
        VoteInfo voteInfo1 = new VoteInfo(gameId, "player1");
        VoteInfo voteInfo2 = new VoteInfo(gameId, "player2");
        VoteInfo voteInfo3 = new VoteInfo(gameId, "player3");
        ResponseEntity<Void> responseEntity = webSocketController.vote(voteInfo1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        responseEntity = webSocketController.vote(voteInfo2);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        responseEntity = webSocketController.vote(voteInfo3);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ReflectionTestUtils.setField(webSocketController, "game", mockGsController);

        char[] testChar = {'s', 'c', 'r', 'a', 'p'};
        ArrayList<Character> testLetters = new ArrayList<Character>();
        for (char c : testChar) {
            testLetters.add(c);
        }
        List<Integer> blankIndexes = new ArrayList<Integer>();

//      reset letter variable in PlayerInfo to the test letters
//        ReflectionTestUtils.setField(player1, "rack", testLetters);

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        TurnInfo turnInfo = new TurnInfo("player1", testChar, 7, 7, true, blankIndexes);
        responseEntity = webSocketController.placeWord(turnInfo);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        GameStateInfo tempGSI = webSocketController.getGame().getBody();
        assertEquals(GameStatus.FINISHED, tempGSI.status());
    }

    @Test
    void testVoteToEndWhenGameHasEnded(){
        ReflectionTestUtils.setField(mockGsController.getGameState(), "status", GameStatus.FINISHED);
        when(gameStateController.getGameState()).thenReturn(mockGsController.getGameState());
        String gameId = (String) ReflectionTestUtils.getField(mockGsController.getGameState(), "id");
        VoteInfo voteInfo1 = new VoteInfo(gameId, "player1");
        ResponseEntity<Void> responseEntity = webSocketController.vote(voteInfo1);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testVoteWithInvalidGame(){
        String gameId = "wrong game haha!";
        VoteInfo voteInfo1 = new VoteInfo(gameId, "player1");
        ResponseEntity<Void> responseEntity = webSocketController.vote(voteInfo1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testChallenge(){
        ReflectionTestUtils.setField(mockGsController.getGameState(), "status", GameStatus.CHALLENGE);
        ChallengeInfo challengeInfo = new ChallengeInfo(mockGsController.getGameState().getId(), "player1");
        char[] testChar = {'s', 't', 'a', 'p', 'l', 'e', 'r'};
        ArrayList<Character> testLetters = new ArrayList<Character>();
        for (char c : testChar) {
            testLetters.add(c);
        }

        //      reset letter variable in PlayerInfo to the test letters
        ReflectionTestUtils.setField(player1, "rack", (ArrayList)testLetters.clone());

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        List<WordInfo> words = new ArrayList<>();
        words.add(new WordInfo("strap", 7,7, true));
        words.add(new WordInfo("sap", 7,7, false));
        turnController.setWords(words);
        CountDownLatch latch = new CountDownLatch(1);
        turnController.setLatch(latch);
        ResponseEntity<Void> responseEntity = webSocketController.challengeWord(challengeInfo);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testChallengeWhenGameStatusIsNotChallenge(){
        ChallengeInfo challengeInfo = new ChallengeInfo(mockGsController.getGameState().getId(), "player1");
        char[] testChar = {'s', 't', 'a', 'p', 'l', 'e', 'r'};
        ArrayList<Character> testLetters = new ArrayList<Character>();
        for (char c : testChar) {
            testLetters.add(c);
        }

        //      reset letter variable in PlayerInfo to the test letters
        ReflectionTestUtils.setField(player1, "rack", (ArrayList)testLetters.clone());

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        List<WordInfo> words = new ArrayList<>();
        words.add(new WordInfo("strap", 7,7, true));
        words.add(new WordInfo("sap", 7,7, false));
        turnController.setWords(words);
        CountDownLatch latch = new CountDownLatch(1);
        turnController.setLatch(latch);
        ResponseEntity<Void> responseEntity = webSocketController.challengeWord(challengeInfo);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testChallengeWithUnknownPlayer(){
        ReflectionTestUtils.setField(mockGsController.getGameState().getStatus(), "status", GameStatus.CHALLENGE);
        ChallengeInfo challengeInfo = new ChallengeInfo(mockGsController.getGameState().getId(), "unknown");
        ResponseEntity<Void> responseEntity = webSocketController.challengeWord(challengeInfo);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

}
