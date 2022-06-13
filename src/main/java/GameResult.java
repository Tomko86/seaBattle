import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameResult {

    private boolean isGameEnded;
    private Condition shotResult;

}
