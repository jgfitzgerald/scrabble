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
        // console.log(response);
        if (response.body) {
          let data = JSON.parse(response.body);
          // console.log('DATA:::');
          // console.log(data);
          setState(data);
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
  const [placedThisTurn, updatePlaced] = useState([]);


  const word = "face";

  const [turnState, setTurnState] = useState({
    id: name,
    word: word,
    row: 0,
    column: 0,
    isHorizontal: false
  });

  const [state, setState] = useState({
    id: '49851',
    playerMap: {
      [name]: {
        id: name,
        rack: ['a', 'b', 'c', 'd', 'e', 'f', 'g'],
        totalScore: 0
      }
    },
    board: null,
  });
  console.log(state);

  const startGame = () => {
    axios.post('/start', {
    }).then((response) => {
      // console.log('start response:::');
      // console.log(response);
    }).catch((error) => {
      console.log(error);
    });
  }
  const getState = () => {
    axios.get('/gamestate', {
    }).then((response) => {
      // console.log(response);
      if (response.status === axios.HttpStatusCode.Ok) {
        let data = response.data;
        // console.log('DATA:::');
        // console.log(data);
        setState(data);
        // console.log('PLAYERS:::');
        // console.log(data.players);
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

  function placeTile(e) {
    let stateCopy = {...state};
    // keep track of where tiles are being placed in a turn
    let newPlaced = [...placedThisTurn]
    newPlaced.push(e.dropData.name);
    updatePlaced(newPlaced);
    console.log(placedThisTurn);
    // place tile on board
    let coords = e.dropData.name.split('/');
    stateCopy.board.board[parseInt(coords[0])][parseInt(coords[1])].letter = e.dragData.letter;
    // remove tile from rack
    stateCopy.playerMap[name].rack.splice(stateCopy.playerMap[name].rack.indexOf(e.dragData.letter), 1);
    // save new state
    setState(stateCopy);
    console.log('stateCopy: ', stateCopy);
  }

  // this needs to have the char as well
  function returnToRack(e, coords) {
    console.log(e);
    let stateCopy = {...state};
    // update tiles placed this turn
    console.log('placedThisTurn: ', placedThisTurn);
    let newPlaced = [...placedThisTurn]
    console.log('name ', coords);
    newPlaced.slice(newPlaced.indexOf(coords), 1);
    console.log(newPlaced);
    updatePlaced(newPlaced);
    console.log(placedThisTurn);
    // remove tile from board
    coords = coords.split('/');
    stateCopy.board.board[parseInt(coords[0])][parseInt(coords[1])].letter = state.board.default;
    // add tile to rack
    stateCopy.playerMap[name].rack.push(e.dropData.letter); // THIS is what's throuwing the error, there is no drop data
    // save new state
    setState(stateCopy);
    console.log('stateCopy: ', stateCopy);
  }

  function reorder(list, from, to) {
    let newList = list.slice(0);
    newList.splice(from, 1)
    newList.splice(to, 0, list[from]);
    return newList;
  }

  // const onDragEnd = result => {
  //   if (!result.destination) return;

  //   if (result.destination.droppableId === 'tile-rack'){
  //     //update the rack order
  //     let player = playerData;
  //     player.rack = reorder(player.rack, result.source.index, result.destination.index);
  //     updatePlayerData(player);
  //   } else if (result.destination.droppableId === 'board-square') {
  //     // set this tile in the board
  //   }
  // }

  return <div className="gamePage">
    <div className='texture'></div>
    <div className="header">
      <h1>Lobby {state.id.split('-')[0]}</h1>
    </div>
    {/* <DragDropContext onDragEnd={onDragEnd}> */}
      <div className='game'>
        <Board data={state.board} thisTurn={placedThisTurn} tileClick={returnToRack} />
        <div className="players">
          {Object.entries(state.playerMap).map( ([key, value]) =>
            <div className={"namePlate playing"}>
              <p>{key}</p>
              <p>({value.totalScore} points)</p>
            </div>
          )}
        </div>
        {/* <Droppable droppableId="tile-rack" type="tile" isDropDisabled={false}> */}
          <div className="tileRack" style={{gridTemplateColumns: `repeat(${state.playerMap[name].rack.length}, 1fr)`}}>
            {state.playerMap[name].rack.map( (char) =>
              <Tile char={char} drag={true} onDrop={placeTile}/>
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