package com.avenue.java.spb.seaBattle;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.Scanner;

@Slf4j
public class Game {

    private static final Scanner scanner = new Scanner(System.in);

    private static int CURSOR = new Random().nextInt(2);
    private static final int COUNT_OF_SHIPS = 1;

    public static void main(String[] args) {
        log.info("====== Welcome to Sea Battle! ======");
        log.info("  Please, enter your name for player one: ");
        Player onePlayer = new Player(scanner.nextLine());
        fillCanvasByShips(onePlayer);
        log.info("  Enter your name for player two: ");
        Player twoPlayer = new Player(scanner.nextLine());
        fillCanvasByShips(twoPlayer);
        log.info("Ok, let's start the battle!\n");

        while (true) {
            Player currentPlayer = CURSOR % 2 == 0 ? onePlayer : twoPlayer;
            log.info("{}, make a shot, please.", currentPlayer.getName());
            currentPlayer.getCanvasOfOwnFigures().drawCanvas();
            currentPlayer.getCanvasOfOpponentFigures().drawCanvas();
            log.info("Enter the coordinate of one cell: ");
            String shot = scanner.nextLine();
            char[] getCoordinatesOfCell = shot.toCharArray();
            int x = Canvas.getPREFIX_OF_VIEW_CANVAS().indexOf(getCoordinatesOfCell[0]) - 1;
            int y = Character.getNumericValue(getCoordinatesOfCell[1]) - 1;
            GameResult result = currentPlayer == onePlayer ? twoPlayer.makeShot(shot) : onePlayer.makeShot(shot);
            if (result.isGameEnded()) {
                log.info("The winner is {}", currentPlayer.getName());
                break;
            } else {
                if (result.getShotResult().equals("AWAY!")) {
                    log.info("AWAY!\n");
                    currentPlayer.getCanvasOfOpponentFigures().getCanvasOfFigures()[y][x] = Figure.AWAY.getView();

                    CURSOR++;
                }
                if (result.getShotResult().equals("WOUNDED!")) {
                    log.info("WOUNDED!");
                    currentPlayer.getCanvasOfOpponentFigures().getCanvasOfFigures()[y][x] = Figure.DESTROY.getView();
                }
                if (result.getShotResult().equals("KILLED!")) {
                    log.info("KILLED!");
                    currentPlayer.getCanvasOfOpponentFigures().getCanvasOfFigures()[y][x] = Figure.DESTROY.getView();
                }
                if (result.getShotResult().equals("THE SAME SHOT!")) {
                    log.info("THE SAME SHOT!");
                }
            }
        }
    }

    private static void fillCanvasByShips(Player player) {
        String coordinates;
        for (int i = 0; i < COUNT_OF_SHIPS; i++) {
            if (i == 0) {
                log.info("  Enter the coordinates of four-deck ship (for example - A1,A2,A3,A4): ");
                 coordinates = scanner.nextLine();
                 player.getOwnShips()[i] = new Ship("BATTLESHIP", coordinates, 4);
                 player.addShipsOnCanvas();
                 player.getCanvasOfOwnFigures().drawCanvas();
            }
            /*if (i > 0 && i < 3) {
                log.info("  Enter the coordinates of three-deck ship (for example - C1,C2,C3): ");
                coordinates = scanner.nextLine();
                player.getOwnShips()[i] = new Ship("CRUISER", coordinates, 3);
                gameCanvas.addShipsOnCanvas(player);
                gameCanvas.drawCanvas();
            }
            if (i > 2 && i < 6) {
                log.info("  Enter the coordinates of two-deck ship (for example - E1,E2): ");
                coordinates = scanner.nextLine();
                player.getOwnShips()[i] = new Ship("DESTROYER", coordinates, 2);
                gameCanvas.addShipsOnCanvas(player);
                gameCanvas.drawCanvas();
            }
            if (i > 5) {
                log.info("  Enter the coordinates of one-deck ship (for example - F1): ");
                coordinates = scanner.nextLine();
                player.getOwnShips()[i] = new Ship("TORPEDO BOAT", coordinates, 1);
                gameCanvas.addShipsOnCanvas(player);
                gameCanvas.drawCanvas();
            }*/
        }
    }


}
