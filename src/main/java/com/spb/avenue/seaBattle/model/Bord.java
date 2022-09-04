package com.spb.avenue.seaBattle.model;

import com.spb.avenue.seaBattle.process.GameResult;
import com.spb.avenue.seaBattle.process.Player;
import com.spb.avenue.seaBattle.process.ShortResult;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Bord {
    public static final String PREFIX_OF_VIEW_CANVAS = " ABCDEFGHI";
    public static final String VALIDATE_DIGITAL_CHARACTER = "123456789";
    public static final Map<Character, Integer> CONVERT_CHAR_TO_NUM = Map.of(
            'A', 0,
            'B', 1,
            'C', 2,
            'D', 3,
            'E', 4,
            'F', 5,
            'G', 6,
            'H', 7,
            'I', 8);
    private static final Scanner SCANNER = new Scanner(System.in);
    @Getter
    private static final int SIZE = 9;
    @Getter
    @Setter
    private char[][] cells;

    public Bord() {
        cells = new char[SIZE][SIZE];
        for (char[] row : cells) {
            Arrays.fill(row, Figure.EMPTY.getView());
        }
    }

    public void drawBoard(boolean isOwn) {
        log.info(PREFIX_OF_VIEW_CANVAS);
        for (int i = 0; i < cells.length; i++) {
            StringBuilder viewBoard = new StringBuilder();
            viewBoard.append(i + 1);
            for (int j = 0; j < cells.length; j++) {
                if (isOwn) {
                    viewBoard.append(cells[i][j]);
                } else if (cells[i][j] == Figure.ALIVE.getView()){
                    viewBoard.append(Figure.EMPTY.getView());
                } else {
                    viewBoard.append(cells[i][j]);
                }
            }
            log.info(viewBoard.toString());
        }
    }

    /**
     * This method requests the coordinates of the ships from the current player and places them on the playing field
     * @param player - is a value of current player
     */
    public static void fillBoardByShips(Player player) {
        String coordinates;
        for (int i = 0; i < Player.getCOUNT_OF_SHIPS(); i++) {
            log.info("  How many decks will the ship being introduced have: ");
            int countDeck;
            do {
                try {
                    countDeck = Integer.parseInt(SCANNER.nextLine());
                } catch (NumberFormatException e) {
                    log.info("You try to input not a number! Please, try again.");
                    countDeck = 0;
                }

            } while (!isCountDeckGood(countDeck));
            log.info("  Enter the coordinates of " + countDeck + "-deck ship (for example - A1,A2...An): ");
            coordinates = SCANNER.nextLine().toUpperCase();
            String[] arrayCoordinates = coordinates.split(",");
            if (isCoordinatesValid(countDeck, arrayCoordinates) &&
                    isCoordinateOrder(arrayCoordinates) &&
                    checkAndAddShipsOnBoard(player, arrayCoordinates)) {
                player.getShips().add(new Ship(arrayCoordinates, countDeck));
                player.setCountCellsAllShips(player.getCountCellsAllShips() + arrayCoordinates.length);
                player.getBord().drawBoard(true);
            } else {
                i--;
            }
        }
    }

    private static boolean isCountDeckGood(int countDeck) {
        if (countDeck > 4 || countDeck <= 0) {
            log.info("The number of decks should not be negative and should not be more than 4.");
            return false;
        }
        return true;
    }

    private static boolean isCoordinatesValid(int lengthShip, String[] coordinates) {
        if (coordinates.length != lengthShip) {
            log.warn("You have entered {} coordinates, and {} are required! Try again.",
                    coordinates.length, lengthShip);
            return false;
        }
        for (String arrayCoordinate : coordinates) {
            char[] charCoordinate = arrayCoordinate.toCharArray();
            if (charCoordinate.length != 2) {
                if (charCoordinate.length > 2) {
                    log.warn("You have entered too many characters for the coordinate! Try again.");
                    return false;
                }
                log.warn("You have entered too less characters for the coordinate! Try again.");
                return false;
            } else {
                if (null == CONVERT_CHAR_TO_NUM.get(charCoordinate[0])) {
                    log.warn("You have entered the first character - {}. Enter only characters from ABCDEFGHI",
                            charCoordinate[0]);
                    return false;
                }
                if (VALIDATE_DIGITAL_CHARACTER.indexOf(charCoordinate[1]) == -1) {
                    log.warn("You have entered the second character - {}. Enter only characters from {}",
                            charCoordinate[1], VALIDATE_DIGITAL_CHARACTER);
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isCoordinateOrder(String[] coordinates) {
        for (int i = 0; i < coordinates.length - 1; i++) {
            if (i != coordinates.length - 1) {
                char[] beginCoordinate = coordinates[0].toCharArray();
                char[] duringCoordinate = coordinates[i].toCharArray();
                char[] nextCoordinate = coordinates[i + 1].toCharArray();
                char[] endCoordinate = coordinates[coordinates.length - 1].toCharArray();
                if (beginCoordinate[0] == endCoordinate[0]) {
                    if (!(Math.abs(nextCoordinate[1] - duringCoordinate[1]) == 1 && nextCoordinate[0] == duringCoordinate[0])) {
                        log.info("Wrong coordinates! Try again.");
                        return false;
                    }
                } else {
                    if (beginCoordinate[1] == endCoordinate[1]) {
                        if (!(Math.abs(nextCoordinate[0] - duringCoordinate[0]) == 1 && nextCoordinate[1] == duringCoordinate[1])) {
                            log.info("Wrong coordinates! Try again.");
                            return false;
                        }
                    } else {
                        log.warn("Wrong coordinates! Try again.");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean checkAndAddShipsOnBoard(Player player, String[] coordinates) {
        for (String addressCell : coordinates) {
            int x = CONVERT_CHAR_TO_NUM.get(addressCell.toCharArray()[0]);
            int y = Character.getNumericValue(addressCell.toCharArray()[1]) - 1;
            for (int i = y - 1; i < y + 2; i++) {
                for (int j = x - 1; j < x + 2; j++) {
                    if (i >= 0 && i < 9 && j >= 0 && j < 9 && player.getBord().getCells()[i][j] != Figure.EMPTY.getView()) {
                        log.warn("Wrong! The ships should not crosse and touch each other!");
                        return false;
                    }
                }
            }
        }
        addShipOnBoard(player, coordinates);
        return true;
    }

    private static void addShipOnBoard(Player player, String[] coordinates) {
        for (String addressCell : coordinates) {
            int x = CONVERT_CHAR_TO_NUM.get(addressCell.toCharArray()[0]);
            int y = Character.getNumericValue(addressCell.toCharArray()[1]) - 1;
            player.getBord().getCells()[y][x] = Figure.ALIVE.getView();
        }
    }

    public static GameResult acceptShot(Player player, String coordinateOfCell) {
        char[] getCoordinatesOfCell = coordinateOfCell.toCharArray();
        int x = CONVERT_CHAR_TO_NUM.get(getCoordinatesOfCell[0]);
        int y = Character.getNumericValue(getCoordinatesOfCell[1]) - 1;
        if (player.getBord().getCells()[y][x] == Figure.DESTROYED.getView() ||
                player.getBord().getCells()[y][x] == Figure.MISSED.getView()) {
            return new GameResult(false, ShortResult.SAME_SHOT);
        } else {
            if (player.getBord().getCells()[y][x] == Figure.ALIVE.getView()) {
                for (Ship ship : player.getShips()) {
                    String[] coordinates = ship.getCoordinates();
                    for (String coordinate : coordinates) {
                        if (coordinate.contains(coordinateOfCell)) {
                            player.getBord().getCells()[y][x] = Figure.DESTROYED.getView();
                            ship.setLength(ship.getLength() - 1);
                            player.setCountCellsAllShips(player.getCountCellsAllShips() - 1);
                            if (ship.getLength() == 0) {
                                fillNearbyCells(player, coordinates);
                                if (player.getCountCellsAllShips() == 0) {
                                    return new GameResult(true, ShortResult.KILLED);
                                } else {
                                    return new GameResult(false, ShortResult.KILLED);
                                }
                            } else {
                                return new GameResult(false, ShortResult.WOUNDED);
                            }
                        }
                    }
                }
            } else {
                player.getBord().getCells()[y][x] = Figure.MISSED.getView();
                return new GameResult(false, ShortResult.AWAY);
            }
        }
        return new GameResult(false, ShortResult.AWAY);
    }

    /**
     * This method checks coordinates of ship and saves them in the player's field, if there are no ships already installed at a distance of one cell.
     *
     * @param player - is a value of player who tries to set a ship on canvas
     * @param coordinates - is a value of new ship
     */
    public static void fillNearbyCells(Player player, String[] coordinates) {
        for (String addressCell : coordinates) {
            int x = CONVERT_CHAR_TO_NUM.get(addressCell.toCharArray()[0]);
            int y = Character.getNumericValue(addressCell.toCharArray()[1]) - 1;
            for (int i = y - 1; i < y + 2; i++) {
                for (int j = x - 1; j < x + 2; j++) {
                    if (i >= 0 && i < 9 && j >= 0 && j < 9 && player.getBord().getCells()[i][j] != Figure.DESTROYED.getView()) {
                        player.getBord().getCells()[i][j] = Figure.MISSED.getView();
                    }
                }
            }
        }
    }





}
