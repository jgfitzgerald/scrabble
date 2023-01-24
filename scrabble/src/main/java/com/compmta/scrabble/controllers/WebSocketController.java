package com.compmta.scrabble.controllers;

import com.compmta.scrabble.model.GameState;
import com.compmta.scrabble.model.PlayerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private BoardController boardController;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/join")
    public ResponseEntity<PlayerInfo> join(@RequestParam("id") String id) throws Exception {
        log.info("join request for id: {}", id);
        return ResponseEntity.ok(game.joinGame(id));
    }

    @GetMapping("start")
    @SendTo("/game")
    public ResponseEntity<Void> start() {
        log.info("starting...");
        game.setUpGame();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/game")
    public ResponseEntity<GameState> getGame() throws Exception {
        log.info("fetch game");
        return ResponseEntity.ok(GameStateController.getGameState());
    }
}