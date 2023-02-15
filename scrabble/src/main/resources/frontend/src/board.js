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
    const styles = {
        'N' : { backgroundColor: 'grey' },
        'C' : { backgroundColor: '#831399' },
        'DW': { backgroundColor: '#c526ab' },
        'DL': { backgroundColor: '#4bcae0' },
        'TW': { backgroundColor: '#e49631' },
        'TL': { backgroundColor: '#2718af' },
    }
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

    function dropped(e) {
        console.log('Dropped!!!');
    }

    const data = {
         '0/0': 'TW',  '0/1': 'N',   '0/2': 'N',   '0/3': 'DL',  '0/4': 'N',   '0/5': 'N',   '0/6': 'N',   '0/7': 'TW',  '0/8': 'N',   '0/9': 'N',   '0/10': 'N',   '0/11': 'DL',  '0/12': 'N',   '0/13': 'N',   '0/14': 'TW',
         '1/0': 'N',   '1/1': 'DW',  '1/2': 'N',   '1/3': 'N',   '1/4': 'N',   '1/5': 'TL',  '1/6': 'N',   '1/7': 'N',   '1/8': 'N',   '1/9': 'TL',  '1/10': 'N',   '1/11': 'N',   '1/12': 'N',   '1/13': 'DW',  '1/14': 'N',
         '2/0': 'N',   '2/1': 'N',   '2/2': 'DW',  '2/3': 'N',   '2/4': 'N',   '2/5': 'N',   '2/6': 'DL',  '2/7': 'N',   '2/8': 'DL',  '2/9': 'N',   '2/10': 'N',   '2/11': 'N',   '2/12': 'DW',  '2/13': 'N',   '2/14': 'N',
         '3/0': 'DL',  '3/1': 'N',   '3/2': 'N',   '3/3': 'DW',  '3/4': 'N',   '3/5': 'N',   '3/6': 'N',   '3/7': 'DL',  '3/8': 'N',   '3/9': 'N',   '3/10': 'N',   '3/11': 'DW',  '3/12': 'N',   '3/13': 'N',   '3/14': 'DL',
         '4/0': 'N',   '4/1': 'N',   '4/2': 'N',   '4/3': 'N',   '4/4': 'DW',  '4/5': 'N',   '4/6': 'N',   '4/7': 'N',   '4/8': 'N',   '4/9': 'N',   '4/10': 'DW',  '4/11': 'N',   '4/12': 'N',   '4/13': 'N',   '4/14': 'N',
         '5/0': 'N',   '5/1': 'TL',  '5/2': 'N',   '5/3': 'N',   '5/4': 'N',   '5/5': 'TL',  '5/6': 'N',   '5/7': 'N',   '5/8': 'N',   '5/9': 'TL',  '5/10': 'N',   '5/11': 'N',   '5/12': 'N',   '5/13': 'TL',  '5/14': 'N',
         '6/0': 'N',   '6/1': 'N',   '6/2': 'DL',  '6/3': 'N',   '6/4': 'N',   '6/5': 'N',   '6/6': 'DL',  '6/7': 'N',   '6/8': 'DL',  '6/9': 'N',   '6/10': 'N',   '6/11': 'N',   '6/12': 'DL',  '6/13': 'N',   '6/14': 'N',
         '7/0': 'TW',  '7/1': 'N',   '7/2': 'N',   '7/3': 'DL',  '7/4': 'N',   '7/5': 'N',   '7/6': 'N',   '7/7': 'C',   '7/8': 'N',   '7/9': 'N',   '7/10': 'N',   '7/11': 'DL',  '7/12': 'N',   '7/13': 'N',   '7/14': 'TW',
         '8/0': 'N',   '8/1': 'N',   '8/2': 'DL',  '8/3': 'N',   '8/4': 'N',   '8/5': 'N',   '8/6': 'DL',  '8/7': 'N',   '8/8': 'DL',  '8/9': 'N',   '8/10': 'N',   '8/11': 'N',   '8/12': 'DL',  '8/13': 'N',   '8/14': 'N',
         '9/0': 'N',   '9/1': 'TL',  '9/2': 'N',   '9/3': 'N',   '9/4': 'N',   '9/5': 'TL',  '9/6': 'N',   '9/7': 'N',   '9/8': 'N',   '9/9': 'TL',  '9/10': 'N',   '9/11': 'N',   '9/12': 'N',   '9/13': 'TL',  '9/14': 'N',
        '10/0': 'N',  '10/1': 'N',  '10/2': 'N',  '10/3': 'N',  '10/4': 'DW', '10/5': 'N',  '10/6': 'N',  '10/7': 'N',  '10/8': 'N',  '10/9': 'N',  '10/10': 'DW', '10/11': 'N',  '10/12': 'N',  '10/13': 'N',  '10/14': 'N',
        '11/0': 'DL', '11/1': 'N',  '11/2': 'N',  '11/3': 'DW', '11/4': 'N',  '11/5': 'N',  '11/6': 'N',  '11/7': 'DL', '11/8': 'N',  '11/9': 'N',  '11/10': 'N',  '11/11': 'DW', '11/12': 'N',  '11/13': 'N',  '11/14': 'DL',
        '12/0': 'N',  '12/1': 'N',  '12/2': 'DW', '12/3': 'N',  '12/4': 'N',  '12/5': 'N',  '12/6': 'DL', '12/7': 'N',  '12/8': 'DL', '12/9': 'N',  '12/10': 'N',  '12/11': 'N',  '12/12': 'DW', '12/13': 'N',  '12/14': 'N',
        '13/0': 'N',  '13/1': 'DW', '13/2': 'N',  '13/3': 'N',  '13/4': 'N',  '11/5': 'TL', '13/6': 'N',  '13/7': 'N',  '13/8': 'N',  '11/9': 'TL', '13/10': 'N',  '13/11': 'N',  '11/12': 'N',  '13/13': 'DW', '13/14': 'N',
        '14/0': 'TW', '14/1': 'N',  '14/2': 'N',  '14/3': 'DL', '14/4': 'N',  '14/5': 'N',  '14/6': 'N',  '14/7': 'TW', '14/8': 'N',  '14/9': 'N',  '14/10': 'N',  '14/11': 'DL', '14/12': 'N',  '14/13': 'N',  '14/14': 'TW',
    };

    return <div style={boardStyle}>

        {Object.entries(data).map((val) =>
            <DropTarget
                targetKey="square"
                onHit={dropped}
                dropData={{name: `(${val[0].split('/')[0]},${val[0].split('/')[1]})`}}
            >
                <div style={{...square, ...styles[val[1]]}}>
                    <p>{val[0].split('/')[0]} {val[0].split('/')[1]}</p>
                </div>
            </DropTarget>
        )}

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

        {/* <DropTarget targetKey="square"><div style={{...square, ...tripleWord}}></div></DropTarget>
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
        <DropTarget targetKey="square"><div style={{...square, ...tripleWord}}></div></DropTarget> */}
    </div>
}

export default Board;