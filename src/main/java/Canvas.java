import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class Canvas {

    @Getter
    private static final String PREFIX_OF_VIEW_CANVAS = " ABCDEFGHI";
    @Getter
    private static final String VALIDATE_LITERAL_CHARACTER = "ABCDEFGHI";
    @Getter
    private static final String VALIDATE_DIGITAL_CHARACTER = "123456789";
    @Getter
    private static final int SIZE = 9;
    @Getter
    @Setter
    private char[][] cells;

    public Canvas() {
        cells = new char[SIZE][SIZE];
        for (char[] row : cells) {
            Arrays.fill(row, Figure.EMPTY.getView());
        }
    }

    public void drawCanvas() {
        log.info(PREFIX_OF_VIEW_CANVAS);
        for (int i = 0; i < cells.length; i++) {
            StringBuilder viewCanvas = new StringBuilder();
            viewCanvas.append(i + 1);
            for (int j = 0; j < cells.length; j++) {
                viewCanvas.append(cells[i][j]);
            }
            log.info(viewCanvas.toString());
        }
    }



}
