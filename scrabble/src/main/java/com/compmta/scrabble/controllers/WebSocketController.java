package com.compmta.scrabble.controllers;

import com.compmta.scrabble.controllers.DTO.GameStateInfo;
import com.compmta.scrabble.controllers.DTO.LettersInfo;
import com.compmta.scrabble.controllers.DTO.PlayerId;
import com.compmta.scrabble.controllers.DTO.TurnInfo;
import com.compmta.scrabble.model.GameState;
import com.compmta.scrabble.model.PlayerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

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
    public ResponseEntity<Void> join(@RequestBody PlayerId id) throws Exception {
        log.info("join request for id: {}", id);
        game.joinGame(id.id());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Sends a request to GameStateController to set up the game
     */
    @PostMapping("/start")
    public ResponseEntity<Void> start() {
        log.info("starting...");
        game.setUpGame();
        simpMessagingTemplate.convertAndSend("/game/gameState", game.getGameState());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Fetches the game state
     */
    @GetMapping("/gamestate")
    @SendTo("/game/gameState")
    public ResponseEntity<GameState> getGame() throws Exception {
        log.info("fetch game");
        return ResponseEntity.ok(game.getGameState());
    }

    /**
     * Fetches the current player
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
        if(game.getGameState() == null){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            log.info("Received request to place word " + turnInfo.toString());
            turnController.takeTurn(turnInfo);
            simpMessagingTemplate.convertAndSend("/game/gameState", game.getGameState());

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            log.info("Error: " + e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/pass")
    public ResponseEntity<Void> passTurn(@RequestBody PlayerId id) {
        if(game.getGameState() == null){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            log.info("Received request to pass turn");
            turnController.passTurn(id.id());
            simpMessagingTemplate.convertAndSend("/game/gameState", game.getGameState());

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            log.info("Error: " + e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/exchange")
    public ResponseEntity<Void> exchangeLetters(@RequestBody LettersInfo toExchange) {
        if(game.getGameState() == null){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            log.info("Received request to exchange letters " + toExchange);
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
     * Queries TurnController to challenge the turn with the specified turnInfo
     */
    // TODO implement challenge functionality
    /*@PostMapping("/challenge")
    public ResponseEntity<Void> challengeWord(@RequestBody TurnInfo turnInfo){
        if(game.getGameState() == null){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            log.info("Received request to challenge move " + turnInfo.toString());
            turnController.challengeWord(turnInfo.startCoords(), turnInfo.endCoords(), turnInfo.word(), turnInfo.i());
            simpMessagingTemplate.convertAndSend("/game", game.getGameState());

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            log.info("Error: " + e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/

}