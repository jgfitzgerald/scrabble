package com.compmta.scrabble.controllers.DTO;

public record ChallengeInfo(String challengerId, String playerId, String word, int row, int column, boolean isHorizontal) {
}
