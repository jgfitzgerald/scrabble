package com.compmta.scrabble.controllers.DTO;

import com.compmta.scrabble.model.Board;
import com.compmta.scrabble.model.GameStatus;
import com.compmta.scrabble.model.PlayerInfo;

import java.util.Map;

public record GameStateInfo(String gameId, GameStatus status, Board board, Map<String, PlayerInfo> players) {

}