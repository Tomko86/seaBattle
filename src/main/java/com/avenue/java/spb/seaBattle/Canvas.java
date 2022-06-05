package com.avenue.java.spb.seaBattle;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class Canvas {

    @Getter
    private static final String PREFIX_OF_VIEW_CANVAS = " ABCDEFGHI";
    @Getter
    private static final int SIZE = 9;
    @Getter
    private final char[][] canvasOfFigures;

    public Canvas() {
        canvasOfFigures = new char[SIZE][SIZE];
        for (char[] row : canvasOfFigures) {
            Arrays.fill(row, Figure.EMPTY.getView());
        }
    }

    public void drawCanvas() {
        log.info(PREFIX_OF_VIEW_CANVAS);
        for (int i = 0; i < canvasOfFigures.length; i++) {
            StringBuilder viewCanvas = new StringBuilder();
            viewCanvas.append(i + 1);
            for (int j = 0; j < canvasOfFigures.length; j++) {
                viewCanvas.append(canvasOfFigures[i][j]);
            }
            log.info(viewCanvas.toString());
        }
    }



}
