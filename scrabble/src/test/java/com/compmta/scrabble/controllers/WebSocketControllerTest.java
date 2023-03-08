package com.compmta.scrabble.controllers;

import com.compmta.scrabble.controllers.DTO.LettersInfo;
import com.compmta.scrabble.controllers.DTO.PlayerId;
import com.compmta.scrabble.controllers.DTO.TurnInfo;
import com.compmta.scrabble.controllers.DTO.VoteInfo;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        PlayerId playerId = new PlayerId("player1");
        ResponseEntity<Void> responseEntity = webSocketController.join(playerId);
        verify(gameStateController, times(1)).joinGame("player1");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testJoinWhenGameIsInProgress() throws Exception {
        PlayerId playerId = new PlayerId("player4");
        ReflectionTestUtils.setField(mockGsController.getGameState(), "status", GameStatus.IN_PROGRESS);
        when(gameStateController.getGameState()).thenReturn(mockGsController.getGameState());
        ResponseEntity<Void> responseEntity = webSocketController.join(playerId);
        verify(gameStateController, times(0)).joinGame(anyString());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testStart() {
        ResponseEntity<Void> responseEntity = webSocketController.start();
        verify(gameStateController, times(1)).setUpGame();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testStartWhenGameIsInProgress() {
        ReflectionTestUtils.setField(mockGsController.getGameState(), "status", GameStatus.IN_PROGRESS);
        when(gameStateController.getGameState()).thenReturn(mockGsController.getGameState());
        ResponseEntity<Void> responseEntity = webSocketController.start();
        verify(gameStateController, times(0)).setUpGame();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testGetGame() throws Exception {
        ReflectionTestUtils.setField(mockGsController.getGameState(), "status", GameStatus.PENDING);
        when(gameStateController.getGameState()).thenReturn(mockGsController.getGameState());
        ResponseEntity<GameState> responseEntity = webSocketController.getGame();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockGsController.getGameState(), responseEntity.getBody());
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
    void testPassTurn() {
        PlayerId playerId = new PlayerId("player1");
        ResponseEntity<Void> responseEntity = webSocketController.passTurn(playerId);
        verify(turnController, times(1)).passTurn(playerId.id());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        responseEntity = webSocketController.passTurn(playerId);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
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

//    @Test
//    void testVoteToEnd() throws InterruptedException{
//        String gameId = (String) ReflectionTestUtils.getField(mockGsController.getGameState(), "id");
//        VoteInfo voteInfo1 = new VoteInfo(gameId, "player1");
//        VoteInfo voteInfo2 = new VoteInfo(gameId, "player2");
//        VoteInfo voteInfo3 = new VoteInfo(gameId, "player3");
//        ResponseEntity<Void> responseEntity = webSocketController.voteToEnd(voteInfo1);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        responseEntity = webSocketController.voteToEnd(voteInfo2);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        responseEntity = webSocketController.voteToEnd(voteInfo3);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        ReflectionTestUtils.setField(webSocketController, "game", mockGsController);
//
//        char[] testChar = {'s', 'c', 'r', 'a', 'p'};
//        ArrayList<Character> testLetters = new ArrayList<Character>();
//        for (char c : testChar) {
//            testLetters.add(c);
//        }
//        List<Integer> blankIndexes = new ArrayList<Integer>();
//
////      reset letter variable in PlayerInfo to the test letters
//        ReflectionTestUtils.setField(player1, "rack", testLetters);
//
//        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
//        TurnInfo turnInfo = new TurnInfo("player4", testChar, 7, 7, true, blankIndexes);
//        responseEntity = webSocketController.placeWord(turnInfo);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
////        GameStatus endGameStatus = (GameStatus) ReflectionTestUtils.getField(webSocketController.getGameState(),"status");
////        assertEquals(GameStatus.FINISHED, endGameStatus);
//    }

    @Test
    void testVoteToEndWhenGameHasEnded(){
        ReflectionTestUtils.setField(mockGsController.getGameState(), "status", GameStatus.FINISHED);
        when(gameStateController.getGameState()).thenReturn(mockGsController.getGameState());
        String gameId = (String) ReflectionTestUtils.getField(mockGsController.getGameState(), "id");
        VoteInfo voteInfo1 = new VoteInfo(gameId, "player1");
        ResponseEntity<Void> responseEntity = webSocketController.voteToEnd(voteInfo1);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testVoteToEndWithInvalidGame(){
        String gameId = "wrong game haha!";
        VoteInfo voteInfo1 = new VoteInfo(gameId, "player1");
        ResponseEntity<Void> responseEntity = webSocketController.voteToEnd(voteInfo1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
