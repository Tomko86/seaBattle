import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.Scanner;

@Slf4j
public class Game {

    private static final int LENGTH_COORDINATE_SHOT = 1;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static int CURSOR = new Random().nextInt(2);
    private static final SaveLoadGame saveLoadGame = new SaveLoadGame();

    public static void play() {
        log.info("====== Welcome to Sea Battle! ======");
        Player onePlayer, twoPlayer, currentPlayer;
        if (saveLoadGame.loadGame() != null) {
            onePlayer = saveLoadGame.loadGame()[0];
            twoPlayer = saveLoadGame.loadGame()[1];
            saveLoadGame.saveGame(null);
            log.info("Ok, let's continue the game...\n");
        } else {
            log.info("  Please, enter your name for player one: ");
            onePlayer = new Player(SCANNER.nextLine());
            fillCanvasByShips(onePlayer);
            log.info("  Enter your name for player two: ");
            twoPlayer = new Player(SCANNER.nextLine());
            fillCanvasByShips(twoPlayer);
            log.info("\nOk, let's start the battle!\n");
        }

        while (true) {
            currentPlayer = CURSOR % 2 == 0 ? onePlayer : twoPlayer;
            log.info("{}, make a shot, please.", currentPlayer.getName());
            currentPlayer.getCellsOfOwnFigures().drawCanvas();
            currentPlayer.getCellsOfOpponentFigures().drawCanvas();
            log.info("If you want to save and close the game, enter -- s -- \nEnter the coordinate of one cell: ");
            String shot = SCANNER.nextLine().toUpperCase();
            if (shot.equals("S")) {
                saveLoadGame.saveGame(new Player[] {onePlayer, twoPlayer});
                System.exit(0);
            }
            char[] getCoordinatesOfCell = shot.toCharArray();
            int x = Canvas.getVALIDATE_LITERAL_CHARACTER().indexOf(getCoordinatesOfCell[0]);
            int y = Character.getNumericValue(getCoordinatesOfCell[1]) - 1;
            if (isCoordinatesValid(LENGTH_COORDINATE_SHOT, shot)) {
                GameResult result = currentPlayer == onePlayer ? twoPlayer.makeShot(x, y, shot) : onePlayer.makeShot(x, y, shot);
                if (result.isGameEnded()) {
                    log.info("The winner is {}", currentPlayer.getName());
                    saveLoadGame.saveGame(null);
                    break;
                } else {
                    if (result.getShotResult().equals(Condition.AWAY)) {
                        log.info("AWAY!\n");
                        currentPlayer.getCellsOfOpponentFigures().getCells()[y][x] = Figure.AWAY.getView();
                        CURSOR++;
                    }
                    if (result.getShotResult().equals(Condition.WOUNDED)) {
                        log.info("WOUNDED!");
                        currentPlayer.getCellsOfOpponentFigures().getCells()[y][x] = Figure.DESTROY.getView();
                    }
                    if (result.getShotResult().equals(Condition.KILLED)) {
                        log.info("KILLED!");
                        currentPlayer.getCellsOfOpponentFigures().getCells()[y][x] = Figure.DESTROY.getView();
                        fillNearbyCells(currentPlayer, result.getCoordinate());
                    }
                    if (result.getShotResult().equals(Condition.SAME_SHOT)) {
                        log.info("THE SAME SHOT!");
                    }
                }
            }
        }
    }

