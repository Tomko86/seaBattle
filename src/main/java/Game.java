import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.Scanner;

@Slf4j
public class Game {

    private static final int LENGTH_COORDINATE_SHOT = 1;
    private static final Scanner SCANNER = new Scanner(System.in);

    private static int CURSOR = new Random().nextInt(2);

    public static void main(String[] args) {
        log.info("====== Welcome to Sea Battle! ======");
        log.info("  Please, enter your name for player one: ");
        Player onePlayer = new Player(SCANNER.nextLine());
        fillCanvasByShips(onePlayer);
        log.info("  Enter your name for player two: ");
        Player twoPlayer = new Player(SCANNER.nextLine());
        fillCanvasByShips(twoPlayer);
        log.info("\nOk, let's start the battle!\n");

        while (true) {
            Player currentPlayer = CURSOR % 2 == 0 ? onePlayer : twoPlayer;
            log.info("{}, make a shot, please.", currentPlayer.getName());
            currentPlayer.getCellsOfOwnFigures().drawCanvas();
            currentPlayer.getCellsOfOpponentFigures().drawCanvas();
            log.info("Enter the coordinate of one cell: ");
            String shot = SCANNER.nextLine().toUpperCase();
            char[] getCoordinatesOfCell = shot.toCharArray();
            int x = Canvas.getVALIDATE_LITERAL_CHARACTER().indexOf(getCoordinatesOfCell[0]);
            int y = Character.getNumericValue(getCoordinatesOfCell[1]) - 1;
            if (isCoordinatesValid(LENGTH_COORDINATE_SHOT, shot)) {
                GameResult result = currentPlayer == onePlayer ? twoPlayer.makeShot(x, y, shot) : onePlayer.makeShot(x, y, shot);
                if (result.isGameEnded()) {
                    log.info("The winner is {}", currentPlayer.getName());
                    break;
                } else {
                    if (result.getShotResult().equals("AWAY!")) {
                        log.info("AWAY!\n");
                        currentPlayer.getCellsOfOpponentFigures().getCells()[y][x] = Figure.AWAY.getView();
                        CURSOR++;
                    }
                    if (result.getShotResult().equals("WOUNDED!")) {
                        log.info("WOUNDED!");
                        currentPlayer.getCellsOfOpponentFigures().getCells()[y][x] = Figure.DESTROY.getView();
                    }
                    if (result.getShotResult().equals("KILLED!")) {
                        log.info("KILLED!");
                        currentPlayer.getCellsOfOpponentFigures().getCells()[y][x] = Figure.DESTROY.getView();
                    }
                    if (result.getShotResult().equals("THE SAME SHOT!")) {
                        log.info("THE SAME SHOT!");
                    }
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
                        player.getShips()[i] = new Ship("BATTLESHIP", coordinates, Ship.getFOUR_DECK());
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
                        player.getShips()[i] = new Ship("CRUISER", coordinates, Ship.getTHREE_DECK());
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
                        player.getShips()[i] = new Ship("DESTROYER", coordinates, Ship.getTWO_DECK());
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
                    player.getShips()[i] = new Ship("TORPEDO BOAT", coordinates, Ship.getONE_DECK());
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
