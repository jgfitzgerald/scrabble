package com.compmta.scrabble.controllers.DTO;

import java.util.List;
public record TurnInfo(String id, char[] word, int row, int column, boolean isHorizontal, List<Integer> blankIndexes) {

}
