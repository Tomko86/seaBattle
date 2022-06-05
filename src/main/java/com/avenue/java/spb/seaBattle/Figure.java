package com.avenue.java.spb.seaBattle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Figure {
    LIVE('#'),
    DESTROY('X'),
    AWAY('*'),
    EMPTY('_');

    private final char view;
}
