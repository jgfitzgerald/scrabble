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
     * Sends a request to GameStateController to add a new player to the game
    */
    @PostMapping("/join")
    public ResponseEntity<GameStateInfo> join(@RequestBody PlayerId id) throws Exception {
        log.info("join request for id: {}", id);
        game.joinGame(id.id());
        return ResponseEntity.ok(new GameStateInfo(game.getGameState().getId(), game.getGameState().getStatus(), game.getGameState().getBoard(), game.getGameState().getPlayerMap()));
    }

    /**
     * Fetches the game state
     */
    @GetMapping("/gamestate")
    @SendTo("/game/gameState")
    public ResponseEntity<GameStateInfo> getGame() throws Exception {
        log.info("fetch game");
        return ResponseEntity.ok(new GameStateInfo(game.getGameState().getId(), game.getGameState().getStatus(), game.getGameState().getBoard(), game.getGameState().getPlayerMap()));
    }

    /**
     * Fetches the current player, or the winner if the game has finished
     */
    @GetMapping("/currPlayer")
    public ResponseEntity<PlayerInfo> getCurrPlayer() throws Exception {
        return ResponseEntity.ok(turnController.currPlayer);
    }

    /**
     * Queries TurnController to make the move with the specified turnInfo
     */
    @PostMapping("/move")
    public ResponseEntity<Void> placeWord(@RequestBody TurnInfo turnInfo){
        if (game.getGameState().getStatus() != IN_PROGRESS) {
            log.info("Error: Cannot place move when game is " + game.getGameState().getStatus());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(game.getGameState() == null){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!game.getGameState().getPlayerMap().containsKey(turnInfo.id())) {
            log.info("Invalid request, player not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            log.info(String.format("Received request from %s to place letters: " + Arrays.toString(turnInfo.word()), turnInfo.id()));
            turnController.takeTurn(turnInfo);
            simpMessagingTemplate.convertAndSend("/game/gameState", game.getGameState());
            turnController.challengePhase(turnInfo);
            simpMessagingTemplate.convertAndSend("/game/gameState", game.getGameState());
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
        if (game.getGameState().getStatus() != IN_PROGRESS) {
            log.info("Error: Cannot pass turn when game is " + game.getGameState().getStatus());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(game.getGameState() == null){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            log.info(String.format("Received request to pass %s's turn.",id));
            turnController.passTurn(id.id());
            simpMessagingTemplate.convertAndSend("/game/gameState", game.getGameState());

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
        if (game.getGameState().getStatus() != IN_PROGRESS) {
            log.info("Error: Cannot exchange tiles when game is " + game.getGameState().getStatus());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(game.getGameState() == null){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            log.info(String.format("Received request from %s to exchange letters: " + Arrays.toString(toExchange.letters()), toExchange.id()));
            turnController.exchangeLetters(toExchange.id(), toExchange.letters());
            simpMessagingTemplate.convertAndSend("/game/gameState", game.getGameState());

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
        if (game.getGameState().getStatus() == FINISHED) {
            log.info("This game has already ended.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (game.getGameState() == null || !game.getGameState().getId().equals(vote.gameId())){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            log.info(String.format("Received vote request from %s.", vote.playerId()));
            game.vote(vote.playerId());
            simpMessagingTemplate.convertAndSend("/game/gameState", game.getGameState());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            log.info("Error: " + e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/challenge")
    public ResponseEntity<Void> challengeWord(@RequestBody ChallengeInfo challengerInfo) {
        if (game.getGameState().getStatus() != CHALLENGE) {
            log.info("Can't challenge word when game status is " + game.getGameState().getStatus());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (game.getGameState() == null || !game.getGameState().getId().equals(challengerInfo.gameId())){
            log.info("Game not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!game.getGameState().getPlayerMap().containsKey(challengerInfo.challengerId())) {
            System.out.println(game.getGameState().getPlayerMap().toString());
            log.info("Player not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            log.info(String.format("Received request from %s to challenge turn.", challengerInfo.challengerId()));
            turnController.challengeWord(challengerInfo);
            simpMessagingTemplate.convertAndSend("/game/gameState", game.getGameState());
        } catch (Exception e) {
            log.info("Error: " + e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}