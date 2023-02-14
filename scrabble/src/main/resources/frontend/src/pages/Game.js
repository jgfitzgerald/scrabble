import './Game.css';
import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import axios from 'axios';
import Board from '../board.js';
import Tile from '../tile.js';
import Stomp, {Client} from '@stomp/stompjs';
import { DragDropContext, Droppable } from 'react-drag-drop-container';


const Game = (props) => {

  useEffect(() => {
    let onConnected = () => {
      console.log("connected");
      client.subscribe("/game/gameState", (response) => {
        console.log(response);
        if (response.body) {
          let data = JSON.parse(response.body);
          console.log('DATA:::');
          console.log(data);
          setState(data);
          console.log('PLAYERS:::');
          console.log(data.players);
          let localPlayer = data.players.find( (value, key) => {
            console.log(key);
            console.log(value);
            return value.id.includes(name);
          });
          console.log('THIS PLAYER:::');
          console.log(localPlayer);
          updatePlayerData(localPlayer);
        }
      });
    }

    let onDisconnected = () => {
      console.log("disconnected");
    }
  
    const client = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: onConnected,
      onDisconnect: onDisconnected
    });
    client.activate();
  }, []);

  const name = localStorage.getItem('name');
  const word = "face";
  
  const [numTiles, updateNumTiles] = useState(7);

  const [turnState, setTurnState] = useState({
    id: name,
    word: word,
    row: 0,
    column: 0,
    isHorizontal: false
  });

  const [playerData, updatePlayerData] = useState({
    id: name,
    rack: ['a', 'b', 'c', 'd', 'e', 'f', 'g'],
    totalScore: 0
  });
  
  const [state, setState] = useState({
    id: '49851',
    players: [playerData]
  });

  const startGame = () => {
    axios.post('/start', {
    }).then((response) => {
      console.log('start response:::');
      console.log(response);
    }).catch((error) => {
      console.log(error);
    });
  }
  const getState = () => {
    axios.get('/gamestate', {
    }).then((response) => {
      console.log(response);
      if (response.status === axios.HttpStatusCode.Ok) {
        let data = response.data;
        console.log('DATA:::');
        console.log(data);
        setState(data);
        console.log('PLAYERS:::');
        console.log(data.players);
        let localPlayer = data.players.find( (value, key) => {
          console.log(key);
          console.log(value);
          return value.id.includes(name);
        });
        console.log('THIS PLAYER:::');
        console.log(localPlayer);
        updatePlayerData(localPlayer);
      }
    }).catch((error) => {
      console.log(error);
    });
  }
  const makeMove = () => {
    console.log(turnState);
    axios.post('/move', {
      turnInfo: turnState
    }).then((response)=> {
      console.log('MOVE RESPONSE:::');
      console.log(response);
    }).catch((error) => {
      console.log(error);
    })
  }
  const passTurn = () => {
    axios.post('/pass', {
      id: name
    }).then((response)=> {
      console.log('PASS RESPONSE:::');
      console.log(response);
    }).catch((error) => {
      console.log(error);
    })
  }

  function reorder(list, from, to) {
    let newList = list.slice(0);
    newList.splice(from, 1)
    newList.splice(to, 0, list[from]);
    return newList;
  }

  const onDragEnd = result => {
    if (!result.destination) return;

    if (result.destination.droppableId === 'tile-rack'){
      //update the rack order
      let player = playerData;
      player.rack = reorder(player.rack, result.source.index, result.destination.index);
      updatePlayerData(player);
    } else if (result.destination.droppableId === 'board-square') {
      // set this tile in the board
    }
  }

  return <div className="gamePage">
    <div className='texture'></div>
    <div className="header">
      <h1>Lobby {state.id.split('-')[0]}</h1>
    </div>
    {/* <DragDropContext onDragEnd={onDragEnd}> */}
      <div className='game'>
        <Board />
        <div className="players">
          {state.players.map( (player) =>
            <div className={"namePlate playing"}>
              <p>{player.id}</p>
              <p>({player.totalScore} points)</p>
            </div>
          )}
        </div>
        {/* <Droppable droppableId="tile-rack" type="tile" isDropDisabled={false}> */}
          <div className="tileRack" style={{gridTemplateColumns: `repeat(${numTiles}, 1fr)`}}>
            {playerData.rack.map( (char) =>
              <Tile char={char} />
            )}
          </div>
        {/* </Droppable> */}
      </div>
    {/* </DragDropContext> */}
    <div className="gameMenu">
      <Button
        variant='contained'
        onClick={() => startGame()}>
        Start Game
      </Button>
      <Button
        variant='contained'
        onClick={() => getState()}>
        Game State
      </Button>
      <Button
        variant='contained'
        onClick={() => makeMove()}>
        Play Move
      </Button>
      <Button
        variant='contained'
        onClick={() => passTurn()}>
        Pass Turn
      </Button>
    </div>
  </div>
}

export default Game;