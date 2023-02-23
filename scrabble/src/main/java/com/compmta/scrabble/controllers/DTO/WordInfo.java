package com.compmta.scrabble.controllers.DTO;

import lombok.Setter;

public record WordInfo(String word, int row, int col, boolean isHorizontal) {
}
