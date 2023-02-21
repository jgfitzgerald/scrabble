package com.compmta.scrabble.controllers.DTO;

import java.util.Map;
//TODO change from map to another structure that will allow duplicate keys
public record TurnInfo(String id, char[] word, int row, int column, boolean isHorizontal, Map<Character, int[]> blanks) {

}
