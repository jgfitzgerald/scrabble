import React from 'react';
import { Droppable } from 'react-beautiful-dnd';

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
    const normal= {
        borderRadius: '5px',
        backgroundColor: 'grey',
    };
    const center= {
        borderRadius: '5px',
        backgroundColor: '#831399',
    };
    const doubleWord = {
        borderRadius: '5px',
        backgroundColor: '#c526ab',
    };
    const doubleLetter = {
        borderRadius: '5px',
        backgroundColor: '#4bcae0',
    };
    const tripleWord = {
        borderRadius: '5px',
        backgroundColor: '#e49631',
    };
    const tripleLetter = {
        borderRadius: '5px',
        backgroundColor: '#2718af',
    };

    function onDrop(data) {
        console.log(`dropped: ${data}`);
    }

    return <div style={boardStyle}>
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
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>
        <div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div><div style={normal}></div>

        {/* <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleWord}></div></Droppable> */}

        {/* <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>

        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>

        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>

        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>

        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>

        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>

        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={center}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleWord}></div></Droppable>

        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>

        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>

        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>

        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>

        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>

        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>

        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleWord}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={doubleLetter}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={normal}></div></Droppable>
        <Droppable droppableId="board-square" type="tile" isDropDisabled={false}><div style={tripleWord}></div></Droppable> */}
    </div>
}

export default Board;