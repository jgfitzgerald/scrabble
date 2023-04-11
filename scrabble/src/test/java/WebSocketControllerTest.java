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

    private GameStateInfo gameStateInfo;

    private String gameID;

    private PlayerInfo player1;
    private PlayerInfo player2;
    private PlayerInfo player3;
    private PlayerInfo player4;

    private JoinInfo player1Joins;
    private JoinInfo player2Joins;
    private JoinInfo player3Joins;
    private JoinInfo player4Joins;


    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        mockGsController = new GameStateController();
        ReflectionTestUtils.setField(webSocketController, "game", mockGsController);
//        MockitoAnnotations.openMocks(this);
        gameStateInfo = webSocketController.newGame().getBody();
        gameID = gameStateInfo.gameId();

        player1 = new PlayerInfo("player1");
        player2 = new PlayerInfo("player2");
        player3 = new PlayerInfo("player3");
        player4 = new PlayerInfo("player4");

//        players join game
        player1Joins = new JoinInfo(gameID,"player1");
        player2Joins = new JoinInfo(gameID,"player2");
        player3Joins = new JoinInfo(gameID,"player3");

        webSocketController.join(player1Joins);
        webSocketController.join(player2Joins);
        webSocketController.join(player3Joins);

        mockGsController.setUpGame(gameID);
        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        webSocketController.getGameDatabase().getBody().get(gameID).setCurrPlayer(player1);
    }

    @Test
    void testJoin() throws Exception {
        player4Joins = new JoinInfo(gameID,"player4");
        ResponseEntity<GameStateInfo> responseEntity = webSocketController.join(player4Joins);
        assertNotNull(responseEntity.getStatusCode());

//        test join with an existing player name
        assertThrows(IllegalStateException.class, () -> webSocketController.join(player1Joins));

//        test join when game has reached the maximum players
        JoinInfo player5Joins = new JoinInfo(gameID,"player5");
        assertThrows(IllegalStateException.class, () -> webSocketController.join(player5Joins));
    }

    @Test
    void testJoinWhenGameIsInProgress() throws Exception {
        player4Joins = new JoinInfo(gameID,"player4");
        ReflectionTestUtils.setField(mockGsController.getGameDatabase().get(gameID), "status", GameStatus.IN_PROGRESS);
        ResponseEntity<GameStateInfo> responseEntity = webSocketController.join(player4Joins);
        assertNotNull(responseEntity.getStatusCode());
    }

    @Test
    void testJoinWhenGameIsFinished() throws Exception {
        player4Joins = new JoinInfo(gameID,"player4");
        ReflectionTestUtils.setField(mockGsController.getGameDatabase().get(gameID), "status", GameStatus.FINISHED);
        ResponseEntity<GameStateInfo> responseEntity = webSocketController.join(player4Joins);
        assertNotNull(responseEntity.getStatusCode());
//        Incorrect, needs to be fixed
    }

    @Test
    void testJoinWithInvalidGame() throws Exception {
        player4Joins = new JoinInfo("Invalid game, haha!","player4");
        ReflectionTestUtils.setField(mockGsController.getGameDatabase().get(gameID), "status", GameStatus.FINISHED);
        ResponseEntity<GameStateInfo> responseEntity = webSocketController.join(player4Joins);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetGame() throws Exception {
        GameStateInfo expectedGSI = new GameStateInfo(gameID, mockGsController.getGameDatabase().get(gameID).getStatus(), mockGsController.getGameDatabase().get(gameID).getBoard(), mockGsController.getGameDatabase().get(gameID).getPlayerMap());
        ResponseEntity<GameStateInfo> responseEntity = webSocketController.getGame(gameID);
        assertEquals(expectedGSI, responseEntity.getBody());
    }


    @Test
    void testGetCurrPlayer() throws Exception {
//        set player3 as the current player
        mockGsController.getGameDatabase().get(gameID).setCurrPlayer(player3);
        ResponseEntity<PlayerInfo> responseEntity = webSocketController.getCurrPlayer(gameID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(player3, responseEntity.getBody());

//        now test with an invalid game
        responseEntity = webSocketController.getCurrPlayer("Invalid game, haha!");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
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
        TurnInfo turnInfo = new TurnInfo(gameID, "player1", testChar, 7, 7, true, blankIndexes);

        ReflectionTestUtils.setField(mockGsController.getGameDatabase().get(gameID), "status",  GameStatus.IN_PROGRESS);

        ResponseEntity<Void> responseEntity = webSocketController.placeWord(turnInfo);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testPlaceWordWithUnknownPlayer(){
        char[] testChar = {'s', 'c', 'r', 'a', 'p'};
        List<Integer> blankIndexes = new ArrayList<Integer>();
        TurnInfo turnInfo = new TurnInfo(gameID,"unknown", testChar, 7, 7, true, blankIndexes);
        ResponseEntity<Void> responseEntity = webSocketController.placeWord(turnInfo);
        assertNotEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testPassTurn() {
        PlayerId playerId = new PlayerId(gameID, "player1");
        ResponseEntity<Void> responseEntity = webSocketController.passTurn(playerId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testPassTurnWhenGameHasEnded() {
        ReflectionTestUtils.setField(mockGsController.getGameDatabase().get(gameID), "status", GameStatus.FINISHED);
        PlayerId playerId = new PlayerId(gameID, "player1");
        ResponseEntity<Void> responseEntity = webSocketController.passTurn(playerId);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testPassTurnWithInvalidGame() throws Exception {
        ReflectionTestUtils.setField(mockGsController.getGameDatabase().get(gameID), "status", GameStatus.FINISHED);
        PlayerId playerId = new PlayerId("Invalid game, haha!", "player1");
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

        LettersInfo lettersInfo = new LettersInfo(gameID,"player1", testChar);
        ResponseEntity<Void> responseEntity = webSocketController.exchangeLetters(lettersInfo);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testExchangeLettersWhenGameHasEnded(){
        ReflectionTestUtils.setField(mockGsController.getGameDatabase().get(gameID), "status", GameStatus.FINISHED);
        char[] testChar = {'s', 'c', 'r', 'a', 'p', 'x', 't'};
        ArrayList<Character> testLetters = new ArrayList<Character>();
        for (char c : testChar) {
            testLetters.add(c);
        }

        //      reset letter variable in PlayerInfo to the test letters
        ReflectionTestUtils.setField(player1, "rack", (ArrayList)testLetters.clone());

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        LettersInfo lettersInfo = new LettersInfo(gameID,"player1", testChar);
        ResponseEntity<Void> responseEntity = webSocketController.exchangeLetters(lettersInfo);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testVoteToEnd() throws Exception{
        HashMap<String, PlayerInfo> playerMap = mockGsController.getGameDatabase().get(gameID).getPlayerMap();
        //        players vote to start
        playerMap.get(player1.getId()).setVote(true);
        playerMap.get(player2.getId()).setVote(true);
        playerMap.get(player3.getId()).setVote(true);

        VoteInfo voteInfo1 = new VoteInfo(gameID, "player1");
        VoteInfo voteInfo2 = new VoteInfo(gameID, "player2");
        VoteInfo voteInfo3 = new VoteInfo(gameID, "player3");
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
        ReflectionTestUtils.setField(player1, "rack", testLetters);

        ReflectionTestUtils.setField(turnController, "gsController", mockGsController);
        TurnInfo turnInfo = new TurnInfo(gameID,"player1", testChar, 7, 7, true, blankIndexes);
        responseEntity = webSocketController.placeWord(turnInfo);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testVoteToEndWhenGameHasEnded(){
        ReflectionTestUtils.setField(mockGsController.getGameDatabase().get(gameID), "status", GameStatus.FINISHED);
        VoteInfo voteInfo1 = new VoteInfo(gameID, "player1");
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
        ReflectionTestUtils.setField(mockGsController.getGameDatabase().get(gameID), "status", GameStatus.CHALLENGE);
        ChallengeInfo challengeInfo = new ChallengeInfo(gameID, "player1");
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
        ResponseEntity<Void> responseEntity = webSocketController.challengeWord(challengeInfo);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testChallengeWhenGameStatusIsNotChallenge(){
        ChallengeInfo challengeInfo = new ChallengeInfo(gameID, "player1");
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
        ResponseEntity<Void> responseEntity = webSocketController.challengeWord(challengeInfo);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testChallengeWithUnknownPlayer(){
        ChallengeInfo challengeInfo = new ChallengeInfo(gameID, "unknown");
        ResponseEntity<Void> responseEntity = webSocketController.challengeWord(challengeInfo);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

}
