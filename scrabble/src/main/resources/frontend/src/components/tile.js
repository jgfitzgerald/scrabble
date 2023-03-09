import React from 'react';
import background from '../assets/wood.jpg';
import { DragDropContainer } from 'react-drag-drop-container';

const Tile = (props) => {
    const char = props.char;
    const draggable = props.drag;
    const currTurn = props.currTurn;

    const values = {
        ' ': 0,
        'a': 1,
        'b': 3,
        'c': 3,
        'd': 2,
        'e': 1,
        'f': 4,
        'g': 2,
        'h': 4,
        'i': 1,
        'j': 8,
        'k': 5,
        'l': 1,
        'm': 3,
        'n': 1,
        'o': 1,
        'p': 3,
        'q': 10,
        'r': 1,
        's': 1,
        't': 1,
        'u': 1,
        'v': 4,
        'w': 4,
        'x': 8,
        'y': 4,
        'z': 10,
        '4': '',
        '0': ''
    };

    const tyleStyle = {
        position: 'relative',
        aspectRatio: 1,
        backgroundColor: 'white',
        backgroundImage: `url(${background})`,
        backgroundSize: '100%',
        borderRadius: '5px',
        display: 'grid',
        placeItems: 'center',
        color: 'black',
    };
    const placedStyle = {
        width: '100%',
        fontSize: '60%',
        cursor: 'pointer'
    };
    const dragStyle = {
        cursor: 'pointer',
        width: '50px',
        fontSize: '1.5em',
    };
    const valueStyle = {
        position: 'absolute',
        bottom: 1,
        right: 3,
    };

    if (draggable) {
        return <DragDropContainer
            targetKey="square"
            dragData={{letter: char}}
            onDrop={props.onDrop}
            render= {() => {
                return <div style={{...tyleStyle, ...dragStyle}}>
                    <h1>{char.toUpperCase()}</h1>
                    <p style={valueStyle}>{values[char]}</p>
                </div>
            }}
        />
    } else if (currTurn) {
        return <DragDropContainer
            targetKey="square"
            dragData={{letter: char}}
            onDrop={props.onDrop}
            render= {() => {
                return <div
                    style={{...tyleStyle, ...placedStyle}}
                    onClick={props.onClick}
                >
                    <h2>{char.toUpperCase()}</h2>
                    <p style={valueStyle}>{values[char]}</p>
                </div>
            }}
        />
    } else {
        return <div style={{...tyleStyle, ...placedStyle, ...{cursor: 'default'}}}>
            <h2>{char.toUpperCase()}</h2>
            <p style={valueStyle}>{values[char]}</p>
        </div>
    }
}

export default Tile;