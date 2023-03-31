import './Lobby.css';
import { useState } from 'react';
import Button from '@mui/material/Button';
import axios from 'axios';

// Probably want this as an overlay for Game.js
// - it automatically get's gamestate updates so we know when to hide it
// - ... that's about it honestly

const Lobby = (props) => {

  console.log(props.state);

  const voteToStart = () => {
    axios.patch('/vote', {
      gameId: props.state.id || props.state.gameId,
      playerId: sessionStorage.getItem('name')
    // }).then((response) => {
    //   console.log('VOTE RESPONSE:::');
    //   console.log(response);
    }).catch((error) => {
      props.popup("Something went wrong :(");
      console.log(error);
    });
  }

  return <div className='lobbyPage'>
  <div className='texture'></div>
    <div className='lobbyPlayerRow'>
      {Object.entries(props.state.playerMap).map( ([key, value]) =>
        <div className='lobbyCard'>
          <h3>{key}</h3>
          <p>{value.vote ? "ready" : "waiting"}</p>
        </div>
      )}
    </div>
    <Button
      variant='contained'
      color='primary'
      disabled={props.state.playerMap[sessionStorage.getItem('name')].vote}
      onClick={() => voteToStart()}>
        Vote to Start
    </Button>
  </div>
}

export default Lobby;