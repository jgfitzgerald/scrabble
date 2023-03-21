package com.compmta.scrabble.controllers;

import com.compmta.scrabble.controllers.DTO.*;
import com.compmta.scrabble.model.GameState;
import com.compmta.scrabble.model.PlayerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

import static com.compmta.scrabble.model.GameStatus.*;

@RestController
@Slf4j
@CrossOrigin
public class WebSocketController {

    @Autowired
    private GameStateController game;

    @Autowired
    private TurnController turnController;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Sends a request to GameStateController to create a new game
     */
    @PostMapping("/newGame")
    public ResponseEntity<GameStateInfo> newGame() {
        log.info("Received request to create new game.");
        GameState newGame = game.newGame();
        return ResponseEntity.ok(new GameStateInfo(newGame.getId(), newGame.getStatus(), newGame.getBoard(), newGame.getPlayerMap()));
    }

    /**
     * Sends a request to GameStateController to add a new player to the game
    */
    @PostMapping("/join")
    public ResponseEntity<GameStateInfo> join(@RequestBody JoinInfo joinInfo) throws Exception {
        log.info("join request for id: {}, game id {}", joinInfo.gameId(), joinInfo.playerId());
        if (game.getGameDatabase().get(joinInfo.gameId()) == null) {
            log.info("Error: Game id not found: " + joinInfo.gameId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        game.joinGame(joinInfo.gameId(), joinInfo.playerId());
        sendGameState(joinInfo.gameId());
        return ResponseEntity.ok(new GameStateInfo(joinInfo.gameId(), game.getGameDatabase().get(joinInfo.gameId()).getStatus(), game.getGameDatabase().get(joinInfo.gameId()).getBoard(), game.getGameDatabase().get(joinInfo.gameId()).getPlayerMap()));
    }

    /**
     * Fetches the game state
     */
    @GetMapping("/gamestate")
    @SendTo("/game/gameState")
    public ResponseEntity<GameStateInfo> getGame(@RequestParam String gameId) throws Exception {
        if (game.getGameDatabase().get(gameId) == null) {
            log.info("Error: Game id not found: " + gameId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("fetch game");
        return ResponseEntity.ok(new GameStateInfo(gameId, game.getGameDatabase().get(gameId).getStatus(), game.getGameDatabase().get(gameId).getBoard(), game.getGameDatabase().get(gameId).getPlayerMap()));
    }

    /**
     * Fetches the current player, or the winner if the game has finished
     */
    @GetMapping("/currPlayer")
    public ResponseEntity<PlayerInfo> getCurrPlayer(@RequestParam String gameId) throws Exception {
        if (game.getGameDatabase().get(gameId) == null) {
            log.info("Error: Game id not found: " + gameId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game.getGameDatabase().get(gameId).getCurrPlayer());
    }

    /**
     * Queries TurnController to make the move with the specified turnInfo
     */
    @PostMapping("/move")
    public ResponseEntity<Void> placeWord(@RequestBody TurnInfo turnInfo){
        if (game.getGameDatabase().get(turnInfo.gameId()) == null) {
            log.info("Error: Cannot find game ID: " + turnInfo.gameId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!game.getGameDatabase().get(turnInfo.gameId()).getPlayerMap().containsKey(turnInfo.playerId())) {
            log.info("Invalid request, player not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (game.getGameDatabase().get(turnInfo.gameId()).getStatus() != IN_PROGRESS) {
            log.info("Error: Cannot place move when game is " + game.getGameDatabase().get(turnInfo.gameId()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try{
            log.info(String.format("Received request from %s to place letters: " + Arrays.toString(turnInfo.word()), turnInfo.playerId()));
            turnController.takeTurn(turnInfo);
            sendGameState(turnInfo.gameId());
            turnController.challengePhase(turnInfo);
            sendGameState(turnInfo.gameId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            log.info("Error: " + e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows a player to pass their turn
     * @param id The player who wishes to pass their turn
     */
    @PostMapping("/pass")
    public ResponseEntity<Void> passTurn(@RequestBody PlayerId id) {
        if(game.getGameDatabase().get(id.gameId()) == null){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (game.getGameDatabase().get(id.gameId()).getStatus() != IN_PROGRESS) {
            log.info("Error: Cannot pass turn when game is " + game.getGameDatabase().get(id.gameId()).getStatus());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try{
            log.info(String.format("Received request to pass %s's turn.",id));
            turnController.passTurn(id.gameId(), id.playerId());
            sendGameState(id.gameId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            log.info("Error: " + e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows a player to exchange letters
     * @param toExchange DTO containing the player and the letters they wish to exchange
     */
    @PatchMapping("/exchange")
    public ResponseEntity<Void> exchangeLetters(@RequestBody LettersInfo toExchange) {
        if(game.getGameDatabase().get(toExchange.gameId()) == null){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (game.getGameDatabase().get(toExchange.gameId()).getPlayerMap().get(toExchange.playerId()) == null) {
            log.info("Invalid request, player not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (game.getGameDatabase().get(toExchange.gameId()).getStatus() != IN_PROGRESS) {
            log.info("Error: Cannot exchange tiles when game is " + game.getGameDatabase().get(toExchange.gameId()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try{
            log.info(String.format("Received request from %s to exchange letters: " + Arrays.toString(toExchange.letters()), toExchange.playerId()));
            turnController.exchangeLetters(toExchange.gameId(), toExchange.playerId(), toExchange.letters());
            sendGameState(toExchange.gameId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            log.info("Error: " + e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows a player to vote to end the game (eg. if they think there are no possible moves left)
     * If all players vote to end the game, then the game ends on the next turn.
     * @param vote
     */
    @PatchMapping("/vote")
    public ResponseEntity<Void> vote(@RequestBody VoteInfo vote) {
        if (game.getGameDatabase().get(vote.gameId()) == null) {
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (game.getGameDatabase().get(vote.gameId()).getPlayerMap().get(vote.playerId()) == null) {
            log.info("Invalid request, player not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (game.getGameDatabase().get(vote.gameId()).getStatus() == FINISHED) {
            log.info("This game has already ended.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try{
            log.info(String.format("Received vote request from %s.", vote.playerId()));
            game.vote(vote.gameId(), vote.playerId());
            sendGameState(vote.gameId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            log.info("Error: " + e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/challenge")
    public ResponseEntity<Void> challengeWord(@RequestBody ChallengeInfo challengerInfo) {
        if (game.getGameDatabase().get(challengerInfo.gameId()) == null){
            log.info("Game not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!game.getGameDatabase().get(challengerInfo.gameId()).getPlayerMap().containsKey(challengerInfo.challengerId())) {
            System.out.println(game.getGameDatabase().get(challengerInfo.gameId()).getPlayerMap().toString());
            log.info("Player not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (game.getGameDatabase().get(challengerInfo.gameId()).getStatus() != CHALLENGE) {
            log.info("Can't challenge word when game status is " + game.getGameDatabase().get(challengerInfo.gameId()).getStatus());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            log.info(String.format("Received request from %s to challenge turn.", challengerInfo.challengerId()));
            turnController.challengeWord(challengerInfo);
            sendGameState(challengerInfo.gameId());
        } catch (Exception e) {
            log.info("Error: " + e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getDatabase")
    public ResponseEntity<Map<String, GameState>> getGameDatabase() {
        return ResponseEntity.ok(game.getGameDatabase());
    }

    private void sendGameState(String gameStateId){
        log.info("Websocket broadcasting game to lobby ({})", gameStateId);
        simpMessagingTemplate.convertAndSend("/game/gameState/" + gameStateId, game.getGameDatabase().get(gameStateId));
    }
}