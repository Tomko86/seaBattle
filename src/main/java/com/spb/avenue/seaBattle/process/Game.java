package com.spb.avenue.seaBattle.process;

import com.spb.avenue.seaBattle.util.SaveLoadGame;
import com.spb.avenue.seaBattle.model.Bord;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.Scanner;

/**
 *  This class control the game. Introduce players, and then ech of them takes shots until misses or game ends.
 */
@Slf4j
public class Game {
    private static final String SAVE = "S";
    private static final Scanner SCANNER = new Scanner(System.in);
    private static int CURSOR = new Random().nextInt(2);
    private static final SaveLoadGame saveLoadGame = new SaveLoadGame();

    public static void play() {
        log.info("====== Welcome to Sea Battle! ======");
        Player onePlayer, twoPlayer, currentPlayer, opponentPlayer;
        Player[] save = saveLoadGame.loadGame();
        if (save != null) {
            onePlayer = save[0];
            twoPlayer = save[1];
            saveLoadGame.saveGame(null);
            log.info("Ok, let's continue the game...\n");
        } else {
            log.info("  Please, enter your name for player one: ");
            onePlayer = new Player(SCANNER.nextLine());
            Bord.fillBoardByShips(onePlayer);
            log.info("  Please, enter your name for player two: ");
            twoPlayer = new Player(SCANNER.nextLine());
            Bord.fillBoardByShips(twoPlayer);
            log.info("\nOk, let's start the battle!\n");
        }

        while (true) {
            if (CURSOR % 2 == 0) {
                currentPlayer = onePlayer;
                opponentPlayer = twoPlayer;
            } else {
                currentPlayer = twoPlayer;
                opponentPlayer = onePlayer;
            }
            log.info("{}, make a shot, please.", currentPlayer.getName());
            currentPlayer.getBord().drawBoard(true);
            opponentPlayer.getBord().drawBoard(false);
            log.info("If you want to save and close the game, enter -- s -- \nEnter the coordinate of one cell: ");
            String shot = SCANNER.nextLine().toUpperCase();
            if (shot.equals(SAVE)) {
                saveLoadGame.saveGame(new Player[] {onePlayer, twoPlayer});
                System.exit(0);
            }

            GameResult result = currentPlayer == onePlayer ? Bord.acceptShot(twoPlayer, shot) : Bord.acceptShot(onePlayer, shot);
            if (result.isGameEnded()) {
                log.info("The winner is {}", currentPlayer.getName());
                saveLoadGame.saveGame(null);
                break;
            } else {
                switch (result.getShotResult()) {
                    case AWAY -> {
                        log.info("AWAY!\n");
                        CURSOR++;
                    }
                    case WOUNDED -> log.info("WOUNDED!");
                    case KILLED -> log.info("KILLED!");
                    case SAME_SHOT -> log.info("THE SAME SHOT!");
                }
            }
        }
    }




}
