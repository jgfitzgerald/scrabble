package com.compmta.scrabble.controllers.DTO;

public record TurnInfo(String id, String word, int[] startCoords, int[] endCoords) {

}
