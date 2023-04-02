import './Lobby.css';
import { useState } from 'react';
import Button from '@mui/material/Button';
import axios from 'axios';


const Lobby = (props) => {

  const voteToStart = () => {
    axios.patch('/vote', {
      gameId: props.state.id || props.state.gameId,
      playerId: sessionStorage.getItem('name')
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