package com.avenue.java.spb.seaBattle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Ship {

    @Getter
    private final String name;
    @Getter
    private final String coordinates;
    @Getter
    @Setter
    private int length;
}
