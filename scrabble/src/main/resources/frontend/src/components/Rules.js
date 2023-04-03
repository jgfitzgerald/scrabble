import './Rules.css';
import React, { useState } from 'react';

/**
 * The Rules function is a dropdown menu that displays the rules of Scrabble.
 *
 * @return A dropdown menu with a list of rules
 *
 * @docauthor Trelent
 */
function Rules(props) {

  const toggleDropdown = () => {
    props.toggleDropdown();
  }

  return (
    <div className="dropdown">
      <button
        className="dropdown-toggle"
        onClick={toggleDropdown}
      >
        <div classNamne='rules'>Rules</div>
        <div classNamne='drop-icon'>☰</div>
      </button>
      <div className={`dropdown-menu ${props.isOpen ? 'show' : ''}`}>
        <ol>
          <br></br>
          <li>The first player combines two or more of his or her letters to form a word and places it on the board to read either across or down with one letter on the center square. (Diagonal words are not allowed.) After playing a word, the player receives replacement letters, one for each letter played.</li>
          <br></br>
          <li>Each new word must connect to the existing words, in one of the following ways:
                        <ul>
                        <br></br>
                        <li>Adding one or more letters to a word or letters already on the board.</li>
                        <br></br>
                        <li>Placing a word at right angles to a word already on the board. The new word must use one of the letters already on the board or must add a letter to one of the words on the board.</li>
                        <br></br>
                        <li>Placing a complete word parallel to a word already played so that adjacent letters also form complete words.</li>
                        </ul>
                        </li>
            <br></br>
            <li>Each of the two blank tiles may be used as any letter. When playing a blank, you must state which letter it represents. It remains that letter for the rest of the game.</li>
            <br></br>
            <li>You may use a turn to exchange all, some, or none of the letters. To do this, place your discarded letter(s) facedown. Draw the same number of letters from the pool of remaining letters, and then mix your discarded letter(s) into the pool. This ends your turn. You may not exchange more tiles from your rack than are in the pool (or are in your rack, of course), but there is no other limit on how many tiles you may exchange.</li>
            <br></br>
            <li>The game ends when all of the letters are either in the player's racks or on the board, and one player uses his or her last letter, or when all players have decided that there are no possible moves left.</li>
        </ol>
      </div>
    </div>
  );
}

export default Rules;