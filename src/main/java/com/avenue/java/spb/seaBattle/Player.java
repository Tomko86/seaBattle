package com.avenue.java.spb.seaBattle;

import lombok.Getter;
import lombok.Setter;

public class Player {
    private static final int COUNT_OF_SHIPS = 1;
    private static int countAllCellsShips = 4;
    @Getter
    private final String name;
    @Getter
    @Setter
    private Ship[] ownShips;

    @Getter
    private final Canvas canvasOfOwnFigures = new Canvas();

    @Getter
    private final Canvas canvasOfOpponentFigures = new Canvas();

    public Player(String name) {
        this.name = name;
        ownShips = new Ship[COUNT_OF_SHIPS];
    }

    public void addShipsOnCanvas() {
        for (Ship ownShip : ownShips) {
            if (ownShip != null) {
                String[] getCoordinateOfShip = ownShip.getCoordinates().split(",");
                for (String addressCell : getCoordinateOfShip) {
                    char[] charAddressCell = addressCell.toCharArray();
                    int x = Canvas.getPREFIX_OF_VIEW_CANVAS().indexOf(charAddressCell[0]) - 1;
                    int y = Character.getNumericValue(charAddressCell[1]) - 1;
                    canvasOfOwnFigures.getCanvasOfFigures()[y][x] = Figure.LIVE.getView();
                }
            }
        }
    }

    public GameResult makeShot(String coordinateOfCell) {
        char[] getCoordinatesOfCell = coordinateOfCell.toCharArray();
        int x = Canvas.getPREFIX_OF_VIEW_CANVAS().indexOf(getCoordinatesOfCell[0]) - 1;
        int y = Character.getNumericValue(getCoordinatesOfCell[1]) - 1;
        if (canvasOfOwnFigures.getCanvasOfFigures()[y][x] == Figure.DESTROY.getView() ||
                canvasOfOwnFigures.getCanvasOfFigures()[y][x] == Figure.AWAY.getView()) {
            return new GameResult(false, "THE SAME SHOT!");
        } else {
            if (canvasOfOwnFigures.getCanvasOfFigures()[y][x] == Figure.LIVE.getView()) {
                for (Ship ownShip : ownShips) {
                    if (ownShip.getCoordinates().contains(coordinateOfCell)) {
                        if (ownShip.getName().equals("TORPEDO BOAT")) {
                            canvasOfOwnFigures.getCanvasOfFigures()[y][x] = Figure.DESTROY.getView();
                            countAllCellsShips--;
                            if (countAllCellsShips == 0) {
                                return new GameResult(true, "KILLED!");
                            } else {
                                return new GameResult(false, "KILLED!");
                            }
                        } else {
                            canvasOfOwnFigures.getCanvasOfFigures()[y][x] = Figure.DESTROY.getView();
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
                canvasOfOwnFigures.getCanvasOfFigures()[y][x] = Figure.AWAY.getView();
                return new GameResult(false, "AWAY!");
            }
        }
        return null;
    }
}
