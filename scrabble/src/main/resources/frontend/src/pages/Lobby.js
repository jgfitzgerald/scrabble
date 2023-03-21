import './Lobby.css';
import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import Button from '@mui/material/Button';
import axios from 'axios';

// Probably want this as an overlay for Game.js
// - it automatically get's gamestate updates so we know when to hide it
// - ... that's about it honestly

const Lobby = (props) => {

    const { state } = useLocation();
    const [gameState, setGameState] = useState(state);

    const voteToStart = () => {
        axios.patch('/vote', {
          gameId: gameState.id,
          playerId: sessionStorage.getItem('name')
        }).then((response) => {
          console.log('VOTE RESPONSE:::');
          console.log(response);
        }).catch((error) => {
          console.log(error);
        });
    }

    return <div className='lobbyPage'>
        <div className='lobbyPlayerRow'>
            <div className='lobbyCard'>
                <h3>Player Name</h3>
            </div>
        </div>
        <Button
          variant='contained'
          color='primary'
          onClick={() => voteToStart()}>
            Vote to Start
        </Button>
    </div>
}

export default Lobby;