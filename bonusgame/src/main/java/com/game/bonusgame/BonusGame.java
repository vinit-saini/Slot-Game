package com.game.bonusgame;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * This is a simple Box-picking game.
 *
 * Here, players gets the 5 boxes containing 5 coins each on random basis except one box which contains the 0 or Bomb.
 * Player is allowed to choose the box by box number ranging from 1 to 5.
 *
 * If players picks up the box which contains the coins,
 * coins gets credited to the wallet whereas picking-up the box containing bomb shall instantly finish the game.
 *
 * All of the coins accumulated will be credited to the player's wallet at the end and player will be redirected to the
 * original slot-game.
 *
 * Basic validations are present like user can enter box numbers only from 1 to 5.
 * and player isn't allowed to enter the box number which is already opened.
 *
 */
public class BonusGame {

    final static Logger logger = Logger.getLogger("Runner");

    private final Scanner scanner = new Scanner(System.in);

    public int playBonusGame(boolean isSimulation) {
        logger.info("::::::playing bonus game::::::");
        BoxSupply boxSupply = new BoxSupply();
        Random random = new Random();
        int choice = 0;
        int total_coins = 0;
        int credit = 0;
        final List<Integer> bursted_boxes = new ArrayList<>();
        boolean gameLoop = true;
        while(gameLoop) {
            for (int i = 0; i < boxSupply.getBoxes().size() - bursted_boxes.size(); i++) {
                logger.info("Choose any box number between 1 to 5 OR press any char to exit the bonus round: ");
                try {
                    if (isSimulation) {
                        //TODO: simulation logic
                    } else {
                        choice = scanner.nextInt();
                    }
                } catch (InputMismatchException ime) {
                    logger.info("Exiting box-picking  game...!");
                    gameLoop = false;
                    break;
                }

                if (choice > 5 || choice < 1) {
                    logger.info("Wrong input... please enter correct box number from 1 to 5 only!");
                    break;
                }

                if (bursted_boxes.contains(choice)) {
                    logger.info("Box is already open, open different box");
                    break;
                }

                credit = boxSupply.getBoxes().get(choice - 1);

                if (credit == 0) {
                    logger.info("You clicked the bomb : game end");
                    gameLoop = false;
                    break;
                } else {
                    bursted_boxes.add(choice);
                    logger.info("You have earned " + credit + " coins. ");
                    total_coins += credit;
                }
            }
        }
        return total_coins;
    }
}