    /**
     * This method checks coordinates of ship and saves them in the player's field, if there are no ships already installed at a distance of one cell.
     *
     * @param player - is a value of player who tries to set a ship on canvas
     * @param coordinate - is a value of new ship
     */
    private static void fillNearbyCells(Player player, String coordinate) {
        String[] arrayCoordinate = coordinate.split(",");
        for (String addressCell : arrayCoordinate) {
            int x = Canvas.getPREFIX_OF_VIEW_CANVAS().indexOf(addressCell.toCharArray()[0]) - 1;
            int y = Character.getNumericValue(addressCell.toCharArray()[1]) - 1;
            if (y == 0 && x == 0) {
                if (player.getCellsOfOpponentFigures().getCells()[y][x + 1] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y][x + 1] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x + 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y + 1][x] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x] = Figure.AWAY.getView();
                }
            }
            if (y == 0 && x != 8 && x != 0) {
                if (player.getCellsOfOpponentFigures().getCells()[y][x + 1] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y][x + 1] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x + 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y + 1][x] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x - 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y][x - 1] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y][x - 1] = Figure.AWAY.getView();
                }
            }
            if (y == 0 && x == 8) {
                if (player.getCellsOfOpponentFigures().getCells()[y + 1][x] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x - 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y][x - 1] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y][x - 1] = Figure.AWAY.getView();
                }
            }
            if (x == 0 && y != 8 && y != 0) {
                if (player.getCellsOfOpponentFigures().getCells()[y + 1][x] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x + 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y][x + 1] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y][x + 1] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x + 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y - 1][x] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x] = Figure.AWAY.getView();
                }
            }
            if (y != 0 && x != 0 && x != 8 && y != 8) {
                if (player.getCellsOfOpponentFigures().getCells()[y + 1][x] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x + 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y][x + 1] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y][x + 1] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x + 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y - 1][x] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x - 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y][x - 1] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y][x - 1] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x - 1] = Figure.AWAY.getView();
                }
            }
            if (x == 8 && y != 0 && y != 8) {
                if (player.getCellsOfOpponentFigures().getCells()[y - 1][x] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x - 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y][x - 1] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y][x - 1] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x - 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y + 1][x] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y + 1][x] = Figure.AWAY.getView();
                }
            }
            if (y == 8 && x == 0) {
                if (player.getCellsOfOpponentFigures().getCells()[y][x + 1] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y][x + 1] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x + 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y - 1][x] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x] = Figure.AWAY.getView();
                }
            }
            if (y == 8 && x != 0 && x != 8) {
                if (player.getCellsOfOpponentFigures().getCells()[y][x + 1] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y][x + 1] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x + 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y - 1][x] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x - 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y][x - 1] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y][x - 1] = Figure.AWAY.getView();
                }
            }
            if (y == 8 && x == 8) {
                if (player.getCellsOfOpponentFigures().getCells()[y - 1][x] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x] = Figure.AWAY.getView();
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x - 1] = Figure.AWAY.getView();
                }
                if (player.getCellsOfOpponentFigures().getCells()[y - 1][x] != Figure.DESTROY.getView()) {
                    player.getCellsOfOpponentFigures().getCells()[y - 1][x] = Figure.AWAY.getView();
                }
            }
        }

    }

    private static void fillCanvasByShips(Player player) {
        String coordinates;
        for (int i = 0; i < Player.getCOUNT_OF_SHIPS(); i++) {
            if (i == 0) {
                log.info("  Enter the coordinates of four-deck ship (for example - A1,A2,A3,A4): ");
                coordinates = SCANNER.nextLine().toUpperCase();
                if (isCoordinatesValid(Ship.getFOUR_DECK(), coordinates)) {
                    if (isCoordinateOrder(coordinates)) {
                        player.getShips().add(new Ship(coordinates, Ship.getFOUR_DECK()));
                        if (player.addShipsOnCanvas(coordinates)) {
                            player.getCellsOfOwnFigures().drawCanvas();
                        } else {
                            i--;
                        }
                    } else {
                        i--;
                    }
                } else {
                    i--;
                }

            }
            if (i > 0 && i < 3) {
                log.info("  Enter the coordinates of three-deck ship (for example - C1,C2,C3): ");
                coordinates = SCANNER.nextLine().toUpperCase();
                if (isCoordinatesValid(Ship.getTHREE_DECK(), coordinates)) {
                    if (isCoordinateOrder(coordinates)) {
                        player.getShips().add(new Ship(coordinates, Ship.getTHREE_DECK()));
                        if (player.addShipsOnCanvas(coordinates)) {
                            player.getCellsOfOwnFigures().drawCanvas();
                        } else {
                            i--;
                        }
                    } else {
                        i--;
                    }
                } else {
                    i--;
                }
            }
            if (i > 2 && i < 6) {
                log.info("  Enter the coordinates of two-deck ship (for example - E1,E2): ");
                coordinates = SCANNER.nextLine().toUpperCase();
                if (isCoordinatesValid(Ship.getTWO_DECK(), coordinates)) {
                    if (isCoordinateOrder(coordinates)) {
                        player.getShips().add(new Ship(coordinates, Ship.getTWO_DECK()));
                        if (player.addShipsOnCanvas(coordinates)) {
                            player.getCellsOfOwnFigures().drawCanvas();
                        } else {
                            i--;
                        }
                    } else {
                        i--;
                    }
                } else {
                    i--;
                }
            }
            if (i > 5) {
                log.info("  Enter the coordinates of one-deck ship (for example - F1): ");
                coordinates = SCANNER.nextLine().toUpperCase();
                if (isCoordinatesValid(Ship.getONE_DECK(), coordinates)) {
                    player.getShips().add(new Ship(coordinates, Ship.getONE_DECK()));
                    if (player.addShipsOnCanvas(coordinates)) {
                        player.getCellsOfOwnFigures().drawCanvas();
                    } else {
                        i--;
                    }
                } else {
                    i--;
                }
            }
        }
    }


    private static boolean isCoordinateOrder(String coordinates) {
        String[] arrayCoordinates = coordinates.split(",");
        for (int i = 0; i < arrayCoordinates.length; i++) {
            if (i != arrayCoordinates.length - 1) {
                char[] beginCoordinate = arrayCoordinates[0].toCharArray();
                char[] duringCoordinate = arrayCoordinates[i].toCharArray();
                char[] nextCoordinate = arrayCoordinates[i + 1].toCharArray();
                char[] endCoordinate = arrayCoordinates[arrayCoordinates.length - 1].toCharArray();
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

    private static boolean isCoordinatesValid(int lengthShip, String coordinates) {
        String[] arrayCoordinates = coordinates.split(",");
        if (arrayCoordinates.length != lengthShip) {
            log.warn("You have entered {} coordinates, and {} are required! Try again.",
                    arrayCoordinates.length, lengthShip);
            return false;
        }
        for (String arrayCoordinate : arrayCoordinates) {
            char[] charCoordinate = arrayCoordinate.toCharArray();
            if (charCoordinate.length != 2) {
                if (charCoordinate.length > 2) {
                    log.warn("You have entered too many characters for the coordinate! Try again.");
                    return false;
                }
                log.warn("You have entered too less characters for the coordinate! Try again.");
                return false;
            } else {
                if (Canvas.getVALIDATE_LITERAL_CHARACTER().indexOf(charCoordinate[0]) == -1) {
                    log.warn("You have entered the first character - {}. Enter only characters from {}",
                            charCoordinate[0], Canvas.getVALIDATE_LITERAL_CHARACTER());
                    return false;
                }
                if (Canvas.getVALIDATE_DIGITAL_CHARACTER().indexOf(charCoordinate[1]) == -1) {
                    log.warn("You have entered the second character - {}. Enter only characters from {}",
                            charCoordinate[1], Canvas.getVALIDATE_DIGITAL_CHARACTER());
                    return false;
                }
            }
        }
        return true;
    }


}
