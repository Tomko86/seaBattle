import lombok.AllArgsConstructor;
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
