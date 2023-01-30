package com.compmta.scrabble.controllers.DTO;

import com.compmta.scrabble.model.Board;
import com.compmta.scrabble.model.PlayerInfo;

import java.util.ArrayList;
public record GameStateInfo(String id, Board board, ArrayList<PlayerInfo> players) {

}
