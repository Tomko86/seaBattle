import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The class Ship with fields <b>coordinates</b> and <b>length</b>.
 *
 * @author Alexandr Tomko
 * @version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
public class Ship {

    /**
     * This static field consists count of cells a four-deck ship.
     */
    @Getter
    private static final int FOUR_DECK = 4;

    /**
     * This static field consists count of cells a three-deck ship.
     */
    @Getter
    private static final int THREE_DECK = 3;

    /**
     * This static field consists count of cells a two-deck ship.
     */
    @Getter
    private static final int TWO_DECK = 2;

    /**
     * This static field consists count of cells a one-deck ship.
     */
    @Getter
    private static final int ONE_DECK = 1;

    /**
     * This field consists coordinates of ship.
     */
    @Getter
    private String coordinates;

    /**
     * This field consists length of ship.
     */
    @Getter
    @Setter
    private Integer length;
}
