import "./Home.css";
import Button from '@mui/material/Button';
import Board from '../board.js';
import Tile from '../tile.js';
import {useState} from 'react';
import {Link, useLocation} from 'react-router-dom';

const Home = (props) => {

  const location = useLocation();
  const {name} = location.state;

  const [numTiles, updateNumTiles] = useState(7);

  return <div className="homePage">
    <div className='texture' />
    <div className="header">
      <h1>Scrabble</h1>
    </div>
    <div className="game">
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
    <div className="homeMenu">
        <Button className='homeMenuBtn' variant='contained'>Play</Button>
        <Button className='homeMenuBtn' variant='contained'>Rules</Button>
        {/* <Button className='homeMenuBtn' variant='contained'> Account </Button> */}
    </div>
  </div>
}

export default Home;