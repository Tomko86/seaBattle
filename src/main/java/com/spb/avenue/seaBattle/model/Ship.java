package com.spb.avenue.seaBattle.model;

import lombok.Getter;
import lombok.Setter;

/**
 * The class com.spb.avenue.seaBattle.model.Ship with fields <b>coordinates</b> and <b>length</b>.
 *
 * @author Alexandr Tomko
 * @version 1.0
 */

public class Ship {
    /**
     * This field consists coordinates of ship.
     */
    @Getter
    private final String[] coordinates;
    /**
     * This field consists length of ship.
     */
    @Getter
    @Setter
    private Integer length;

    public Ship(String[] coordinates, Integer length) {
        this.coordinates = coordinates;
        this.length = length;
    }
}
