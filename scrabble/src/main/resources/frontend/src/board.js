import React from 'react';
import { useState, useRef, useEffect } from 'react';
import Tile from './tile.js';
import { DropTarget } from 'react-drag-drop-container';

const Board = ({data, thisTurn, tileClick}) => {

    console.log('BOARD RENDERED');
    console.log(thisTurn);

    // Board stylings
    const boardStyle = {
        display: 'grid',
        gridTemplateColumns: 'repeat(15, 1fr)',
        gridTemplateRows: 'repeat(15, 1fr)',
        gap: '2px',
        width: '100%',
        height: '100%',
        borderRadius: '5px',
        backgroundColor: '#bfbc95',
        padding: '2px',
    };
    const square = {
        width: '100%',
        height: '100%',
        borderRadius: '5px',
    };
    const styles = {
        'N' : { backgroundColor: 'grey' },
        'C' : { backgroundColor: '#831399' },
        'DW': { backgroundColor: '#c526ab' },
        'DL': { backgroundColor: '#4bcae0' },
        'TW': { backgroundColor: '#e49631' },
        'TL': { backgroundColor: '#2718af' },
    }

    // Default scrabble board
    const squares = [
        ['TW', 'N' , 'N' , 'DL', 'N' , 'N',  'N',  'TW', 'N',  'N',  'N',  'DL', 'N' , 'N',  'TW'],
        ['N' , 'DW', 'N' , 'N' , 'N' , 'TL', 'N',  'N',  'N',  'TL', 'N',  'N',  'N' , 'DW', 'N'],
        ['N' , 'N' , 'DW', 'N' , 'N' , 'N',  'DL', 'N',  'DL', 'N',  'N',  'N',  'DW', 'N',  'N'],
        ['DL', 'N' , 'N' , 'DW', 'N' , 'N',  'N',  'DL', 'N',  'N',  'N',  'DW', 'N',  'N',  'DL'],
        ['N' , 'N' , 'N' , 'N' , 'DW', 'N',  'N',  'N',  'N',  'N',  'DW', 'N',  'N',  'N',  'N'],
        ['N' , 'TL', 'N' , 'N' , 'N' , 'TL', 'N',  'N',  'N',  'TL', 'N' , 'N',  'N',  'TL', 'N'],
        ['N' , 'N' , 'DL', 'N' , 'N' , 'N',  'DL', 'N',  'DL', 'N',  'N' , 'N',  'DL', 'N',  'N'],
        ['TW', 'N' , 'N' , 'DL', 'N' , 'N',  'N',  'C',  'N',  'N',  'N' , 'DL', 'N',  'N',  'TW'],
        ['N' , 'N' , 'DL', 'N' , 'N' , 'N',  'DL', 'N',  'DL', 'N',  'N' , 'N',  'DL', 'N',  'N'],
        ['N' , 'TL', 'N' , 'N' , 'N' , 'TL', 'N',  'N',  'N',  'TL', 'N' , 'N',  'N',  'TL', 'N'],
        ['N' , 'N' , 'N' , 'N' , 'DW', 'N',  'N',  'N',  'N',  'N',  'DW', 'N',  'N',  'N',  'N'],
        ['DL', 'N' , 'N' , 'DW', 'N' , 'N',  'N',  'DL', 'N',  'N',  'N',  'DW', 'N',  'N',  'DL'],
        ['N' , 'N' , 'DW', 'N' , 'N' , 'N',  'DL', 'N',  'DL', 'N',  'N',  'N',  'DW', 'N',  'N'],
        ['N' , 'DW', 'N' , 'N' , 'N' , 'TL', 'N',  'N',  'N',  'TL', 'N',  'N',  'N',  'DW', 'N'],
        ['TW', 'N' , 'N' , 'DL', 'N' , 'N',  'N',  'TW', 'N',  'N',  'N',  'DL', 'N',  'N',  'TW'],
    ];

    function dropped(e) {
        console.log(e);
        console.log('Dropped!!!');
    }

    return (
        <div style={boardStyle}>
            {
                data ? 
                // show the current game board
                data.board.map((row, i) => {
                    return (
                        row.map((val, j) => {
                            // console.log(`val.letter !== data.default ? ${val.letter !== data.default}`)
                            return (
                                val.letter !== data.default ?
                                <Tile
                                    char={val.letter}
                                    drag={false}
                                    currTurn={thisTurn.includes(`${i}/${j}`)}
                                    onClick={(e) => tileClick(e, `${i}/${j}`)} //need to somehow pass the char too
                                /> :
                                <DropTarget
                                    targetKey="square"
                                    onHit={dropped}
                                    dropData={{name: `${i}/${j}`}}
                                >
                                    <div style={{...square, ...styles[squares[i][j]]}}>
                                        <p>{i} {j}</p>
                                    </div>
                                </DropTarget>
                            );
                        })
                    );
                })
                :
                // show the standard board
                squares.map((row, i) => {
                    return (
                        row.map((val, j) => {
                            // console.log(`${i} ${j}: ${val}`)
                            return (
                                <DropTarget
                                    targetKey="square"
                                    onHit={dropped}
                                    dropData={{name: `${i}/${j}`}}
                                >
                                    <div style={{...square, ...styles[val]}}>
                                        <p>{i} {j}</p>
                                    </div>
                                </DropTarget>
                            );
                        })
                    );
                })
            }
        </div>
    );

}

export default Board;