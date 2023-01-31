import './Game.css';
import React, {useState} from 'react';
import Board from '../board.js';
import Tile from '../tile.js';

const Game = (props) => {
  
    const name = localStorage.getItem('name');
    
    const [numTiles, updateNumTiles] = useState(7);
    const lobbyNumber = 49851;

    return <div className="gamePage">
      <div className='texture'></div>
      <div className="header">
        <h1>Lobby {lobbyNumber}</h1>
      </div>
      <div className='game'>
        <Board />
        <div className="players">
          <div className="namePlate playing">
            <p>{name}</p>
            <p>(100 points)</p></div>
          <div className="namePlate">
            <p>Valeria Forbes</p>
            <p>(100 points)</p>
          </div>
          <div className="namePlate">
            <p>Baron Martinez</p>
            <p>(100 points)</p>
          </div>
          <div className="namePlate">
            <p>Micah Schroeder</p>
            <p>(100) points</p>
          </div>
        </div>
        <div className="tileRack" style={{gridTemplateColumns: `repeat(${numTiles}, 1fr)`}}>
          <Tile char='A' val='1'></Tile>
          <Tile char='B' val='3'></Tile>
          <Tile char='C' val='3'></Tile>
          <Tile char='D' val='2'></Tile>
          <Tile char='E' val='1'></Tile>
          <Tile char='F' val='4'></Tile>
          <Tile char='G' val='2'></Tile>
        </div>
      </div>
    </div>
}

export default Game;