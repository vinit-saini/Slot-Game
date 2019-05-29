package com.game.wallet;

import org.apache.log4j.Logger;

/**
 * Simple wallet implementation.
 * It tracks the amount of player's coins earning and spending during the game.
 *
 * Whatever amount of coins player earns will be credited to this wallet.
 * Also, whenever player places a bet in slot-game 10 coins gets debited from
 * this wallet as game charges.
 *
 * However, Wallet is initialized with the 10 coins to allow player to start the game.
 *
 */
public class Wallet {

    final static Logger logger = Logger.getLogger(Wallet.class);
    private int total_bal;

    /**
     * Constructor initialized with 10 coins every-time.
     */
    public Wallet() {
        total_bal = 10;
    }

    private int withdrawCoins() {
        int costToPlay = 0;

        if(total_bal >= 10) {
            total_bal -= 10;
            costToPlay = 10;
        }
        return costToPlay;
    }

    /**
     * Method to add the earned coins into wallet.
     * @param coins
     */
    public void addCoins(int coins) {
        total_bal += coins;
    }

    /**
     * Method used to place the bet, each bet costs 10 coins deduction from wallet.
     * @param freeGameHit
     * @return
     */
    public boolean placeBet(boolean freeGameHit) {
        if (freeGameHit) {
            return true;
        }
        return withdrawCoins() == 10;
    }

    /**
     * Prints the available coins in wallet.
     */
    public void printBalance() {
        logger.info("Wallet balance : " + total_bal);
    }

    /**
     * returns total available balance
     * @return
     */
    public int getBalance() {
        return total_bal;
    }
}
