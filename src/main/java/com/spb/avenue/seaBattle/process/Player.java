package com.spb.avenue.seaBattle.process;

import com.spb.avenue.seaBattle.model.Bord;
import com.spb.avenue.seaBattle.model.Ship;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

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
    private static final int COUNT_OF_SHIPS = 2;
    /**
     * This field is a sum of all cells of all ships.
     * Every time when user to create the ship, countCellsAllShips increments on its length.
     */
    @Getter
    @Setter
    private int countCellsAllShips;
    @Getter
    private String name;
    @Getter
    @Setter
    private List<Ship> ships;

    /**
     * This field consists the current state of the field of the player who making the move.
     */
    @Getter
    private final Bord bord = new Bord();

    public Player(String name) {
        this.name = name;
        ships = new ArrayList<>();
        countCellsAllShips = 0;
    }
}
