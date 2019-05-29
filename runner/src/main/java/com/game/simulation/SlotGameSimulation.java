package com.game.simulation;

import com.game.bonusgame.BonusGame;
import com.game.wallet.Wallet;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

public class SlotGameSimulation {

    final static Logger logger = Logger.getLogger(SlotGameSimulation.class.getSimpleName());

    public static void main(String[] args) {

        final String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);

        //TODO: simulate for a million runs (not completed)

        Wallet wallet = new Wallet();
                wallet.printBalance();
        for (int i = 0; i < 100 ; i++) {
            logger.info("/n/n*************** round:" + i);
            BonusGame bonusGame = new BonusGame();
            wallet.addCoins(bonusGame.playBonusGame(true));
        }
        wallet.printBalance();
    }
}
