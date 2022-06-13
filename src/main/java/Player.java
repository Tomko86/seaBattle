import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Player {
    private static int countAllCellsShips = 4;
    @Getter
    private static final int COUNT_OF_SHIPS = 1;
    @Getter
    private final String name;
    @Getter
    @Setter
    private Ship[] ships;

    @Getter
    private final Canvas cellsOfOwnFigures = new Canvas();

    @Getter
    private final Canvas cellsOfOpponentFigures = new Canvas();

    public Player(String name) {
        this.name = name;
        ships = new Ship[COUNT_OF_SHIPS];
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


    private boolean isCheckXAndY(int beginY, int beginX) {
        if (cellsOfOwnFigures.getCells()[beginY][beginX] == Figure.EMPTY.getView()) {
            if (beginY == 0 && beginX == 0) {
                if (cellsOfOwnFigures.getCells()[beginY][beginX + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY + 1][beginX + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY + 1][beginX] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (beginY == 0 && beginX != 8) {
                if (cellsOfOwnFigures.getCells()[beginY][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY + 1][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY + 1][beginX] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY + 1][beginX + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY][beginX + 1] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (beginY == 0 && beginX == 8) {
                if (cellsOfOwnFigures.getCells()[beginY][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY + 1][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY + 1][beginX] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (beginX == 0 && beginY != 8) {
                if (cellsOfOwnFigures.getCells()[beginY - 1][beginX] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY - 1][beginX + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY][beginX + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY + 1][beginX + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY + 1][beginX] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (beginY != 0 && beginX != 0 && beginX != 8 && beginY != 8) {
                if (cellsOfOwnFigures.getCells()[beginY + 1][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY - 1][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY - 1][beginX] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY - 1][beginX + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY][beginX + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY + 1][beginX + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY + 1][beginX] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (beginX == 8 && beginY != 0) {
                if (cellsOfOwnFigures.getCells()[beginY - 1][beginX] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY - 1][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY - 1][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY - 1][beginX] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (beginY == 8 && beginX == 0) {
                if (cellsOfOwnFigures.getCells()[beginY - 1][beginX] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY - 1][beginX + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY][beginX + 1] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (beginY == 8 && beginX != 0) {
                if (cellsOfOwnFigures.getCells()[beginY][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY - 1][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY - 1][beginX] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY - 1][beginX + 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY][beginX + 1] == Figure.EMPTY.getView()) {
                    return true;
                }
            }
            if (beginY == 8 && beginX == 8) {
                return cellsOfOwnFigures.getCells()[beginY][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY + 1][beginX - 1] == Figure.EMPTY.getView() &&
                        cellsOfOwnFigures.getCells()[beginY - 1][beginX] == Figure.EMPTY.getView();
            }
        }
        return false;
    }

    public GameResult makeShot(int x, int y, String coordinateOfCell) {
        if (cellsOfOwnFigures.getCells()[y][x] == Figure.DESTROY.getView() ||
                cellsOfOwnFigures.getCells()[y][x] == Figure.AWAY.getView()) {
            return new GameResult(false, "THE SAME SHOT!");
        } else {
            if (cellsOfOwnFigures.getCells()[y][x] == Figure.LIVE.getView()) {
                for (Ship ownShip : ships) {
                    if (ownShip.getCoordinates().contains(coordinateOfCell)) {
                        if (ownShip.getName().equals("TORPEDO BOAT")) {
                            cellsOfOwnFigures.getCells()[y][x] = Figure.DESTROY.getView();
                            countAllCellsShips--;
                            if (countAllCellsShips == 0) {
                                return new GameResult(true, "KILLED!");
                            } else {
                                return new GameResult(false, "KILLED!");
                            }
                        } else {
                            cellsOfOwnFigures.getCells()[y][x] = Figure.DESTROY.getView();
                            ownShip.setLength(ownShip.getLength() - 1);
                            countAllCellsShips--;
                            if (ownShip.getLength() == 0) {
                                if (countAllCellsShips == 0) {
                                    return new GameResult(true, "KILLED!");
                                } else {
                                    return new GameResult(false, "KILLED!");
                                }
                            } else {
                                return new GameResult(false, "WOUNDED!");
                            }

                        }
                    }
                }
            } else {
                cellsOfOwnFigures.getCells()[y][x] = Figure.AWAY.getView();
                return new GameResult(false, "AWAY!");
            }
        }
        return null;
    }
}
