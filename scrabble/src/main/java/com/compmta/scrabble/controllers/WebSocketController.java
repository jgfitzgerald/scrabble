package com.compmta.scrabble.controllers;

import com.compmta.scrabble.controllers.DTO.LettersInfo;
import com.compmta.scrabble.controllers.DTO.PlayerId;
import com.compmta.scrabble.controllers.DTO.TurnInfo;
import com.compmta.scrabble.controllers.DTO.VoteInfo;
import com.compmta.scrabble.model.GameState;
import com.compmta.scrabble.model.PlayerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import static com.compmta.scrabble.model.GameStatus.FINISHED;

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
        if (game.getGameState() != null) {
            log.info("Invalid request, game is already in progress.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("join request for id: {}", id);
        game.joinGame(id.id());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Sends a request to GameStateController to set up the game
     */
    @PostMapping("/start")
    public ResponseEntity<Void> start() {
        if (game.getGameState() != null) {
            log.info("Invalid request, game is already in progress.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
        if (game.getGameState().getStatus() == FINISHED) {
            log.info("This game has already ended.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(game.getGameState() == null){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            log.info(String.format("Received request from %s to place letters: " + turnInfo.word().toString(), turnInfo.id()));
            turnController.takeTurn(turnInfo);
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
        if (game.getGameState().getStatus() == FINISHED) {
            log.info("This game has already ended.");
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
    @PostMapping("/exchange")
    public ResponseEntity<Void> exchangeLetters(@RequestBody LettersInfo toExchange) {
        if (game.getGameState().getStatus() == FINISHED) {
            log.info("This game has already ended.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(game.getGameState() == null){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            log.info(String.format("Received request from %s to exchange letters: " + toExchange.letters().toString(), toExchange.id()));
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
    @PostMapping("/voteToEnd")
    public ResponseEntity<Void> voteToEnd(@RequestBody VoteInfo vote) {
        System.out.println(vote.gameId());
        System.out.println(game.getGameState().getId());
        if (game.getGameState().getStatus() == FINISHED) {
            log.info("This game has already ended.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (game.getGameState() == null || !game.getGameState().getId().equals(vote.gameId())){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            log.info(String.format("Received request from %s to end game.", vote.playerId()));
            game.voteToEnd(vote.playerId());
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
    /*@PostMapping("/challenge")
    public ResponseEntity<Void> challengeWord(@RequestBody ChallengeInfo challenge){
        if(game.getGameState() == null){
            log.info("Invalid request, game not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            log.info("Received request to challenge move " + challenge.toString());
            turnController.challengeWord(challenge);
            simpMessagingTemplate.convertAndSend("/game", game.getGameState());

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            log.info("Error: " + e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/

}