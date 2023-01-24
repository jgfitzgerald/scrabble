package com.compmta.scrabble.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum Letter {

    A('a', 1, 9),
    B('b', 3, 2),
    C('c', 3, 2),
    D('d', 2, 4),
    E('e', 1, 12),
    F('f', 4, 2),
    G('g', 2, 3),
    H('h', 4, 2),
    I('i', 1, 9),
    J('j', 8, 1),
    K('k', 5, 1),
    L('l', 1, 4),
    M('m', 3, 2),
    N('n', 1, 6),
    O('o', 1, 8),
    P('p', 3, 2),
    Q('q', 10, 1),
    R('r', 1, 6),
    S('s', 1, 4),
    T('t', 1, 6),
    U('u', 1, 4),
    V('v', 4, 2),
    W('w', 4, 2),
    X('x', 8, 1),
    Y('y', 4, 2),
    Z('z', 10, 1),
    BLANK(' ', 0, 2);

    private char letter;
    private int baseScore;
    private int initialAmount;

    public static final Map<Character, Letter> map = Collections.unmodifiableMap(initializeMapping());

    private static Map<Character, Letter> initializeMapping() {
        Map<Character, Letter> map = new HashMap<Character, Letter>();
        for (Letter l : Letter.values()) {
            map.put(l.getLetter(), l);
        }
        return map;
    }

}
