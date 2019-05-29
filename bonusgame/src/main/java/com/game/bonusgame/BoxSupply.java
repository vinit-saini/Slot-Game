package com.game.bonusgame;

import java.util.*;

/**
 * This will supply 5 boxes containing 5 coins each except one box which contains 0 coin.
 * Box containing Zero coin will serve as a bomb and will finish the game instantly.
 */
public class BoxSupply {

    private List<Integer> list = null;
    public BoxSupply() {
        list = Arrays.asList(5,5,0,5,5);
        Collections.shuffle(list);
    }

    public List<Integer> getBoxes() {
        return list;
    }

}
