import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Ship {
    @Getter
    private static final int FOUR_DECK = 4;
    @Getter
    private static final int THREE_DECK = 3;
    @Getter
    private static final int TWO_DECK = 2;
    @Getter
    private static final int ONE_DECK = 1;

    @Getter
    private final String coordinates;
    @Getter
    @Setter
    private int length;
}
