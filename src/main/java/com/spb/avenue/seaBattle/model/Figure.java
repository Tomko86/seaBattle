package com.spb.avenue.seaBattle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Figure {
    ALIVE('#'),
    DESTROYED('X'),
    MISSED('*'),
    EMPTY('_');

    private final char view;
}
