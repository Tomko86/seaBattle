import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;

/**
 * The class consists fields: <b>countAllCellsShips</b>, <b>name</b>, <b>ships</b>, <b>cellsOfOwnFigures</b> and <b>cellsOfOpponentFigures</b>.
 *
 * @author or Alexandr Tomko
 *
 * @version 1.0
 */

@Slf4j
@NoArgsConstructor
public class Player {

    /**
     * This field contains a value of all count of ships.
     */
    @Getter
    private static final int COUNT_OF_SHIPS = 1;

    /**
     * This field is sum of all cells of all ships.
     * Four-deck ship (1) three-deck ship (2) two-deck ship (3)
     * one-deck ship (4) Total: <b>countAllCellsShips</b> = 20.
     */
    @Getter
    @Setter
    private Integer countAllCellsShips;
    @Getter
    private String name;
    @Getter
    @Setter
    private ArrayList<Ship> ships;

    /**
     * This field consists the current state of the field of the player who making the move.
     */
    @Getter
    private final Canvas cellsOfOwnFigures = new Canvas();

    /**
     * This field consists the current state of the field of the opponent.
     */
    @Getter
    private final Canvas cellsOfOpponentFigures = new Canvas();


    public Player(String name) {
        this.name = name;
        ships = new ArrayList<>();
        countAllCellsShips = 4;
    }

    public boolean addShipsOnCanvas(String coordinate) {
        String[] getCoordinateOfShip = coordinate.split(",");
        char[] beginCell = getCoordinateOfShip[0].toCharArray();
        int beginX = Canvas.getPREFIX_OF_VIEW_CANVAS().indexOf(beginCell[0]) - 1;
        int beginY = Character.getNumericValue(beginCell[1]) - 1;

        char[] endCell = getCoordinateOfShip[getCoordinateOfShip.length - 1].toCharArray();
        int endX = Canvas.getPREFIX_OF_VIEW_CANVAS().indexOf(endCell[0]) - 1;
        int endY = Character.getNumericValue(endCell[1]) - 1;
        if (isCheckXAndY(beginY, beginX) && isCheckXAndY(endY, endX)) {
            for (String charAddressCell : getCoordinateOfShip) {
                int x = Canvas.getPREFIX_OF_VIEW_CANVAS().indexOf(charAddressCell.toCharArray()[0]) - 1;
                int y = Character.getNumericValue(charAddressCell.toCharArray()[1]) - 1;
                cellsOfOwnFigures.getCells()[y][x] = Figure.LIVE.getView();
            }
            return true;
        } else {
            log.warn("Wrong! The ships should not crosse and touch each other!");
            return false;
        }
    }

    /**
     * This method checks the entered coordinates of the ship when adding it to the playing field.
     * If the user enters the coordinates of the ship in such a way that the cell in which he is trying
     * to install the ship will be empty and neighboring cells will be empty as well, the method will return true.
     *
     * @param y is a value from range <b>1 - 9</b>.
     * @param x is a value from range <b>A, B, C... J</b>
     * @return <b>true</b> or <b>false</b>
     */
    private boolean isCheckXAndY(int y, int x) {
        if (cellsOfOwnFigures.getCells()[y][x] == Figure.EMPTY.getView()) {
            if (y == 0 && x == 0) {
                if (cellsOfOwnFigures.getCells()[y][x + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y + 1][x + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y + 1][x] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (y == 0 && x != 8 && x != 0) {
                if (cellsOfOwnFigures.getCells()[y][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y + 1][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y + 1][x] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y + 1][x + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y][x + 1] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (y == 0 && x == 8) {
                if (cellsOfOwnFigures.getCells()[y][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y + 1][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y + 1][x] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (x == 0 && y != 8 && y != 0) {
                if (cellsOfOwnFigures.getCells()[y - 1][x] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y - 1][x + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y][x + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y + 1][x + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y + 1][x] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (y != 0 && x != 0 && x != 8 && y != 8) {
                if (cellsOfOwnFigures.getCells()[y + 1][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y - 1][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y - 1][x] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y - 1][x + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y][x + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y + 1][x + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y + 1][x] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (x == 8 && y != 0 && y != 8) {
                if (cellsOfOwnFigures.getCells()[y - 1][x] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y - 1][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y - 1][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y - 1][x] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (y == 8 && x == 0) {
                if (cellsOfOwnFigures.getCells()[y - 1][x] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y - 1][x + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y][x + 1] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (y == 8 && x != 0 && x != 8) {
                if (cellsOfOwnFigures.getCells()[y][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y - 1][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y - 1][x] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y - 1][x + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y][x + 1] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (y == 8 && x == 8) {
                return cellsOfOwnFigures.getCells()[y][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y - 1][x - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[y - 1][x] == Figure.EMPTY.getView();
            }
        }
        return false;
    }


    public GameResult makeShot(int x, int y, String coordinateOfCell) {
        if (cellsOfOwnFigures.getCells()[y][x] == Figure.DESTROY.getView() ||
                cellsOfOwnFigures.getCells()[y][x] == Figure.AWAY.getView()) {
            return new GameResult(false, Condition.SAME_SHOT);
        } else {
            if (cellsOfOwnFigures.getCells()[y][x] == Figure.LIVE.getView()) {
                for (Ship ownShip : ships) {
                    if (ownShip.getCoordinates().contains(coordinateOfCell)) {
                        cellsOfOwnFigures.getCells()[y][x] = Figure.DESTROY.getView();
                        ownShip.setLength(ownShip.getLength() - 1);
                        countAllCellsShips--;
                        if (ownShip.getLength() == 0) {
                            if (countAllCellsShips == 0) {
                                return new GameResult(true, Condition.KILLED, ownShip.getCoordinates());
                            } else {
                                return new GameResult(false, Condition.KILLED, ownShip.getCoordinates());
                            }
                        } else {
                            return new GameResult(false, Condition.WOUNDED);
                        }
                    }
                }
            } else {
                cellsOfOwnFigures.getCells()[y][x] = Figure.AWAY.getView();
                return new GameResult(false, Condition.AWAY);
            }
        }
        return null;
    }
}
