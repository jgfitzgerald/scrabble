package com.compmta.scrabble.controllers;

//imports
import com.compmta.scrabble.controllers.DTO.GameStateInfo;
import com.compmta.scrabble.controllers.DTO.TurnInfo;
import com.compmta.scrabble.model.Board;
import com.compmta.scrabble.model.PlayerInfo;
import com.compmta.scrabble.model.Turn;
import com.compmta.scrabble.util.WordJudge;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class TurnController {

    //instance variables
    private static PlayerInfo currPlayer;
    static Board board;

    /**
     * Takes a TurnInfo DTO and applies the given requests if applicable.
     *
     * @return null if the GameState has not changed, the GameStateInfo after the effects of the turn if otherwise
     */
    public static GameStateInfo takeTurn(TurnInfo turnInfo) {
        if (turnInfo == null) { // pass
            return null;
        }
        // validate the move here, throw exception if invalid (TO-DO)
        Turn newMove = new Turn(turnInfo.id(), turnInfo.word(), turnInfo.startCoords(), turnInfo.endCoords(), WordJudge.scoreMove(turnInfo));
        GameStateController.getGameState().getTurnLog().add(newMove);
        return applyTurn(newMove);
    } //startTurn()


    private static GameStateInfo applyTurn(Turn turn) {
        board.placeWord(turn.getStartCoords(),turn.getEndCoords(), turn.getWord());
        GameStateController.players.get(turn.getPlayerId()).updateScore(turn.getScore());
        for (char c : turn.getWord().toCharArray()) {
            removeTileFromRack(currPlayer.getRack().indexOf(c));
        }

        // add new letters to rack here

        return new GameStateInfo(GameStateController.getGameState().getId(), GameStateController.gameState.getBoard(), GameStateController.gameState.getPlayers());
    }
 
    /**
     * endTurn ends the turn of the player
     */
    private void endTurn() {
        // DO STUFF
    }

    /**
     * removeTileFromRack removes the given tile from the rack
     * @param index of the tile
     */
    public static void removeTileFromRack(int index) {
        currPlayer.getRack().remove(index);
    }

    /**
     * Checks if the word is part of the scrabble dictionary
     * 
     * @param startCoords beginning coordinate of word
     * @param endCoords ending coordinate of word
     * @param word the word in question
     * @param i the index of the word that was part of a previous turn
     */
    public void challengeWord(int[] startCoords, int[] endCoords, String word, int i) {
        if (!WordJudge.verifyWord(word)) {
            board.removeWord(startCoords, endCoords, i);
        }
    }
}
