import React from 'react';
import { useState, useRef, useEffect } from 'react';
import Tile from './tile.js';
import { DropTarget } from 'react-drag-drop-container';

const Board = ({data, thisTurn, tileClick}) => {

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
                data.board.map((row, r) => {
                    return (
                        row.map((val, c) => {
                            // console.log(`val.letter !== data.default ? ${val.letter !== data.default}`)
                            return (
                                val.letter !== "\u0000" ?
                                <Tile
                                    char={val.letter}
                                    drag={false}
                                    currTurn={Object.keys(thisTurn).includes(`${r}/${c}`)}
                                    onClick={(e) => tileClick(e, `${r}/${c}`)}
                                /> :
                                <DropTarget
                                    targetKey="square"
                                    onHit={dropped}
                                    dropData={{name: `${r}/${c}`}}
                                >
                                    <div style={{...square, ...styles[squares[r][c]]}}>
                                        { squares[r][c] !== 'N' ? <p>{squares[r][c]}</p> : <div /> }
                                    </div>
                                </DropTarget>
                            );
                        })
                    );
                })
                :
                // show the standard board
                squares.map((row, r) => {
                    return (
                        row.map((val, c) => {
                            return (
                                <DropTarget
                                    targetKey="square"
                                    onHit={dropped}
                                    dropData={{name: `${r}/${c}`}}
                                >
                                    <div style={{...square, ...styles[val]}}>
                                        { val !== 'N' ? <p>{val}</p> : <div /> }
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