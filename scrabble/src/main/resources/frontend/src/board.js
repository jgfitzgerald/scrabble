import React from 'react';
import { DropTarget } from 'react-drag-drop-container';

const Board = (props) => {

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
    const normal = {
        backgroundColor: 'grey',
    };
    const center = {
        backgroundColor: '#831399',
    };
    const doubleWord = {
        backgroundColor: '#c526ab',
    };
    const doubleLetter = {
        backgroundColor: '#4bcae0',
    };
    const tripleWord = {
        backgroundColor: '#e49631',
    };
    const tripleLetter = {
        backgroundColor: '#2718af',
    };

    function onDrop(data) {
        console.log(`dropped: ${data}`);
    }

    return <div style={boardStyle}>
        {/* <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div> */}

        <DropTarget targetKey="square"><div style={{...square, ...tripleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleWord}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...tripleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...center}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleWord}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>

        <DropTarget targetKey="square"><div style={{...square, ...tripleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleWord}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...doubleLetter}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...normal}}></div></DropTarget>
        <DropTarget targetKey="square"><div style={{...square, ...tripleWord}}></div></DropTarget>
    </div>
}

export default Board;