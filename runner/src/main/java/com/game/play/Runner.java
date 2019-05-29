package com.game.play;

import com.game.bonusgame.BonusGame;
import com.game.reward.Payout;
import com.game.slotgame.SlotGame;
import com.game.wallet.Wallet;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.util.Scanner;

/**
 * This is the program to play the Slot-game. It also allows player to play the Box-picking-game as a bonus game which
 * depends on the outcome of the slot-game.
 *
 * On high level - This game has the following winning percentages
 *
 * ------------------------------------|
 * Payout Type  -  Winning chances(%)  |
 * ------------------------------------|
 * Twenty coins -  30%                 |
 * Free game    -  10%                 |
 * Bonus round  -  10%                 |
 * ------------------------------------|
 */
public class Runner {

    final static Scanner scanner = new Scanner(System.in);
    final static Wallet wallet = new Wallet();
    final static Logger logger = Logger.getLogger(Runner.class.getSimpleName());

    /**
     * This is the 'play' method (entry point) to play the game.
     *
     * Output of the program can be seen as activity logs in applog.txt file under root folder. Same logs can be seen on
     * standard console also.
     *
     * Player gets 10 coins initially to start the play. Game validates the player's wallet for the available coins as
     * every time 10 coins are needed to play the game.
     * Once the player has enough amount of coins 10 coins gets deducted from the wallet for each round of spin.
     *
     * Player can get the payout as per the outcome of wheel's spin.
     * Three types of rewards :-
     *
     * 1. Twenty coins earned
     * 2. Free round (where player gets a chance to spin the wheel for free. No deduction of 10 coins from wallet)
     * 3. Bonus round (where player plays another box-picking game to earn the coins)
     * 4. Zero coin if player doesn't get any of the above reward
     *
     * Free game doesn't need any confirmation from the user, It just runs if player hits the 'free game' reward
     * (why would anyone deny for a free spin?) However, program asks for the confirmation from player like
     * 'Do you want to play more?' before each round.
     *
     */
    public void play() {

        final String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);

        logger.info("::::::::::::::::Welcome to the slot-game::::::::::::::::");
        boolean gameLoop = true;
        boolean freeGameHit = false;
        while (gameLoop) {
            int coins_earned_from_bonus = 0;
            if (wallet.placeBet(freeGameHit)) {
                Payout payout = playSlotGame();
                freeGameHit = false;
                switch (payout) {
                    case TWENTY_COINS:
                        logger.info("Result : Congratulations! You have earned 20 coins.");
                        // add 20 coins reward to the player's wallet
                        wallet.addCoins(20);
                        gameLoop = playMore();
                        break;
                    case FREE_GAME:
                        logger.info("Result : Wow, you hit a free game! play next round for free.");
                        freeGameHit = true;
                        break;
                    case BONUS_GAME:
                        logger.info("Result : Great, You hit the bonus game! play box-picking game.");
                        coins_earned_from_bonus = playBonusGame();

                        // add coins_earned from the box-picking-game to the player's wallet
                        wallet.addCoins(coins_earned_from_bonus);
                        gameLoop = playMore();
                        break;
                    case ZERO:
                        logger.info("Result : Better luck next time!!");
                        gameLoop = playMore();
                        break;
                }
                // print the wallet balance after each round of spin
                //wallet.printBalance();
            } else {
                logger.info("Sorry! you are out of credit.");
                gameLoop = false;
            }
        }
        logger.info("************::::End Game::::******************");
    }

    /**
     * Asks for the confirmation to play more from the player
     * @return
     */
    private boolean playMore() {
        wallet.printBalance();
        System.out.println("\nDo you want to play more?  Wallet balance is : "+ wallet.getBalance()+"\n Type 'Y' for yes or any character to exit the game.");

        return scanner.next().equalsIgnoreCase("Y") ? true : false;
    }

    /**
     * Play slot-game
     * @return
     */
    private Payout playSlotGame() {
        SlotGame slotGame = new SlotGame();
        Payout prize = slotGame.spin();

        String result = slotGame.toString();
        logger.info("outcome :: " + result);

        return prize;
    }

    /**
     * Play bonus-game
     * @return
     */
    private int playBonusGame() {
        BonusGame bonusGame = new BonusGame();
        return bonusGame.playBonusGame(false);
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.play();
    }
}
