import lombok.Getter;
import lombok.Setter;

public class Player {
    private static int countAllCellsShips = 20;
    @Getter
    private static final int COUNT_OF_SHIPS = 10;
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

    public void addShipsOnCanvas() {
        for (Ship ship : ships) {
            if (ship != null) {
                String[] getCoordinateOfShip = ship.getCoordinates().split(",");
                for (String addressCell : getCoordinateOfShip) {
                    char[] charAddressCell = addressCell.toCharArray();
                    int x = Canvas.getPREFIX_OF_VIEW_CANVAS().indexOf(charAddressCell[0]) - 1;
                    int y = Character.getNumericValue(charAddressCell[1]) - 1;
                    cellsOfOwnFigures.getCells()[y][x] = Figure.LIVE.getView();
                }
            }
        }
    }

    public GameResult makeShot(String coordinateOfCell) {
        char[] getCoordinatesOfCell = coordinateOfCell.toCharArray();
        int x = Canvas.getPREFIX_OF_VIEW_CANVAS().indexOf(getCoordinatesOfCell[0]) - 1;
        int y = Character.getNumericValue(getCoordinatesOfCell[1]) - 1;
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
                                    return new GameResult(true,"KILLED!");
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
