package com.game.slotgame;

import com.game.reward.Payout;

import java.util.EnumMap;
import java.util.Random;

/**
 * This is a simple slot-game with three wheels.
 *
 * Each wheel stops on one symbol out of 3 symbols (Seven, House, Cherry) on each spin. Each spin revolves all three wheels
 * and program counts the occurrence of each symbol on all of three wheels. for example - if all three wheels stops at
 * same symbol/figure its a win and payout would be a Bonus round.
 *
 */
public class SlotGame {

    /**
     * Enum for possible symbols in slot-game
     */
    public enum Symbol {
        SEVEN, HOUSE, CHERRY
    }

    private final Random random = new Random();
    private final Symbol[] wheels = new Symbol[3];

    private final static EnumMap<Symbol, Integer> ZERO_COUNT;

    static {
        ZERO_COUNT = new EnumMap<>(Symbol.class);
        for (int s = 0; s < Symbol.values().length; s++) {
            ZERO_COUNT.put(Symbol.values()[s], 0);
        }
    }

    /**
     * Spins all wheels and returns the payout according to the symbols.
     * Wheels are fixed to 3.
     * This method generates a random symbol out of enum Symbol on each wheel.
     * So output will consist of 3 symbols on three wheels on random basis.
     *
     * Method also counts the number of same symbols and accordingly payout can be calculated.
     *
     */
    public final Payout spin() {
        EnumMap<Symbol, Integer> symCounts = new EnumMap<>(ZERO_COUNT);
        for (int i = 0; i < wheels.length; i++) {
            int randInt = random.nextInt(Symbol.values().length);
            this.wheels[i] = Symbol.values()[randInt];
            symCounts.put(this.wheels[i], 1 + symCounts.get(this.wheels[i]));
        }
        return payout(symCounts);
    }

    /**
     * This function is used to calculate the payouts on the basis of outcome of slot-game.
     *
     * On high level - This game has the following winning rules
     *
     * ----------------------------------------------------------------------------|
     * Payout Type    -  Winning combinations                                      |
     * ----------------------------------------------------------------------------|
     * Bonus round    -  '7 7 7' OR 'House House House' OR 'CHERRY CHERRY CHERRY'  |
     * Free game      -  '7 7 House' in any order OR '7 7 CHERRY' in any order     |
     * Twenty coins   -  'HOUSE HOUSE CHERRY' in any order of occurrence           |
     * Zero coins     -   In all other combinations                                |
     * ----------------------------------------------------------------------------|
     *
     * @param count
     * @return
     */
    private Payout payout(EnumMap<Symbol, Integer> count) {
        if (count.get(Symbol.SEVEN) == 3 || count.get(Symbol.HOUSE) == 3 || count.get(Symbol.CHERRY) == 3) {
            return Payout.BONUS_GAME;
        } else if (count.get(Symbol.SEVEN) == 1 && count.get(Symbol.HOUSE) == 2) {
            return Payout.FREE_GAME;
        } else if (count.get(Symbol.SEVEN) == 2 && (count.get(Symbol.HOUSE) == 1) || (count.get(Symbol.CHERRY) == 1)) {
            return Payout.TWENTY_COINS;
        } else if (count.get(Symbol.HOUSE) == 2 && count.get(Symbol.CHERRY) == 1) {
            return Payout.TWENTY_COINS;
        } else {
            return Payout.ZERO;
        }
    }

    /**
     * Returns a space-separated string of the symbols on the wheels.
     */
    public String toString() {
        return this.wheels[0] + " " + this.wheels[1] + " " + this.wheels[2];
    }
}